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

package com.jnetedit.gui.gcomponents;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JMenuItem;

public class GMenuItem extends JMenuItem {
	private static final long serialVersionUID = -1942864015296942487L;

	private boolean selected = false;
	
	public GMenuItem( String text, Icon icon ) {
		super( text, icon );
		
		setFont( new Font("SansSerif", Font.PLAIN, 12));
		
		setPreferredSize(new Dimension(200, 30));
		
		setToolTipText(text);
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean b) {
		selected = b;
	}
	
	public void toggleSelected() {
		selected = !selected;
	}
}
