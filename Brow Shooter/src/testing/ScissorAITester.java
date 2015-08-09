package testing;

import processing.core.PApplet;
import processing.core.PImage;
import fisica.FBox;
import fisica.FPoly;
import fisica.FWorld;
import fisica.Fisica;

public class ScissorAITester extends PApplet {

	private FWorld world;
	private PImage bottomImg, topImg;
	private FBox bottom, top;
	private boolean gradRotComplete = false;

	public void setup() {
		size(400, 600);
		bottomImg = loadImage("brow_scissors/1.png");
		topImg = loadImage("brow_scissors/2.png");
		
		bottomImg.resize(width/4, height/4);
		topImg.resize(width/4,  height/4);
		Fisica.init(this);
		
		bottom = new FBox(10, 10);
		bottom.setStatic(true);
		bottom.attachImage(bottomImg);
		bottom.setPosition(200, 0);
		
		top = new FBox(10, 10);
		top.setStatic(true);
		top.attachImage(topImg);
		top.setPosition(200, 0);
		
		world = new FWorld();
		world.setGravity(0, 0);
		world.add(bottom);
		world.add(top);
	}

	public void draw() {		
		background(0);

		if (!gradRotComplete) {
			gradualRotation(radians(360), 5f);
		}
		else {
			bottom.setPosition(bottom.getX()-5, bottom.getY());
			top.setPosition(top.getX()-5, top.getY());
		}
		
		/*top.setRotation(radians(-17));
		bottom.setRotation(radians(-17));
		bottom.setPosition(bottom.getX()-7, bottom.getY()+7);
		top.setPosition(top.getX()-7, top.getY()+7);*/
		world.draw();
		world.step();
	}
	
	
	public boolean gradualRotation(float angle, float speed) {
		float current = bottom.getRotation();
		if (Math.abs(current-angle) <= 0.1) {
			gradRotComplete = true;
			return true;
		}
		bottom.setRotation(bottom.getRotation()+radians(speed));
		top.setRotation(top.getRotation()+radians(speed));
		return false;
		
	}

}
