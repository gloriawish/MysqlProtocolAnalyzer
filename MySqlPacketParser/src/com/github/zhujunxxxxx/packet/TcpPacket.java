package com.github.zhujunxxxxx.packet;

import java.util.List;

public class TcpPacket {
	
	private List<String> dataList;
	private String info;
	private int length;
	public List<String> getDataList() {
		return dataList;
	}
	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("info:"+info);
		sb.append("\n");
		sb.append("length:"+length);
		sb.append("data:");
		sb.append("\n");
		sb.append("    ");
		int index=0;
		for (String item : dataList) {
			sb.append(item);
			sb.append(" ");
			index++;
			if(index != 0 && index != dataList.size() && index % 16 == 0){
				sb.append("\n");
				sb.append("    ");
			}
		}
		return sb.toString();
	}
	
}
