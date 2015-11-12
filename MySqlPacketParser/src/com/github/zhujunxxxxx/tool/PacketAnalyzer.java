package com.github.zhujunxxxxx.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.zhujunxxxxx.packet.MySqlOkPacket;
import com.github.zhujunxxxxx.packet.MySqlPacket;
import com.github.zhujunxxxxx.packet.TcpPacket;

public class PacketAnalyzer extends TcpDumpFileReader {

	//tcpdump文件解析器
	private TcpDumpFileHelper helper;
	//mysql packet解析器
	private PacketBuilder builder;
	private List<MySqlPacket> packetList;
	private List<MySqlPacket> commitOkPacket;
	private Map<String,ArrayList<MySqlPacket>> groupMap;
	//private List<TransactionGroup> transactionList;//所有事务列表(ps:无序的)
	private String serverIPPort;
	@Override
	protected void beforeResolve() {
		// TODO Auto-generated method stub
		logger("file name is :"+getFileName());
		logger("file size is :"+getFileSize()/1000+"kb");
		logger("start resolve...");
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		if(getFileName()==null)
			throw new NullPointerException();
		if(serverIPPort==null)
			throw new NullPointerException();
		helper=ToolFactory.geHelperInstance(serverIPPort);
		builder=ToolFactory.getBuilderInstance();
		commitOkPacket=new ArrayList<MySqlPacket>();
		groupMap=new HashMap<String,ArrayList<MySqlPacket>>();
		//transactionList=new ArrayList<TransactionGroup>();
	}

	private void logger(String log){
		System.out.println("[INFO]:"+log);
	}
	private void error(String log){
		System.out.println("[ERROR]:"+log);
	}
	
	private TcpPacket tcp;
	private MySqlPacket packet;
	private long processSize=0;
	private int oldPercent=0;
//	private long begin=0;
//	private long end=0;
	
	@Override
	public void fetchLine(String line) {
		if(helper==null || builder==null)
			throw new NullPointerException();
		tcp=helper.resolveTcpPacket(line);
		if(tcp!=null)
			processTcpPacket(tcp);
		processSize+=line.getBytes().length+1;
		int percent=(int)(((float)processSize/getFileSize())*100);
		if(percent!=oldPercent){
			oldPercent=percent;
			System.out.println("resolve percent:"+makeProcessBar(percent)+percent+"%");
		}
	}
	private void processTcpPacket(TcpPacket tcp){
		if(tcp==null)
			throw new NullPointerException();
		try {
			packet=builder.resolvePacket(tcp);
			if(packet!=null){
				packetList.add(packet);
			}
			//分组包
			if(packet instanceof MySqlOkPacket){
				MySqlOkPacket ok=(MySqlOkPacket)packet;
				if(ok.getMessage()!=null && ok.getMessage().length>0)
					commitOkPacket.add(packet);
			}
			String key=TcpDumpFileHelper.processHeader(packet.getInfo()).get("ip");
			if(groupMap.containsKey(key)){
				groupMap.get(key).add(packet);
			} else {
				groupMap.put(key, new ArrayList<MySqlPacket>());
				groupMap.get(key).add(packet);
			}
			
		} catch (RuntimeException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			error(e.getMessage());
		}
	}

	@Override
	public void fetchComplete() {
		// TODO Auto-generated method stub
		tcp=helper.resolveLastPacket();
		if(tcp!=null)
			processTcpPacket(tcp);
	}
	public static String makeProcessBar(int per){
		StringBuilder bar=new StringBuilder();
		bar.append("[");
		for (int i = 0; i < 100; i++) {
			if(i<per){
				bar.append("=");
			} else {
				bar.append(" ");
			}
		}
		bar.append("]");
		return bar.toString();
	}

}
