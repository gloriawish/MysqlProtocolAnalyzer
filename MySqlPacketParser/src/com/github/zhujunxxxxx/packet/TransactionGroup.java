package com.github.zhujunxxxxx.packet;

import java.util.ArrayList;
import java.util.List;


public class TransactionGroup {
	private List<MySqlPacket> list;
	private String logID;
	private boolean noAffect;
	public TransactionGroup(){
		list=new ArrayList<MySqlPacket>();
	}
	public List<MySqlPacket> getList() {
		return list;
	}
	public void setList(List<MySqlPacket> list) {
		this.list = list;
	}
	public String getLogID() {
		return logID;
	}
	public void setLogID(String logID) {
		this.logID = logID;
	}
	public boolean isNoAffect() {
		return noAffect;
	}
	public void setNoAffect(boolean noAffect) {
		this.noAffect = noAffect;
	}
	public void addPacket(MySqlPacket packet)
	{
		this.list.add(packet);
	}

}
