package com.github.zhujunxxxxx.packet;

public class MySqlAuthenticationPacket extends MySqlPacket{

	private PacketByte[] client_flag;//size 2
	private PacketByte[] max_pakcet_size;//size 3
	private PacketByte charset_number;//size 1
	private PacketByte[] filler;//size 23 [0x00]
	private PacketByte[] user;//string<NUL>
	private PacketByte[] scramble_buff;//(length coded binary)
	private PacketByte[] databasename;//string<NUL>
	
	public MySqlAuthenticationPacket(PacketHolder holder){
		super(holder);
	}
	public PacketByte[] getClient_flag() {
		return client_flag;
	}
	public PacketByte[] getMax_pakcet_size() {
		return max_pakcet_size;
	}
	public PacketByte getCharset_number() {
		return charset_number;
	}
	public PacketByte[] getFiller() {
		return filler;
	}
	public PacketByte[] getUser() {
		return user;
	}
	public PacketByte[] getScramble_buff() {
		return scramble_buff;
	}
	public PacketByte[] getDatabasename() {
		return databasename;
	}
	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
	}
	
}
