/** 
* @author chenhuowei: 
* @version 创建时间：2017年7月20日 上午9:51:06 
* 类说明 
*/ 
package socketFrameTest;

import java.io.Serializable;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int online_msg = 1;
	public static int sys_msg = 2;
	public static int send_msg = 3;
	private String content;
	private int messageType;
	private int userId;
	private int toUserId;
	private String username;
	public static int getOnline_msg() {
		return  online_msg;
	}
	public static void setOnline_msg(int online_msg) {
		Message.online_msg = online_msg;
	}
	public static int getSys_msg() {
		return sys_msg;
	}
	public static void setSys_msg(int sys_msg) {
		Message.sys_msg = sys_msg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public Message(String content, int messageType) {
		super();
		this.content = content;
		this.messageType = messageType;
	}
	public Message(String content, int messageType, int userId, String username) {
		super();
		this.content = content;
		this.messageType = messageType;
		this.userId = userId;
		this.username = username;
	}
	public Message() {
		super();
		
	}
	
	public static int getSend_msg() {
		return send_msg;
	}
	public static void setSend_msg(int send_msg) {
		Message.send_msg = send_msg;
	}
	public int getToUserId() {
		return toUserId;
	}
	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return username+"["+userId+"]:"+content+"";
	}
	
	public String toString(String type) {
		return username+"["+userId+"]: "+content+" ["+type+"]";
	}
	
	
	
}
