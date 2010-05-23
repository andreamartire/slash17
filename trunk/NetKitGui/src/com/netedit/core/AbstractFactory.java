package com.netedit.core;

import com.netedit.common.ItemType;
import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.AbstractLink;
import com.netedit.core.project.AbstractProject;


public interface AbstractFactory {

	/** create an host of this type */
	public AbstractHost createHost( ItemType type );
	
	/** create a collision domain */
	public AbstractCollisionDomain createCollisionDomain();
	
	/** create the tap */
	public AbstractCollisionDomain createTap();
	
	/** connect this host to this collision domain */
	public AbstractLink createLink( AbstractHost host, AbstractCollisionDomain collisionDomain ); 
	
	/** create the netkit project */
	public AbstractProject createProject( String name, String directory );
}
