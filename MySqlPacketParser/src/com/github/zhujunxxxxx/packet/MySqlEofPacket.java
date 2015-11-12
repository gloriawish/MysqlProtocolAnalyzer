package com.github.zhujunxxxxx.packet;

import com.github.zhujunxxxxx.tool.PacketByteTool;

public class MySqlEofPacket extends MySqlPacket {

	private PacketByte field_count;//always fe
	private PacketByte[] warning_count;//size 2
	private PacketByte[] server_status;//size 2

	
	public PacketByte getField_count() {
		return field_count;
	}

	public PacketByte[] getWarning_count() {
		return warning_count;
	}

	public PacketByte[] getServer_status() {
		return server_status;
	}

	public MySqlEofPacket(PacketHolder holder){
		super(holder);
	}

	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		this.field_count=getPacketContent()[0];
		warning_count=PacketByteTool.subArray(getPacketContent(), 1, 2);
		server_status=PacketByteTool.subArray(getPacketContent(), 3, 2);
	}
	
	
}
