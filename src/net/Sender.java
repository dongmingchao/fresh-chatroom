package net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class Sender extends Thread {
	Scanner in;
	DataOutputStream out;
	//DataInputStream infromsk;
	boolean shut=false;
	@Override
	public void run() {
		String x=null;
		while(!shut){
			try{
			    if(x!=null){
                    out.writeUTF(x);
                    if(x.equals("exit")) {
                        break;
                    }
                    x=null;
                }else{
                    //System.out.println("服务器说"+serverin.readUTF());
                    if(in.hasNext()) x=in.nextLine();
                }
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		in.close();
    }
	public Sender(OutputStream dos){
			//infromsk=new DataInputStream(sk.getInputStream());
			out = new DataOutputStream(dos);
			in = new Scanner(System.in);
	}
}
