package de.cs12.reliability.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ptolemy.plot.Plot;
import de.cs12.reliability.common.Samples;
import de.cs12.reliability.function.Function;

/**
 * The {@code ReliabilityPanel} is a basic GUI to visualize the reliability
 * {@code Aspects} for given {@code Functions}.
 * 
 * @author glass
 * 
 */
public class ReliabilityPanel extends JPanel {

	/**
	 * Standard serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The used ptolemy plot.
	 */
	protected Plot plot;

	/**
	 * The used sampler to determine the {@code Samples} of a {@code Function}
	 * under a given {@code Aspect}.
	 */
	protected Sampler sampler;

	/**
	 * The list of aspects that can be chosen.
	 */
	protected final List<Aspect> aspects;

	/**
	 * The used aspect picker.
	 */
	protected AspectPicker picker;

	/**
	 * The panel.
	 */
	protected JPanel panel = new JPanel();

	/**
	 * The list of functions that shall be plotted.
	 */
	protected SortedMap<String, Function> functions;

	/**
	 * The map keeps track of the aspect and its index in the picker.
	 */
	protected final Map<Aspect, Integer> indices = new HashMap<Aspect, Integer>();

	/**
	 * The {@code AspectPicker} is used to choose between the different {@code
	 * Aspects}.
	 * 
	 * @author lukasiewycz, glass
	 * 
	 */
	protected class AspectPicker extends JToolBar implements ActionListener {

		private static final long serialVersionUID = 1L;

		protected final List<Aspect> aspects;

		protected JComboBox comboBox = new JComboBox();

		protected Aspect currentAspect;

		protected ReliabilityPanel panel;

		/**
		 * Constructs an {@code AspectPicker} with a given {@code JPanel} and
		 * the {@code Aspects}.
		 * 
		 * @param panel
		 *            the panel
		 * @param aspects
		 *            the aspects
		 */
		public AspectPicker(ReliabilityPanel panel, List<Aspect> aspects) {
			super();
			this.panel = panel;
			this.aspects = aspects;

			List<String> strings = new ArrayList<String>();

			int i = 0;
			for (Aspect aspect : aspects) {
				String s = aspect.getName();
				strings.add(s);
				indices.put(aspect, i);
				i++;
			}

			String[] e = new String[strings.size()];
			final String[] elements = strings.toArray(e);
			comboBox = new JComboBox(elements);

			comboBox.addActionListener(AspectPicker.this);
			comboBox.setMaximumSize(comboBox.getPreferredSize());

			add(new JLabel("Reliability Aspect: "));
			add(comboBox);
			setFloatable(false);

			initSelection();
		}

		/**
		 * Initializes the picker with a selected aspect.
		 */
		private void initSelection() {
			Aspect aspect = aspects.get(0);
			comboBox.setSelectedIndex(indices.get(aspect));
			set(aspect);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean changed = false;
			Aspect aspect = aspects.get(comboBox.getSelectedIndex());
			if (currentAspect != aspect) {
				currentAspect = aspect;
				changed = true;
			}

			if (changed) {
				panel.paint(currentAspect);
			}

		}

		/**
		 * Returns the current {@code Aspect}.
		 * 
		 * @return the current aspect
		 */
		public Aspect get() {
			return currentAspect;
		}

		/**
		 * Sets the current {@code Aspect}.
		 * 
		 * @param aspect
		 *            the aspect to be set
		 */
		public void set(Aspect aspect) {
			this.currentAspect = aspect;
		}

	}

	/**
	 * Constructs a {@code ReliabilityPanel} with the given {@code Aspects}.
	 * 
	 * @param aspects
	 *            the aspects
	 */
	public ReliabilityPanel(List<Aspect> aspects) {
		this.aspects = aspects;
	}

	/**
	 * Returns the {@code JPanel} for a given set of {@code Functions}.
	 * 
	 * @param functions
	 *            the functions
	 * @return the panel
	 */
	protected JPanel get(SortedMap<String, Function> functions) {
		this.functions = functions;

		plot = new Plot();
		sampler = new Sampler();

		picker = new AspectPicker(ReliabilityPanel.this, aspects);
		Aspect currentAspect = picker.get();

		Color[] colors = { Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW,
				Color.GREEN, Color.ORANGE };
		plot.setColors(colors);
		int i = 0;
		for (Entry<String, Function> entry : functions.entrySet()) {
			plot.addLegend(i, entry.getKey());
			i++;
		}

		panel.setLayout(new BorderLayout());
		panel.add(picker, BorderLayout.NORTH);
		panel.add(plot, BorderLayout.CENTER);

		setLabels(currentAspect.getXAxis(), currentAspect.getYAxis());
		paint(currentAspect);

		panel.setPreferredSize(new Dimension(600, 400));

		panel.revalidate();
		panel.repaint();

		return panel;
	}

	/**
	 * Sets the labels for the axes.
	 * 
	 * @param xLabel
	 *            the label for the x-axis
	 * @param yLabel
	 *            the label for the y-axis
	 */
	protected void setLabels(final String xLabel, final String yLabel) {
		plot.setXLabel(xLabel);
		plot.setYLabel(yLabel);
	}

	/**
	 * Repaints the diagram under a given {@code Aspect}.
	 * 
	 * @param aspect
	 *            the aspect
	 */
	protected void paint(Aspect aspect) {
		plot.clear(false);
		double min = 0.0;
		double max = 0.0;
		int i = 0;
		SortedMap<String, Samples> samples = sampler.getSamples(functions,
				aspect, 500);
		for (Entry<String, Samples> entry : samples.entrySet()) {
			Samples sample = entry.getValue();
			for (Entry<Double, Double> value : sample.entrySet()) {
				double x = value.getKey();
				double y = value.getValue();
				if (y > max) {
					max = y;
				}
				plot.addPoint(i, x, y, true);
			}
			i++;
		}
		max = max + (0.1 * max);
		plot.setYRange(min, max);
		plot.revalidate();
		plot.repaint();
	}

}