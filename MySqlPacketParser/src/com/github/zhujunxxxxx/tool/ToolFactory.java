package com.github.zhujunxxxxx.tool;

public class ToolFactory {
	
	public static TcpDumpFileHelper getHelperInstance(String serverIP){
		TcpDumpFileHelper.setServerIP(serverIP);
		TcpDumpFileHelper instance=HelperHolder.instance;
		return instance;
	}

	public static class HelperHolder{
		private static TcpDumpFileHelper instance=new TcpDumpFileHelper();
	}

	public static PacketBuilder getBuilderInstance(){
		return BuilderHolder.instance;
	}

	public static class BuilderHolder{
		private static PacketBuilder instance=new PacketBuilder();
	}
}
