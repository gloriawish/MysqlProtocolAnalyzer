package com.github.zhujunxxxxx.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

import com.github.zhujunxxxxx.packet.TcpPacket;
import com.github.zhujunxxxxx.packet.TransactionGroup;

public class TcpDumpFileHelper {
	
	private Queue<String> queue;
	private static String serverIP;
	
	public TcpDumpFileHelper(){
		queue=new ArrayBlockingQueue<String>(10000);
	}

	public TcpPacket resolveTcpPacket(String input){
		try {
			List<String> group=checkTcpPacketComplete(input);
			if(group != null){
				return buildTcpPacket(group);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public TcpPacket resolveLastPacket(){
		try {
			List<String> group=new ArrayList<String>();
			while(!queue.isEmpty()){
				String line=queue.poll();
				group.add(line);
			}
			return buildTcpPacket(group);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 检查一个tcp包是否读取完整了
	 * @param input
	 * @return
	 */
	private List<String> checkTcpPacketComplete(String input){
		queue.add(input);
		if(input.contains("IP") && input.contains("Flags")){
			List<String> list=null;
			while(!queue.isEmpty()){
				String line=queue.peek();
				if(input.contains("IP") && input.contains("Flags")){
					if(list != null)
						return list;
					else {
						list=new ArrayList<String>();
						queue.poll();
						list.add(line);
					}
				} else {
					
					if(list != null){
						queue.poll();
						list.add(line);
					}
				}
			}
			if (list != null) {//应该不会执行到这里
				for (int i = 0; i < list.size(); i++) {
					queue.add(list.get(i));
				}
			}
		}
		return null;
	}
	
	private TcpPacket buildTcpPacket(List<String> list){
		if(list == null)
			throw new NullPointerException();
		if(list.size()<=0)
			throw new RuntimeException();
		TcpPacket tcpPacket=new TcpPacket();
		String header=list.get(0);
		String info=null;
		info=header.substring(0, header.indexOf("Flags")-2);
		if(!info.contains(serverIP)){
			System.out.println("IP not correct!");
			return null;
		}
		tcpPacket.setInfo(info);
		String temp=header.substring(header.indexOf("length")).replace("length ", "");
		int length=Integer.parseInt(temp);
		
		Stack<String> stack=new Stack<String>();
		for (int i = 1; i < list.size(); i++) {
			String[] array=cleanData(list.get(i));
			if(array != null) {
				for (int j = 0; j < array.length; j++) {
					if(array[j].length()==4){
						stack.push(array[j].substring(0, 2));
						stack.push(array[j].substring(2, 4));
					} else if(array[j].length()==2){
						stack.push(array[j]);
					} else {
						throw new IllegalArgumentException();
					}
				}
			}
		}
		tcpPacket.setLength(length);
		String[] result=new String[length];
		while(length>0){
			result[length-1]=stack.pop();
			length--;
		}
		tcpPacket.setDataList(Arrays.asList(result));
		return tcpPacket;
	}
	
	private String[] cleanData(String line){
		if(line.length() <= 10){
			return null;
		} else {
			line=line.substring(10);//去除前面多余的字符
		}
		String temp=line;
		temp=temp.substring(0,39);
		String[] array=temp.split(" ");
		String[] newArray=new String[array.length];
		for (int i = 0; i < newArray.length; i++) {//好像是多余的操作
			newArray[i]=array[i];
		}
		return newArray;
	}
	
	public static Map<String,String> processHeader(String head){
		Map<String,String> map=new HashMap<String,String>();
		String[] infos=head.split(" ");
		String time=infos[0];
		String leftIP=infos[2];
		String rightIP=infos[4];
		StringBuilder key=new StringBuilder();
		if(leftIP.equals(serverIP)){
			key.append(rightIP);
		} else if (rightIP.equals(serverIP)) {
			key.append(leftIP);
		}
		map.put("time",time);
		map.put("ip", key.toString());
		return map;
	}
	
	public static int getRigthID(TransactionGroup group){
		
		String idPair=group.getLogID();
		idPair=idPair.substring("!$logIDMap begin:<".length());
		idPair=idPair.substring(0, idPair.length()-1);
		String[] ids=idPair.split(",");
		return Integer.parseInt(ids[1]);
		
	}
	
	public static String getTime(String head){
		return processHeader(head).get("time");
	}
	
	
	public static String getServerIP(){
		return serverIP;
	}
	
	public static void setServerIP(String ip){
		serverIP=ip;
	}
}
