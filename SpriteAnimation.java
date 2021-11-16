package aj;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.util.*;


public class SpriteAnimation extends Transition {
	private final ImageView imageView;
	private final int frameWidth;

	public SpriteAnimation(ImageView imageView, Duration duration, int frameWidth) {
		this.imageView = imageView;
		this.frameWidth = frameWidth;

		setCycleDuration(duration);
		setCycleCount(Animation.INDEFINITE);
		setInterpolator(Interpolator.LINEAR);
		this.imageView.setViewport(new Rectangle2D(0, 0, frameWidth, frameWidth));
	}

	protected void interpolate(double frac) {
		final int index = Math.min((int) Math.floor(3 * frac), 2);
		final int x = (index % 3) * frameWidth;
		final int y = (index / 3) * frameWidth;
		imageView.setViewport(new Rectangle2D(x, y, frameWidth, frameWidth));
	}
}
