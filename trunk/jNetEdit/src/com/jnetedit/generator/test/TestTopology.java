/**
 * jNetEdit - Copyright (c) 2010 Salvatore Loria
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.jnetedit.generator.test;

import java.util.LinkedList;

import com.jnetedit.common.ItemType;
import com.jnetedit.core.Factory;
import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.nodes.components.AbstractInterface;
import com.jnetedit.core.project.AbstractProject;
import com.jnetedit.generator.Topology;
import com.jnetedit.generator.VeryBasicLayouting;

import junit.framework.TestCase;

public class TestTopology extends TestCase {

	Topology t;
	
	public TestTopology(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		LinkedList<String> areas = new LinkedList<String>();
		areas.add("red");
		areas.add("dmz");
		areas.add("green");
		t = new Topology("test", "tests", areas, new Factory(), new VeryBasicLayouting())
			.createTopology();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		t = null;
	}

	public void testCreateTopology() {
		AbstractProject proj = t.getProject();
		
		/* Basic not null assertions */
		assertNotNull(proj);
		assertNotNull(t.getTAP());
		assertNotNull(t.getMainGateway());
		
		/* Assert the number of firewalls in the topology is exactly 2
		 */
		assertEquals(2, t.getFirewallsNumber());
		
		/* Assert the number of interfaces of each host is more 
		 * then a minimum and lesser then a maximum 
		 * (no isolated host, no useless router)
		 */
		for( AbstractHost host : proj.getHosts() ) {
			int minIfaces = 0, maxIfaces = 8;
			ItemType type = host.getType();
			if( type == ItemType.FIREWALL || type == ItemType.ROUTER ) 
				minIfaces = 2;
			else if( type == ItemType.PC || type == ItemType.SERVER || type == ItemType.NATTEDSERVER ) 
				minIfaces = 1;
			
			if( host.getInterfaces().size() < minIfaces ) 
				fail(host.getName() + " has " + host.getInterfaces().size() + " interfaces.\n" +
						"A " + type + " must have at least " + minIfaces + " interfaces.");
			else if( host.getInterfaces().size() > maxIfaces ) 
				fail(host.getName() + " has " + host.getInterfaces().size() + " interfaces.\n" +
						"A " + type + " must have lesser then " + maxIfaces + " interfaces.");
			
			// assert there's a path from the main gateway (rootNode) to this host
			// (the topology must be connected)
			if( !existsPath(t.getMainGateway(), host, null) ) 
				fail(host.getName() + " is not reacheable from the main gateway.\n" +
						"The topology must be connected.");
		}
		
		/* Assert the number of hosts connected to each collision domain
		 * is more then 2 (no isolated/useless domain)
		 */
		for( AbstractCollisionDomain domain : proj.getCollisionDomains() ) {
			int minConnection = 2;
			
			if( domain.getConnectedInterfaces().size() < minConnection ) 
				fail(domain.getName() + " has " + domain.getConnectedInterfaces().size() +
						" connections.\nA collision domain must have at least " + 
						minConnection + " connections.");
		}
	}

	private boolean existsPath(AbstractHost from, AbstractHost to, AbstractInterface notToVisit) {
		if( from.getName().equals(to.getName()) )
			return true;
		
		for( AbstractInterface iface : from.getInterfaces() ) {
			if( iface != notToVisit ) {
				AbstractCollisionDomain domain = iface.getCollisionDomain();
				for( AbstractInterface hostIface : domain.getConnectedInterfaces() ) {
					AbstractHost host = hostIface.getHost();
					if( !host.getName().equals(from.getName()) ) 
						if( existsPath(hostIface.getHost(), to, hostIface) ) 
							return true;
				}
			}
		}
		return false;
	}
}
