/**kienbk1910
 *TODO
 * Jun 29, 2014
 */
package com.example.demozing.model;

import java.io.Serializable;

/**
 * @author kienbk1910
 *
 */
public class Group implements Serializable{
	String title;
	String urlImage1;
	String urlImage2;
	String urlImage3;
	String urlImage4;
	int number;
	/**
	 * 
	 */
	public Group(String title,
	String urlImage1,
	String urlImage2,
	String urlImage3,
	String urlImage4,
	int number) {
		// TODO Auto-generated constructor stub
		this.title=title;
		this.urlImage1=urlImage1;
		this.urlImage2=urlImage2;
		this.urlImage3=urlImage3;
		this.urlImage4=urlImage4;
		this.number=number;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrlImage1() {
		return urlImage1;
	}
	public void setUrlImage1(String urlImage1) {
		this.urlImage1 = urlImage1;
	}
	public String getUrlImage2() {
		return urlImage2;
	}
	public void setUrlImage2(String urlImage2) {
		this.urlImage2 = urlImage2;
	}
	public String getUrlImage3() {
		return urlImage3;
	}
	public void setUrlImage3(String urlImage3) {
		this.urlImage3 = urlImage3;
	}
	public String getUrlImage4() {
		return urlImage4;
	}
	public void setUrlImage4(String urlImage4) {
		this.urlImage4 = urlImage4;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	

}
