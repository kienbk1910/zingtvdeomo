/**kienbk1910
 *TODO
 * Jun 14, 2014
 */
package com.example.demozing.model;

import android.R.bool;

/**
 * @author kienbk1910
 *
 */
/**
 * @author kienbk1910
 *
 */
public class Category {
	int icon;
	String title;
	TypeCategory type;
	boolean selected;
	/**
	 * 
	 */
	public Category(int icon, String title,TypeCategory type,boolean selected) {
		// TODO Auto-generated constructor stub
		this.icon=icon;
		this.type=type;
		this.title=title;
		this.selected=selected;
		
	}
	public Category( String title,TypeCategory type) {
		// TODO Auto-generated constructor stub
		this.type=type;
		this.title=title;
		
		
	}
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
/**
 * @return the type
 */
public TypeCategory getType() {
	return type;
}
/**
 * @param type the type to set
 */
public void setType(TypeCategory type) {
	this.type = type;
}

}
