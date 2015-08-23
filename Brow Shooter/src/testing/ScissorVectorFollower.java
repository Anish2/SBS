package testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import processing.core.PApplet;
import processing.core.PImage;
import fisica.FBox;
import fisica.FWorld;
import fisica.Fisica;

public class ScissorVectorFollower extends PApplet {

	private FWorld world;
	private PImage bottomImg, topImg;
	private FBox sc, bottom, top;
	private BufferedReader in;
	private PrintStream out;
	private PrintWriter writer;
	private String p;

	public void setup() {
		Fisica.init(this);
		in = createReader("C:\\Users\\Anish\\git\\SBS\\Brow Shooter\\src\\data\\brow_motion.txt");
		//writer = createWriter("brow_motion.txt");
		//out = new PrintStream(new File("brow_motion.txt"));
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
		bottom.setPosition(200, -250);

		top = new FBox(10, 10);
		top.setStatic(true);
		top.attachImage(topImg);
		top.setPosition(200, -250);

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
		/*if (frameCount >= 0 && frameCount <= 150)
			follow(190, 219, 1);
		if (frameCount >= 150 && frameCount <= 250)
			follow(189, 247, 1);
		if (frameCount >= 250 && frameCount <= 350)
			follow(191, 184, 1);
		if (frameCount >= 350 && frameCount <= 450)
			follow(193, 326, 1);*/		
		try {
			p = in.readLine();
			if(p != null) {
				String[] input = p.split(" ");
				follow(Integer.parseInt(input[0]), Integer.parseInt(input[1]), 3);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//follow(mouseX, mouseY, 1);
		//writer.println(mouseX+" "+mouseY);

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

	public void mousePressed() {
		System.out.println(mouseX+" "+mouseY);
		writer.flush();
		writer.close();
		exit();
	}

}
