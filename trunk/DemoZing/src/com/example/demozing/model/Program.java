/**kienbk1910
 *TODO
 * Jun 16, 2014
 */
package com.example.demozing.model;

import java.io.Serializable;

/**
 * @author kienbk1910
 *
 */
public class Program implements Serializable{
  String url;
  int like;
  String  title;
  double rate;
  String category;
  /**
 * 
 */
public Program(String url) {
	// TODO Auto-generated constructor stub
	this.url=url;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public int getLike() {
	return like;
}
public void setLike(int like) {
	this.like = like;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public double getRate() {
	return rate;
}
public void setRate(double rate) {
	this.rate = rate;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
}
