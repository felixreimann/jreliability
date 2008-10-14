/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package de.cs12.reliability.gui;

import de.cs12.reliability.evaluator.IntegralEvaluator;
import de.cs12.reliability.function.Function;

/**
 * The {@code DensityAspect} represents the density of a {@code Function}.
 * 
 * @author glass
 * 
 */
public class DensityAspect extends AbstractAspect {

	/**
	 * Constructs a {@code DensityAspect}.
	 */
	public DensityAspect() {
		super("Density", "time t", "density function f(t)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.cs12.reliability.gui.Aspect#getUpper(de.cs12.reliability.function.
	 * Function)
	 */
	@Override
	public double getUpper(Function function) {
		IntegralEvaluator evaluator = new IntegralEvaluator();
		return evaluator.getUpperBound(function);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getY(double,
	 * de.cs12.reliability.function.Function)
	 */
	@Override
	public double getY(double x, Function function) {
		double deltaT = 0.00000001;
		double y = function.getY(x);
		double yPrime = function.getY(x + deltaT);
		double density = (y - yPrime) / deltaT;
		return density;
	}

}
