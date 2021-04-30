package com.team.app.miniGame3.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SpeedGauge extends Entity {
	private int num;
	private final int startNum = 30;
//	private GraphicsEnvironment DOSMyungjo;

	private Font font;
	private Font font2;
	private int tempo;

	public SpeedGauge() {
		num = 0;
		setX(40);
		setY(260);
		font = new Font("Dialog", Font.BOLD, 70);
		font2 = new Font("Dialog", Font.BOLD, 40);
		tempo = 8;

//	      try{
//	          DOSMyungjo = GraphicsEnvironment.getLocalGraphicsEnvironment();
//	          DOSMyungjo.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/배달의민족 도현체.ttf")));
//	      } catch (IOException | FontFormatException e) {}
//	      
//	      .setFont(new Font("배달의민족 도현체", Font.PLAIN, 18));
	}

	public void reset() {
		if( num > 30) {
			num = startNum;			
		}
		tempo = 8;
	}

	@Override
	public void update() {
		if (tempo == 0 && num != 80 ) {
			num++;
			tempo = 8;
		}
		tempo--;
	}

	@Override
	public void paint(Graphics g) {
		int x = (int) getX();
		int y = (int) getY();
		String numStr = Integer.toString(num);

		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Speed", x, y);
		
		
		if (num < 60)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);
		
		
		g.drawString(numStr, x + 40, y + 90);
		
		g.setColor(Color.WHITE);
		g.setFont(font2);
		g.drawString("km/hr", x + 140, y + 90);

	}

}
