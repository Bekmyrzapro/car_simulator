package aj;

import javafx.geometry.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.util.*;

public class Car extends Pane {
	public static final int FRAME_WIDTH = 256;
	public static int CAR_SCALE = 72;

	public static final double MAX_XSPEED = 1;
	public static final double MIN_XSPEED = -1;
	public static final double MAX_COHERENT_SPEED = 40;

	private Car frontCar = null;
	private Car backCar = null;

	private boolean isEmergencyBrake = false;

	private double xPos = 0;
	private double yPos = 0;
	private double xSpeed = 0;
	private double xDirection = 1;

	private Image frames;
	private ImageView framesView;

	public SpriteAnimation animation;

	private Glow effect = new Glow(0.8);
	
	private boolean overSpeedMode = false;

	public Car(String framesImage){
		frames = new Image(getClass().getResourceAsStream(framesImage));

		framesView = new ImageView(frames);
		framesView.setFitHeight(CAR_SCALE);
		framesView.setFitWidth(CAR_SCALE);
		framesView.setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_WIDTH));

		animation = new SpriteAnimation(this.framesView, Duration.millis(200), FRAME_WIDTH);
		getChildren().addAll(this.framesView);
	}


	synchronized public void setXPos(double xPos) {
		if (xPos < Main.XMINPOS) {
			xPos = Main.XMINPOS;
		}

		if (xPos > Main.XMAXPOS) {
			xPos = Main.XMAXPOS;
		}

		this.xPos = xPos;
		this.setLayoutX(xPos);
	}


	public double getXPos() {
		return this.xPos;
	}


	public void setYPos(int yPos) {
		this.yPos = yPos;
		this.setLayoutY(yPos);
	}


	synchronized public void setXSpeed(double xSpeed) {
		if (xSpeed > MAX_XSPEED) {
			xSpeed = MAX_XSPEED;
		}

		if (xSpeed < MIN_XSPEED) {
			xSpeed = MIN_XSPEED;
		}

		this.xSpeed = xSpeed;
	}


	public double getXSpeed() {
		return this.xSpeed;
	}
	
	// prevent this car to be overspeed
	synchronized public void stopSpeeding() {
		xDirection = -xDirection;
		if (xDirection > 0) {
			setXSpeed(xSpeed + 0.001);
		} else {
			setXSpeed(xSpeed - 0.001);
		}
	}
	
	synchronized public void setOverspeedMode(boolean overSpeed) {
		// finish overspeed mode
		if (this.overSpeedMode && !overSpeed) {
			stopSpeeding();
		}
		this.overSpeedMode = overSpeed;
	}
	
	synchronized public boolean isInOverSpeedMode() {
		return this.overSpeedMode;
	}


	synchronized public void autoSpeed() {
		// currently in overspeed mode hazard
		if (this.overSpeedMode) {
			return;
		}
		
		double newXPos = xPos + xSpeed * xDirection;

		if (newXPos > Main.XMAXPOS) {
			xDirection = -xDirection;
		}

		if (this.frontCar != null) {
			if (newXPos + CAR_SCALE + 10 > this.frontCar.getXPos()) {
				xDirection = -xDirection;
			}
		}

		if (newXPos < Main.XMINPOS) {
			xDirection = -xDirection;
		}

		if (this.backCar != null) {
			if (newXPos < this.backCar.getXPos() + CAR_SCALE + 10) {
				xDirection = -xDirection;
			}
		}

		setXPos(newXPos);

		if (xDirection > 0) {
			setXSpeed(xSpeed + 0.001);
		} else {
			setXSpeed(xSpeed - 0.001);
		}
	}


	synchronized public double getCoherentSpeed() {
		return 25.0 + 15 * xSpeed * xDirection;
	}
	synchronized public void setEmergency() {
		framesView.setEffect(effect);
	}
	synchronized public void removeEmergency() {
		framesView.setEffect(null);
	}

	public void setFrontCar(Car frontCar) {
		this.frontCar = frontCar;
		frontCar.setBackCar(this);
	}

	public void setBackCar(Car backCar) {
		this.backCar = backCar;
	}

	synchronized public void emergencyBrake() {
		isEmergencyBrake = true;
		this.animation.stop();
		if (this.backCar != null) {
			this.backCar.emergencyBrake();
		}
	}

	synchronized public void resetFromBrake() {
		isEmergencyBrake = false;
		this.animation.play();
		if (this.backCar != null) {
			this.backCar.resetFromBrake();
		}
	}

	synchronized public boolean isEmergencyBrake() {
		return this.isEmergencyBrake;
	}
}
