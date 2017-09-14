/** 
* @author chenhuowei: 
* @version 创建时间：2017年7月20日 上午9:50:08 
* 类说明 
*/ 
package socketFrameTest;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	@Override
	public String toString() {
		return "客户端 [id=" + id + ", name=" + name + "]";
	}
	
	
	
	
	
}
