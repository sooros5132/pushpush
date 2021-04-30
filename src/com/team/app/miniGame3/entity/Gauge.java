package com.team.app.miniGame3.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.miniGame3.canvas.MiniGame3Canvas;

public class Gauge extends Entity {
	  // private final double END_POINT = 130000;
	   // private double score = 30;
	   // private double currentLocation = 0;
	   // ============================================================

	   // 생성 =========================================================
	   // gauge = new Gauge(1425, 50, 25, 300, "res/고경표.png");
	   // ============================================================

	   // 게이지 채우기 ===================================================
	   // currentLocation += score + speed;
	   // gauge.per(currentLocation / END_POINT * 100);
	   //
	   // 게이지 50% 때 바뀜
	   // if( currentLocation / END_POINT * 100 > 50 ) {
	   // gauge.changeImage(40, 25, "res/고경표1.png");
	   // }
	   // ============================================================

	   private double MAX_HEIGHT;

		private Image img1;
		private Image img2;

	   private Image face;
	   private int faceImgW;
	   private int faceImgH;
	   private double faceX;
	   private double faceY;
	   private double faceW;
	   private double faceH;

	   private Image flag;
	   private int flagImgW;
	   private int flagImgH;

	   private boolean drawFace;
	   private boolean drawFlag;

	   private boolean collision;
	   private int collisionTempo;

	   private Color gaugeColor = Color.red;
	   //new Color(0xF8D800);
	   //0xF8D800
	   private GaugeFillListener fillListener;


	   public void setFillListener(GaugeFillListener fillListener) {
	      this.fillListener = fillListener;
	   }

	   public Gauge() {
	      this(100, 100, 25, 0, "");
	   }

	   public Gauge(double x, double y, double width, double height) {
	      this(x, y, width, height, "");
	   }

