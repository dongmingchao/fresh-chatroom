package net;

import java.net.*;
import java.util.Scanner;
import java.io.*;
 
public class GreetingClient
{
	
   public static void main(String [] args)
   {
      //String serverName = args[0];
      //int port = Integer.parseInt(args[1]);
	   String serverName = "localhost";
	   int port = 9000;
	   try{
         System.out.println("尝试链接服务器" + serverName + "端口号" + port);
         Socket client = new Socket(serverName, port);
         System.out.println("已连接" + client.getRemoteSocketAddress());
         
         //发送信息
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         //用户输入
         Scanner in= new Scanner(System.in);
         String x;
         //获取信息
	     //InputStream inFromServer = client.getInputStream();
	     //DataInputStream serverin = new DataInputStream(inFromServer);
	     //System.out.println(serverin.readUTF());
	     Receiver t=new Receiver(client);
	     t.start();
	     x=in.nextLine();
         while(true){
	         out.writeUTF(x);
	         if(x.equals("exit")){
	        	 	//t.shut=true;
	        	 	break;
	         }else{
		         //System.out.println("服务器说"+serverin.readUTF());
		         x=in.nextLine();
	         }
         }
         System.out.print("正在退出……");
         in.close();
         out.close();
         //client.close();
	     //---------------------------------------------------------
      }catch(IOException e){
         //e.printStackTrace();
         System.out.println("服务器已断开链接！");
      }
   }
}