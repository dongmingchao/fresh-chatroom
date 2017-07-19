package Communicator;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.*;

public class Controller {
    boolean online=true;
    @FXML
    private TextArea textArea;
    @FXML
    private AnchorPane showpan;
    @FXML
    private MenuItem roomMessage;
    @FXML
    private Label talk;
    public String linend=System.getProperty("line.separator");
    public String got="GOT!!";
    public void sendMessage(){
        String ms=textArea.getText();
        Message(ms);
        aclient.sendToserver(ms);
    }
    public void Message(String obj) {
        talk.setText(talk.getText()+obj+linend);
    }
    public void login() {
        try {
            AnchorPane loginpane = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene loginscene = new Scene(loginpane);
            Stage log=new Stage();
            log.setScene(loginscene);
            log.setTitle("登陆");
            log.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    GreetingServer aserver = null;//服务器线程
    public void startroom(){
        Stage roomPort = new Stage();
        HBox roompane = new HBox();
        TextField locate = new TextField("端口号");
        Button confirm = new Button("确定");
        confirm.setOnAction(event -> {
			try {
				aserver=new GreetingServer(Integer.parseInt(locate.getText()));
				aserver.setControl(this);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			aserver.start();
            roomPort.close();
        });
        roompane.getChildren().addAll(locate,confirm);
        roomPort.setTitle("新建房间");
        roomPort.setScene(new Scene(roompane));
        roomPort.show();
    }/*
    private Main app;
    public void setMain(Main app){
        this.app=app;
    }*/
    public void Output(String string){
        Runnable todo= () -> {
            Message(string);
        };
        Platform.runLater(todo);
    }
    public void exit(){
        if(aserver!=null)
            if(aserver.online)
                aserver.close();
        if(aclient!=null) aclient.close();
        Platform.exit();
    }
    GreetingClient aclient=null;//客户端
    public void joinroom(){
        Stage messtable = new Stage();
        //HBox table = new HBox();
        TextField ipandport = new TextField("192.168.1.145:9000");
        Button confirm = new Button("确定");
        //table.getChildren().addAll(ipandport,confirm);
        messtable.setScene(new Scene(new HBox(ipandport,confirm)));
        messtable.setTitle("加入房间");
        messtable.show();
        confirm.setOnAction(event -> {
            aclient= new GreetingClient(ipandport.getText().split(":"),this);
            Receiver in = new Receiver(aclient.client);
            in.setController(this);
            in.start();
            messtable.close();
        });
    }
    public void quitroom(){
        if(aclient==null&&aserver==null) {
            Output("您并没有在房间中……");
            return;
        }
        Output("正在退出房间");
        aclient.close();
    }
}
