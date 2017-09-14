/** 
* @author chenhuowei: 
* @version 创建时间：2017年7月20日 上午10:40:10 
* 类说明 
*/ 
package socketFrameTest.client;

import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JTextArea;

import socketFrameTest.Message;

//这是客户端接收服务器消息在线程中的实现代码

public class ClientConServerThread extends Thread{

private Socket s;

private JTextArea showMsg;


public JTextArea getShowMsg() {
	return showMsg;
}

public void setShowMsg(JTextArea showMsg) {
	this.showMsg = showMsg;
}

public Socket getS() {

return s; 

}

public void setS(Socket s) {

this.s = s;

}

public ClientConServerThread(Socket s){

this.s=s;

}



public void run(){

while(true)

{

try {

//不停的读取从服务器端发来的消息
if ( (!s.isClosed()&&s.isConnected())){
	
ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

Message m = (Message) ois.readObject();


if(m.getMessageType() == Message.send_msg)

{

	showMsg.append(m.toString()+"\r\n");

}else if (m.getMessageType() == Message.sys_msg) {
	
	showMsg.append(m.toString("系统消息")+"\r\n");
}else if (m.getMessageType() == Message.online_msg){
	
	showMsg.append(m.toString("系统消息")+"\r\n");
}

}

} catch (Exception e) {

e.printStackTrace();


}

}

}



}
