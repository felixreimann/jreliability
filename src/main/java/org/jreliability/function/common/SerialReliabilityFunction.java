/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.function.common;

import java.util.Set;

import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.ReliabilityFunctionSet;

/**
 * The {@code SerialReliabilityFunction} corresponds to a {@code serial-structure} of elements in a system as known from
 * {@code Serial-Parallel systems}. Basically, the {@code SerialReliabilityFunction} holds a set of
 * {@code ReliabilityFunctions} and multiplies their {@code y}-values to derive the {@code y}-value of the whole
 * {@code serial-structure}.
 * 
 * @author glass
 * 
 */
public class SerialReliabilityFunction extends ReliabilityFunctionSet {

	/**
	 * Constructs a {@code SerialReliabilityFunction}.
	 * 
	 */
	public SerialReliabilityFunction() {
		super();
	}

	/**
	 * Constructs a {@code SerialReliabilityFunction} with a given set of {@code ReliabilityFunctions}.
	 * 
	 * @param functions
	 *            the reliability functions
	 */
	public SerialReliabilityFunction(Set<ReliabilityFunction> functions) {
		super(functions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		if (functions.size() == 0) {
			throw new IllegalStateException("Trying to evaluate empty SerialReliabilityFunction.");
		}
		double y = 1.0;
		for (ReliabilityFunction function : functions) {
			y *= function.getY(x);
		}
		return y;

	}

}
