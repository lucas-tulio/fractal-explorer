package fractal;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import fractal.pixel.PixelData;

public class Mandelbrot extends Set implements IFractal {
	
	// Attributes
	private double realP, imaginaryP;
	
	/**
	 * Creates a new Mandelbrot Set
	 */
	public Mandelbrot() {
    		super();
    }
    
    /**
     * Calculates the Set
     */
    public PixelData updateSet(GameContainer container, PixelData pixels,
    		double zoom, int maxIterations, double xOffset, double yOffset,
    		int[] colorFactor) {
    	
		for (int y = 0; y < container.getHeight(); y++) {
            for (int x = 0; x < container.getWidth(); x++) {
            	
            	// Calculate the initial real and imaginary parts of z
            	realP = ((double)container.getWidth() / (double)container.getHeight()) * (x - container.getWidth() / 2) / (0.5 * zoom * container.getWidth()) + xOffset;
            	imaginaryP = (y - container.getHeight() / 2) / (0.5 * zoom * container.getHeight()) + yOffset;
            	
            	// Zero the new and old real and imaginary parts
            	newReal = newImaginary = oldReal = oldImaginary = 0;
            	
            	// Number of iterations
            	int i = 0;
            	
            	// Start iterating
            	for(i = 0; i < maxIterations; i++) {
            		
            		// Get the values of the previous iteration
            		oldReal = newReal;
            		oldImaginary = newImaginary;
            		
            		// Calculate the new real and imaginary parts
            		newReal = (oldReal * oldReal) - (oldImaginary * oldImaginary) + realP;
            		newImaginary = 2 * oldReal * oldImaginary + imaginaryP;
            		
            		// Exit condition
            		if((newReal * newReal + newImaginary * newImaginary) > 4) break;
            	}
            	
            	// Color, based on the number of iterations and the colorScheme
            	if(colorFactor[0] == 1 && colorFactor[1] == 1 && colorFactor[2] == 1) {
            		pixels.putPixel(new Color(i, i, i));
            	} else {
            		pixels.putPixel(new Color(i % colorFactor[0], i % colorFactor[1], i % colorFactor[2]));
            	}
            }
        }
		
		// Returns an array of Pixels as result
		return pixels;
	}
}