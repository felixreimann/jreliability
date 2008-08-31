package de.cs12.reliability.distribution;

/**
 * The {@code Distribution} is an interface to represent functions
 * {@code y = f(x)} for each variable used in the BDD.
 * 
 * @author glass
 * 
 */
public interface Distribution {

	/**
	 * Returns the {@code y} value for {@code y = f(x)}.
	 * 
	 * @param x
	 *            the x value
	 * @return the y for y = f(x)
	 */
	public double getY(double x);

}
