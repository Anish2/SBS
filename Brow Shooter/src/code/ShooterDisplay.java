package code;

import processing.core.PApplet;
import processing.core.PImage;

public class ShooterDisplay {

	private PImage base, title, start, options, hanger, leaderboard, left_brow, right_brow;
	private float tint = 0f;
	private float titleX, titleY;
	private float titleSpeed, menu_speed;
	private int startXPos, optionsXPos;
	private PApplet p;
	
	public ShooterDisplay(PApplet p) {
		this.p = p;
	}

	public void setup() {		
		p.size(400, 600, p.P2D);
		// size(displayWidth, displayHeight, P2D); // for android
		p.background(0);
		base = p.loadImage("Brow Fighte base.png");
		title = p.loadImage("Brow Fighter title.png");
		start = p.loadImage("Brow Fighter start.png");
		options = p.loadImage("Brow Fighter options second edition.png");
		hanger = p.loadImage("Brow Fighter hanger second edition.png");
		leaderboard = p.loadImage("Brow Fighterleaderboard second edition.png");
		right_brow = p.loadImage("Brow Fighter default right brow.png");
		left_brow = p.loadImage("Brow Fighter default left brow.png");
		
		base.resize(p.width, p.height);
		title.resize(p.width, p.height);
		start.resize(p.width, p.height);
		options.resize(p.width, p.height);
		hanger.resize(p.width, p.height);
		leaderboard.resize(p.width, p.height);
		right_brow.resize(p.width, p.height);
		left_brow.resize(p.width, p.height);

		titleX = 0;
		titleY = (-180f/600)*p.height;
		titleSpeed = (20f/600)*p.height;
		startXPos = -p.width/2;
		optionsXPos = p.width/2;
		menu_speed = (float) ((20.0/400)*p.width);
	}

	public void draw() {
		if (tint < 10)	{
			tint += 0.2;
			p.tint(255, tint);
			p.image(base, 0,0);
		}
		else {
			p.tint(255,255);
			p.image(base, 0,0);
			p.image(title, titleX, titleY);

			if (titleY < (10f/600)*p.height)
				titleY += titleSpeed;

			// slide buttons
			p.image(start, startXPos, 0); // from left
			p.image(options, startXPos, 0); // from right
			p.image(hanger, optionsXPos, 0);
			p.image(leaderboard, optionsXPos, 0);
			p.image(left_brow, startXPos, 0);
			p.image(right_brow, optionsXPos, 0);
			if (startXPos != 0 && optionsXPos != 0) {
				startXPos += menu_speed;
				optionsXPos -= menu_speed;
			}
		}

	}

}
