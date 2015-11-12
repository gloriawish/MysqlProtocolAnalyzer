package com.github.zhujunxxxxx.packet;

import java.util.List;

public class PacketHolder {

	private TcpPacket tcpPacket;
	private List<MySqlPacket> packetList;
	private PacketByte sequence;
	private String flag;
	private int length;
	public PacketHolder(TcpPacket tcp,List<MySqlPacket> list){
		setTcpPacket(tcp);
		setPacketList(list);
		setSequence(list.get(0).getPacketSequence());
		setFlag(list.get(0).getPacketContent()[0].getValue());
		setLength(list.size());
	}
	public TcpPacket getTcpPacket() {
		return tcpPacket;
	}
	public void setTcpPacket(TcpPacket tcpPacket) {
		this.tcpPacket = tcpPacket;
	}
	public List<MySqlPacket> getPacketList() {
		return packetList;
	}
	public void setPacketList(List<MySqlPacket> packetList) {
		this.packetList = packetList;
	}
	public PacketByte getSequence() {
		return sequence;
	}
	public void setSequence(PacketByte sequence) {
		this.sequence = sequence;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
}
