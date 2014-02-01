package main;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.ImageData;

import fractal.IFractal;
import fractal.Julia;
import fractal.Mandelbrot;
import fractal.pixel.PixelData;

public class FractalRenderer extends BasicGame {

	// Settings
	public final static int MANDELBROT_SET = 0;
	public final static int JULIA_SET = 1;
	private int fractalType;
	private double constantReal;
	private double constantImaginary;
	
	private int currentColorControl = RED;
	private final static int RED = 0;
	private final static int GREEN = 1;
	private final static int BLUE = 2;
	private int[] colorFactor;
	private boolean showText;
	
	// The set we're working with (Mandelbrot, Julia, etc)
	private IFractal set;
	 
	// Input
    private Input input;
    
    // Renderer
    private PixelData pixels;
    private Image image;
    private Texture texture;
    	
    // Zoom
    private double zoom = 1d;
    private double zoomDelta = 1.001d;
    
    // Pan
    private double xOffset = 0d;
    private double yOffset = 0d;
    private double movementDelta = 0.003d;
	    
    // Calculation control
    private boolean isCalculating = false;    
    private int maxIterations = 255;
    private int maxIterationsDelta = 255;
    
    /**
     * Constructor
     * @param title
     */
	public FractalRenderer(String title, int fractalType, double constantReal, double constantImaginary) {
		super(title);
		
		// Settings
		this.fractalType = fractalType;
		this.constantReal = constantReal;
		this.constantImaginary = constantImaginary;
		
		// Colors
		colorFactor = new int[3];
		colorFactor[RED] = 1;
		colorFactor[GREEN] = 1;
		colorFactor[BLUE] = 1;
		
		// Show text
		showText = true;
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		
		// Set up the rendering system
		texture = PixelData.createTexture(container.getWidth(), container.getHeight(), ImageData.Format.RGB, Image.FILTER_NEAREST);
		image = new Image(texture);
		pixels = new PixelData(container.getWidth(), container.getHeight());
		pixels.clear(0x00);
		
		// Creates the Set
		if(fractalType == MANDELBROT_SET) {
			set = new Mandelbrot();
		} else if(fractalType == JULIA_SET) {
			set = new Julia(constantReal, constantImaginary);
		}
		
		// Calculates the first frame
		pixels = set.updateSet(container, pixels, zoom, maxIterations, xOffset, yOffset, colorFactor);
		
		// Updates the texture
		pixels.apply(texture);
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {

		// Get Input
		input = container.getInput();
		
		// Calculate
		if(isCalculating) {
			pixels = set.updateSet(container, pixels, zoom, maxIterations, xOffset, yOffset, colorFactor);
			pixels.apply(texture);
			isCalculating = false;
		}

		// Movement Up/Down
		if(input.isKeyDown(Input.KEY_W)) {
			
			yOffset -= movementDelta * delta / zoom;
			isCalculating = true;
			
		} else if(input.isKeyDown(Input.KEY_S)) {
			
			yOffset += movementDelta * delta / zoom;
			isCalculating = true;
			
		}
		
		// Movement Left/Right
		if(input.isKeyDown(Input.KEY_A)) {

			xOffset -= movementDelta * delta / zoom;
			isCalculating = true;

		} else if(input.isKeyDown(Input.KEY_D)) {

			xOffset += movementDelta * delta / zoom;
			isCalculating = true;
			
		}
		
		// Zoom Controls
		if(input.isKeyDown(Input.KEY_E)) {

			zoom *= Math.pow(zoomDelta, delta);
			isCalculating = true;
			
		} else if(input.isKeyDown(Input.KEY_Q)) {

			zoom /= Math.pow(zoomDelta, delta);
			isCalculating = true;
			
		}
		
		// Maximum Iterations Control
		if(input.isKeyPressed(Input.KEY_C)) {

			maxIterations += maxIterationsDelta;
			isCalculating = true;
			
		} else if(input.isKeyPressed(Input.KEY_Z)) {

			maxIterations -= maxIterationsDelta;
			isCalculating = true;
			
		}
		
		// Change Color Control
		if       (input.isKeyPressed(Input.KEY_1)) {
			currentColorControl = RED;
		} else if(input.isKeyPressed(Input.KEY_2)) {
			currentColorControl = GREEN;
		} else if(input.isKeyPressed(Input.KEY_3)) {
			currentColorControl = BLUE;
		}

		// Change Color Factors
		if(input.isKeyDown(Input.KEY_MINUS)) {
				
			if(input.isKeyDown(Input.KEY_LSHIFT)) {
				colorFactor[currentColorControl] -= 32;
			} else {
				colorFactor[currentColorControl] -= 1;
			}
			
			if(colorFactor[currentColorControl] < 1) {
				colorFactor[currentColorControl] = 1;
			}
			
		} else if (input.isKeyDown(Input.KEY_EQUALS)) {
			
			if(input.isKeyDown(Input.KEY_LSHIFT)) {
				colorFactor[currentColorControl] += 32;
			} else {
				colorFactor[currentColorControl] += 1;
			}
			
			if(colorFactor[currentColorControl] > 256) {
				colorFactor[currentColorControl] = 256;
			}
		}
		
		// Force calculation
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			isCalculating = true;
		}
		
		// Show text
		if(input.isKeyPressed(Input.KEY_H)) {
			showText = !showText;
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		
		// Draws the Image
		g.drawImage(image, 0, 0);
		
		// Draws the Stats
		if(showText) {
			g.drawString("fps = " + container.getFPS(), 0, 20);
			g.drawString("zoom = " + zoom, 0, 40);
			g.drawString("maxIterations = " + maxIterations, 0, 60);
			g.drawString("container width = " + container.getWidth(), 0, 80);
			g.drawString("container height = " + container.getHeight(), 0, 100);
			g.drawString("screen ratio = " + ((float)container.getWidth() / (float)container.getHeight()) , 0, 120);
			g.drawString("xOffset = " + xOffset, 0, 140);
			g.drawString("yOffset = " + yOffset, 0, 160);
			
			String colorText = "";
			if(currentColorControl == RED) {
				colorText = "red";
			} else if(currentColorControl == GREEN) {
				colorText = "green";
			} else if(currentColorControl == BLUE) {
				colorText = "blue";
			}
			g.drawString("current color change = " + colorText, 0, 220);
			g.drawString("red = " + colorFactor[RED], 0, 240);
			g.drawString("green = " + colorFactor[GREEN], 0, 260);
			g.drawString("blue = " + colorFactor[BLUE], 0, 280);
			
			g.drawString("WASD to move around", 0, 320);
			g.drawString("E and Q to zoom in/out", 0, 340);
			g.drawString("C and Z to increase/decrease max iterations", 0, 360);
			g.drawString("Select color with 1 (red), 2 (green), 3 (blue)", 0, 380);
			g.drawString("and use -/+ to change it", 0, 400);
			g.drawString("Hold down LShift to change colors faster", 0, 420);
			g.drawString("Press Space to apply colors", 0, 440);
			g.drawString("H to hide screen text", 0, 480);
			
			if(isCalculating)
				g.drawString("Calculating, plz w8...", 100 , 0);
		}
	}
}
