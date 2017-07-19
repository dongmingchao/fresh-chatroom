package net;

import Communicator.Controller;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class GreetingServer extends Thread
{
    public boolean online=true;
    public volatile String output =null;
    private ServerSocket serverSocket;
    public GreetingServer(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);//设置超时时间
    }/*
    PrintWriter output= new PrintWriter(new ObjectOutputStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {

        }
    }));*/
    private Controller controller;
    public void setControl(Controller controller){
        this.controller=controller;
    }
    @Override
    public void run()
    {
        while(online)
        {
            try
            {
                output ="等待客户端链接端口" + serverSocket.getLocalPort() + "...";
                controller.Output(output);
                //System.out.println(output);
                Socket server = serverSocket.accept();
                output="已连接" + server.getRemoteSocketAddress();
                controller.Output(output);
                DataOutputStream out =  new DataOutputStream(server.getOutputStream());
                out.writeUTF("欢迎进入服务端：" + server.getLocalSocketAddress());
                //接受数据
                DataInputStream clientin = new DataInputStream(server.getInputStream());
                Scanner in =new Scanner(System.in);
                String textfromC =new String();
                Sender r=new Sender(server.getOutputStream());
                r.start();
                do{
                    //------------------------------------------------------------
                    //反馈数据
                    textfromC=clientin.readUTF();
                    output="对方说"+textfromC;
                    controller.Output(output);
                    if(textfromC.equals("exit")) {
                        out.writeUTF("bye");
                        out.flush();
                        break;
                    }
                    /*if(in.hasNextLine()){
                            String x = in.nextLine();
                            out.writeUTF(x);
                    }*/
                }while(true);
                r.shut=true;
                output="客户端离线了";
                controller.Output(output);
                in.close();
                server.close();
            }catch(SocketTimeoutException s){
                output="连接超时";
                controller.Output(output);
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }catch(IOException e){
                e.printStackTrace();
                output="客户端已断开链接";
                controller.Output(output);
                try {
					serverSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                break;
            }
        }
    }
    public static void main(String args)
    {
        int port = Integer.parseInt(args);
        //int port=9000;
        try{
               Thread t = new GreetingServer(port);
               t.start();
        }catch(IOException e){
               e.printStackTrace();
        }
    }
    public void close(){
        online=false;
        try {
            serverSocket.close();
            this.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}