package com.github.zhujunxxxxx.packet;

public class MySqlCommandQueryPacket  extends MySqlCommandPacket{

	private PacketByte[] arg;//size n string<EOF>
	
	
	public PacketByte[] getArg() {
		return arg;
	}


	public MySqlCommandQueryPacket(PacketHolder holder) {
		super(holder);
	}


	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		PacketByte[] temp=new PacketByte[getPacketContent().length-1];
		int index=1;
		for (int i = 0; i < temp.length; i++) {
			temp[i]=getPacketContent()[index++];
		}
		this.arg=temp;
	}
}
