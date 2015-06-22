package testing;
import processing.core.PApplet;
import fisica.FBox;
import fisica.FCircle;
import fisica.FWorld;
import fisica.Fisica;


public class ShooterTesting extends PApplet {

	private FWorld world;
	private FBox shooter;
	private FCircle ball;
	private float velocity = -1000f;
	private boolean left, right;
	private int speed = 5;

	public void setup() {
		size(500, 550);
		background(0);
		smooth();
		Fisica.init(this);
		world = new FWorld();
		world.setGravity(0, 0);
		shooter = new FBox(240,50);
		shooter.setPosition(260, 500);
		world.add(shooter);
		ball = makeBall();
		world.add(ball);

	}

	public void draw() {
		background(0);
		strokeWeight(1);
		if (frameCount % 10 == 0) {
			world.add(makeBall((int) shooter.getX()));
		}

		noStroke();
		handleShooterMovement();
		world.draw();
		world.step();

	}

	public FCircle makeBall(int xPos) {
		FCircle b = new FCircle(15);
		b.setPosition(xPos, 450);
		b.setForce(-9.8f, -9.8f);
		b.setNoStroke();
		b.setVelocity(0f, velocity);
		return b;
	}
	
	public FCircle makeBall() {
		FCircle b = new FCircle(15);
		b.setPosition(250, 450);
		b.setForce(-9.8f, -9.8f);
		b.setNoStroke();
		b.setVelocity(0f, velocity);
		return b;
	}

	private void handleShooterMovement() {
		if(left & !right && !(shooter.getX() < 0))
			shooter.setPosition(shooter.getX() - speed, shooter.getY());
		if(right && ! left && !(shooter.getX() > width))
			shooter.setPosition(shooter.getX() + speed, shooter.getY());
	}

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

}
