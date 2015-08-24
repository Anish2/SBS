package code;

import processing.core.PApplet;
import processing.core.PImage;

public class HangerDisplay {
	
	private PImage bade, footing, heading;
	private float tint = 0f;
	private float titleX, titleY, footerX, footerY;
	private float titleSpeed, footerSpeed;
	private PApplet p;
	
	public HangerDisplay(PApplet p) {
		this.p = p;
	}
	
	public void setup() {
		p.size(400, 600, p.P2D);
		p.background(0);
		bade = p.loadImage("hanger_bade.png");
		footing = p.loadImage("hanger_footing.png");
		heading = p.loadImage("hanger_heading.png");
		int width = p.width, height = p.height;
		bade.resize(width, height);
		footing.resize(width, height);
		heading.resize(width, height);
		
		titleX = 0;
		titleY = (-180f/600)*height;
		titleSpeed = (20f/600)*height;
		
		footerX = 0;
		footerY = (180f/600)*height;
		footerSpeed = (-20f/600)*height;
		
	}
	
	public void draw() {
		if (tint < 10)	{
			tint += 0.2;
			p.tint(255, tint);
			p.image(bade, 0,0);
		}
		else {
			p.tint(255,255);
			p.image(bade, 0,0);
			p.image(heading, titleX, titleY);
			p.image(footing, footerX, footerY);

			if (titleY < 0)
				titleY += titleSpeed;
			if (footerY > 0)
				footerY += footerSpeed;
			
		}
	}
	
	public void mousePressed() {
		
	}

}
