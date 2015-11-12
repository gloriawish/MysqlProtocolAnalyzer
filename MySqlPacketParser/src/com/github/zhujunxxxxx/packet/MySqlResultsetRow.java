package com.github.zhujunxxxxx.packet;

import java.util.ArrayList;
import java.util.List;

import com.github.zhujunxxxxx.type.LengthEncodedInteger;
import com.github.zhujunxxxxx.type.LengthEncodedString;

public class MySqlResultsetRow  extends MySqlPacket{

	private List<PacketByte[]> value;//string<lenenc> or 0xfb(NULL)

	public List<PacketByte[]> getValue() {
		return value;
	}
	

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		value=new ArrayList<PacketByte[]>();
	}


	public MySqlResultsetRow(PacketHolder holder){
		super(holder);
	}
	
	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		try {
			int index=0;
			while(index<getPacketContent().length){
				PacketByte[] length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
				PacketByte[] item=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
				index += length.length==1 ? 1 : length.length+1;
				index += item.length;
				value.add(item);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
}
