package testing;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import fisica.FBox;
import fisica.FWorld;
import fisica.Fisica;

public class CurveRightBottom extends PApplet {

	private FWorld world;
	private PImage bottomImg, topImg;
	private ArrayList<FBox> bottom, top;

	public void setup() {
		size(400, 600);
		bottomImg = loadImage("brow_scissors/1.png");
		topImg = loadImage("brow_scissors/2.png");

		bottomImg.resize(width/4, height/4);
		topImg.resize(width/4,  height/4);
		Fisica.init(this);
		
		bottom = new ArrayList<FBox>();
		top = new ArrayList<FBox>();
		
		world = new FWorld();
		world.setGravity(0, 0);
		
		//addMore(5, 0, 150, 200);
		addMore(1, -100, 150, 200);
	}

	public void draw() {		
		background(0);
		float hor_speed = 15;
		float vert_speed = 2;
		for (int i = 0; i < bottom.size(); i++) {			
			top.get(i).setPosition(top.get(i).getX()+hor_speed, top.get(i).getY()+vert_speed);
			bottom.get(i).setPosition(bottom.get(i).getX()+hor_speed, bottom.get(i).getY()+vert_speed);
			
			top.get(i).setRotation(top.get(i).getRotation()+radians(1f));
			bottom.get(i).setRotation(bottom.get(i).getRotation()+radians(1f));
			hor_speed -= 0.75;
			vert_speed += 1;
		}
		
		if (bottom.size() < 10 && bottom.get(bottom.size()-1).getX() >= 0) {
			addMore(1, -100, 150, 200);
		}

		world.draw();
		world.step();
	}
	
	public void addMore(int num, int xCounter, int y, int rotation) {
		for (int i = 0; i < num; i++) {			
			FBox bottomTemp = new FBox(10, 10);
			bottomTemp.setStatic(true);
			bottomTemp.attachImage(bottomImg);
			bottomTemp.setPosition(xCounter, y);
			bottomTemp.setRotation(radians(rotation));

			FBox topTemp = new FBox(10, 10);
			topTemp.setStatic(true);
			topTemp.attachImage(topImg);
			topTemp.setPosition(xCounter, y);
			topTemp.setRotation(radians(rotation));
			bottom.add(bottomTemp);
			top.add(topTemp);
			
			world.add(bottomTemp);
			world.add(topTemp);
			xCounter -= 100;
		}
	}



}
