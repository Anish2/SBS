package testing;

import fisica.FBox;
import fisica.FWorld;
import fisica.Fisica;
import processing.core.PApplet;
import processing.core.PImage;

public class ScissorCollisionData extends PApplet {
	
	/*
	 * Case 1:
	 * C - (215, 230)
	 * D - (235, 215)
	 * B - (169, 171)	
	 * A - (163, 179)
	 * 
	 * Case 2:
	 * C - (232, 207)
	 * D - (242, 184)
	 * B - (159, 200)
	 * A - (160, 209)
	 * 
	 * Case 3:
	 * C - (187, 229)
	 * D - (214, 241)
	 * B - (198, 159)
	 * A - (188, 158)
	 * 
	 * Case 4:
	 * C - (170, 212)
	 * D - (179, 239)
	 * B - (226, 169)
	 * A - (219, 163)
	 * 
	 * Case 5:
	 * C - (167, 188)
	 * D - (153, 209)
	 * B - (236, 197)
	 * A - (236, 189)
	 * 
	 * Case 6:
	 * C - (184, 168)
	 * D - (158, 177)
	 * B - (227, 225)
	 * A - (235, 218)
	 * 
	 * Case 7:
	 * C - (211, 166)
	 * D - (183, 154)
	 * B - (203, 237)
	 * A - (211, 237)
	 * 
	 * Case 8:
	 * C - (229, 184)
	 * D - (218, 158)
	 * B - (174, 227)
	 * A - (178, 232)
	 */
	
	private PImage bottomImg, topImg;
	private FWorld world;
	
	public void setup() {
		background(0);
		size(400, 600);
		bottomImg = loadImage("brow_scissors1.png");
		topImg = loadImage("brow_scissors2.png");

		bottomImg.resize(width/4, height/4);
		topImg.resize(width/4,  height/4);
		Fisica.init(this);
		
		world = new FWorld();
		world.setGravity(0, 0);
		
		float angle = 315f;
		
		FBox bottomTemp = new FBox(10, 10);
		bottomTemp.setStatic(true);
		bottomTemp.attachImage(bottomImg);
		bottomTemp.setPosition(200, 200);
		bottomTemp.setRotation(radians(angle));

		FBox topTemp = new FBox(10, 10);
		topTemp.setStatic(true);
		topTemp.attachImage(topImg);
		topTemp.setPosition(200, 200);
		topTemp.setRotation(radians(angle));
		
		world.add(bottomTemp);
		world.add(topTemp);
		
		System.out.println(degrees(bottomTemp.getRotation()));
	}
	
	public void draw() {
		background(0);
		world.draw();
		world.step();
	}
	
	public void mousePressed() {
		System.out.println(mouseX+" "+mouseY);		
	}

}
