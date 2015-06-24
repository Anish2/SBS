package testing;
import processing.core.PApplet;
import processing.core.PImage;
import fisica.FBlob;
import fisica.FBody;
import fisica.FPoly;
import fisica.FWorld;
import fisica.Fisica;


public class ScissorRotationTester extends PApplet {

	private FWorld world;
	private PImage left, right;
	private FPoly blob;
	private int moving_counter = 0, y_counter = 0;
	private int rotation_left = 30, rotation_right = 0;
	private boolean rotate_left = true, rotate_right = true;


	public void setup() {
		size(500, 550);
		background(0);
		smooth();
		Fisica.init(this);
		world = new FWorld();
		//world.setGravity(0, 0);
		left = loadImage("L (1).png");
		right = loadImage("R (1).png");
		left.resize(width, height);
		right.resize(width, height);
		blob = new FPoly();
		//blob.attachImage(left);
		//blob.attachImage(right);
		//blob.attachImage(other);
		/*blob.setDrawable(true);
		blob.vertex(1,1);
		blob.vertex(1,100);
		blob.vertex(200,1);
		blob.setPosition(20 , 20);
		world.add(blob);*/

	}

	public void draw() {
		background(0);
		translate(50,0);
		//translate(moving_counter, y_counter);
		translate(85, 253);
		rotate(radians(rotation_left));
		translate(-85, -253);
		image(left, 0, 0);
		//rotate(radians(30));
		//translate(-moving_counter, -y_counter);
		translate(85, 253);
		rotate(radians(-rotation_left-rotation_right));
		translate(-85, -253);
		image(right, 0, 0);
		world.draw();
		world.step();
		moving_counter += 5;
		y_counter += 5;

		if (rotation_left == 0)
			rotate_left = false;
		else if (rotation_left == 30)
			rotate_left = true;
		if (rotate_left)
			rotation_left -= 2;
		else
			rotation_left += 2;

		if (rotation_right == -30)
			rotate_right = false;
		else if (rotation_right == 0)
			rotate_right = true;
		if (rotate_right)
			rotation_right -= 2;
		else
			rotation_right += 2;


	}

	public void mousePressed() {
		System.out.println(mouseX+" "+mouseY);
	}


}
