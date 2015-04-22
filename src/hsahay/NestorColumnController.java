package hsahay;

import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;

public class NestorColumnController extends SolitaireReleasedAdapter {

	
	/** The Nestor Game. */
	protected Nestor theGame;

	/** The specific Column columnView being controlled. */
	protected ColumnView src;
	
	/**
	 * NestorFoundationController constructor comment.
	 */
	public NestorColumnController(Nestor theGame, ColumnView columnView) {
		
		super(theGame);
		this.theGame = theGame;
		this.src = columnView;
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
			CardView cardView = src.getCardViewForTopCard (me);
			
			// an invalid selection of some sort.
			if (cardView == null) {
				c.releaseDraggingObject();
				return;
			}
			
			// If we get here, then the user has indeed clicked on the top card in the ColumnView and
			// we are able to now move it on the screen at will. For smooth action, the bounds for the
			// cardView widget reflect the original card location on the screen.
			Widget w = c.getActiveDraggingObject();
			if (w != Container.getNothingBeingDragged()) {
				System.err.println ("NestorColumnController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
				return;
			}
		
			// Tell container which object is being dragged, and where in that widget the user clicked.
			c.setActiveDraggingObject (cardView, me);
			
			// Tell container which source widget initiated the drag
			c.setDragSource (src);
		
			// The only widget that could have changed is ourselves. If we called refresh, there
			// would be a flicker, because the dragged widget would not be redrawn. We simply
			// force the Column's image to be updated, but nothing is refreshed on the screen.
			// This is patently OK because the card has not yet been dragged away to reveal the
			// card beneath it.  A bit tricky and I like it!
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
			System.err.println ("NestorColumnController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}

		/** Recover the from BuildablePile */
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("NestorColumnController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}

		if(fromWidget.getModelElement() instanceof Column){
			Column from = (Column) fromWidget.getModelElement();
			
			// Determine the To Pile
			Column to = (Column) src.getModelElement();
			
			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			
			Move move = new PairColumnsMove(from, to, theCard);
		
			if (move.doMove(theGame)) {
				theGame.pushMove (move);     // Successful Move has been made
				theGame.refreshWidgets();
			} else {
				fromWidget.returnWidget (draggingWidget);
			}
		}
		else if(fromWidget.getModelElement() instanceof BuildablePile){
			BuildablePile from = (BuildablePile) fromWidget.getModelElement();
			
			// Determine the To Pile
			Column to = (Column) src.getModelElement();
			
			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			
			Move move = new PairReserveColumnMove(from, to, theCard);
			
			if (move.doMove(theGame)) {
				theGame.pushMove (move);     // Successful Move has been made
				theGame.refreshWidgets();
			} else {
				fromWidget.returnWidget (draggingWidget);
			}
			
			//flip the top card on the reserve BuildablePileView after the previous top card is used
			if(!(from.empty())){
				from.flipCard();
			}
		}
		else{
			throw new NullPointerException("Not being dragged from anything. If this case is reached, something in wrong.");
		}
		
		// release the dragging object, (this will reset dragSource)
		c.releaseDraggingObject();
		
		// finally repaint
		c.repaint();
	}

}



