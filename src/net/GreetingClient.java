package net;

import Communicator.Controller;

import java.net.*;
import java.util.Scanner;
import java.io.*;
 
public class GreetingClient
{
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    protected String serverName=null;
    private DataOutputStream out=null;
    public Socket client=null;
    public GreetingClient(String args[],Controller controller){
        setController(controller);
        main(args);
    }
    public void main(String [] args)//单独调试加static
   {
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);
	    //String serverName = "localhost";
	    //int port = 9000;
	    try{
            String output="尝试链接服务器" + serverName + "端口号" + port;
            controller.Output(output);
            client = new Socket(serverName, port);
            output="已连接" + client.getRemoteSocketAddress();
            controller.Output(output);
         
            //发送信息
            out = new DataOutputStream(client.getOutputStream());
        }catch(IOException e){
         //e.printStackTrace();
         controller.Output("服务器已断开链接！");
        }
   }
   public void sendToserver(String string){
       try {
           out.writeUTF(string);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   public void close(){
       try {
           out.writeUTF("exit");
           out.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}