package frc.robot;


import edu.wpi.first.vision.VisionPipeline; //edu.wpi.first.wpilibj.vision.VisionPipeline; (deprecated)
import org.opencv.core.*;

/**
* BasicVisionPipeline class.
*
* <p>A class that does no image processing.
*
* @author GRIP
*/
public class BasicVisionPipeline implements VisionPipeline {

	Mat capturedImage;
	/**
	 * This is the primary method that runs the entire pipeline and updates the outputs.
	 */
	@Override 
	public void process(Mat source0) {
		capturedImage = source0;
	}

	public Mat getCapturedImage() {
		return capturedImage;
	}
}

