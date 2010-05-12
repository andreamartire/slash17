package project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import project.gui.listeners.GActionListener;
import project.gui.listeners.GActionListener.ActionType;

public class GFrame extends JFrame {
	private static final long serialVersionUID = 3661490807594270819L;

	/** menu bar */
	JMenuBar menuBar;
	
	/** file menu */
	JMenu fileMenu;
	/** file menu item */
	JMenuItem newItem, openItem, saveItem, exitItem;
	
	/** lab menu */
	JMenu labMenu;
	/** lab menu item */
	JMenuItem 	startLabItem, stopLabItem, addRouterItem, addFirewallItem,
				addLinkItem, addPcItem, addServerItem, addNattedServerItem, 
				addCollisionDomainItem, addAreaItem, addTapItem;
		
	/** help menu */
	JMenu helpMenu;
	/** help menu item */
	JMenuItem aboutItem, licenceItem;
	
	/** horizontal tool bar */
	JToolBar horizontalToolbar;
	/** horizontal tool bar's button */
	JButton newFile, open, save, start, stop, exportImg;
	
	/** vertical tool bar */
	JToolBar verticalToolbar;
	/** vertical tool bar button */
	JButton router, firewall, pc, collisionDomain, server, 
			nattedServer, area, tap, link, delete;
	
	/** state bar's components */
	JLabel stateLabel;
	JPanel statePanel;

	/** the canvas */
	GCanvas canvas;

	/**
	 * Create the NetKit GUI window
	 */
	public GFrame() {
		super("NetKit GUI");
		
		createFrame();
	}
	
