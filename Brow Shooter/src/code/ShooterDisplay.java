package code;

import processing.core.PApplet;
import processing.core.PImage;

public class ShooterDisplay extends PApplet {

	private PImage base, title, start, options, hanger, leaderboard, left_brow, right_brow;
	private float tint = 0;
	private float titleX = width/2, titleY = -180;
	private float titleSpeed = 10f;
	private int startXPos = -200, optionsXPos = 200;

	public void setup() {
		size(400, 600);
		background(0);
		base = loadImage("Brow Fighte base.png");
		title = loadImage("Brow Fighter title.png");
		start = loadImage("Brow Fighter start.png");
		options = loadImage("Brow Fighter options second edition.png");
		hanger = loadImage("Brow Fighter hanger second edition.png");
		leaderboard = loadImage("Brow Fighterleaderboard second edition.png");
		right_brow = loadImage("Brow Fighter default right brow.png");
		left_brow = loadImage("Brow Fighter default left brow.png");
		//System.out.println(base.loaded);
		base.resize(width, height);
		title.resize(width, height);
		start.resize(width, height);
		options.resize(width, height);
		hanger.resize(width, height);
		leaderboard.resize(width, height);
		right_brow.resize(width, height);
		left_brow.resize(width, height);
	}

	public void draw() {
		if (tint < 10)	{
			tint += 0.08;
			tint(255, tint);
			image(base, 0,0);
		}
		else {
			tint(255,255);
			image(base, 0,0);
			image(title, titleX, titleY);

			if (titleY < 10)
				titleY += titleSpeed;

			// slide buttons
			image(start, startXPos, 0); // from left
			image(options, startXPos, 0); // from right
			image(hanger, optionsXPos, 0);
			image(leaderboard, optionsXPos, 0);
			image(left_brow, startXPos, 0);
			image(right_brow, optionsXPos, 0);
			if (startXPos != 0 && optionsXPos != 0) {
				startXPos += 10;
				optionsXPos -= 10;
			}
		}

	}

}
