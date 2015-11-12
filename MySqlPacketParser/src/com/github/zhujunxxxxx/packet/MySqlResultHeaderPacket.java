package com.github.zhujunxxxxx.packet;

import com.github.zhujunxxxxx.type.LengthEncodedInteger;
import com.github.zhujunxxxxx.type.LengthEncodedString;

public class MySqlResultHeaderPacket extends MySqlPacket {

	private PacketByte[] field_count;//size 1-9
	private PacketByte[] extra;//size 1-9
	
	public PacketByte[] getField_count() {
		return field_count;
	}

	public PacketByte[] getExtra() {
		return extra;
	}

	public MySqlResultHeaderPacket(PacketHolder holder){
		super(holder);
	}

	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		int index=0;
		
		PacketByte[] length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
		field_count=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
		index += length.length==1 ? 1 : length.length+1;
		index += field_count.length;
		
		//extra
		if(index<getPacketContent().length){
			length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
			extra=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
			index += length.length==1 ? 1 : length.length+1;
			index += extra.length;
		}
	}
	
	
}
