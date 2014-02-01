package main;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import javax.swing.*;

public class Start {
	public static void main(String args[]) {
		
		try {
			
			// Attributes
			int width = 0;
			int height = 0;
			boolean fullscreen = false;
			int fractalType = 0;
			double constantReal = 0d;			// Julia sets only
			double constantImaginary = 0d;	// Julia sets only
			
			// Width
			width = Integer.parseInt(JOptionPane.showInputDialog(null, "Screen width", "Width", JOptionPane.QUESTION_MESSAGE));
			
			// Height
			height = Integer.parseInt(JOptionPane.showInputDialog(null, "Screen height", "Height", JOptionPane.QUESTION_MESSAGE));
			
			// Fullscreen?
			fullscreen = (JOptionPane.showConfirmDialog(null, "Fullscreen?", "Fullscreen?", JOptionPane.YES_NO_OPTION) == 0 ? true : false);
			
			// Fractal Type
			Object[] fractalOptions = {"Mandelbrot set", "Julia set"};
			fractalType = JOptionPane.showOptionDialog(null, "Fractal type", "Fractal type", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, fractalOptions, fractalOptions[0]);
			
			// If it's a Julia Set, we need to get the constant values
			if(fractalType == FractalRenderer.JULIA_SET) {
				
				// Constant Real
				constantReal = Double.parseDouble(JOptionPane.showInputDialog(null, "c = ", "Constant real part", JOptionPane.QUESTION_MESSAGE));
				
				// Constant Imaginary
				constantImaginary = Double.parseDouble(JOptionPane.showInputDialog(null, "i = ", "Constant imaginary part", JOptionPane.QUESTION_MESSAGE));
			}
		
			// Starts the Graphic Environment
			AppGameContainer container;
			container = new AppGameContainer(new FractalRenderer("SCIENCE", fractalType, constantReal, constantImaginary));
			container.setDisplayMode(width, height, fullscreen);
			container.setAlwaysRender(true);
			container.setUpdateOnlyWhenVisible(false);
			container.setShowFPS(false);
    		container.setVSync(true);
    		container.setTargetFrameRate(60);
			container.start();
			
		} catch(SlickException e) {e.printStackTrace(); System.exit(0);}	
	}
}