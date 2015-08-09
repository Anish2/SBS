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
		
		int yCounter = 0;
		for (int i = 0; i < 5; i++) {
			
			FBox bottomTemp = new FBox(10, 10);
			bottomTemp.setStatic(true);
			bottomTemp.attachImage(bottomImg);
			bottomTemp.setPosition(150, yCounter);
			bottomTemp.setRotation(radians(-83));

			FBox topTemp = new FBox(10, 10);
			topTemp.setStatic(true);
			topTemp.attachImage(topImg);
			topTemp.setPosition(150, yCounter);
			topTemp.setRotation(radians(-83));
			bottom.add(bottomTemp);
			top.add(topTemp);
			
			world.add(bottomTemp);
			world.add(topTemp);
			yCounter -= 100;
		}
		

		/*bottom = new FBox(10, 10);
		bottom.setStatic(true);
		bottom.attachImage(bottomImg);
		bottom.setPosition(150, 0);
		bottom.setRotation(radians(-83));

		top = new FBox(10, 10);
		top.setStatic(true);
		top.attachImage(topImg);
		top.setPosition(150, 0);
		top.setRotation(radians(-83));*/

		
		/*world.add(bottom);
		world.add(top);*/
	}

	public void draw() {		
		background(0);
		int speed = 15;
		for (int i = 0; i < bottom.size(); i++) {
			top.get(i).setPosition(top.get(i).getX(), top.get(i).getY()+speed);
			bottom.get(i).setPosition(bottom.get(i).getX(), bottom.get(i).getY()+speed);
		}
		
		if (bottom.size() < 10 && bottom.get(bottom.size()-1).getY() >= 0) {
			addMore(1);
		}

		world.draw();
		world.step();
	}
	
	public void addMore(int num) {
		int yCounter = -100;
		for (int i = 0; i < num; i++) {
			
			FBox bottomTemp = new FBox(10, 10);
			bottomTemp.setStatic(true);
			bottomTemp.attachImage(bottomImg);
			bottomTemp.setPosition(150, yCounter);
			bottomTemp.setRotation(radians(-83));

			FBox topTemp = new FBox(10, 10);
			topTemp.setStatic(true);
			topTemp.attachImage(topImg);
			topTemp.setPosition(150, yCounter);
			topTemp.setRotation(radians(-83));
			bottom.add(bottomTemp);
			top.add(topTemp);
			
			world.add(bottomTemp);
			world.add(topTemp);
			yCounter -= 100;
		}
	}



}
