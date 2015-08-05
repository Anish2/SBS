package code;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import fisica.FBox;
import fisica.FCircle;
import fisica.FRevoluteJoint;
import fisica.FWorld;
import fisica.Fisica;

public class GameDisplay extends PApplet {

	private PImage left_browImg, right_browImg, flame_oneImg, flame_twoImg, flame_threeImg, flame_fourImg;
	private PImage bg, barImg;
	private PImage bullets1, bullets2, bullets3, bullets4, bullets5;
	private FWorld world;
	private FBox left_brow, right_brow, flame_one, flame_two, flame_three, flame_four;
	private FBox bar;
	private float velocity = -800f;
	private float bgXPos = 0, bgYPos;
	private boolean left, right, up, down;
	private int mussle_num = 0;
	private int hor_speed = 7, vert_speed = 7;
	private boolean isTransformed = false;
	private float prevXPos, prevYPos;
	private ArrayList<FCircle> bullets = new ArrayList<FCircle>();

	public void setup() {
		Fisica.init(this);
		frameRate(75);
		size(400, 600);
		background(0);
		left_browImg = loadImage("brow BBBB L.png");
		right_browImg = loadImage("brow BBBB R.png");
		flame_oneImg = loadImage("1.png");
		flame_twoImg = loadImage("2.png");
		flame_threeImg = loadImage("3.png");
		flame_fourImg = loadImage("4.png");
		bullets1 = loadImage("bullets1.png");
		bullets2 = loadImage("bullets2.png");
		bullets3 = loadImage("bullets3.png");
		bullets4 = loadImage("bullets4.png");
		bullets5 = loadImage("bullets5.png");
		barImg = loadImage("brow bars 1.png");
		bg = loadImage("background.png");


		left_browImg.resize(width, height);
		right_browImg.resize(width, height);
		barImg.resize(width, height);
		flame_oneImg.resize(width/3, height/3);
		flame_twoImg.resize(width/3, height/3);
		flame_threeImg.resize(width/3, height/3);
		flame_fourImg.resize(width/3, height/3);

		bullets1.resize(width, height);
		bullets2.resize(width, height);
		bullets3.resize(width, height);
		bullets4.resize(width, height);
		bullets5.resize(width, height);

		bg.resize((int)(width*1.5), (int)(height*1.5));

		normalSetup();
	}

	public void draw() {
		//handleScreenMotion();
		background(120);
		//handleBarMotion();
		//image(bg, bgXPos, bgYPos);

		mussleFire();
		//noStroke();

		handleShooterMovement();

		handleBulletMovement();

		//flame_one.dettachImage();
		//flame_two.dettachImage();

		world.draw();
		world.step();
		mussle_num++;

	}

	public void handleBulletMovement() {
		for (int i = bullets.size()-1; i >= 0; i--) {
			FCircle c = bullets.get(i);
			c.setPosition(c.getX(), c.getY()-8);
			int offset = 180;
			if (c.getX() < 0 || c.getX() > width || c.getY() < -offset || c.getY() > height-offset) {
				bullets.remove(i);
				world.remove(c);
			}
		}
	}

	public void handleBarMotion() {
		if (Math.random() > 0.5)
			bar.setPosition(bar.getX()-5, bar.getY()+4);
		else
			bar.setPosition(bar.getX()+5, bar.getY()+4);
	}

	public void handleScreenMotion() {
		float x = flame_one.getX();
		float y = flame_one.getY();
		float scale = 0.4f;
		if (prevXPos <= x) {
			bgXPos -= (x-prevXPos)*scale;
		}		
		else if (prevXPos >= x) {
			bgXPos += (prevXPos-x)*scale;
		}

		if (prevYPos >= y) {
			bgYPos += (prevYPos-y)*scale;
		}
		else if (prevYPos <= y) {
			bgYPos -= (y-prevYPos)*scale;
		}

		prevXPos = x;
		prevYPos = y;
	}