	   public Gauge(double x, double y, double width, double height, String faceSrc) {
	      collision = false;
	      collisionTempo = 0;
	      setX(x);
	      setY(y);
	      setWidth(width);
	      setHeight(height);
	      this.MAX_HEIGHT = 500;
	      drawFace = false;
	      drawFlag = true;
	      if (faceSrc != null) {
	         drawFace = true;
	      }
	      faceX = x - 50;
	      faceY = y + MAX_HEIGHT;
	      faceW = 35;
	      faceH = 35;

	      try {
	         face = ImageIO.read(new File(faceSrc));
	         flag = ImageIO.read(new File("res/game3/flag.png"));

	         img1 = ImageIO.read(new File("res/game3/고경표.png"));
	         img2 = ImageIO.read(new File("res/game3/고경표1.png"));
//	             이미지의 w, h 불러오기
	         faceImgW = face.getWidth(MiniGame3Canvas.instance);
	         faceImgH = face.getHeight(MiniGame3Canvas.instance);
	         flagImgW = flag.getHeight(MiniGame3Canvas.instance);
	         flagImgH = flag.getHeight(MiniGame3Canvas.instance);
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      face = img2;
	   }

	   public boolean isCollision() {
	      return collision;
	   }

	   public void setCollision(boolean collision) {
	      this.collision = collision;
	   }

	   public void per(double setPer) {
	      double per = MAX_HEIGHT / 100;

	      setSize(getWidth(), setPer * per);

	   }

	   public void setSize(double newWidth, double newHeight) {
	      if(isActive()) {
	         setWidth(newWidth);
	   
	         if (newHeight < 0)
	            setHeight(0);
	         else if (MAX_HEIGHT < newHeight)
	            setHeight(MAX_HEIGHT);
	         else
	            setHeight(newHeight);
	   
	         double height = getHeight();
	   
	         if (height == MAX_HEIGHT) {
	            setHeight(MAX_HEIGHT);
	            if (fillListener != null)
	               fillListener.onFilld();
	         }
//		      fillListener.progress(getWidth(), setPer * per);
	      }
	   }

	   @Override
	   public void update() {
//	      if (isCollision()) {
//	         changeImage(60, 45, "res/game3/고경표.png");
//	         // img1
//	         if (collisionTempo == 15) {
//	            changeImage(35, 35, "res/game3/고경표1.png");
//	            // img 2
//	            setImages(String img1Src, Stromg img2Src);
//	            collision=false;
//	            collisionTempo=0;
//	         }
//	         collisionTempo++;
//	      }
		   
		   
		   if (isCollision()) {
		       		face = img1;
			         faceImgW = img1.getWidth(MiniGame3Canvas.instance);
			         faceImgH = img1.getHeight(MiniGame3Canvas.instance);
		         // img1
		         if (collisionTempo == 50) {
		            face = img2;
			         faceImgW = img2.getWidth(MiniGame3Canvas.instance);
			         faceImgH = img2.getHeight(MiniGame3Canvas.instance);
		            // img 2
		            collision=false;
		            collisionTempo=0;
		         }
		         collisionTempo++;
		      }
	   }

	   @Override
	   public void paint(Graphics g) {

	      double height = getHeight();
	      double width = getWidth();
	      double x = getX();
	      double y = getY();
	      double dx1 = x;
	      // Math.ceil: 소수점 아래 올림
	      double dy1 = Math.ceil(y + MAX_HEIGHT - height);

	      // 검은색 뒷 배경
//	      double rectX = x - width / 2;
//	      double rectY = y - width / 2;
//	      double rectW = width * 2;
//	      double rectH = MAX_HEIGHT + width;
//	      g.setColor(Color.black);
//	      g.fillRect((int) rectX, (int) rectY, (int) rectW, (int) rectH);

	      g.setColor(gaugeColor);
	      
	      // 게이지 테두리
	      g.setColor(gaugeColor);
	      g.drawRect((int) x, (int) y, (int) width - 1, (int) MAX_HEIGHT - 1);
	      // 게이지바
	      g.setColor(gaugeColor);
	      g.fillRect((int) dx1, (int) dy1, (int) width, (int) height);

	      if (drawFace) {
	         int iDx1 = (int) (faceX - faceW / 2);
	         int iDy1 = (int) (dy1 - faceH / 2);
	         int iDx2 = (int) (faceX + faceW);
	         int iDy2 = (int) (dy1 + faceH);
	         // 얼굴
	         g.drawImage(face, iDx1 - 20, iDy1 - 25, iDx2 - 20, iDy2 - 17, 0, 0, faceImgW, faceImgH,
	               MiniGame3Canvas.instance);
	         // 삼각형
	         int pX[] = { 
	               (int)(x - 23),
	               (int)(x - 23),
	               (int)(x - 3)};
	         int pY[] = { 
	               (int)(dy1 - 10), 
	               (int)(dy1 + 10), 
	               (int)(dy1)};
	         g.setColor(gaugeColor);
	         g.fillPolygon(pX, pY, 3);
	      }

	      if (drawFlag) {
	         // 깃발
	         int fDx1 = (int) (x + flagImgW / 2 - 7);
	         int fDy1 = (int) (y - flagImgH);
	         int fDx2 = (int) (x + flagImgW + flagImgW / 2 - 7);
	         int fDy2 = (int) (y);
	         g.drawImage(flag, fDx1, fDy1, fDx2, fDy2, 0, 0, flagImgW, flagImgH, MiniGame3Canvas.instance);
	      }

//	         double dx1 = x;
//	         // 빨간바의 아래부분 - 현재값 ( 밑에서부터 위로 여기까지 빨간색을 칠하겠다 )
//	         double dy1 = y + MAX_HEIGHT - height;
//	         // 빨간색바의 너비
//	         double dx2 = x + width;
//	         // 빨간바의 아래
//	         double dy2 = y + MAX_HEIGHT;
	      //
//	         g.drawImage(img, (int)dx1, (int)dy1, (int)dx2, (int)dy2,
//	                           0,         0,       1,        1,
//	                                                   ActionCanvas.instance);
	   }

	   // public void faceMove() {
//	         
	   // }
	   //
	   // public void facePer(double setPer) {
//	         double per = MAX_HEIGHT / 100;
//	         
//	         setHeight(setPer * per);
	   // }


       public void setImages(String img1Src, String img2Src) {
 	      try {
	         img1 = ImageIO.read(new File(img1Src));
	         img2 = ImageIO.read(new File(img2Src));
	         faceImgW = img2.getWidth(MiniGame3Canvas.instance);
	         faceImgH = img2.getHeight(MiniGame3Canvas.instance);
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
 	      face = img2;
       }
	  
       
	   public void changeImage(String src) {
//	      try {
//	         face = ImageIO.read(new File(src));
//	         faceImgW = face.getWidth(Game2Canvas.instance);
//	         faceImgH = face.getHeight(Game2Canvas.instance);
//	      } catch (IOException e) {
//	         e.printStackTrace();
//	      }
	      this.changeImage(getX(), getY(), 0, 0, src);
	   }

	   public void changeImage(double faceW, double faceH, String src) {
	      this.changeImage(getX(), getY(), faceW, faceH, src);
	   }

	   public void changeImage(double x, double y, double faceW, double faceH, String src) {
	      try {
	         face = ImageIO.read(new File(src));
	         setX(x);
	         setY(y);
	         this.faceW = faceW;
	         this.faceH = faceH;
	         faceImgW = face.getWidth(MiniGame3Canvas.instance);
	         faceImgH = face.getHeight(MiniGame3Canvas.instance);
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }

	   public void setGaugeColor(Color gaugeColor) {
	      this.gaugeColor = gaugeColor;
	   }

	   public void drawFace(boolean face) {
	      this.drawFace = face;
	   }

	   public boolean isDrawFace() {
	      return drawFace;
	   }

	   public void setDrawFace(boolean drawFace) {
	      this.drawFace = drawFace;
	   }

	   public double getFaceW() {
	      return faceW;
	   }

	   public double getFaceH() {
	      return faceH;
	   }

	   public Color getGaugeColor() {
	      return gaugeColor;
	   }

	   public double getMAX_HEIGHT() {
	      return MAX_HEIGHT;
	   }

	   public void setMAX_HEIGHT(double MAX_HEIGHT) {
	      this.MAX_HEIGHT = MAX_HEIGHT;
	   }

	   public boolean isDrawFlag() {
	      return drawFlag;
	   }

	   public void setDrawFlag(boolean drawFlag) {
	      this.drawFlag = drawFlag;
	   }

	}