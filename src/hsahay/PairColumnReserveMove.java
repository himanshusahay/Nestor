package hsahay;

import ks.common.games.Solitaire;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Stack;

	/**
	 * Move card from top of a Column to the top of another Column
	 * @author Himanshu
	 *
	 */


public class PairColumnReserveMove extends Move {

	Column from;
	BuildablePile to;
	Card cardBeingDragged;
	Stack stack;
	
	
	public PairColumnReserveMove(Column from, BuildablePile to, Card cardBeingDragged) {
		this.from = from;
		this.cardBeingDragged = cardBeingDragged;
		this.to = to;
		this.stack = new Stack();

	}
	
	@Override
	public boolean doMove(Solitaire game) {
		
			if (!valid(game)) { return false; }
			
			Card toCard = to.get();
			stack.add(cardBeingDragged);
			stack.add(toCard);
			game.updateScore(+2);
			
			//flip the top card on the reserve BuildablePileView after the previous top card is used
			if(!(to.empty())) {
			to.flipCard();
			}
			
			return true;
		
	}

	@Override
	public boolean undo(Solitaire game) {
		
		Card toCard = stack.get();
		if(to.count()<4){
			to.flipCard();
		}
		to.add(toCard);
		
		Card fromCard = stack.get();
		from.add(fromCard);
		game.updateScore(-2);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(to.empty()) {
			return false;
		}
		
		int toCardRank = to.rank();
		
		//Rank of card which in the 'to' BuildablePile which will be paired with the cardBeingDragged
		if(cardBeingDragged.getRank() == toCardRank) {
			return true;
		}
			
		//not a match!
		return false;
		
	}
}
