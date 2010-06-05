package com.netedit.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.project.AbstractProject;

import edu.umd.cs.piccolo.util.PObjectOutputStream;


public class ProjectHandler {

	private AbstractProject project;
	
	private static ProjectHandler projectHandler;
	
	private static boolean saved = true;
	
	/** Singleton implementation for the ProjectHandler */
	private ProjectHandler() {
	}

	public static ProjectHandler getInstance() {
		if( projectHandler == null )  
			projectHandler = new ProjectHandler();
		
		return projectHandler;
	}
	/****************************************************/
	
	public boolean newProject() {
		if( !saved ) {
			int choose = JOptionPane.showConfirmDialog(GuiManager.getInstance().getFrame(), 
					"Are you sure you want to exit without save the project?", 
					"Project not saved", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if( choose == JOptionPane.NO_OPTION ) {
				return false;
			}
		}
		
		String projectName = JOptionPane.showInputDialog(GuiManager.getInstance().getFrame(), "Insert the project's name:", "New Project", JOptionPane.QUESTION_MESSAGE);
		
		if( projectName == null ) {
			return false;
		}
		
		while( projectName.equals("") ) {
			projectName = JOptionPane.showInputDialog(GuiManager.getInstance().getFrame(), "Insert a valid name:", "New Project", JOptionPane.QUESTION_MESSAGE);
			if( projectName == null ) 
				return false;
		}
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select the project's directory");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		int choose = fc.showDialog( GuiManager.getInstance().getFrame(), "Select" );
		
		if( choose == JFileChooser.APPROVE_OPTION ) {
			String dir = fc.getSelectedFile().getAbsolutePath();

			project = GFactory.getInstance().createProject(projectName, dir + "/" + projectName);
			GuiManager.getInstance().setProject(project);
			
			saved = false;
			return true;
		} 
		return false;
	}
	
	public File saveProject() {
		project = Lab.getInstance().getProject();
		if( project != null ) {
			String projDir = project.getDirectory();
			
			createDirectory(projDir);
			
			String labConfcontent = project.getLabConfFile();
			
			for( AbstractHost host : project.getHosts() ) {
				String hostName = host.getName();
				createDirectory( projDir + "/" + hostName );
				
				String startupContent = host.getStartupFile();
				
				createFile( hostName + ".startup", projDir, startupContent );
			}
			
			createFile( "lab.conf", project.getDirectory(), labConfcontent );
			
			saved = true;
			
			try {
				PObjectOutputStream out = new PObjectOutputStream( 
						new FileOutputStream(projDir + "/" + project.getName() + ".jne", false)); 
				out.writeObjectTree(Lab.getInstance());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), "Project Saved");
			return new File(projDir + "/" + project.getName() + ".jne");
		} else {
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), "Unable to save the project", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	private void createFile( String fileName, String projDir, String content ) {
		File f = new File( projDir + "/" + fileName );
		if( f.exists() ) {
			f.delete();
			try {
				f.createNewFile();
			} catch (IOException e) {
			}
		}
		
		try {
			PrintWriter out = new PrintWriter(f);
			
			out.println( content );
			out.flush();
			
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String createDirectory( String directory ) {
		File proj = new File( directory );
		if( !proj.exists() ) 
			proj.mkdirs();
		
		String dir = proj.getAbsolutePath();
		return dir;
	}

	public void setSaved(boolean b) {
		saved = b;
	}

	public boolean isSaved() {
		return saved;
	}

	public void openProject() {
		if( !saved ) {
			int choose = JOptionPane.showConfirmDialog(GuiManager.getInstance().getFrame(), 
					"Are you sure you want to exit without save the project?", 
					"Project not saved", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if( choose == JOptionPane.NO_OPTION ) {
				return;
			}
		}
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "jNetEdit file - .jne";
			}
			
			@Override
			public boolean accept(File f) {
				if( f.isDirectory() ) 
					return true;
				return f.getName().endsWith(".jne");
			}
		});
		int choose = fileChooser.showOpenDialog(GuiManager.getInstance().getFrame());
		if( choose == JFileChooser.APPROVE_OPTION ) {
			File file = fileChooser.getSelectedFile();
			try {
				ObjectInputStream in = new ObjectInputStream( 
						new FileInputStream(file)); 
				Lab lab = (Lab) in.readObject();
				Lab.setInstance(lab);
				GuiManager.getInstance().setProject(Lab.getInstance().getProject());
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void openProject(File projectFile) {
		if( !saved ) {
			int choose = JOptionPane.showConfirmDialog(GuiManager.getInstance().getFrame(), 
					"Are you sure you want to exit without save the project?", 
					"Project not saved", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if( choose == JOptionPane.NO_OPTION ) {
				return;
			}
		}
		try {
			ObjectInputStream in = new ObjectInputStream( 
					new FileInputStream(projectFile)); 
			Lab lab = (Lab) in.readObject();
			Lab.setInstance(lab);
			GuiManager.getInstance().setProject(Lab.getInstance().getProject());
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