	public void normalSetup() {
		right_brow = new FBox(10,10);
		right_brow.attachImage(right_browImg);
		right_brow.setPosition(194+8+10+3, 410);
		right_brow.setStatic(true);

		left_brow = new FBox(10,10);
		left_brow.attachImage(left_browImg);
		left_brow.setPosition(194-8+10+3,410);
		//left_brow.setVelocity(-550f, 0);
		left_brow.setStatic(true);

		flame_one = new FBox(10,10);
		flame_one.attachImage(flame_oneImg);
		flame_one.setPosition(184+3, 405);
		flame_one.setStatic(true);
		prevXPos = flame_one.getX();
		prevYPos = flame_one.getY();

		flame_two = new FBox(10,10);
		flame_two.attachImage(flame_oneImg);
		flame_two.setPosition(207+3, 405);
		flame_two.setStatic(true);

		FBox temp = new FBox(50, 50);
		temp.setPosition(189, 489);
		temp.setStatic(true);

		bar = new FBox(10,10);
		bar.attachImage(barImg);
		bar.setPosition(200,50);
		bar.setStatic(true);

		bgXPos = -0.25f*width;
		bgYPos = -0.25f*height;

		FRevoluteJoint left_joint = new FRevoluteJoint(left_brow, temp);
		//left_joint.setEnableLimit(true);
		left_joint.setEnableMotor(true);
		left_joint.setMotorSpeed(200f);
		left_joint.setAnchor(temp.getX(), temp.getY());

		//FRevoluteJoint right_joint = new FRevoluteJoint(right_brow, flame_two);


		// world configuration
		world = new FWorld();
		world.setGrabbable(false);
		world.setGravity(0, 0);
		world.add(left_brow);
		world.add(right_brow);
		world.add(flame_one);
		world.add(flame_two);
		//world.add(temp);
		world.add(bar);

		//world.add(left_joint);
		//world.add(right_joint);
	}

	public void mussleFire() {
		int num = mussle_num % 4;
		if (mussle_num % 1 == 0) {
			switch (num) {
			case 0:
				flame_one.dettachImage();
				flame_one.attachImage(flame_twoImg);
				flame_two.dettachImage();
				flame_two.attachImage(flame_twoImg);
				break;
			case 1:
				flame_one.dettachImage();
				flame_one.attachImage(flame_threeImg);
				flame_two.dettachImage();
				flame_two.attachImage(flame_threeImg);
				break;
			case 2:
				flame_one.dettachImage();
				flame_one.attachImage(flame_fourImg);
				flame_two.dettachImage();
				flame_two.attachImage(flame_fourImg);
				break;
			case 3:
				flame_one.dettachImage();
				flame_one.attachImage(flame_oneImg);
				flame_two.dettachImage();
				flame_two.attachImage(flame_oneImg);
				break;
			}
		}

		strokeWeight(1);
		PImage temp2 = flame_twoImg;
		PImage temp1 = flame_oneImg;
		FCircle bullet = null;
		if (frameCount % 10 == 0) {
			temp2.resize(width/3, height/3);
			flame_two.attachImage(temp2);
			bullet = makeBall((int) (flame_two.getX()-2),  (int) (flame_two.getY()-126));
			world.add(bullet);
			flame_two.dettachImage();
			temp2.resize(1, 1);
			flame_two.attachImage(temp2);	
			if (temp2 == flame_twoImg) {
				temp2 = flame_threeImg;
			}
			else if (temp2 == flame_threeImg) {
				temp2 = flame_fourImg;
			}
			else if (temp2 == flame_fourImg) {
				temp2 = flame_oneImg;
			}
			else if (temp2 == flame_oneImg) {
				temp2 = flame_twoImg;
			}
		}
		else if (frameCount % 10 == 5) {
			temp1.resize(width/3, height/3);
			flame_one.attachImage(temp1);
			bullet = makeBall((int) (flame_one.getX()-2), (int) (flame_one.getY()-126));
			world.add(bullet);
			//System.out.println((int) (flame_one.getY()-2));
			flame_one.dettachImage();
			temp1.resize(1, 1);
			flame_one.attachImage(temp1);

			if (temp1 == flame_twoImg) {
				temp1 = flame_threeImg;
			}
			else if (temp1 == flame_threeImg) {
				temp1 = flame_fourImg;
			}
			else if (temp1 == flame_fourImg) {
				temp1 = flame_oneImg;
			}
			else if (temp1 == flame_oneImg) {
				temp1 = flame_twoImg;
			}
		}
		if (bullet != null)
			bullets.add(bullet);
	}

