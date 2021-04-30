package com.team.app.main.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.team.app.entity.Entity;

public class TextInput extends Entity {
	
	private String text = "";
	private int maxLength = 15;
	
	public TextInput() {
		this(0,0,0,0);
	}
	public TextInput(double x, double y, double width, double height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void paint(Graphics g) {
		int x = (int)getX();
		int y = (int)getY();
		int w = (int)getWidth();
		int h = (int)getHeight();


		g.setColor(Color.lightGray);
		if( isActive() ) {
			g.setColor(Color.orange);
			g.drawRect(x-1, y-1, w+1, h+1);
			g.drawRect(x-2, y-2, w+3, h+3);
			g.drawRect(x-3, y-3, w+5, h+5);
			
			g.setColor(new Color(0xf2f2f2));
			g.fillRect(x, y, w, h);
		} else {
			g.fillRect(x, y, w, h);
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		g.drawString("IP: ", x+2, (int)(y+(h*0.9)));
		g.drawString(text, x+37, (int)(y+(h*0.9)));
	}
	
	public void reset() {
		text = "";
	}

	public void append(char key) {
		if( isActive() ) {
			boolean keyChk = Character.toString(key).matches("[0-9.]");

			if( key == KeyEvent.VK_BACK_SPACE )
				delete(1);
			else
				if( keyChk && text.length() < maxLength)
					text += Character.toString(key);
			
//			if( key == KeyEvent.VK_DELETE){
//				deleteKey(1);
//				return;
//			}
			
		}
	}

	public void delete(int num) {
		int len = text.length();
		if(num > 0 && len > 0) {
			text = text.substring(0, len - num);
		}
	}
	
//	public void deleteKey(int num) {
//		int len = text.length();
//		if(num > 0 && len > 0) {
//			text = text.substring(0, len - num);
//		}
//	}
	
	public boolean isSelected(int mouseX, int mouseY) {
		int w = (int) getWidth();
		int h = (int) getHeight();
		int x1 = (int) getX();
		int y1 = (int) getY();
		int x2 = (int) x1 + w;
		int y2 = (int) y1 + h;

		if ((x1 <= mouseX && mouseX <= x2) &&
			(y1 <= mouseY && mouseY <= y2))
			return true;

		return false;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
}
