package code;

import processing.core.PApplet;
import processing.core.PImage;

public class GameManager extends PApplet {

	private ShooterDisplay d;
	private GameDisplay gd;
	private HangerDisplay hg;
	private int state;
	private Point[] startBtn, hangerBtn;
	private PImage loading;

	public void setup() {
		startBtn = new Point[] {
				makeRelativePoint(110, 249),
				makeRelativePoint(124, 228),
				makeRelativePoint(280, 219),
				makeRelativePoint(306, 241),
				makeRelativePoint(280, 268),
				makeRelativePoint(133, 277)
		};
		hangerBtn = new Point[] {
				makeRelativePoint(105, 326),
				makeRelativePoint(134, 294),
				makeRelativePoint(278, 296),
				makeRelativePoint(303, 318),
				makeRelativePoint(291, 344),
				makeRelativePoint(144, 348)
		};
		state = 0;
		loading = loadImage("loading.png");			
		d = new ShooterDisplay(this);
		gd = new GameDisplay(this);
		hg = new HangerDisplay(this);
		d.setup();
	}

	public void draw() {
		if (state == 0)			
			d.draw();
		/*else if (state == 1 && !gd.isReady()) {
			background(0);
			image(loading, 0, 0);
		}*/
		else if (state == 1) {
			//gd.nonComputeDraw();
			gd.draw();
		}
		else if (state == 2)
			hg.draw();
	}

	public void mousePressed() {
		//System.out.println(mouseX+" "+mouseY);
		if (state == 0) {
			if (contains(startBtn, new Point(mouseX, mouseY))) {
				gd.setup();
				//thread("gameNormalSetup");
				state = 1;
			}
			else if (contains(hangerBtn, new Point(mouseX, mouseY))) {
				hg.setup();
				state = 2;
			}
		}
		else {
			if (state == 1) gd.mousePressed();
			else if (state == 2) hg.mousePressed();
		}
	}

	private void goBack() {
		state = 0;
	}

	public void onBackPressed() {
		goBack();
	}

	private boolean contains(Point[] points, Point test) {
		int i, j;
		boolean result = false;
		for (i = 0, j = points.length - 1; i < points.length; j = i++) {
			if ((points[i].y > test.y) != (points[j].y > test.y) &&
					(test.x < (points[j].x - points[i].x) * (test.y - points[i].y) / (points[j].y-points[i].y) + points[i].x)) {
				result = !result;
			}
		}
		return result;
	}

	private Point makeRelativePoint(int x, int y) {
		return new Point((x/400f)*width, (y/600f)*height);
	}

	private class Point {
		public float x, y;
		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}		
	}

}
