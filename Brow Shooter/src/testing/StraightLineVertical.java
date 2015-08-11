package testing;

import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;
import fisica.FBox;
import fisica.FPoly;
import fisica.FWorld;
import fisica.Fisica;

public class StraightLineVertical extends PApplet {

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
		
		addMore(1, 0, 150, -83);

	}

	public void draw() {		
		background(0);
		int speed = 5;
		for (int i = 0; i < bottom.size(); i++) {
			top.get(i).setPosition(top.get(i).getX(), top.get(i).getY()+speed);
			bottom.get(i).setPosition(bottom.get(i).getX(), bottom.get(i).getY()+speed);
		}
		
		if (bottom.size() < 10 && bottom.get(bottom.size()-1).getY() >= 0) {
			addMore(1, -100, 150, -83);
		}

		world.draw();
		world.step();
	}
	
	public void addMore(int num, int yCounter, int x, int rotation) {
		for (int i = 0; i < num; i++) {			
			FBox bottomTemp = new FBox(10, 10);
			bottomTemp.setStatic(true);
			bottomTemp.attachImage(bottomImg);
			bottomTemp.setPosition(x, yCounter);
			bottomTemp.setRotation(radians(rotation));

			FBox topTemp = new FBox(10, 10);
			topTemp.setStatic(true);
			topTemp.attachImage(topImg);
			topTemp.setPosition(x, yCounter);
			topTemp.setRotation(radians(rotation));
			bottom.add(bottomTemp);
			top.add(topTemp);
			
			world.add(bottomTemp);
			world.add(topTemp);
			yCounter -= 100;
		}
	}



}
