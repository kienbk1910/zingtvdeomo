/**
 * @u kienbk1910
 * @t 23 Jun 2014
 */
package com.example.demozing.model;

import java.io.Serializable;

/**
 * @author kienbk1910
 *
 */
public class Comment implements Serializable{
	private String userName;
	private String avatar;
	private long  date;
	private String comment;
	/**
	 * 
	 */
	public Comment(String userName,String avatar, long date, String comment) {
		// TODO Auto-generated constructor stub
		this.userName=userName;
		this.avatar=avatar;
		this.date=date;
		this.comment=comment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
 
}
