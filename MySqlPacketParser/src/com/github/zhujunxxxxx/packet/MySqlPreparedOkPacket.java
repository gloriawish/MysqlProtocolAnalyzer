package com.github.zhujunxxxxx.packet;

import java.util.ArrayList;
import java.util.List;

import com.github.zhujunxxxxx.tool.PacketBuilder;
import com.github.zhujunxxxxx.tool.PacketByteTool;
import com.github.zhujunxxxxx.tool.TcpDumpFileHelper;

public class MySqlPreparedOkPacket extends MySqlPacket{

	private PacketByte status; //00
	private PacketByte[] statement_id; //size 4
	private PacketByte[] num_columns; //size 2
	private PacketByte[] num_params; //size 2
	private PacketByte reserved_1; //00
	private PacketByte[] warning_count;//size 2
	
	private List<MySqlColumnDefinitionPacket> params;
	private List<MySqlColumnDefinitionPacket> columns;
	private List<MySqlPacket> packetList;
	
	public MySqlPreparedOkPacket(PacketHolder holder){
		this.packetList=holder.getPacketList();
		setInfo(holder.getTcpPacket().getInfo());
		init();
		resolve();
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		params=new ArrayList<MySqlColumnDefinitionPacket>();
		columns=new ArrayList<MySqlColumnDefinitionPacket>();
	}

	public PacketByte getStatus() {
		return status;
	}
	public PacketByte[] getStatement_id() {
		return statement_id;
	}
	public PacketByte[] getNum_columns() {
		return num_columns;
	}
	public PacketByte[] getNum_params() {
		return num_params;
	}
	public PacketByte getReserved_1() {
		return reserved_1;
	}
	public PacketByte[] getWarning_count() {
		return warning_count;
	}
	public List<MySqlColumnDefinitionPacket> getParams() {
		return params;
	}
	public List<MySqlColumnDefinitionPacket> getColumns() {
		return columns;
	}
	public List<MySqlPacket> getPacketList() {
		return packetList;
	}
	
	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		int index=1;
		MySqlPacket first=packetList.get(0);
		
		//status
		status=first.getPacketContent()[0];
		
		//statement_id
		statement_id=new PacketByte[4];
		for (int i = 0; i < statement_id.length; i++) {
			statement_id[i]=first.getPacketContent()[index++];
		}
		
		//num_columns
		num_columns=new PacketByte[2];
		for (int i = 0; i < num_columns.length; i++) {
			num_columns[i]=first.getPacketContent()[index++];
		}
		//num_params
		num_params=new PacketByte[2];
		for (int i = 0; i < num_params.length; i++) {
			num_params[i]=first.getPacketContent()[index++];
		}
		
		String ipPort=TcpDumpFileHelper.processHeader(getInfo()).get("ip");
		//TODO
		if(!PacketBuilder.setPreparedStatement(ipPort+"="+PacketByte.valueOf(statement_id), PacketByteTool.ByteToDecimal(num_params))){
			throw new RuntimeException();
		}
		//reserved_1
		reserved_1=first.getPacketContent()[index++];
		
		//warning_count
		warning_count=new PacketByte[2];
		for (int i = 0; i < warning_count.length; i++) {
			warning_count[i]=first.getPacketContent()[index++];
		}
		
		int numParams=PacketByteTool.computeLength(num_params);
		int packetIndex=1;
		if(numParams>0){
			for (int i = 0; i < numParams; i++) {
				MySqlColumnDefinitionPacket param=new MySqlColumnDefinitionPacket(packetList.get(packetIndex++));
				params.add(param);
			}
		}
		packetIndex++;//skip EOF packet
		
		int numColumns=PacketByteTool.computeLength(num_columns);
		if(numColumns>0){
			for (int i = 0; i < numColumns; i++) {
				MySqlColumnDefinitionPacket field=new MySqlColumnDefinitionPacket(packetList.get(packetIndex++));
				columns.add(field);
			}
		}
	}
}
