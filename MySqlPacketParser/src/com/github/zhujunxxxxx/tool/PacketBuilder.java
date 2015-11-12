package com.github.zhujunxxxxx.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.zhujunxxxxx.packet.MySqlAuthenticationPacket;
import com.github.zhujunxxxxx.packet.MySqlCommandPacket;
import com.github.zhujunxxxxx.packet.MySqlCommandPreparedPacket;
import com.github.zhujunxxxxx.packet.MySqlCommandQueryPacket;
import com.github.zhujunxxxxx.packet.MySqlErrorPacket;
import com.github.zhujunxxxxx.packet.MySqlExecuteStmtPacket;
import com.github.zhujunxxxxx.packet.MySqlHandShakePacket;
import com.github.zhujunxxxxx.packet.MySqlOkPacket;
import com.github.zhujunxxxxx.packet.MySqlPacket;
import com.github.zhujunxxxxx.packet.MySqlPreparedOkPacket;
import com.github.zhujunxxxxx.packet.MySqlResultsetPacket;
import com.github.zhujunxxxxx.packet.PacketByte;
import com.github.zhujunxxxxx.packet.PacketHolder;
import com.github.zhujunxxxxx.packet.TcpPacket;

public class PacketBuilder {
	private static Map<String,Integer> preparedStatement=new HashMap<String,Integer>();
	
	public static boolean setPreparedStatement(String ip_port_stmt_id,int num_param){
		if(!preparedStatement.containsKey(ip_port_stmt_id)){
			preparedStatement.put(ip_port_stmt_id, num_param);
			return true;
		} else {
			throw new RuntimeException();
		}
	}
	
	public static int getPreparedStatement(String ip_port_stmt_id){
		if(preparedStatement.containsKey(ip_port_stmt_id)){
			return preparedStatement.get(ip_port_stmt_id);
		} else {
			throw new RuntimeException();
		}
	}
	
	public MySqlPacket resolvePacket(TcpPacket tcp){
		if(tcp.getLength()<=0)
			throw new RuntimeException("this is zero length packet!");
		try {
			List<MySqlPacket> list=packetSplit(tcp.getDataList());
			PacketHolder holder=new PacketHolder(tcp, list);
			MySqlPacket packet=determinePacket(holder);
			if(packet!=null) packet.setInfo(tcp.getInfo());
			return packet;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 把16进制的数据按照协议划分为一个或多个Packet
	 * @param list
	 * @return
	 */
	private List<MySqlPacket> packetSplit(List<String> list){
		if(list==null)
			throw new NullPointerException();
		List<MySqlPacket> result=new ArrayList<MySqlPacket>();
		MySqlPacket packet=null;
		List<PacketByte> list_pb=new ArrayList<PacketByte>();
		for (int i = 0; i < list.size(); i++) {
			PacketByte pb=new PacketByte(list.get(i));
			list_pb.add(pb);
		}
		int index=0;
		while(index<list_pb.size()){
			try {
				//检查剩余数据是否有4byte,如果没有则退出，判断是否有不完整的包。主要是排除resultset有的时候只有长度没有真实数据
				if(index + 4 < list_pb.size() && PacketByteTool.computeLength(PacketByteTool.subListToArray(list_pb, index, 3)) + index +4 <= list_pb.size()){
					packet=new MySqlPacket();
					
					//标识长度
					packet.setPacketLength(PacketByteTool.subListToArray(list_pb, index, 3));
					index += 3;
					
					//序号
					packet.setPacketSequence(list_pb.get(index));
					index++;
					
					//内容，长度为3个byte标识
					int length=PacketByteTool.computeLength(packet.getPacketLength());
					packet.setPacketContent(PacketByteTool.subListToArray(list_pb, index, length));
					index+=length;
					
					result.add(packet);
				} else {
					throw new RuntimeException();
				}
			} catch (Exception e) {
				throw e;
			}
		}
		return result;
	}
	
	private MySqlPacket determinePacket(PacketHolder holder){
		int sequence=PacketByteTool.ByteToDecimal(holder.getSequence());
		if(sequence==0 || sequence==1){
			if(sequence==0){
				if(holder.getFlag().equals("0a")){
					return new MySqlHandShakePacket(holder);
				} else {
					if(holder.getFlag().equals(MySqlCommandPacket.COM_STMT_EXECUTE)){
						return new MySqlExecuteStmtPacket(holder);
					} else if(holder.getFlag().equals(MySqlCommandPacket.COM_STMT_PREPARE)){
						return new MySqlCommandPreparedPacket(holder);
					} else if(holder.getFlag().equals(MySqlCommandPacket.COM_QUERY)){
						return new MySqlCommandQueryPacket(holder);
					} else {
						throw new RuntimeException("not support type:"+holder.getFlag());
					}
				}
			} else {
				if(holder.getLength()==1 && holder.getFlag().equals("ff")){
					return new MySqlErrorPacket(holder);
				} else if(holder.getLength()==1 && holder.getFlag().equals("00")){
					return new MySqlOkPacket(holder);
				} else if(holder.getLength()==1){
					return new MySqlAuthenticationPacket(holder);
				} else {
					if(holder.getFlag().equals("00")){
						return new MySqlPreparedOkPacket(holder);
					} else{
						return new MySqlResultsetPacket(holder);
					}
				}
			}
		} else {
			//this packet sequence is invalid
			return null;
		}
	}

}
