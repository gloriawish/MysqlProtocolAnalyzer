package com.github.zhujunxxxxx.packet;

public class MySqlPacket implements BasePacket{

	private int lineNumber;
	private String info;
	private PacketByte[] packetLength;
	private PacketByte packetSequence;
	private PacketByte[] packetContent;
	public MySqlPacket(){
		
	}
	
	public MySqlPacket(PacketHolder holder){
		setPacketLength(holder.getPacketList().get(0).getPacketLength());
		setPacketSequence(holder.getPacketList().get(0).getPacketSequence());
		setPacketContent(holder.getPacketList().get(0).packetContent);
		setInfo(holder.getPacketList().get(0).getInfo());
		this.init();
		this.resolve();
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public PacketByte[] getPacketLength() {
		return packetLength;
	}

	public void setPacketLength(PacketByte[] packetLength) {
		this.packetLength = packetLength;
	}

	public PacketByte getPacketSequence() {
		return packetSequence;
	}

	public void setPacketSequence(PacketByte packetSequence) {
		this.packetSequence = packetSequence;
	}

	public PacketByte[] getPacketContent() {
		return packetContent;
	}

	public void setPacketContent(PacketByte[] packetContent) {
		this.packetContent = packetContent;
	}

	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
}
