package testing;

import fisica.FBox;
import fisica.FWorld;
import fisica.Fisica;
import processing.core.PApplet;
import processing.core.PImage;

public class ScissorCollisionData extends PApplet {
	
	/*
	 * Case 2:
	 * C - (236, 271)
	 * D - (241, 245)
	 * B - (164, 262)
	 * A - (164, 271)
	 * 
	 * Case 3:
	 * C - (127, 236)
	 * D - (151, 243)
	 * B - (133, 163)
	 * A - (128, 164)
	 * 
	 * Case 4:
	 * C - (124, 169)
	 * D - (134, 195)
	 * B - (179, 128)
	 * A - (173, 124)
	 * 
	 * Case 5:
	 * C - (163, 126)
	 * D - (153, 148)
	 * B - (234, 135)
	 * A - (234, 127)
	 * 
	 * Case 6:
	 * C - (223, 122)
	 * D - (200, 132)
	 * B - (269, 177)
	 * A - (274, 173)
	 * 
	 * Case 7:
	 * C - (273, 163)
	 * D - (248, 152)
	 * B - (265, 233)
	 * A - (271, 234)
	 * 
	 * Case 8:
	 * C - (274, 225)
	 * D - (265, 201)
	 * B - (219, 269)
	 * A - (225, 273)
	 */
	
	private PImage bottomImg, topImg;
	private FWorld world;
	
	public void setup() {
		background(0);
		size(400, 600);
		bottomImg = loadImage("brow_scissors/1.png");
		topImg = loadImage("brow_scissors/2.png");

		bottomImg.resize(width/4, height/4);
		topImg.resize(width/4,  height/4);
		Fisica.init(this);
		
		world = new FWorld();
		world.setGravity(0, 0);
		
		FBox bottomTemp = new FBox(10, 10);
		bottomTemp.setStatic(true);
		bottomTemp.attachImage(bottomImg);
		bottomTemp.setPosition(200, 200);
		bottomTemp.setRotation(radians(315));

		FBox topTemp = new FBox(10, 10);
		topTemp.setStatic(true);
		topTemp.attachImage(topImg);
		topTemp.setPosition(200, 200);
		topTemp.setRotation(radians(315));
		
		world.add(bottomTemp);
		world.add(topTemp);
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
