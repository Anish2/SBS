package testing;

import processing.core.PApplet;
import processing.core.PImage;
import fisica.FBox;
import fisica.FWorld;
import fisica.Fisica;

public class ScissorVectorFollower extends PApplet {
	
	private FWorld world;
	private PImage bottomImg, topImg;
	private FBox sc, bottom, top;
	
	public void setup() {
		Fisica.init(this);;
		size(400, 600);
		world = new FWorld();
		world.setGravity(0, 0);
		
		bottomImg = loadImage("brow_scissors1.png");
		topImg = loadImage("brow_scissors2.png");
		bottomImg.resize(width/4, height/4);
		topImg.resize(width/4,  height/4);
		
		bottom = new FBox(10, 10);
		bottom.setStatic(true);
		bottom.attachImage(bottomImg);
		bottom.setPosition(200, 200);
		
		top = new FBox(10, 10);
		top.setStatic(true);
		top.attachImage(topImg);
		top.setPosition(200, 200);
		
		world.add(bottom);
		world.add(top);
		sc = new FBox(10, 10);
		sc.setPosition(200, 200);
		sc.setStatic(true);
		//world.add(sc);
		
		
	}
	
	public void draw() {
		//System.out.println(degrees(sc.getRotation()));
		//sc.setRotation(sc.getRotation() + radians(1f));
		//bottom.setRotation(bottom.getRotation() + radians(1f));
		//top.setRotation(top.getRotation() + radians(1f));
		
		follow(mouseX, mouseY, 1);
		
		background(0);
		world.draw();
		world.step();
	}
	
	public void follow(float a, float b, float speed) {
		float m = (top.getY() - b)/(top.getX() - a);
		float theta = degrees(atan(m));
		if (theta < 0) {
			theta += 180;			
		}
		if (b < top.getY()) {
			theta += 180;
		}		
		float comp_x = speed*cos(radians(theta));
		float comp_y = speed*sin(radians(theta));
		top.setPosition(top.getX() + comp_x, top.getY() + comp_y);
		bottom.setPosition(bottom.getX() + comp_x, bottom.getY() + comp_y);
		theta -= 170;
		bottom.setRotation(radians(theta));
		top.setRotation(radians(theta));
	}

}
