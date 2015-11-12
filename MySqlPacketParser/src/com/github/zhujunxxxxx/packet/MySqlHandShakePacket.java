package com.github.zhujunxxxxx.packet;


public class MySqlHandShakePacket extends MySqlPacket {

	private PacketByte protocol_version;	//size 1 [0x0a]
	private PacketByte[] server_version;	//size n string<nul>
	private PacketByte[] thread_id;			//size 4
	private PacketByte[] scramble_buff;		//size 8
	private PacketByte filler;				//size 1 [0x00]
	private PacketByte[] server_capabilities;//size 2
	private PacketByte server_language;		//size 1
	private PacketByte[] server_status;		//size 2
	private PacketByte[] fillers;			//size 10
	
	public PacketByte getProtocol_version() {
		return protocol_version;
	}

	public PacketByte[] getServer_version() {
		return server_version;
	}

	public PacketByte[] getThread_id() {
		return thread_id;
	}

	public PacketByte[] getScramble_buff() {
		return scramble_buff;
	}

	public PacketByte getFiller() {
		return filler;
	}

	public PacketByte[] getServer_capabilities() {
		return server_capabilities;
	}

	public PacketByte getServer_language() {
		return server_language;
	}

	public PacketByte[] getServer_status() {
		return server_status;
	}

	public PacketByte[] getFillers() {
		return fillers;
	}

	public MySqlHandShakePacket(PacketHolder holder){
		super(holder);
	}

	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
	}
	
	
}
