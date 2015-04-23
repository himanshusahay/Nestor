package hsahay;

import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.view.BuildablePileView;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;

public class NestorBuildablePileController extends SolitaireReleasedAdapter {

	/** The Nestor Game. */
	protected Nestor theGame;

	/** The specific Column columnView being controlled. */
	protected BuildablePileView src;

	
	public NestorBuildablePileController(Nestor theGame, BuildablePileView reserveView) {
		super(theGame);
		this.theGame = theGame;
		this.src = reserveView;
	}
	
	/**
	 * Coordinate reaction to the beginning of a Drag Event.
	 * <p>
	 * @param me java.awt.event.MouseEvent
	 */
	public void mousePressed(MouseEvent me) {
		 
			// The container manages several critical pieces of information; namely, it
			// is responsible for the draggingObject; in our case, this would be a CardView
			// Widget managing the card we are trying to drag between two piles.
			Container c = theGame.getContainer();
		
			// Get a card to move from ColumnView. Note: this returns a CardView.
			// Note that this method will alter the model for BuildablePileView if the condition is met.
			ColumnView columnView = src.getColumnView(me);
//			CardView cardView = columnView.getCardViewForTopCard (me);
			
			// an invalid selection of some sort.
			if (columnView == null) {
				c.releaseDraggingObject();
				return;
			}
			
			// If we get here, then the user has indeed clicked on the top card in the ColumnView of the BuildblePileView and
			// we are able to now move it on the screen at will. For smooth action, the bounds for the
			// cardView widget reflect the original card location on the screen.
			Widget w = c.getActiveDraggingObject();
			if (w != Container.getNothingBeingDragged()) {
				System.err.println ("NestorBuildablePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
				return;
			}
		
			// Tell container which object is being dragged, and where in that widget the user clicked.
			c.setActiveDraggingObject (columnView, me);
			
			// Tell container which source widget initiated the drag
			c.setDragSource (src);

			
			src.redraw();
	}

	/**
	 * Coordinate reaction to the completion of a Drag Event.
	 * @param me java.awt.event.MouseEvent
	 */
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();
		
		/** Return if there is no card being dragged chosen. */
		Widget draggingWidget = c.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("NestorBuildablePileController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}

		
		/** Recover the from Column or BuildablePile */
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("NestorBuildablePileController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}
		
	
		//Normal case when pair made from Column to Reserve
		if(fromWidget.getModelElement() instanceof Column) {
			Column from = (Column) fromWidget.getModelElement();
			// Determine the To BuildablePile
			BuildablePile to = (BuildablePile) src.getModelElement();
			
			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			
			Move move = new PairColumnReserveMove(from, to, theCard);
			if (move.doMove(theGame)) {
				theGame.pushMove (move);     // Successful Move has been made
			}
			else{
				fromWidget.returnWidget(draggingWidget);
			}
		
		}
		
//!!!!!When card (columnView) dragged from BuildablePile to random space on board and released. Should go back to the BuildablePile.
		else if(fromWidget.getModelElement() instanceof BuildablePile) {
// Not working			
			fromWidget.returnWidget (draggingWidget);
		}
		
		// release the dragging object, (this will reset dragSource)
		c.releaseDraggingObject();
		
		// finally repaint
		c.repaint();
	}

}
