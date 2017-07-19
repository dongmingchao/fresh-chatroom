package net;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import Communicator.*;
public class Receiver extends Thread {
	volatile boolean online=true;
	DataInputStream in;
	private Controller controller;
	public void setController(Controller controller){
	    this.controller=controller;
    }
	@Override
	public void run() {
		try {
			while(online){
			    String x=in.readUTF();
			    if(x.equals("")) continue;
				if(x.equals("bye")) break;
                controller.Output(x);//TODO:Message(x);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
