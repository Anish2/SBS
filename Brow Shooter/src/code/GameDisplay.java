package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import fisica.FBox;
import fisica.FCircle;
import fisica.FWorld;
import fisica.Fisica;

public class GameDisplay {

	private PImage left_browImg, right_browImg, flame_oneImg, flame_twoImg, flame_threeImg, flame_fourImg;
	private PImage bg, barImg, cloud, cloud2, cloud3, cloud4, cloud5, cloud6;
	private PImage cached_cloud, memcached_cloud;
	private PImage bullets1, bullets2, bullets3, bullets4, bullets5;
	private static Random random = new Random();
	private FWorld world;
	private FBox left_brow, right_brow, flame_one, flame_two, flame_three, flame_four;
	private float bgXPos, bgYPos, cloudXPos, cloudYPos, backYPos, fastCloudX, fastCloudY;
	private boolean left, right, up, down;
	private int mussle_num = 0, deploymentNum, currentNum, width, height, frameCount = 0;
	private float hor_speed, vert_speed;
	private boolean isTransformed = false, switchBg = true;
	private float prevXPos, prevYPos;
	private ArrayList<FCircle> bullets = new ArrayList<FCircle>();

	private PImage bottomImg, topImg;
	private ArrayList<FBox> bottom, top;
	private BufferedReader in;
	private String tempStr;
	private ArrayList<int[]> steps = new ArrayList<int[]>();
	private PApplet p;

	public GameDisplay(PApplet p) {
		this.p = p;		
	}

	public void setup() {
		width = 400;
		height = 600;
		Fisica.init(p);
		p.size(width, height, p.P2D);
		//p.frameRate(250);		
		//p.size(500, 750, p.P2D);
		// size(displayWidth, displayHeight, p.P2D); // for android
		normalSetup();
		nonComputeDraw();
	}

