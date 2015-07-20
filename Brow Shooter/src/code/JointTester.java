package code;

import processing.core.PApplet;
import fisica.FCircle;
import fisica.FRevoluteJoint;
import fisica.FWorld;
import fisica.Fisica;

public class JointTester extends PApplet {
	
	private FWorld world;
	private FCircle one, two;

	public void setup() {
		Fisica.init(this);
		
		size(600, 600);
		
		world = new FWorld();
		world.setGravity(0,0);
		
		one = new FCircle(25);
		//one.setAllowSleeping(true);
		one.setPosition(200, 200);
		one.setStatic(true);
		
		two = new FCircle(12);
		//two.setAllowSleeping(true);
		two.setPosition(100, 100);
		two.setVelocity(500, 0);
		two.setRestitution(0);
		
		FRevoluteJoint joint = new FRevoluteJoint(one, two);
		joint.setAnchor(200f, 200f);
		joint.setEnableMotor(true);
		joint.setMotorSpeed(200f);
		joint.setEnableLimit(true);
		joint.setLowerAngle(radians(0));
		joint.setUpperAngle(radians(90));
		
		world.add(one);
		world.add(two);
		world.add(joint);
		
	}
	
	public void draw() {
		background(120);
		if (two.getX() >= 250)
			two.setStatic(true);
		world.draw();
		world.step();
	}

}
