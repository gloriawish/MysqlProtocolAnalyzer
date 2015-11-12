package com.github.zhujunxxxxx.packet;

public class MySqlErrorPacket extends MySqlPacket {

	private PacketByte field_count;//always 0xff
	private PacketByte[] errno;//size 2
	private PacketByte sqlstate_marker;//#
	private PacketByte[] sqlstate;//size 5
	private PacketByte[] message;//size n string<EOF>

	public PacketByte getField_count() {
		return field_count;
	}

	public PacketByte[] getErrno() {
		return errno;
	}

	public PacketByte getSqlstate_marker() {
		return sqlstate_marker;
	}

	public PacketByte[] getSqlstate() {
		return sqlstate;
	}

	public PacketByte[] getMessage() {
		return message;
	}

	public MySqlErrorPacket(PacketHolder holder){
		super(holder);
	}

	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		this.field_count=getPacketContent()[0];
		
		int index=1;
		this.errno=new PacketByte[2];
		for (int i = 0; i < errno.length; i++) {
			errno[i]=getPacketContent()[index++];
		}
		
		this.sqlstate=new PacketByte[5];
		for (int i = 0; i < sqlstate.length; i++) {
			sqlstate[i]=getPacketContent()[index++];
		}
		
		this.message=new PacketByte[getPacketContent().length-index-1];
		for (int i = 0; i < message.length; i++) {
			message[i]=getPacketContent()[index++];
		}
	}
	
	
}
