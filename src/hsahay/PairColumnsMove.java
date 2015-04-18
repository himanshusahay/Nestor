package hsahay;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Stack;

	/**
	 * Move card from top of a Column to the top of another Column
	 * @author Himanshu
	 *
	 */


public class PairColumnsMove extends Move {

	Column from;
	Column to;
	Card cardBeingDragged;
	Stack stack;
	
	
	public PairColumnsMove(Column from, Column to, Card cardBeingDragged) {
		this.from = from;
		this.cardBeingDragged = cardBeingDragged;
		this.to = to;

	}
	
	@Override
	public boolean doMove(Solitaire game) {
		
			if (!valid(game)) { return false; }
			
			Card fromCard = from.get();
			Card toCard = to.get();
			stack.add(fromCard);
			stack.add(toCard);
			game.updateScore(+2);
			
			return true;
		
	}

	@Override
	public boolean undo(Solitaire game) {
		
		Card toCard = stack.get();
		to.add(toCard);
		Card fromCard = stack.get();
		from.add(fromCard);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(!(from.empty())){
			return false;
		}
		
		int toCardRank = to.rank();
		//Rank of card which in the 'to' column which will be paired with the cardBeingDragged
		
		if(cardBeingDragged.getRank() == toCardRank) {
			return true;
		}
		
		//not a match!
		return false;
		
	}
}
