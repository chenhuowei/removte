/** 
* @author chenhuowei: 
* @version 创建时间：2017年7月20日 上午9:27:38 
* 类说明 
*/ 
package socketFrameTest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import socketFrameTest.client.ClientConServerThread;
import socketFrameTest.client.ManageClientConServerThread;
  
public class ChatFrameClient2{  
    private PrintWriter pw;  
    private JFrame frame;  
    private JPanel pane_buttom;  
    private JSplitPane pane_center;  
  
    //显示内容的文本框，输入内容的文本框,发送内容按钮  
    private JScrollPane pane_showWindow;  
    private JScrollPane pane_inputWindow;  
    private JTextArea area_showWindow;  
    private JTextArea area_inputWindow;  
  
    private JButton btn_send;  
  
    private Dimension dimension;//用于设置area_showWindow可拖拉的大小  
    private User u = new User(2002, "Test");
  
  
    //初始化  
    public ChatFrameClient2() {  
        frame = new JFrame();  
        pane_buttom = new JPanel();  
        pane_showWindow = new JScrollPane();  
        pane_inputWindow = new JScrollPane();  
        area_showWindow = new JTextArea();  
        area_inputWindow = new JTextArea();  
        pane_center = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, pane_showWindow, pane_inputWindow);  
        btn_send = new JButton("发送");  
  
        dimension = new Dimension(50, 300);  
    }  
  
    //调用方法显示窗口  
    public void showFrame(){  
        initFrame();  
        initChatTextArea();  
        initButton();  
        btn_send();  
        socket();  
    }  
  
    //主窗体  
    public void initFrame(){  
        frame.setTitle("客户端-"+u.getName());  
        int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
        int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
        frame.setBounds(width / 2, height / 2, 400, 450);  
        frame.setVisible(true);  
    }  
  
    //内容显示文本框和输入内容文本框  
    private void initChatTextArea(){  
        //取得视图焦点  
        pane_showWindow.getViewport().add(area_showWindow);  
        pane_inputWindow.getViewport().add(area_inputWindow);  
        //将显示文本域设置为不可编辑  
        area_showWindow.setEditable(false);  
        //设置显示文本域可拖拉的大小   
        pane_showWindow.setMinimumSize(dimension);  
        frame.add(pane_center, BorderLayout.CENTER);  
    }  
  
    //发送文件，发送内容按钮  
    public void initButton(){  
        pane_buttom.add(btn_send);  
        frame.add(pane_buttom, BorderLayout.SOUTH);  
    }  
  
    private void btn_send(){  
        btn_send.addActionListener(new ActionListener() {  
  
            public void actionPerformed(ActionEvent e) {  
                String info = area_inputWindow.getText();  
                area_showWindow.append(u.getName()+":"+info+"\r\n");  
                
                ClientConServerThread serverThread =  ManageClientConServerThread.getClientConServerThread(u.getId());
                Message m = new Message(info, Message.send_msg, u.getId(), u.getName());
                m.setToUserId(1001);
                try {
					ObjectOutputStream oos=new ObjectOutputStream(serverThread.getS().getOutputStream());
					oos.writeObject(m);
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
                area_inputWindow.setText("");  
            }  
        });  
    }  
      
    private void socket(){  
        try {  
            Socket s = new Socket("127.0.0.1",9988);
            System.out.println("client start ok...");
            ClientConServerThread serverThread = new ClientConServerThread(s);//add server thread
            serverThread.setShowMsg(area_showWindow);
            ManageClientConServerThread.addClientConServerThread(u.getId(), serverThread);
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(u);// notify server who connect
            serverThread.start();
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    public static void main(String[] args) {  
        ChatFrameClient2 chat = new ChatFrameClient2();  
        chat.showFrame();  
    }  
}  
