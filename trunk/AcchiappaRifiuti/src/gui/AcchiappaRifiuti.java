package gui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class AcchiappaRifiuti extends JApplet {
	
	private static final long serialVersionUID = 3779225454776476552L;
	static JFrame framePrincipale;
	static PannelloPricipale gioco;
	static Container rootPane;
	
	public static void main(String[] args) {
		/** main usato per lanciare AcchiappaRifiuti come applicazione desktop */
		gioca();
	}
	
	public static void gioca() {
		gioco = new PannelloPricipale();
		framePrincipale = new JFrame("L'Acchiappa Rifiuti!");
		framePrincipale.add(gioco);
		framePrincipale.setResizable(false);
			
		framePrincipale.pack();
		framePrincipale.setVisible(true);
		framePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		rootPane = framePrincipale;
	}
	
	public static Container getFramePrincipale() {
		return rootPane;
	}
	
	public static PannelloPricipale getPannello() {
		return gioco;
	}
	
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) { 
            System.err.println("createGUI didn't complete successfully");
        }
    }
    
    /**
     * Create the GUI. For thread safety, this method should
     * be invoked from the event-dispatching thread.
     */
    private void createGUI() {
        //Create and set up the content pane.
        PannelloPricipale newContentPane = new PannelloPricipale();
        setContentPane(newContentPane);     
		Dimension d = new Dimension(1150, 690);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setPreferredSize(d);
		this.setSize(d);
		
		rootPane = getRootPane();
    } 
}
