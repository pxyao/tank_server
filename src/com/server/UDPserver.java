package com.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;


//这是服务器用来接收坦克和子弹的位置
public class UDPserver extends Thread{
	private DatagramPacket data=null;
	
	public class Info{ 
		private String username;
		private String x;
		private String y;
		private String orientation;
		private int live;
		public int getLive() {
			return live;
		}
		public void setLive(int live) {
			this.live = live;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getX() {
			return x;
		}
		public void setX(String x) {
			this.x = x;
		}
		public String getY() {
			return y;
		}
		public void setY(String y) {
			this.y = y;
		}
		public String getOrientation() {
			return orientation;
		}
		public void setOrientation(String orientation) {
			this.orientation = orientation;
		}
	}
	
	//存储坦克的数量 以及他们的位置和子弹的位置
	private HashMap<String,Info> infos=new HashMap();
		
	
	public UDPserver(DatagramPacket data){
		this.data=data;
	}
	public void run(){
		String command=new String(data.getData()).trim();
		System.out.println(command);
		String[] s=command.split(",");
		Object obj=infos.get(s[0]);
		if(obj==null){
			Info info=new Info();
			info.setUsername(s[0]);
			info.setX(s[1]);
			info.setY(s[2]);
			info.setOrientation(s[3]);
			info.setLive(Integer.parseInt(s[4]));
			infos.put(s[0], info);
		}else{
			Info info=(Info)obj;
			info.setX(s[1]);
			info.setY(s[2]);
			info.setOrientation(s[3]);
			info.setLive(Integer.parseInt(s[4]));
		}
	}
	
	static DatagramSocket server=null;
	
	private static void openServer() throws Exception{
		server =new DatagramSocket(9999); 
		while(true){
			byte[] b=new byte[1000];
			DatagramPacket data=new DatagramPacket(b, b.length);
			server.receive(data);
			new UDPserver(data).start();
		}
	}
	public static void main(String[] args) throws Exception {
		openServer();//打开接收坦克信息的 服务端
	}
}
