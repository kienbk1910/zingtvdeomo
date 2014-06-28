/**
 * @u kienbk1910
 * @t 27 Jun 2014
 */
package com.example.demozing.model;

import java.io.Serializable;

/**
 * @author kienbk1910
 * 
 */
public class Video implements Serializable{
	private String title;
	private String showTitle;
	private int viewNumber;
	private String url;
	private int duration;
	private String urlImage;

	/**
	 * 
	 */
	public Video(String title, String showTitle, int viewNumber, String url,
			int duration, String urlImage) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.showTitle = showTitle;
		this.viewNumber = viewNumber;
		this.url = url;
		this.duration = duration;
		this.urlImage = urlImage;
	}
/**
 * 
 */
public Video( String urlImage) {
	// TODO Auto-generated constructor stub
	this.urlImage= urlImage;
}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	public int getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(int viewNumber) {
		this.viewNumber = viewNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	

}
