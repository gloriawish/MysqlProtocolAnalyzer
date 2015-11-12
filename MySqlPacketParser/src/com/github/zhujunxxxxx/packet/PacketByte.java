package com.github.zhujunxxxxx.packet;

public class PacketByte {
	
	
	static String[] characters=new String[]{"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

	private String value;
	
	public PacketByte(String value)
	{
		if(value.length()!=2){
			throw new RuntimeException();
		}
		boolean flag=false;
		for (int i = 0; i < value.length(); i++) {
			for (int j = 0; j < characters.length; j++) {
				if(characters[j].equals(String.valueOf(value.charAt(i))))
					flag=true;
			}
			if(!flag)
				throw new RuntimeException();
		}
		this.value=value;
	}

	public String getValue() {
		return value;
	}
	
	public String getFirst()
	{
		return value.substring(0,1);
	}
	
	public String getSecond()
	{
		return value.substring(1,2);
	}
	
	public String toString()
	{
		return value;
	}
	
	public static String valueOf(PacketByte pb)
	{
		return pb.getValue();
	}
	
	public static String valueOf(PacketByte[] pbs)
	{
		if(pbs==null){
			throw new NullPointerException();
		}
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < pbs.length; i++) {
			sb.append(pbs[i].getValue());
			if(i!=pbs.length-1)
				sb.append(" ");
		}
		return sb.toString();
	}
	
	public static String valueOf(PacketByte[] pbs,int start,int length)
	{
		if(pbs==null){
			throw new NullPointerException();
		}
		if(start>=pbs.length || start+length>pbs.length){
			throw new IndexOutOfBoundsException();
		}
		StringBuilder sb=new StringBuilder();
		for (int i = start; i < length; i++) {
			sb.append(pbs[i].getValue());
			if(i!=length-1)
				sb.append(" ");
		}
		return sb.toString();
	}
}
