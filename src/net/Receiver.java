package net;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver extends Thread {
	volatile boolean shut=false;
	DataInputStream in;
	@Override
	public void run() {
		try {
			while(true){
			    String x=in.readUTF();
				if(x.equals("bye")) break;
                System.out.println(x);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Receiver(Socket sk){
		try {
			in = new DataInputStream(sk.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
