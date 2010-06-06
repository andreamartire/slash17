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

package com.jnetedit.core.nodes;

import java.util.ArrayList;

import com.jnetedit.core.nodes.components.AbstractInterface;

public interface AbstractCollisionDomain {

	public String getName();
	
	public void setName( String name );
	
	public String getLabel();
	
	public void setLabel(String label);
	
	public boolean delete();
	
	public void addConnection( AbstractInterface hostInterface );
	
	public void removeConnection( AbstractInterface hostInterface );

	public boolean isTap();

	public void setIsTap(boolean isTap);
	
	public void setArea( String area );
	
	public String getArea();

	public void setMinimumIp(int min);

	public int getMinimumIp();
	
	public ArrayList<AbstractInterface> getConnectedInterfaces();
}
