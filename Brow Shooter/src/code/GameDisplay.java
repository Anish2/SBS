package code;

import fisica.FCircle;
import fisica.FWorld;
import fisica.Fisica;
import processing.core.PApplet;
import processing.core.PImage;

public class GameDisplay extends PApplet {

	private PImage left_brow, right_brow, flame_one, flame_two, flame_three, flame_four;
	private PImage bullets1, bullets2, bullets3, bullets4, bullets5;
	private FWorld world;
	private float velocity = -700f;
	private boolean left, right;
	private int mussle_num = 0;

	public void setup() {
		size(400, 600);
		background(0);
		left_brow = loadImage("Brow Fighter default left brow.png");
		right_brow = loadImage("Brow Fighter default right brow.png");
		flame_one = loadImage("1.png");
		flame_two = loadImage("2.png");
		flame_three = loadImage("3.png");
		flame_four = loadImage("4.png");
		bullets1 = loadImage("bullets1.png");
		bullets2 = loadImage("bullets2.png");
		bullets3 = loadImage("bullets3.png");
		bullets4 = loadImage("bullets4.png");
		bullets5 = loadImage("bullets5.png");
		

		left_brow.resize(width/2, height/2);
		right_brow.resize(width/2, height/2);
		flame_one.resize(width, height);
		flame_two.resize(width, height);
		flame_three.resize(width, height);
		flame_four.resize(width, height);
		
		bullets1.resize(width, height);
		bullets2.resize(width, height);
		bullets3.resize(width, height);
		bullets4.resize(width, height);
		bullets5.resize(width, height);

		smooth();
		Fisica.init(this);
		world = new FWorld();
		world.setGravity(0, 0);
	}

	public void draw() {
		background(0);
		int num = mussle_num % 4;
		if (mussle_num % 3 == 0) {
			int yPosMussle = -70;
			switch (num) {
			case 0:
				image(flame_one, 0, yPosMussle);
				break;
			case 1:
				image(flame_two, 0, yPosMussle);
				break;
			case 2:
				image(flame_three, 0, yPosMussle);
				break;
			case 3:
				image(flame_four, 0, yPosMussle);
				break;
			}
		}
		image(left_brow, 97, 260);
		image(right_brow, 97, 260);

		strokeWeight(1);
		if (frameCount % 10 == 0) {
			world.add(makeBall(200));
		}

		noStroke();
		//handleShooterMovement();
		world.draw();
		world.step();
		mussle_num++;

	}

	public FCircle makeBall(int xPos) {
		FCircle b = new FCircle(15);
		b.setPosition(xPos, 295);
		b.setForce(-9.8f, -9.8f);
		b.setNoStroke();
		b.setVelocity(0f, velocity);
		b.attachImage(bullets1);
		return b;
	}

	/*private void handleShooterMovement() {
		if(left & !right)	
			shooter.setPosition(shooter.getX() - speed, shooter.getY());
		if(right && ! left)
			shooter.setPosition(shooter.getX() + speed, shooter.getY());
	}*/

	public void keyReleased()
	{
		if (key == CODED)
		{
			if (keyCode == LEFT)
			{
				left = false;
			}
			if (keyCode == RIGHT)
			{
				right = false;
			}
		}
	}

	public void keyPressed()
	{
		if (key == CODED)
		{
			if (keyCode == LEFT)
			{
				left = true;
			}
			if (keyCode == RIGHT)
			{
				right = true;
			}
		}
	}

	public void mousePressed() {
		System.out.println(mouseX+" "+mouseY);
	}

}
