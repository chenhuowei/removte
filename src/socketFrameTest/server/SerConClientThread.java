/** 
* @author chenhuowei: 
* @version 创建时间：2017年7月20日 上午9:57:08 
* 类说明 
*/ 
package socketFrameTest.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JTextArea;

import socketFrameTest.Message;
import socketFrameTest.User;

public class SerConClientThread extends Thread {

	private JTextArea showMsgArea;
	
	
	public JTextArea getShowMsgArea() {
		return showMsgArea;
	}

	public void setShowMsgArea(JTextArea showMsgArea) {
		this.showMsgArea = showMsgArea;
	}

	Socket s;

	public SerConClientThread(Socket s){

	//把服务器和该客户端的连接赋给s

	this.s=s;

	}

	//让该线程去通知其他用户

	public void notifyother(User u)

	{
//获得以保存的客户端
	HashMap<Integer, SerConClientThread> hm=ManageClientThread.clientThreadMap;

	Iterator<Integer> it=hm.keySet().iterator();

	while(it.hasNext())

	{

	Message m=new Message();

	int onLineUserId=it.next();
	if (onLineUserId !=  u.getId()){
		m.setUserId(u.getId());
		m.setUsername(u.getName());
		m.setMessageType(Message.getOnline_msg());
		m.setContent("好友上线");
	}else {
		m = new Message("登陆成功", Message.sys_msg, u.getId(), u.getName());
	}
//通知其他客户端的消息

	try {

	ObjectOutputStream oos=new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());

	oos.writeObject(m);

	} catch (IOException e) {

	// TODO Auto-generated catch block

	e.printStackTrace();

	}

	}

	 

	}

	public void run(){

	while(true){

	try {
	if (!s.isClosed()&&s.isConnected()){
		
		InputStream in = s.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(in);
		if (null != in){
			
		}
		Message m = (Message) ois.readObject();
	
		if(m.getMessageType() == Message.send_msg)
	
		{
	
		//取得接收人的线程  实现转发   
	
		SerConClientThread sc=ManageClientThread.getClientThread(m.getToUserId());
	
		ObjectOutputStream oos=new ObjectOutputStream(sc.s.getOutputStream());
	
		oos.writeObject(m);
		showMsgArea.append(m.toString("消息转发")+",目标ID="+m.getToUserId()+"\r\n");
		System.out.println("from:"+s.getInetAddress()+":"+s.getPort());
		}
	}else {
		System.out.println("wait client...");
	}

	} catch (Exception e) {


	e.printStackTrace();

	}

	}

	}

	 

	}
