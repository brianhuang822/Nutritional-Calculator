import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Slider1Demo extends JPanel
implements ActionListener,
WindowListener,
ChangeListener {

    protected JButton b2;

    //Set up animation parameters.
    static final int FPS_MIN = 0;
    static final int FPS_MAX = 30;
    static final int FPS_INIT = 0;    //initial frames per second
    int frameNumber = 0;
    int numberOfFoods = 11;
    //This label uses ImageIcon to show the doggy pictures.
    JLabel picture;
    JSlider f[] = new JSlider [numberOfFoods];
    int size [] = new int  [numberOfFoods];
    JLabel [] sliderLabel = new JLabel [numberOfFoods];
    public Slider1Demo() {
        b2 = new JButton("Recommend");
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        //Create the label.

        for (int i = 0; i <numberOfFoods; i++) {
            f[i] =  new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
            f[i].addChangeListener(this);
            size [i] = 0;
        }

        //Turn on labels at major tick marks.

        for (int i = 0; i <numberOfFoods; i++) {
            sliderLabel[i]= new JLabel("Apple: " + size [i], JLabel.CENTER);
            sliderLabel[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            add(sliderLabel[i]);
            f[i].setMajorTickSpacing(10);
            f[i].setMinorTickSpacing(1);
            f[i].setPaintTicks(true);
            f[i].setPaintLabels(true);
            f[i].setBorder(
                BorderFactory.createEmptyBorder(0,20,1,20));
            Font font = new Font("Serif", Font.ITALIC, 12);
            f[i].setFont(font);
            add(f[i]);
            System.out.print (i);
        }
        //Put everything together.

        b2.addActionListener(this);
        add(b2);
    }

    /** Add a listener for window events. */
    void addWindowListener(Window w) {
        w.addWindowListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println ("o!!k");
    }
    //React to window events.
    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {}

    public void windowClosing(WindowEvent e) {}

    public void windowClosed(WindowEvent e) {}

    public void windowActivated(WindowEvent e) {}

    public void windowDeactivated(WindowEvent e) {}

    /** Listen to the slider. */
    public void stateChanged(ChangeEvent e) {
        for (int i = 0; i <numberOfFoods; i++) {
            size [i] = f[i].getValue();
            System.out.println (size [i]);
            sliderLabel[i].setText("Apple: " + size[i]);

        }

    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Slider1Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Slider1Demo animator = new Slider1Demo();

        //Add content to the window.
        frame.add(animator, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }
}
