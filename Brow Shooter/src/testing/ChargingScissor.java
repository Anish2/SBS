package testing;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import fisica.FBox;
import fisica.FWorld;
import fisica.Fisica;

public class ChargingScissor extends PApplet {

	private FWorld world;
	private PImage bottomImg, topImg;
	private ArrayList<FBox> bottom, top;
	private float speed = 2f;

	public void setup() {
		size(400, 600);
		bottomImg = loadImage("brow_scissors/1.png");
		topImg = loadImage("brow_scissors/2.png");

		bottomImg.resize(width/2, height/2);
		topImg.resize(width/2,  height/2);
		Fisica.init(this);

		bottom = new ArrayList<FBox>();
		top = new ArrayList<FBox>();
		
		world = new FWorld();
		world.setGravity(0, 0);
		
		
		addMore(1, 100, -10, -83);
	}

	public void draw() {
		background(0);
		
		//speed = 2;
		for (int i = 0; i < bottom.size(); i++) {
			top.get(i).setPosition(top.get(i).getX(), top.get(i).getY()+speed);
			bottom.get(i).setPosition(bottom.get(i).getX(), bottom.get(i).getY()+speed);
		}
		speed += 0.5f;
		
		/*if (bottom.size() < 10 && bottom.get(bottom.size()-1).getY() >= 0) {
			addMore(1);
		}*/

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
			xCounter += 100;
		}
	}



}