	public void normalSetup() {
		left_browImg = p.loadImage("brow BBBB L.png");
		right_browImg = p.loadImage("brow BBBB R.png");
		flame_oneImg = p.loadImage("1.png");
		flame_twoImg = p.loadImage("2.png");
		flame_threeImg = p.loadImage("3.png");
		flame_fourImg = p.loadImage("4.png");
		//bullets1 = loadImage("bullets1.png");
		//bullets1 = loadImage("superbullet1.png");
		bullets1 = p.loadImage("bluebird2.png");
		bullets2 = p.loadImage("bullets2.png");
		bullets3 = p.loadImage("bullets3.png");
		bullets4 = p.loadImage("bullets4.png");
		bullets5 = p.loadImage("bullets5.png");
		barImg = p.loadImage("brow bars 1.png");
		bg = p.loadImage("map.png");
		cloud = p.loadImage("cloud1.png");
		cloud2 = p.loadImage("cloud2.png");
		cloud3 = p.loadImage("cloud3.png");
		cloud4 = p.loadImage("cloud4.png");
		cloud5 = p.loadImage("cloud5.png");
		cloud6 = p.loadImage("cloud6.png");		
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
		int cloud_resX = width, cloud_resY = height;
		cloud.resize(cloud_resX, cloud_resY);
		cloud2.resize(cloud_resX, cloud_resY);
		cloud3.resize(cloud_resX, cloud_resY);
		cloud4.resize(cloud_resX, cloud_resY);
		cloud5.resize(cloud_resX, cloud_resY);
		cloud6.resize(cloud_resX, cloud_resY);

		bottomImg = p.loadImage("brow_scissors1.png");
		topImg = p.loadImage("brow_scissors2.png");
		bottomImg.resize(width/4, height/4);
		topImg.resize(width/4,  height/4);

		cached_cloud = cloud;
		memcached_cloud = cloud;
		in = p.createReader("C:\\Users\\Anish\\git\\SBS\\Brow Shooter\\src\\data\\brow_motion.txt");
		String pstr;
		try {
			pstr = in.readLine();
			while (pstr != null) {
				String[] chunks = pstr.split(" ");
				int[] step = {(int) ((Integer.parseInt(chunks[0])/400f)*width), (int) ((Integer.parseInt(chunks[1])/600f)*height)};
				steps.add(step);
				pstr = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		bottom = new ArrayList<FBox>();
		top = new ArrayList<FBox>();

	}

	public void nonComputeDraw() {
		right_brow = new FBox(10,10);
		right_brow.attachImage(right_browImg);
		right_brow.setPosition(((194+8+10+3)/400f)*width, (410f/600)*height);
		right_brow.setStatic(true);

		left_brow = new FBox(10,10);
		left_brow.attachImage(left_browImg);
		left_brow.setPosition(((194-8+10+3)/400f)*width, (410f/600)*height);
		left_brow.setStatic(true);

		flame_one = new FBox(10,10);
		flame_one.attachImage(flame_oneImg);
		flame_one.setPosition(((184+3)/400f)*width, (405f/600)*height);
		flame_one.setStatic(true);
		prevXPos = flame_one.getX();
		prevYPos = flame_one.getY();

		flame_two = new FBox(10,10);
		flame_two.attachImage(flame_oneImg);
		flame_two.setPosition(((207+3)/400f)*width, (405f/600)*height);
		flame_two.setStatic(true);

		bgXPos = -0.25f*width;
		bgYPos = -0.25f*height;
		backYPos = -height;
		cloudXPos = 0;
		cloudYPos = 0;
		hor_speed = (7f/400)*width;
		vert_speed = (7f/600)*height;

		// world configuration
		world = new FWorld();
		world.setGrabbable(false);
		world.setGravity(0, 0);
		world.add(left_brow);
		world.add(right_brow);
		world.add(flame_one);
		world.add(flame_two);
		//world.add(bar);		
	}

	public boolean isReady() {
		return top != null;
	}

	public void draw() {	
		handleScreenMotion();
		p.background(120);		
		p.image(bg, bgXPos, bgYPos+(0/600f)*height);		
		p.image(bg, bgXPos, backYPos-(450/600f)*height);
		PImage[] clouds = {cloud, cloud2, cloud3, cloud4, cloud5, cloud6};
		if (frameCount % 150 == 60) {
			cloudXPos = randInt((int)((20f/400)*width), (int)((380f/400)*width));
			cloudYPos = (-250f/600)*height;
			int rand = randInt(0, clouds.length-1);
			p.image(clouds[rand], cloudXPos, cloudYPos);
			cached_cloud = clouds[rand];
		}
		else if (frameCount % 80 == 42) {
			fastCloudX = randInt((int)((40f/400)*width), (int)((360f/400)*width));
			fastCloudY = (-250f/600)*height;
			int rand = randInt(0, clouds.length-1);
			p.image(clouds[rand], cloudXPos, cloudYPos);
			memcached_cloud = clouds[rand];
		}
		else {
			p.image(cached_cloud, cloudXPos, cloudYPos);
			p.image(memcached_cloud, fastCloudX, fastCloudY);
		}

		mussleFire();

		handleShooterMovement();
		handleCollisions();
		handleBulletMovement();
		if (frameCount == 15) {
			addMoreStraightLineVertical(1, 0, (100f/400)*width, -83);
			handleStraightLineVertical(100, 4f);
		}
		else if (frameCount > 15 && frameCount <= 350) {
			handleStraightLineVertical(90, 4f);
		}
		else if (frameCount == 375) {
			clearScissors();
			addMoreStraightLineVertical(1, 0, (250f/400)*width, -83);
			handleStraightLineVertical(200, 4f);
		}
		else if (frameCount > 375 && frameCount <= 695) {
			handleStraightLineVertical(200, 4f);
		}
		else if (frameCount == 715) {
			clearScissors();
			addMoreCurveLeftTop(1, (550f/400)*width, (400f/600)*height, 0);
			handleCurveLeftTop(8f, 2f);
		}
		else if (frameCount > 715 && frameCount <= 915) {
			handleCurveLeftTop(8f, 2f);
		}
		else if (frameCount == 950) {
			clearScissors();
			addSnake((200f/400)*width, (-250f/600)*height);
			handleSnake();
		}
		else if (frameCount > 950 && frameCount <= 1600) {	
			handleSnake();
		}
		else {
			clearScissors();
		}
		/*if (frameCount % 250 == 0) {
			for (int i = 0; i < bottom.size(); i++) {
				world.remove(bottom.get(i));
				world.remove(top.get(i));				
			}
			bottom.clear();
			top.clear();
			addMore(1, (550f/400)*width, (400f/600)*height, 0);
			handleScissorMotion();
		}
		else {			
			handleScissorMotion();
		}*/

		world.draw();
		world.step();
		mussle_num++;
		frameCount++;

	}

	public void clearScissors() {
		deploymentNum = 0;
		currentNum = 0;
		for (int i = 0; i < bottom.size(); i++) {
			world.remove(bottom.get(i));
			world.remove(top.get(i));
		}
		bottom.clear();			
		top.clear();
	}

	public void handleCollisions() {
		for (int x = bullets.size()-1; x >= 0; x--) {
			FCircle bullet = bullets.get(x);
			for (int i = bottom.size()-1; i >= 0; i--) {
				FBox scissor = bottom.get(i);
				if (collision(bullet, scissor)) {
					int currentHealth = Integer.parseInt(scissor.getName());
					currentHealth--;
					if (currentHealth == 0) {
						world.remove(scissor);
						world.remove(top.get(i));
						bottom.remove(i);
						top.remove(i);
					}
					else {
						scissor.setName(currentHealth+"");
						//top.get(i).setName(currentHealth+"");
					}
					world.remove(bullet);
					bullets.remove(x);
					//System.out.println("collided");
				}
			}
		}

	}

	public boolean collision(FCircle bullet, FBox scissor) {
		// bullet mapping
		float[] topLeftBullet = {bullet.getX()+((210-209.3f)/400f)*width, bullet.getY()+((472-281.4f)/600f)*height};

		float[] bottomRightBullet = {bullet.getX()+((215-209.3f)/400f)*width, bullet.getY()+((491-281.4f)/600f)*height};

		float[] topRightBullet = {bullet.getX()+((215-209.3f)/400f)*width, bullet.getY()+((472-281.4f)/600f)*height};

		float[] bottomLeftBullet = {bullet.getX()+((210-209.3f)/400f)*width, bullet.getY()+((491-281.4f)/600f)*height};

		float[] bottomLeftScissor = null, topLeftScissor = null, bottomRightScissor = null, topRightScissor = null;
		// scissor mapping
		float angle = p.degrees(scissor.getRotation());
		if (angle < 0) angle += 360f;
		if (angle > 0 && angle < 90) { // case 1
			bottomRightScissor = new float[] {scissor.getX()+((215-200)/400f)*width, scissor.getY()+((230-200)/600f)*height};

			bottomLeftScissor = new float[] {scissor.getX()+((235-200)/400f)*width, scissor.getY()+((215-200)/600f)*height};

			topLeftScissor = new float[] {scissor.getX()+((169-200)/400f)*width, scissor.getY()+((171-200)/600f)*height};

			topRightScissor = new float[] {scissor.getX()+((163-200)/400f)*width, scissor.getY()+((179-200)/600f)*height};
		}
		else if (Math.abs(angle) <= 10) { // case 2
			bottomRightScissor = new float[] {scissor.getX()+((232-200)/400f)*width, scissor.getY()+((207-200)/600f)*height};

			bottomLeftScissor = new float[] {scissor.getX()+((242-200)/400f)*width, scissor.getY()+((184-200)/600f)*height};

			topLeftScissor = new float[] {scissor.getX()+((159-200)/400f)*width, scissor.getY()+((200-200)/600f)*height};

			topRightScissor = new float[] {scissor.getX()+((160-200)/400f)*width, scissor.getY()+((209-200)/600f)*height};
		}
		else if (Math.abs(90-angle) <= 10) { // case 3
			bottomRightScissor = new float[] {scissor.getX()+((187-200)/400f)*width, scissor.getY()+((229-200)/600f)*height};

			bottomLeftScissor = new float[] {scissor.getX()+((214-200)/400f)*width, scissor.getY()+((241-200)/600f)*height};

			topLeftScissor = new float[] {scissor.getX()+((198-200)/400f)*width, scissor.getY()+((159-200)/600f)*height};

			topRightScissor = new float[] {scissor.getX()+((188-200)/400f)*width, scissor.getY()+((158-200)/600f)*height};
		}
		else if (angle > 90 && angle < 180) { // case 4
			bottomRightScissor = new float[] {scissor.getX()+((170-200)/400f)*width, scissor.getY()+((212-200)/600f)*height};

			bottomLeftScissor = new float[] {scissor.getX()+((179-200)/400f)*width, scissor.getY()+((239-200)/600f)*height};

			topLeftScissor = new float[] {scissor.getX()+((226-200)/400f)*width, scissor.getY()+((169-200)/600f)*height};

			topRightScissor = new float[] {scissor.getX()+((219-200)/400f)*width, scissor.getY()+((163-200)/600f)*height};
		}
		else if (Math.abs(angle-180) <= 10) { //case 5
			bottomRightScissor = new float[] {scissor.getX()+((167-200)/400f)*width, scissor.getY()+((188-200)/600f)*height};

			bottomLeftScissor = new float[] {scissor.getX()+((153-200)/400f)*width, scissor.getY()+((209-200)/600f)*height};

			topLeftScissor = new float[] {scissor.getX()+((236-200)/400f)*width, scissor.getY()+((197-200)/600f)*height};

			topRightScissor = new float[] {scissor.getX()+((236-200)/400f)*width, scissor.getY()+((189-200)/600f)*height};
		}
		else if (angle > 180 && angle < 270) { // case 6
			bottomRightScissor = new float[] {scissor.getX()+((184-200)/400f)*width, scissor.getY()+((168-200)/600f)*height};

			bottomLeftScissor = new float[] {scissor.getX()+((158-200)/400f)*width, scissor.getY()+((177-200)/600f)*height};

			topLeftScissor = new float[] {scissor.getX()+((227-200)/400f)*width, scissor.getY()+((225-200)/600f)*height};

			topRightScissor = new float[] {scissor.getX()+((235-200)/400f)*width, scissor.getY()+((218-200)/600f)*height};
		}
		else if (Math.abs(angle-270) <= 10) { // case 7
			bottomRightScissor = new float[] {scissor.getX()+((211-200)/400f)*width, scissor.getY()+((166-200)/600f)*height};

			bottomLeftScissor = new float[] {scissor.getX()+((183-200)/400f)*width, scissor.getY()+((154-200)/600f)*height};

			topLeftScissor = new float[] {scissor.getX()+((203-200)/400f)*width, scissor.getY()+((237-200)/600f)*height};

			topRightScissor = new float[] {scissor.getX()+((211-200)/400f)*width, scissor.getY()+((237-200)/600f)*height};
		}
		else { // case 8
			bottomRightScissor = new float[] {scissor.getX()+((229-200)/400f)*width, scissor.getY()+((184-200)/600f)*height};

			bottomLeftScissor = new float[] {scissor.getX()+((218-200)/400f)*width, scissor.getY()+((158-200)/600f)*height};

			topLeftScissor = new float[] {scissor.getX()+((174-200)/400f)*width, scissor.getY()+((227-200)/600f)*height};

			topRightScissor = new float[] {scissor.getX()+((178-200)/400f)*width, scissor.getY()+((232-200)/600f)*height};
		}

		float[][] polyBullet = {topLeftBullet, bottomRightBullet, topRightBullet, bottomLeftBullet};

		float[][] polyScissor = {bottomRightScissor, bottomLeftScissor, topLeftScissor, topRightScissor};

		return isPolygonsIntersecting(polyBullet, polyScissor);
	}

	public boolean isPolygonsIntersecting(float[][] polyBullet, float[][] polyScissor)
	{
		for (int x=0; x<2; x++)
		{
			float[][] polygon = (x==0) ? polyBullet : polyScissor;

			for (int i1=0; i1<polygon.length; i1++)
			{
				int   i2 = (i1 + 1) % polygon.length;
				float[] p1 = polygon[i1];
				float[] p2 = polygon[i2];

				float[] normal = {p2[1] - p1[1], p1[0] - p2[0]};

				double minA = Double.MAX_VALUE;
				double maxA = Double.MIN_VALUE;

				for (float[] p : polyBullet)
				{
					double projected = normal[0] * p[0] + normal[1] * p[1];

					if (projected < minA)
						minA = projected;
					if (projected > maxA)
						maxA = projected;
				}

				double minB = Double.MAX_VALUE;
				double maxB = Double.MIN_VALUE;

				for (float[] p : polyScissor)
				{
					double projected = normal[0] * p[0] + normal[1] * p[1];

					if (projected < minB)
						minB = projected;
					if (projected > maxB)
						maxB = projected;
				}

				if (maxA < minB || maxB < minA)
					return false;
			}
		}

		return true;
	}

	public void addMoreCurveLeftTop(int num, float xCounter, float y, int rotation) {
		for (int i = 0; i < num; i++) {			
			FBox bottomTemp = new FBox(10, 10);
			bottomTemp.setStatic(true);
			bottomTemp.attachImage(bottomImg);
			bottomTemp.setPosition(xCounter, y);
			bottomTemp.setRotation(p.radians(rotation));
			bottomTemp.setName("1");

			FBox topTemp = new FBox(10, 10);
			topTemp.setStatic(true);
			topTemp.attachImage(topImg);
			topTemp.setPosition(xCounter, y);
			topTemp.setRotation(p.radians(rotation));
			bottomTemp.setName("1");

			bottom.add(bottomTemp);
			top.add(topTemp);

			world.add(bottomTemp);
			world.add(topTemp);
			xCounter += (100f/400)*width;
		}
	}

	public void addMoreStraightLineVertical(int num, float yCounter, float x, int rotation) {
		for (int i = 0; i < num; i++) {			
			FBox bottomTemp = new FBox(10, 10);
			bottomTemp.setStatic(true);
			bottomTemp.attachImage(bottomImg);
			bottomTemp.setPosition(x, yCounter);
			bottomTemp.setRotation(p.radians(rotation));
			bottomTemp.setName("2");

			FBox topTemp = new FBox(10, 10);
			topTemp.setStatic(true);
			topTemp.attachImage(topImg);
			topTemp.setPosition(x, yCounter);
			topTemp.setRotation(p.radians(rotation));
			topTemp.setName("2");

			bottom.add(bottomTemp);
			top.add(topTemp);

			world.add(bottomTemp);
			world.add(topTemp);
			yCounter -= (100f/600)*height;
		}
	}

	public void handleSnake() {
		//System.out.println(bottom);
		final int OFFSET = 43;
		for (int i = 0; i < bottom.size(); i++) {
			int offset = OFFSET*i;
			if (currentNum-offset >= steps.size()) continue;			
			if (currentNum >= offset)
				follow(top.get(i), bottom.get(i), steps.get(currentNum-offset)[0], steps.get(currentNum-offset)[1], (3f/400)*width, (3f/600)*height);			
		}

		currentNum++;

		if (bottom.size() > 0 && deploymentNum < 5) {
			addSnake((200f/400)*width, (-250f/600)*height);
			deploymentNum++;
		}
	}

	public void addSnake(float x, float y) {
		FBox bottomTemp = new FBox(10, 10);
		bottomTemp.setStatic(true);
		bottomTemp.attachImage(bottomImg);
		bottomTemp.setPosition(x, y);
		bottomTemp.setName("15");

		FBox topTemp = new FBox(10, 10);
		topTemp.setStatic(true);
		topTemp.attachImage(topImg);
		topTemp.setPosition(x, y);
		topTemp.setName("15");

		bottom.add(bottomTemp);
		top.add(topTemp);

		world.add(bottomTemp);
		world.add(topTemp);
	}

	public void follow(FBox top, FBox bottom, float a, float b, float speedX, float speedY) {
		float m = (top.getY() - b)/(top.getX() - a);
		float theta = p.degrees(p.atan(m));
		if (theta < 0) {
			theta += 180;			
		}
		if (b < top.getY()) {
			theta += 180;
		}		
		float comp_x = speedX*p.cos(p.radians(theta));
		float comp_y = speedY*p.sin(p.radians(theta));
		top.setPosition(top.getX() + comp_x, top.getY() + comp_y);
		bottom.setPosition(bottom.getX() + comp_x, bottom.getY() + comp_y);
		theta -= 170;
		bottom.setRotation(p.radians(theta));
		top.setRotation(p.radians(theta));
	}

	public static int randInt(int min, int max) {
		int randomNum = random.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public void handleStraightLineVertical(float x, float raw_speed) {
		float speed = (raw_speed/600)*height;
		for (int i = 0; i < bottom.size(); i++) {
			top.get(i).setPosition(top.get(i).getX(), top.get(i).getY()+speed);
			bottom.get(i).setPosition(bottom.get(i).getX(), bottom.get(i).getY()+speed);
		}

		if (bottom.size() > 0 && deploymentNum < 8 && bottom.get(bottom.size()-1).getY() >= 0) {
			addMoreStraightLineVertical(1, (-100f/600)*height, (x/400)*width, -83);
			deploymentNum++;
		}
	}

	public void handleCurveLeftTop(float raw_horspeed, float raw_vertspeed) {
		float hor_speed = (raw_horspeed/400)*width;
		float vert_speed = (raw_vertspeed/600)*height;

		for (int i = 0; i < bottom.size(); i++) {			
			top.get(i).setPosition(top.get(i).getX() - hor_speed, top.get(i).getY() - vert_speed);
			bottom.get(i).setPosition(bottom.get(i).getX() - hor_speed, bottom.get(i).getY() - vert_speed);

			top.get(i).setRotation(top.get(i).getRotation() + p.radians(0.8f));
			bottom.get(i).setRotation(bottom.get(i).getRotation() + p.radians(0.8f));
			hor_speed -= (0.85f/400)*width;
			vert_speed += (2f/600)*height;
		}

		if (bottom.size() > 0 && deploymentNum < 8 && bottom.size() < 8 && bottom.get(bottom.size()-1).getX() >= 0) {
			addMoreCurveLeftTop(1, (550f/400)*width, (400f/600)*height, 0);
			deploymentNum++;
		}
	}

	public void handleBulletMovement() {
		for (int i = bullets.size()-1; i >= 0; i--) {
			FCircle c = bullets.get(i);
			c.setPosition(c.getX(), c.getY()-(8f/600)*height);
			float offset = (180f/600)*height;
			if (c.getX() < 0 || c.getX() > width || c.getY() < -offset || c.getY() > height-offset) {
				bullets.remove(i);
				world.remove(c);
			}
		}
	}

	public void handleScreenMotion() {
		float x = flame_one.getX();
		float y = flame_one.getY();
		float scale = (1f/600)*height;
		float cloudscale = (3.2f/600)*height;

		float x_scale = scale/6;

		if (prevXPos <= x) {
			bgXPos -= (x-prevXPos)*x_scale;
			cloudXPos -= (x-prevXPos)*x_scale;
		}		
		else if (prevXPos >= x) {
			bgXPos += (prevXPos-x)*x_scale;
			cloudXPos += (prevXPos-x)*x_scale;
		}

		float bgOffset = (450f/600)*height;
		if (switchBg && Math.abs(backYPos-(-0.25f*height+bgOffset)) <= 2*scale) {
			bgYPos = -height+bgOffset;
			switchBg = false;
		}
		if (!switchBg && Math.abs(bgYPos-(-0.25f*height)) <= 2*scale) {
			backYPos = -height;
			switchBg = true;
		}
		bgYPos += 4*scale;
		backYPos += 4*scale;
		cloudYPos += 2*cloudscale;
		fastCloudY += 10*cloudscale;

		prevXPos = x;
		prevYPos = y;
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

		p.strokeWeight(1);
		PImage temp2 = flame_twoImg;
		PImage temp1 = flame_oneImg;
		FCircle bullet = null;
		if (frameCount % 10 == 0) {
			temp2.resize(width/3, height/3);
			flame_two.attachImage(temp2);
			bullet = makeBall(flame_two.getX()-(2f/400)*width, flame_two.getY()-(126f/600)*height);
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
			bullet = makeBall(flame_one.getX()-(2f/400)*width, flame_one.getY()-(126f/600)*height);
			world.add(bullet);
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

	public FCircle makeBall(float xPos, float yPos) {
		FCircle b = new FCircle(15);
		b.setPosition(xPos, yPos);
		b.setNoStroke();
		b.setStatic(true);
		b.attachImage(bullets1);
		return b;
	}

	public void transformBrow() {

		world.remove(right_brow);
		world.remove(left_brow);

		if (isTransformed) {
			right_brow = new FBox(10,10);
			right_brow.attachImage(right_browImg);
			right_brow.setPosition(flame_one.getX()+(28f/400)*width, flame_one.getY()+(5f/600)*height);
			right_brow.setStatic(true);

			left_brow = new FBox(10,10);
			left_brow.attachImage(left_browImg);
			left_brow.setPosition(flame_two.getX()-(11f/400)*width, flame_two.getY()+(5f/600)*height);
			left_brow.setStatic(true);
		}
		else {
			right_brow = new FBox(10,10);
			right_brow.attachImage(right_browImg);
			right_brow.setPosition(flame_one.getX()+(105f/400)*width, flame_one.getY()+(45f/600)*height);
			right_brow.setRotation(p.radians(60));
			right_brow.setStatic(true);

			left_brow = new FBox(10,10);
			left_brow.attachImage(left_browImg);
			left_brow.setPosition(flame_two.getX()-(96f/400)*width, flame_two.getY()+(33f/600)*height);
			left_brow.setRotation(p.radians(-60));
			left_brow.setStatic(true);			
		}

		world.add(left_brow);
		world.add(right_brow);

	}


	public void handleShooterMovement() {

		/*float fX = flame_one.getX();
		float fY = flame_one.getY();
		flame_one.setPosition(mouseX + (-8f/400)*width, mouseY + (-82f/600)*height);
		float deltaX = flame_one.getX() - fX;
		float deltaY = flame_one.getY() - fY;
		left_brow.setPosition(left_brow.getX() + deltaX, left_brow.getY() + deltaY);
		right_brow.setPosition(right_brow.getX() + deltaX, right_brow.getY() + deltaY);		
		flame_two.setPosition(flame_two.getX() + deltaX, flame_two.getY() + deltaY);*/

		// physical position of flame one
		float fx = flame_one.getX() + (8f/400)*width;
		float fy = flame_one.getY() + (85f/600)*height;
		float deltax = p.mouseX - fx;
		float deltay = p.mouseY - fy;
		if (Math.abs(deltax) > 0.1 && Math.abs(deltay) > 0.1) {
			float y = (deltay/5f);
			//float x = (Math.abs((y*deltax)/deltay) * deltax)/(-deltax);	
			float x = deltax/5f;
			flame_one.setPosition(flame_one.getX() + x, flame_one.getY() + y);
			left_brow.setPosition(left_brow.getX() + x, left_brow.getY() + y);
			right_brow.setPosition(right_brow.getX() + x, right_brow.getY() + y);		
			flame_two.setPosition(flame_two.getX() + x, flame_two.getY() + y);
		}

		/*if(left && !right) {
			//System.out.println("left");
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
		}*/

	}

	public void keyReleased()
	{
		if (p.key == p.CODED)
		{
			if (p.keyCode == p.LEFT)
			{
				left = false;
			}
			if (p.keyCode == p.RIGHT)
			{
				right = false;
			}
			if(p.keyCode == p.UP)
			{
				up = false;
			}
			if(p.keyCode == p.DOWN)
			{
				down = false;
			}

		}
	}

	public void keyPressed()
	{
		if (p.key == p.CODED)
		{
			if (p.keyCode == p.LEFT)
			{
				left = true;
			}
			if (p.keyCode == p.RIGHT)
			{
				right = true;
			}
			if(p.keyCode == p.UP)
			{
				up = true;
			}
			if(p.keyCode == p.DOWN)
			{
				down = true;
			}

		}
	}

	public void mousePressed() {
		/*System.out.println("Mouse "+mouseX+" "+mouseY);
		System.out.println("Flame one "+flame_one.getX()+" "+flame_one.getY());
		System.out.println("Flame two "+flame_two.getX()+" "+flame_two.getY());
		System.out.println("Top "+top.get(0).getX()+" "+top.get(0).getY()+" "+degrees(top.get(0).getRotation()));
		System.out.println("Bottom "+bottom.get(0).getX()+" "+bottom.get(0).getY()+" "+degrees(bottom.get(0).getRotation())+"\n");*/
		//System.out.println("Bullet 2last "+bullets.get(bullets.size()-2).getX()+" "+bullets.get(bullets.size()-2).getY());
		//System.out.println("Bullet 1last "+bullets.get(bullets.size()-1).getX()+" "+bullets.get(bullets.size()-1).getY()+"\n");
		//transformBrow();
		/*if (!isTransformed) {
			transformBrow();
			isTransformed = true;
		}
		else {
			transformBrow();
			isTransformed = false;
		}*/
	}

}