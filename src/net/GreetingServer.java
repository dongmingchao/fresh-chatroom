package net;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class GreetingServer extends Thread
{
    public volatile String Output=null;
    private ServerSocket serverSocket;
    public GreetingServer(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        //serverSocket.setSoTimeout(10000);//设置超时时间
    }/*
    PrintWriter output= new PrintWriter(new ObjectOutputStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {

        }
    }));*/
    public void getOutput(){

    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
            		String tt="等待客户端链接端口" + serverSocket.getLocalPort() + "...";
            		System.out.println(tt);
                Output=tt;
                Socket server = serverSocket.accept();
                System.out.println("已连接" + server.getRemoteSocketAddress());
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
                    System.out.println("客户端说"+textfromC);
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
                System.out.println("客户端离线了");
                in.close();
                server.close();
            }catch(SocketTimeoutException s){
                System.out.println("连接超时");
                break;
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("客户端已断开链接");
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
}