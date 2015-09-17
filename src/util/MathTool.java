package util;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.util.FastMath;

public class MathTool {
	private static NormalDistribution nd = new NormalDistribution(0,1);
	
	public static double sigma(double z) {
		return 1 / (1+ FastMath.pow(Math.E, -z));
	}

	public static double getNormalDistribution() {
		return nd.sample();
	}
}
