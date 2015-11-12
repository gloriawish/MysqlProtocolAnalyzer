package com.github.zhujunxxxxx.packet;

public class MySqlCommandPacket extends MySqlPacket {

	private PacketByte command;

	public PacketByte getCommand() {
		return command;
	}

	public void setCommand(PacketByte command) {
		this.command = command;
	}
	
	public MySqlCommandPacket(PacketHolder holder){
		super(holder);
	}

	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		this.command=getPacketContent()[0];
	}
	
	public static String typeName(PacketByte pb){
		String temp=pb.getValue();
		
		if(temp.equals(COM_SLEEP))
			return "COM_SLEEP";
		if(temp.equals(COM_QUIT))
			return "COM_QUIT";
		if(temp.equals(COM_INIT_DB))
			return "COM_INIT_DB";
		if(temp.equals(COM_QUERY))
			return "COM_QUERY";
		if(temp.equals(COM_FIELD_LIST))
			return "COM_FIELD_LIST";
		if(temp.equals(COM_CREATE_DB))
			return "COM_CREATE_DB";
		if(temp.equals(COM_DROP_DB))
			return "COM_DROP_DB";
		if(temp.equals(COM_REFRESH))
			return "COM_REFRESH";
		if(temp.equals(COM_SHUTDOWN))
			return "COM_SHUTDOWN";
		if(temp.equals(COM_STATISITICS))
			return "COM_STATISITICS";
		if(temp.equals(COM_PROCESS_INFO))
			return "COM_PROCESS_INFO";
		if(temp.equals(COM_PROCESS_KILL))
			return "COM_PROCESS_KILL";
		if(temp.equals(COM_DEBUG))
			return "COM_DEBUG";
		if(temp.equals(COM_PING))
			return "COM_PING";
		if(temp.equals(COM_TIME))
			return "COM_TIME";
		if(temp.equals(COM_DELAYED_INSERT))
			return "COM_DELAYED_INSERT";
		if(temp.equals(COM_CHANGE_USER))
			return "COM_CHANGE_USER";
		if(temp.equals(COM_BINLOG_DUMP))
			return "COM_BINLOG_DUMP";
		if(temp.equals(COM_TABLE_DUMP))
			return "COM_TABLE_DUMP";
		if(temp.equals(COM_CONNECT_OUT))
			return "COM_CONNECT_OUT";
		if(temp.equals(COM_REGISTER_SLAVE))
			return "COM_REGISTER_SLAVE";
		if(temp.equals(COM_STMT_PREPARE))
			return "COM_STMT_PREPARE";
		if(temp.equals(COM_STMT_EXECUTE))
			return "COM_STMT_EXECUTE";
		if(temp.equals(COM_STMT_SEND_LONG_DATA))
			return "COM_STMT_SEND_LONG_DATA";
		if(temp.equals(COM_STMT_CLOSE))
			return "COM_STMT_CLOSE";
		if(temp.equals(COM_RESET))
			return "COM_RESET";
		if(temp.equals(COM_SET_OPTION))
			return "COM_SET_OPTION";
		if(temp.equals(COM_STMT_FETCH))
			return "COM_STMT_FETCH";
		throw new IllegalArgumentException();
	}
	
	public static final String COM_SLEEP="00";
	public static final String COM_QUIT="01";
	public static final String COM_INIT_DB="02";
	public static final String COM_QUERY="03";
	public static final String COM_FIELD_LIST="04";
	public static final String COM_CREATE_DB="05";
	public static final String COM_DROP_DB="06";
	public static final String COM_REFRESH="07";
	public static final String COM_SHUTDOWN="08";
	public static final String COM_STATISITICS="09";
	public static final String COM_PROCESS_INFO="0a";
	public static final String COM_CONNECT="0b";
	
	public static final String COM_PROCESS_KILL="0c";
	public static final String COM_DEBUG="0d";
	public static final String COM_PING="0e";
	public static final String COM_TIME="0f";
	public static final String COM_DELAYED_INSERT="10";
	public static final String COM_CHANGE_USER="11";
	public static final String COM_BINLOG_DUMP="12";
	public static final String COM_TABLE_DUMP="13";
	public static final String COM_CONNECT_OUT="14";
	public static final String COM_REGISTER_SLAVE="15";
	public static final String COM_STMT_PREPARE="16";
	public static final String COM_STMT_EXECUTE="17";
	public static final String COM_STMT_SEND_LONG_DATA="18";
	public static final String COM_STMT_CLOSE="19";
	public static final String COM_RESET="1a";
	public static final String COM_SET_OPTION="1b";
	public static final String COM_STMT_FETCH="1c";
	
	
}