	public FCircle makeBall(int xPos, int yPos) {
		FCircle b = new FCircle(15);
		b.setPosition(xPos, yPos);
		//b.setForce(-9.8f, -9.8f);
		b.setNoStroke();
		//b.setVelocity(0f, velocity);
		b.setStatic(true);
		b.attachImage(bullets1);
		return b;
	}

	public FCircle makeBall(int xPos) {
		FCircle b = new FCircle(15);
		b.setPosition(xPos, 290);
		//b.setForce(-9.8f, -9.8f);
		b.setNoStroke();
		//b.setVelocity(0f, velocity);
		b.setStatic(true);
		b.attachImage(bullets1);
		return b;
	}

	public void transformBrow() {

		world.remove(right_brow);
		world.remove(left_brow);

		right_brow = new FBox(10,10);
		right_brow.attachImage(right_browImg);
		right_brow.setPosition(194+98, 404+46);
		right_brow.setRotation(radians(60));
		right_brow.setStatic(true);

		left_brow = new FBox(10,10);
		left_brow.attachImage(left_browImg);
		left_brow.setPosition(194-80,438);
		left_brow.setRotation(radians(-60));
		left_brow.setStatic(true);

		world.add(left_brow);
		world.add(right_brow);

		/*flame_one.setPosition(183, 418-42);
		flame_two.setPosition(207, 418-42);*/

		//left_brow.setRotation(radians(90));

	}


	public void handleShooterMovement() {

		if(left && !right) {
			left_brow.setPosition(left_brow.getX() - hor_speed, left_brow.getY());
			right_brow.setPosition(right_brow.getX() - hor_speed, right_brow.getY());
			flame_one.setPosition(flame_one.getX() - hor_speed, flame_one.getY());
			flame_two.setPosition(flame_two.getX() - hor_speed, flame_two.getY());			
		}
		if(right && !left) {
			left_brow.setPosition(left_brow.getX() + hor_speed, left_brow.getY());
			right_brow.setPosition(right_brow.getX() + hor_speed, right_brow.getY());
			flame_one.setPosition(flame_one.getX() + hor_speed, flame_one.getY());
			flame_two.setPosition(flame_two.getX() + hor_speed, flame_two.getY());
		}
		if (down && !up) {
			left_brow.setPosition(left_brow.getX(), left_brow.getY() + vert_speed);
			right_brow.setPosition(right_brow.getX(), right_brow.getY() + vert_speed);
			flame_one.setPosition(flame_one.getX(), flame_one.getY() + vert_speed);
			flame_two.setPosition(flame_two.getX(), flame_two.getY() + vert_speed);
		}
		if (up && !down) {
			left_brow.setPosition(left_brow.getX(), left_brow.getY() - vert_speed);
			right_brow.setPosition(right_brow.getX(), right_brow.getY() - vert_speed);
			flame_one.setPosition(flame_one.getX(), flame_one.getY() - vert_speed);
			flame_two.setPosition(flame_two.getX(), flame_two.getY() - vert_speed);
		}

	}

	public void keyReleased()
	{
		if (key == CODED)
		{
			if (keyCode == LEFT)
			{
				left = false;
			}
			if (keyCode == RIGHT)
			{
				right = false;
			}
			if(keyCode == UP)
			{
				up = false;
			}
			if(keyCode == DOWN)
			{
				down = false;
			}

		}
	}

	public void keyPressed()
	{
		if (key == CODED)
		{
			if (keyCode == LEFT)
			{
				left = true;
			}
			if (keyCode == RIGHT)
			{
				right = true;
			}
			if(keyCode == UP)
			{
				up = true;
			}
			if(keyCode == DOWN)
			{
				down = true;
			}

		}
	}

	public void mousePressed() {		
		//System.out.println(mouseX+" "+mouseY);
		//transformBrow();
		if (!isTransformed) {
			transformBrow();
			isTransformed = true;
		}
		else {
			normalSetup();
			isTransformed = false;
		}
	}

}