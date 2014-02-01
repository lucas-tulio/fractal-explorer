package fractal;
import org.newdawn.slick.GameContainer;

import fractal.pixel.PixelData;

public interface IFractal {
	public PixelData updateSet(GameContainer container, PixelData pixels,
			double zoom, int maxIterations, double xOffset, double yOffset, int[] colorFactor);
}
