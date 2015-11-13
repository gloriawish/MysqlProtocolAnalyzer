package com.github.zhujunxxxxx.packet;

import java.util.ArrayList;
import java.util.List;

import com.github.zhujunxxxxx.tool.PacketBuilder;
import com.github.zhujunxxxxx.tool.PacketByteTool;
import com.github.zhujunxxxxx.tool.TcpDumpFileHelper;
import com.github.zhujunxxxxx.type.LengthEncodedInteger;
import com.github.zhujunxxxxx.type.LengthEncodedString;

public class MySqlExecuteStmtPacket extends MySqlPacket {

	private PacketByte type;//size 1
	private PacketByte[] stmt_id;//size 4
	private PacketByte flags;//size 1
	private PacketByte[] iteration_count;//size 4
	private PacketByte[] NULL_bitmap;//size n if num-param>0
	private PacketByte new_params_bound_flag;//size 1
	private List<PacketByte[]> types;//size n
	private List<PacketByte[]> values;//size n
	private String statement;
	public PacketByte getType() {
		return type;
	}
	public PacketByte[] getStmt_id() {
		return stmt_id;
	}
	public PacketByte getFlags() {
		return flags;
	}
	public PacketByte[] getIteration_count() {
		return iteration_count;
	}
	public PacketByte[] getNULL_bitmap() {
		return NULL_bitmap;
	}
	public PacketByte getNew_params_bound_flag() {
		return new_params_bound_flag;
	}
	public List<PacketByte[]> getTypes() {
		return types;
	}
	public List<PacketByte[]> getValues() {
		return values;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	@Override
	public void resolve() {
		// TODO Auto-generated method stub
		super.resolve();
		
		type=getPacketContent()[0];
		if(!type.getValue().equals(MySqlCommandPacket.COM_STMT_EXECUTE)){
			throw new RuntimeException();
		}
		int index=1;
		//stmt_id
		stmt_id=new PacketByte[4];
		for (int i = 0; i < stmt_id.length; i++) {
			stmt_id[i]=getPacketContent()[index++];
		}
		
		flags=getPacketContent()[index++];
		
		//iteration
		iteration_count=new PacketByte[4];
		for (int i = 0; i < iteration_count.length; i++) {
			iteration_count[i]=getPacketContent()[index++];
		}
		
		String ipPort=TcpDumpFileHelper.processHeader(getInfo()).get("ip");
		int num_param=PacketBuilder.getPreparedStatement(ipPort+"="+PacketByte.valueOf(stmt_id));
		if(num_param!=-1){
			if(num_param>0){
				int null_bitmap_length=(num_param+7)/8;
				NULL_bitmap=new PacketByte[null_bitmap_length];
				for (int i = 0; i < NULL_bitmap.length; i++) {
					NULL_bitmap[i]=getPacketContent()[index++];
				}
			}
		} else {
			throw new RuntimeException("stmt_id "+ipPort+"="+PacketByte.valueOf(stmt_id)+" not prepared.");
		}
		
		new_params_bound_flag=getPacketContent()[index++];
		
		
		if(PacketByteTool.ByteToDecimal(new_params_bound_flag)==1){//表示自带参数类型
			//type  length=num-param * 2
			for (int i = 0; i < num_param; i++) {
				PacketByte[] type=new PacketByte[2];
				
				for (int j = 0; j < type.length; j++) {
					type[j]=getPacketContent()[index++];
				}
				types.add(type);
			}
		}
		
		//values
		for (int i = 0; i < types.size(); i++) {
			if(updateFromBitMap(NULL_bitmap,i)){
				throw new RuntimeException("not supprot null_bitmap");
			}
			PacketByte[] type=types.get(i);
			String typeName=type[0].getValue();
			if(typeName.equals(MYSQL_TYPE_STRING)
					||typeName.equals(MYSQL_TYPE_VARCHAR)
					||typeName.equals(MYSQL_TYPE_VAR_STRING)
					||typeName.equals(MYSQL_TYPE_ENUM)
					||typeName.equals(MYSQL_TYPE_SET)
					||typeName.equals(MYSQL_TYPE_LONG_BLOB)
					||typeName.equals(MYSQL_TYPE_MEDIUM_BLOB)
					||typeName.equals(MYSQL_TYPE_BLOB)
					||typeName.equals(MYSQL_TYPE_TINY_BLOB)
					||typeName.equals(MYSQL_TYPE_GEOMETRY)
					||typeName.equals(MYSQL_TYPE_BIT)
					||typeName.equals(MYSQL_TYPE_DECIMAL)
					||typeName.equals(MYSQL_TYPE_NEWDECIMAL)){
				PacketByte[] length=LengthEncodedInteger.resolveEncodedInteger(getPacketContent(), index);
				PacketByte[] value=LengthEncodedString.resolveEncodedString(getPacketContent(), index);
				index+=length.length==1 ? 1 : length.length+1;
				index+=value.length;
				values.add(value);
			}
			else if(typeName.equals(MYSQL_TYPE_LONGLONG)){
				PacketByte[] value=new PacketByte[8];
				for (int j = 0; j < value.length; j++) {
					value[j]=getPacketContent()[index++];
				}
				values.add(value);
			}
			else if(typeName.equals(MYSQL_TYPE_LONG) || typeName.equals(MYSQL_TYPE_INT24)){
				PacketByte[] value=new PacketByte[4];
				for (int j = 0; j < value.length; j++) {
					value[j]=getPacketContent()[index++];
				}
				values.add(value);
			}
			else if(typeName.equals(MYSQL_TYPE_SHORT) || typeName.equals(MYSQL_TYPE_YEAR)){
				PacketByte[] value=new PacketByte[2];
				for (int j = 0; j < value.length; j++) {
					value[j]=getPacketContent()[index++];
				}
				values.add(value);
			}
			else if(typeName.equals(MYSQL_TYPE_TINY)){
				PacketByte[] value=new PacketByte[1];
				for (int j = 0; j < value.length; j++) {
					value[j]=getPacketContent()[index++];
				}
				values.add(value);
			}
			else if(typeName.equals(MYSQL_TYPE_DOUBLE)){
				PacketByte[] value=new PacketByte[8];
				for (int j = 0; j < value.length; j++) {
					value[j]=getPacketContent()[index++];
				}
				values.add(value);
			}
			else if(typeName.equals(MYSQL_TYPE_FLOAT)){
				PacketByte[] value=new PacketByte[4];
				for (int j = 0; j < value.length; j++) {
					value[j]=getPacketContent()[index++];
				}
				values.add(value);
			}
			else if(typeName.equals(MYSQL_TYPE_DATE) || typeName.equals(MYSQL_TYPE_DATETIME) || typeName.equals(MYSQL_TYPE_TIMESTAMP)){
				int len=PacketByteTool.ByteToDecimal(getPacketContent()[index++]);
				if(len==0 || len==4 || len==7 || len==11){
					PacketByte[] value=new PacketByte[len];
					for (int j = 0; j < value.length; j++) {
						value[j]=getPacketContent()[index++];
					}
					values.add(value);
				} else {
					throw new RuntimeException();
				}
			} else if(typeName.equals(MYSQL_TYPE_TIME)){
				int len=PacketByteTool.ByteToDecimal(getPacketContent()[index++]);
				if(len==0 || len==8 || len==12){
					PacketByte[] value=new PacketByte[len];
					for (int j = 0; j < value.length; j++) {
						value[j]=getPacketContent()[index++];
					}
					values.add(value);
				} else {
					throw new RuntimeException();
				}
			} else {
				throw new RuntimeException();
			}
		}
		
	}
	
	public static boolean updateFromBitMap(PacketByte[] bitmap,int field_index){
		
		
		int byte_pos = field_index / 8;
		int bit_pos = field_index % 8;
		int value=PacketByteTool.ByteToInteger(bitmap[byte_pos]);
		if((value & (1<<bit_pos)) == 1){//为null
			return true;
		}
		return false;
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		types=new ArrayList<PacketByte[]>();
		values=new ArrayList<PacketByte[]>();
	}
	
	public MySqlExecuteStmtPacket(PacketHolder holder){
		super(holder);
	}
	
	public static String typeName(PacketByte pb){
		
		String temp=pb.getValue();
		if(temp.equals(MYSQL_TYPE_DECIMAL))
			return "MYSQL_TYPE_DECIMAL";
		if(temp.equals(MYSQL_TYPE_TINY))
			return "MYSQL_TYPE_TINY";
		if(temp.equals(MYSQL_TYPE_SHORT))
			return "MYSQL_TYPE_SHORT";
		if(temp.equals(MYSQL_TYPE_LONG))
			return "MYSQL_TYPE_LONG";
		if(temp.equals(MYSQL_TYPE_FLOAT))
			return "MYSQL_TYPE_FLOAT";
		if(temp.equals(MYSQL_TYPE_DOUBLE))
			return "MYSQL_TYPE_DOUBLE";
		if(temp.equals(MYSQL_TYPE_NULL))
			return "MYSQL_TYPE_NULL";
		if(temp.equals(MYSQL_TYPE_TIMESTAMP))
			return "MYSQL_TYPE_TIMESTAMP";
		if(temp.equals(MYSQL_TYPE_LONGLONG))
			return "MYSQL_TYPE_LONGLONG";
		if(temp.equals(MYSQL_TYPE_INT24))
			return "MYSQL_TYPE_INT24";
		if(temp.equals(MYSQL_TYPE_DATE))
			return "MYSQL_TYPE_DATE";
		if(temp.equals(MYSQL_TYPE_TIME))
			return "MYSQL_TYPE_TIME";
		if(temp.equals(MYSQL_TYPE_YEAR))
			return "MYSQL_TYPE_YEAR";
		if(temp.equals(MYSQL_TYPE_NEWDATE))
			return "MYSQL_TYPE_NEWDATE";
		if(temp.equals(MYSQL_TYPE_VARCHAR))
			return "MYSQL_TYPE_VARCHAR";
		if(temp.equals(MYSQL_TYPE_BIT))
			return "MYSQL_TYPE_BIT";
		if(temp.equals(MYSQL_TYPE_TIMESTAMP2))
			return "MYSQL_TYPE_TIMESTAMP2";
		if(temp.equals(MYSQL_TYPE_DATETIME2))
			return "MYSQL_TYPE_DATETIME2";
		if(temp.equals(MYSQL_TYPE_TIME2))
			return "MYSQL_TYPE_TIME2";
		if(temp.equals(MYSQL_TYPE_NEWDECIMAL))
			return "MYSQL_TYPE_NEWDECIMAL";
		if(temp.equals(MYSQL_TYPE_ENUM))
			return "MYSQL_TYPE_ENUM";
		if(temp.equals(MYSQL_TYPE_SET))
			return "MYSQL_TYPE_SET";
		
		if(temp.equals(MYSQL_TYPE_TINY_BLOB))
			return "MYSQL_TYPE_TINY_BLOB";
		
		if(temp.equals(MYSQL_TYPE_MEDIUM_BLOB))
			return "MYSQL_TYPE_MEDIUM_BLOB";
		
		if(temp.equals(MYSQL_TYPE_LONG_BLOB))
			return "MYSQL_TYPE_LONG_BLOB";
		
		if(temp.equals(MYSQL_TYPE_BLOB))
			return "MYSQL_TYPE_BLOB";
		
		if(temp.equals(MYSQL_TYPE_VAR_STRING))
			return "MYSQL_TYPE_VAR_STRING";
		if(temp.equals(MYSQL_TYPE_STRING))
			return "MYSQL_TYPE_STRING";
		if(temp.equals(MYSQL_TYPE_GEOMETRY))
			return "MYSQL_TYPE_GEOMETRY";
		
		throw new IllegalArgumentException();
	}
	
	public static final String MYSQL_TYPE_DECIMAL="00";
	public static final String MYSQL_TYPE_TINY="01";
	public static final String MYSQL_TYPE_SHORT="02";
	public static final String MYSQL_TYPE_LONG="03";
	public static final String MYSQL_TYPE_FLOAT="04";
	public static final String MYSQL_TYPE_DOUBLE="05";
	public static final String MYSQL_TYPE_NULL="06";
	public static final String MYSQL_TYPE_TIMESTAMP="07";
	public static final String MYSQL_TYPE_LONGLONG="08";
	public static final String MYSQL_TYPE_INT24="09";
	public static final String MYSQL_TYPE_DATE="0a";
	
	public static final String MYSQL_TYPE_TIME="0b";
	public static final String MYSQL_TYPE_DATETIME="0c";
	public static final String MYSQL_TYPE_YEAR="0c";
	public static final String MYSQL_TYPE_NEWDATE="0e";
	public static final String MYSQL_TYPE_VARCHAR="0f";
	public static final String MYSQL_TYPE_BIT="10";
	public static final String MYSQL_TYPE_TIMESTAMP2="11";
	public static final String MYSQL_TYPE_DATETIME2="12";
	public static final String MYSQL_TYPE_TIME2="13";
	public static final String MYSQL_TYPE_NEWDECIMAL="f6";
	public static final String MYSQL_TYPE_ENUM="f7";
	public static final String MYSQL_TYPE_SET="f8";
	public static final String MYSQL_TYPE_TINY_BLOB="f9";
	public static final String MYSQL_TYPE_MEDIUM_BLOB="fa";
	public static final String MYSQL_TYPE_LONG_BLOB="fb";
	public static final String MYSQL_TYPE_BLOB="fc";
	public static final String MYSQL_TYPE_VAR_STRING="fd";
	public static final String MYSQL_TYPE_STRING="fe";
	public static final String MYSQL_TYPE_GEOMETRY="ff";
	
}