	/** Create and show the frame 
	 */
	private void createFrame() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		/* Operating system check */
		String os = System.getProperty("os.name");
		if( os.equals("Linux") ) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} catch (Exception e) { 
				System.err.println("Cannot set the GTK Look and Feel. Setting the default"); 
			}
		} else {
			String message = "WARNING: some features are not available on your system.\n" +
							"Run NetKitGUI on a GNU/Linux system with a netkit installation.";
			String title = "WARNING: " + os + " is not fully supported";
			
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
			
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
			} catch (Exception e) { 
				System.err.println("Cannot set the Windows Look and Feel. Setting the default"); 
			}
		} 
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		setSize( screenSize );
		setSize( 1280, 800 );
		
		createMenuBar();
		createToolBar();
		createVericalToolBar();
		createStateBar();
		setupListeners();
		
		setVisible(true);
	}

	/** Create the menu bar and add it to the frame
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		
		// creating the file menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		newItem = new GMenuItem("New Project", new ImageIcon("data/images/16x16/new_icon.png"));
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		openItem = new GMenuItem("Open", new ImageIcon("data/images/16x16/open_icon.png"));
		saveItem = new GMenuItem("Save", new ImageIcon("data/images/16x16/save_icon.png"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		exitItem = new GMenuItem("Exit", new ImageIcon("data/images/16x16/exit_icon.png"));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		// creating the project menu
		labMenu = new JMenu("Laboratory");
		labMenu.setMnemonic(KeyEvent.VK_L);
		startLabItem = new GMenuItem("Start lab", new ImageIcon("data/images/16x16/start_icon.png"));
		startLabItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, InputEvent.CTRL_DOWN_MASK));
		stopLabItem = new GMenuItem("Stop lab", new ImageIcon("data/images/16x16/stop_icon.png"));
		stopLabItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, InputEvent.CTRL_DOWN_MASK));
		addRouterItem = new GMenuItem("Add router", new ImageIcon("data/images/16x16/router_icon.png"));
		addCollisionDomainItem = new GMenuItem("Add collision domain", new ImageIcon("data/images/16x16/collisionDomain_icon.png"));
		addLinkItem = new GMenuItem("Add link", new ImageIcon("data/images/16x16/link_icon.png"));
		addPcItem = new GMenuItem("Add pc", new ImageIcon("data/images/16x16/pc_icon.png"));
		addServerItem = new GMenuItem("Add server", new ImageIcon("data/images/16x16/server_icon.png"));
		addNattedServerItem = new GMenuItem("Add natted server", new ImageIcon("data/images/16x16/nattedserver_icon.png"));
		addFirewallItem = new GMenuItem("Add firewall", new ImageIcon("data/images/16x16/firewall_icon.png"));
		addAreaItem = new GMenuItem("Add area", new ImageIcon("data/images/16x16/area_icon.png"));
		addTapItem = new GMenuItem("Add tap", new ImageIcon("data/images/16x16/tap_icon.png"));
		labMenu.add(startLabItem);
		labMenu.add(stopLabItem);
		labMenu.addSeparator();
		labMenu.add(addRouterItem);
		labMenu.add(addCollisionDomainItem);
		labMenu.add(addPcItem);
		labMenu.add(addServerItem);
		labMenu.add(addNattedServerItem);
		labMenu.add(addFirewallItem);
		labMenu.add(addAreaItem);
		labMenu.add(addTapItem);
		labMenu.addSeparator();
		labMenu.add(addLinkItem);
		menuBar.add(labMenu);
		// creating help menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		aboutItem = new GMenuItem("About", new ImageIcon("data/images/16x16/info_icon.png"));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		licenceItem = new GMenuItem("Licence", new ImageIcon("data/images/16x16/info_icon.png"));
		helpMenu.add(aboutItem);
		helpMenu.add(licenceItem);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
	}
	
	/** Create the vertical toolbar and add it to the west side of the frame
	 */
	private void createVericalToolBar() {
		verticalToolbar = new JToolBar();
		verticalToolbar.setOrientation(JToolBar.VERTICAL);
		verticalToolbar.setFloatable(false);
		verticalToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.lightGray));
		
		router = new GButton("Router", "Add a router", "data/images/24x24/router_icon.png");
		server = new GButton("Server", "Add a server", "data/images/24x24/server_icon.png");
		firewall = new GButton("Firewall", "Add a firewall", "data/images/24x24/firewall_icon.png");
		nattedServer = new GButton("NatServer", "Add a natted server", "data/images/24x24/nattedserver_icon.png");
		pc = new GButton("PC", "Add a pc", "data/images/24x24/pc_icon.png");
		collisionDomain = new GButton("Domain", "Add a collision domain", "data/images/24x24/collisionDomain_icon.png");
		area = new GButton("Area", "Create a Network Area", "data/images/24x24/area_icon.png");
		tap = new GButton("Tap", "Add TAP interface", "data/images/24x24/tap_icon.png");
		link = new GButton("Link", "Add a link", "data/images/24x24/link_icon.png");
		delete = new GButton("Delete", "Delete a node", "data/images/24x24/delete_icon.png");
		
		verticalToolbar.add( pc );
		verticalToolbar.add( server );
		verticalToolbar.add( nattedServer );
		verticalToolbar.add( router );
		verticalToolbar.add( firewall );
		verticalToolbar.add( collisionDomain );
		verticalToolbar.add( area );
		verticalToolbar.add( tap );
		verticalToolbar.add( link );
		verticalToolbar.addSeparator();
		verticalToolbar.add( delete );
		
		add( verticalToolbar, BorderLayout.WEST );
	}

	/** Create the standard toolbar and add it to the north side of the frame
	 */
	private void createToolBar() {
		horizontalToolbar = new JToolBar();
		horizontalToolbar.setFloatable(false);
		horizontalToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
		
		newFile = new GButton("New", "Create a new Project", "data/images/24x24/new_icon.png");
		open = new GButton("Open", "Open a Project", "data/images/24x24/open_icon.png");
		save = new GButton("Save", "Save the Project", "data/images/24x24/save_icon.png");
		start = new GButton("Start", "Start the lab", "data/images/24x24/start_icon.png");
		stop = new GButton("Stop", "Stop the lab", "data/images/24x24/stop_icon.png");
		exportImg = new GButton("Export", "Export", "data/images/24x24/save_icon.png");
		
		horizontalToolbar.add( newFile );
		horizontalToolbar.add( open );
		horizontalToolbar.add( save );
		horizontalToolbar.addSeparator();
		horizontalToolbar.add( start );
		horizontalToolbar.add( stop );
		horizontalToolbar.addSeparator();
		horizontalToolbar.add( exportImg );
		 
		add( horizontalToolbar, BorderLayout.NORTH );
	}
	
	/** Create the stateBar and add it to the south side of the frame
	 */
	private void createStateBar() {
		stateLabel = new JLabel("Status: editing", JLabel.TRAILING);
		statePanel = new JPanel(new GridLayout(1, 1));
		
		statePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.lightGray));
		statePanel.add(stateLabel);
		
		add( statePanel, BorderLayout.SOUTH );
	}
	
	public void setCanvas( GCanvas canvas ) {
		this.canvas = canvas;
	}
	
	private void setupListeners() {
		/** set the window's closing event 
		 */
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				closeApplication();
			}
		});
		
		/** Set the window's minimum size
		 */
		addComponentListener( new ComponentAdapter() {
			public void componentResized( ComponentEvent e ) {
				JFrame tmp = (JFrame) e.getSource();
				if ( tmp.getWidth() < 600 || tmp.getHeight() < 600 ) {
					tmp.setSize( 700, 630 );
				}
			}
		});
		
		/********************************
		 * Menu's items listeners
		 ********************************/
		
		newItem.addActionListener( new GActionListener(ActionType.newProject) );
		openItem.addActionListener( new GActionListener(ActionType.openProject) );
		saveItem.addActionListener( new GActionListener(ActionType.saveProject) );
		exitItem.addActionListener( new GActionListener(ActionType.exit) );
		
		aboutItem.addActionListener( new GActionListener(ActionType.showInfo) );
		licenceItem.addActionListener( new GActionListener(ActionType.showLicence) );
		
		startLabItem.addActionListener( new GActionListener(ActionType.startLab) );
		stopLabItem.addActionListener( new GActionListener(ActionType.stopLab) );
		addPcItem.addActionListener( new GActionListener(ActionType.addPc) );
		addRouterItem.addActionListener( new GActionListener(ActionType.addRouter) );
		addFirewallItem.addActionListener( new GActionListener(ActionType.addFirewall) );
		addServerItem.addActionListener( new GActionListener(ActionType.addServer) );
		addNattedServerItem.addActionListener( new GActionListener(ActionType.addNattedServer) );
		addAreaItem.addActionListener( new GActionListener(ActionType.addArea) );
		addCollisionDomainItem.addActionListener( new GActionListener(ActionType.addCollisionDomain) );
		addLinkItem.addActionListener( new GActionListener(ActionType.addLink) );
		addTapItem.addActionListener( new GActionListener(ActionType.addTap) );
		
		/********************************
		 * Buttons listeners
		 ********************************/
		
		newFile.addActionListener( new GActionListener(ActionType.newProject) );
		open.addActionListener( new GActionListener(ActionType.openProject) );
		save.addActionListener( new GActionListener(ActionType.saveProject) );
		exportImg.addActionListener( new  GActionListener(ActionType.exportImage) );
		
		router.addActionListener( new GActionListener(ActionType.addRouter) );
		firewall.addActionListener( new GActionListener(ActionType.addFirewall) );
		server.addActionListener( new GActionListener(ActionType.addServer) );
		nattedServer.addActionListener( new GActionListener(ActionType.addNattedServer) );
		pc.addActionListener( new GActionListener(ActionType.addPc) );
		collisionDomain.addActionListener( new GActionListener(ActionType.addCollisionDomain) );
		link.addActionListener( new GActionListener(ActionType.addLink) );
		tap.addActionListener( new GActionListener(ActionType.addTap) );
		area.addActionListener( new GActionListener(ActionType.addArea) );
		delete.addActionListener( new GActionListener(ActionType.delete) );
		
		start.addActionListener( new GActionListener(ActionType.startLab) );
		stop.addActionListener( new GActionListener(ActionType.stopLab) );
	}
	
	public void closeApplication() {
		
		if( ProjectHandler.getInstance().isSaved() == false ) {
			int close = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit without saving the project?", 
					"Project not saved", JOptionPane.YES_NO_OPTION);
			if( close == JOptionPane.YES_OPTION ) {
				this.dispose();
			}
		} else {
			this.dispose();
		}
	}
}