package com.github.zhujunxxxxx.packet;

import com.github.zhujunxxxxx.type.LengthEncodedInteger;

public class MySqlOkPacket extends MySqlPacket {

	private PacketByte field_count;//size 1 always 00
	private PacketByte[] affectd_row;//size 1-9
	private PacketByte[] insert_id;//size 1-9
	private PacketByte[] server_status;//size 2
	private PacketByte[] warning_count;//size 2
	private PacketByte[] message;
	public MySqlOkPacket(PacketHolder holder){
		super(holder);
	}
	public PacketByte getField_count() {
		return field_count;
	}

	public PacketByte[] getAffectd_row() {
		return affectd_row;
	}

	public PacketByte[] getInsert_id() {
		return insert_id;
	}

	public PacketByte[] getServer_status() {
		return server_status;
	}

	public PacketByte[] getWarning_count() {
		return warning_count;
	}
	
	public PacketByte[] getMessage() {
		return message;
	}

	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		this.field_count=getPacketContent()[0];
		
		int index=1;
		//affectd_row
		affectd_row=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
		index += affectd_row.length==1 ? 1 : affectd_row.length+1;
		
		//insert_id
		insert_id=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
		index += insert_id.length==1 ? 1 : insert_id.length+1;
		
		//server_status
		server_status=new PacketByte[2];
		for (int i = 0; i < server_status.length; i++) {
			server_status[i]=getPacketContent()[index++];
		}
		
		//warning_count
		warning_count=new PacketByte[2];
		for (int i = 0; i < warning_count.length; i++) {
			warning_count[i]=getPacketContent()[index++];
		}
		//message
		message=new PacketByte[getPacketContent().length-index];
		for (int i = 0; i < message.length; i++) {
			message[i]=getPacketContent()[index++];
		}
		
		
	}
	
	
}
