package Communicator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    @FXML
    private TextArea textArea;
    @FXML
    private AnchorPane showpan;
    @FXML
    private MenuItem roomMessage;
    @FXML
    private Label talk;
    public String linend=System.getProperty("line.separator");
    public void sendMessage(){
        Message(textArea.getText());
    }
    public void Message(String obj){
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
    GreetingServer aserver = null;
    public void startroom(){
        Stage roomMess = new Stage();
        HBox roompane = new HBox();
        TextField locate = new TextField("端口号");
        Button confirm = new Button("确定");
        confirm.setOnAction(event -> {
			try {
				aserver = new GreetingServer(9000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            aserver.start();
            roomMess.close();
        });
        Message(aserver.Output);
        roompane.getChildren().addAll(locate,confirm);
        roomMess.setTitle("新建房间");
        roomMess.setScene(new Scene(roompane));
        roomMess.show();
    }
    public void copy(InputStream in, OutputStream out) throws IOException {
        int c;
        while ((c = in.read()) != -1) {//每次读取一个字节放入c
            out.write(c);
        }
    }

}
