package fractal;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import fractal.pixel.PixelData;

public class Julia extends Set implements IFractal {
	
	// Attributes
    protected double constantReal, constantImaginary;
	
    /**
     * 
     * @param constantReal
     * @param constantImaginary
     */
    public Julia(double constantReal, double constantImaginary) {
    		this.constantReal = constantReal;
    		this.constantImaginary = constantImaginary;
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
            	newReal = ((double)container.getWidth() / (double)container.getHeight()) * (x - container.getWidth() / 2) / (0.5 * zoom * container.getWidth()) + xOffset;
            	newImaginary = (y - container.getHeight() / 2) / (0.5 * zoom * container.getHeight()) + yOffset;
            	
            	// Number of iterations
            	int i = 0;
            	
            	// Start iterating
            	for(i = 0; i < maxIterations; i++) {
            		
            		// Get the values of the previous iteration
            		oldReal = newReal;
            		oldImaginary = newImaginary;
            		
            		// Calculate the new real and imaginary parts
            		newReal = (oldReal * oldReal) - (oldImaginary * oldImaginary) + constantReal;
            		newImaginary = 2 * oldReal * oldImaginary + constantImaginary;
            		
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