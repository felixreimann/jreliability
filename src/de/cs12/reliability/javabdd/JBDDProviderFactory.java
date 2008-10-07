/**
 * 
 */
package de.cs12.reliability.javabdd;

import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.bdd.BDDProviderFactory;

/**
 * The {@code JBDDProviderFactory} is a {@code BDDProviderFactory} for the
 * JavaBDD library.
 * 
 * @author reimann
 * 
 */
public class JBDDProviderFactory implements BDDProviderFactory {

	/**
	 * Constructs a {@code JBDDProviderFactory}.
	 */
	public JBDDProviderFactory() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.cs12.reliability.evaluator.bdd.BDDProviderFactory#getProvider(int)
	 */
	public <T> BDDProvider<T> getProvider(int vars) {
		return new JBDDProvider<T>(vars);
	}

}
