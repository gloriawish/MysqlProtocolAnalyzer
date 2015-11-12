package com.github.zhujunxxxxx.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class TcpDumpFileReader {

	private String fileName;
	private BufferedReader br;
	private long fileSize;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public BufferedReader getBr() {
		return br;
	}
	public long getFileSize() {
		return fileSize;
	}
	private boolean open(){
		File file=new File(fileName);
		if(file.exists()){
			this.fileSize=file.length();
			try {
				br=new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				return false;
			}
			return true;
		}
		return false;
	}
	
	public void start(){
		init();
		if(!open()) {
			System.out.println(fileName+" open error!");
			System.exit(-1);
		} else {
			String line=null;
			try {
				beforeResolve();
				while(null != (line=br.readLine())){
					fetchLine(line);
				}
				fetchComplete();
				br.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	protected abstract void beforeResolve();
	protected abstract void init();
	public abstract void fetchLine(String line);
	public abstract void fetchComplete();
		
	
}
