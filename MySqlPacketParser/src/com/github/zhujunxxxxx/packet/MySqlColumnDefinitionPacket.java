package com.github.zhujunxxxxx.packet;

import com.github.zhujunxxxxx.type.LengthEncodedInteger;
import com.github.zhujunxxxxx.type.LengthEncodedString;

public class MySqlColumnDefinitionPacket  extends MySqlPacket{
	
	private PacketByte[] catalog;//string<lenenc>
	private PacketByte[] schema;//string<lenenc>
	private PacketByte[] table;//string<lenenc>
	private PacketByte[] org_table;//string<lenenc>
	private PacketByte[] name;//string<lenenc>
	private PacketByte[] org_name;//string<lenenc>
	private PacketByte filler_1;//size 1
	private PacketByte[] charater_set;//size 2
	private PacketByte[] column_length;//size 4
	private PacketByte column_type;//size 1
	private PacketByte[] flags;//size 2
	private PacketByte decimals;//size 1
	private PacketByte[] filler_2;//always x00
	
	public MySqlColumnDefinitionPacket(PacketHolder holder){
		super(holder);
	}
	public MySqlColumnDefinitionPacket(MySqlPacket packet){
		setPacketLength(packet.getPacketLength());
		setPacketSequence(packet.getPacketSequence());
		setPacketContent(packet.getPacketContent());
	}
	
	public PacketByte[] getCatalog() {
		return catalog;
	}
	public PacketByte[] getSchema() {
		return schema;
	}
	public PacketByte[] getTable() {
		return table;
	}
	public PacketByte[] getOrg_table() {
		return org_table;
	}
	public PacketByte[] getName() {
		return name;
	}
	public PacketByte[] getOrg_name() {
		return org_name;
	}
	public PacketByte getFiller_1() {
		return filler_1;
	}
	public PacketByte[] getCharater_set() {
		return charater_set;
	}
	public PacketByte[] getColumn_length() {
		return column_length;
	}
	public PacketByte getColumn_type() {
		return column_type;
	}
	public PacketByte[] getFlags() {
		return flags;
	}
	public PacketByte getDecimals() {
		return decimals;
	}
	public PacketByte[] getFiller_2() {
		return filler_2;
	}
	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		int index = 0;
		//catalog
		PacketByte[] length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
		catalog=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
		index += length.length==1 ? 1 : length.length+1;
		index += catalog.length;
		
		//schema
		length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
		schema=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
		index += length.length==1 ? 1 : length.length+1;
		index += schema.length;
		
		//table
		length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
		table=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
		index += length.length==1 ? 1 : length.length+1;
		index += table.length;
		
		//org_table
		length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
		org_table=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
		index += length.length==1 ? 1 : length.length+1;
		index += org_table.length;
		
		//name
		length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
		name=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
		index += length.length==1 ? 1 : length.length+1;
		index += name.length;
		
		
		//org_name
		length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
		org_name=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
		index += length.length==1 ? 1 : length.length+1;
		index += org_name.length;
		
		//skip filler_1
		filler_1=getPacketContent()[index++];
		
		//charater_set
		charater_set=new PacketByte[2];
		for (int i = 0; i < charater_set.length; i++) {
			charater_set[i]=getPacketContent()[index++];
		}
		
		//column_length
		column_length=new PacketByte[2];
		for (int i = 0; i < column_length.length; i++) {
			column_length[i]=getPacketContent()[index++];
		}
		
		//column_type
		column_type=getPacketContent()[index++];
		
		//flags
		flags=new PacketByte[2];
		for (int i = 0; i < flags.length; i++) {
			flags[i]=getPacketContent()[index++];
		}
		
		//decimals
		decimals=getPacketContent()[index++];
		
		//filler_2
		filler_2=new PacketByte[2];
		for (int i = 0; i < filler_2.length; i++) {
			filler_2[i]=getPacketContent()[index++];
		}
	}
	

}
