package com.github.zhujunxxxxx.packet;

import java.util.List;

public class MySqlResultsetPacket  extends MySqlPacket{

	private MySqlResultHeaderPacket header;
	private List<MySqlColumnDefinitionPacket> fields;
	private MySqlEofPacket eof_fields;
	private List<MySqlResultsetRow> rowDatas;
	private MySqlEofPacket eof_rowDatas;
	private List<MySqlPacket> packetList;
	
	public MySqlResultsetPacket(PacketHolder holder){
		//super(holder);
		packetList=holder.getPacketList();
	}
	
	public MySqlResultHeaderPacket getHeader() {
		return header;
	}
	public List<MySqlColumnDefinitionPacket> getFields() {
		return fields;
	}
	public MySqlEofPacket getEof_fields() {
		return eof_fields;
	}
	public List<MySqlResultsetRow> getRowDatas() {
		return rowDatas;
	}
	public MySqlEofPacket getEof_rowDatas() {
		return eof_rowDatas;
	}
	public List<MySqlPacket> getPacketList() {
		return packetList;
	}
	
}
