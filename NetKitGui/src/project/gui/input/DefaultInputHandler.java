package project.gui.input;

import java.awt.event.MouseEvent;

import project.gui.GHost;
import project.gui.GNode;
import project.gui.GuiManager;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.util.PDimension;

public class DefaultInputHandler extends PBasicInputEventHandler {
	
	private int pressedButton;

	@Override
	public void mouseClicked(PInputEvent event) {
		super.mouseClicked(event);
		
		GNode node = (GNode) event.getPickedNode();
		
		node.setSelected(true);	
		
		if( node.getType() == GNode.host ) {
			// expand this host's configuration in the trees
			GuiManager.getInstance().getConfPanel().selectHost( ((GHost) node).getLogic().getName() );
		}
		
			
		if( event.getButton() == MouseEvent.BUTTON3 ) {
			node.showMenu(event);
		}
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		GNode node = (GNode) event.getPickedNode();
		node.setSelected(true);
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		GNode node = (GNode) event.getPickedNode();
		node.setSelected(false);
	}
	
	@Override
	public void mousePressed(PInputEvent event) {
		pressedButton = event.getButton();
	}
	
	@Override
	public void mouseReleased(PInputEvent event) {
		pressedButton = -1;
	}
	
	@Override
	public void mouseDragged(PInputEvent event) {
		if( pressedButton == MouseEvent.BUTTON1 ) {
			GNode node = (GNode) event.getPickedNode();
			
			PDimension d = event.getDeltaRelativeTo(node);		
			node.localToParent(d);
			node.offset(d.getWidth(), d.getHeight());
			
			node.setSelected(false);
			node.update();
		}
	}
}
