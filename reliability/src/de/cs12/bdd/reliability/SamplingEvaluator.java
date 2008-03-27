package de.cs12.bdd.reliability;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import de.cs12.bdd.BDD;

/**
 * The {@code SamplingEvaluator} creates samples of the function
 * {@code y = f(x)} as pairs of {@code x} and {@code f(x) = y}.
 * 
 * @author glass
 * @param <T>
 *            the type of the variables
 * 
 */
public class SamplingEvaluator<T> extends AbstractEvaluator<T> {

	/**
	 * Constructs a {@code SamplingEvaluator} with a {@code BDD}.
	 * 
	 * @param bdd
	 *            the bdd
	 */
	public SamplingEvaluator(BDD<T> bdd) {
		super(bdd);
	}

	/**
	 * Returs the samples of the {@code BDD} from {@code low} to {@code high}
	 * using the given {@code step} width.
	 * 
	 * @param distributions
	 *            the distribution of each variable
	 * @param low
	 *            the low value
	 * @param high
	 *            the high value
	 * @param step
	 *            the step width
	 * @return the samples
	 */
	public SortedMap<Double, Double> getValues(
			Map<T, Distribution> distributions, double low, double high,
			double step) {
		SortedMap<Double, Double> values = new TreeMap<Double, Double>();
		for (double i = low; i < high; i += step) {
			values.put(i, calculateTop(distributions, i));
		}
		values.put(high, calculateTop(distributions, high));
		return values;

	}

}
