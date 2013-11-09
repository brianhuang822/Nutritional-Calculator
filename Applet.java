import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.*;

public class Applet extends JPanel
	implements ActionListener, WindowListener, ChangeListener {

	private static final long serialVersionUID = 1L;
	private static final String applicationName = "Eat right!";
	private final HashMap<String, Food> foodDatabase = NutritionalRecommender.getFoodDatabase();
	
	/*
	 * Animation parameters.
	 */
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 30;
	static final int FPS_INIT = 0;
	int frameNumber = 0;
	
	/*
	 * GUI components.
	 */
	private JButton recommendButton;
	private HashMap<String, JSlider> sliders;
	private HashMap<String, JLabel> sliderLabels;
	private HashMap<String, Integer> foodMultipliers;
	
	public Applet()
	{
		/*
		 * Initialize food multipliers.
		 */
		foodMultipliers = new HashMap<String, Integer>();
		for (String foodName: foodDatabase.keySet()) {
			foodMultipliers.put(foodName, 0);
		}
		
		/*
		 * Initialize sliders.
		 */
		sliders = new HashMap<String, JSlider>();
		for (String foodName: foodDatabase.keySet()) {
			JSlider slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
			
			slider.addChangeListener(this);
            slider.setMajorTickSpacing(10);
            slider.setMinorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setBorder(BorderFactory.createEmptyBorder(0, 20, 1, 20));
            slider.setFont(new Font("Serif", Font.ITALIC, 12));
            
			sliders.put(foodName, slider);
		}

		/*
		 * Initialize slider labels.
		 */
		sliderLabels = new HashMap<String, JLabel>();
        for (String foodName: foodDatabase.keySet()) {
        	JLabel label = new JLabel(foodName + ": " + foodMultipliers.get(foodName), JLabel.RIGHT);
            sliderLabels.put(foodName, label);
        }
        
        /*
         * Initialize recommendation button.
         */
		recommendButton = new JButton("Recommend");
        recommendButton.addActionListener(this);
		
		/*
		 * Set layout and add everything together.
		 */
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
        for (String foodName: foodDatabase.keySet()) {
            add(sliderLabels.get(foodName));
            add(sliders.get(foodName));
        }
        add(recommendButton);
    }

	/*
	 * Add listener to window event.
	 */
    void addWindowListener(Window w) {
        w.addWindowListener(this);
    }

    /*
     * Action to be performed after the recommended button is pressed.
     */
    public void actionPerformed(ActionEvent e) {
        System.out.println ("o!!k");
    }
    
    /*
     * Unused window events.
     */
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    /*
     * Listen to sliders.
     */
    public void stateChanged(ChangeEvent e) {
        for (String foodName: foodDatabase.keySet()) {
        	foodMultipliers.remove(foodName);
        	foodMultipliers.put(foodName, sliders.get(foodName).getValue());
        	sliderLabels.remove(foodName);
        	sliderLabels.put(foodName, new JLabel(foodName + ": " + foodMultipliers.get(foodName), JLabel.RIGHT));
        }
    }

    /*
     * Create and show GUI.
     */
    private static void createAndShowGUI() {
       /*
    	* Create and set up the window.
    	*/
        JFrame frame = new JFrame(applicationName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Applet animator = new Applet();

        /*
         * Add content to the window.
         */
        frame.add(animator, BorderLayout.CENTER);

        /*
         * Display the window.
         */
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        javax.swing.SwingUtilities.invokeLater(
        	new Runnable() {
        		public void run() {
        			createAndShowGUI();
        		}
        	}
        );
    }
}
