package de.cs12.reliability.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

import de.cs12.reliability.common.Samples;

/**
 * The {@code ReliabilityViewer} is a basic GUI that shows the {@code
 * ReliabilityPanel}.
 * 
 * @author glass
 * 
 */
public class ReliabilityViewer extends JFrame {

	private static final long serialVersionUID = 1L;

	protected final String title;

	protected ReliabilityPanel reliabilityPanel;

	/**
	 * Constructs a {@code ReliabilityViewer} with a given title and {@code
	 * Samples}.
	 * 
	 * @param title
	 *            the title
	 * @param samples
	 *            the samples
	 */
	public ReliabilityViewer(String title, Samples samples) {
		this.title = title;

		reliabilityPanel = new ReliabilityPanel(samples);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		init();
	}

	/**
	 * Initializes the frame.
	 */
	protected final void init() {
		setTitle(title);
		setLayout(new BorderLayout());

		setPreferredSize(new Dimension(800, 600));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().add(reliabilityPanel.getPanel());

		pack();
		setVisible(true);
	}

}