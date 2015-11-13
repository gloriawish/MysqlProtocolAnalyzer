package com.github.zhujunxxxxx.bootstrap;

import com.github.zhujunxxxxx.tool.PacketAnalyzer;

public class BootStrap {

	public static void main(String[] args) {
		
		
		if(args.length<3)
		{
			System.out.println("arguments must bigger than 2.");
			System.exit(-1);
		}
		
		String fileName="";
		String ip="";
		String port="";
		String lastCommitLogId="0";
		for (int i = 0; i < args.length-1; i++) {
			if(args[i].equals("-f"))
			{
				fileName=args[i+1];
			}
			if(args[i].equals("-i"))
			{
				ip=args[i+1];
			}
			if(args[i].equals("-p"))
			{
				port=args[i+1];
			}
			if(args[i].equals("-log"))
				lastCommitLogId=args[i+1];
		}
		
		PacketAnalyzer analyzer=new PacketAnalyzer();
		analyzer.setFileName(fileName);
		analyzer.setServerIPPort(String.format("%s.%s",ip,port));
		analyzer.setLastLogID(Integer.parseInt(lastCommitLogId));
		analyzer.start();
		
	}
}
