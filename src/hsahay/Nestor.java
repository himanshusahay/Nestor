package hsahay;

import java.util.ArrayList;

import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.view.BuildablePileView;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.IntegerView;
import ks.launcher.Main;

public class Nestor extends Solitaire {

	Deck deck;
	Column[] column = new Column[8];
	BuildablePile reserve;
	
	ColumnView[] columnView = new ColumnView[8];
	BuildablePileView reserveView;
	IntegerView scoreView;

	
	@Override
	public String getName() {
		return "hsahay-Nestor";
	}

	@Override
	public boolean hasWon() {
		return false;
	}

	@Override
	public void initialize() {
		// initialize model
		initializeModel(getSeed());
		initializeView();
		initializeControllers();

		/*prepare the game by dealing cards to all columns, 
		 * with one face up card with no overlap on it
		 */
		
//		ArrayList<Integer> cardRanksReserve = new ArrayList<Integer>();
//		
		ArrayList<Integer> cardRanks0 = new ArrayList<Integer>();
		ArrayList<Integer> cardRanks1 = new ArrayList<Integer>();
		ArrayList<Integer> cardRanks2 = new ArrayList<Integer>();
		ArrayList<Integer> cardRanks3 = new ArrayList<Integer>();
		ArrayList<Integer> cardRanks4 = new ArrayList<Integer>();
		ArrayList<Integer> cardRanks5 = new ArrayList<Integer>();
		ArrayList<Integer> cardRanks6 = new ArrayList<Integer>();
		ArrayList<Integer> cardRanks7 = new ArrayList<Integer>();
		ArrayList<Card> extras = new ArrayList<Card>();
		
		while(column[7].count()<6){
			
			if(deck.count() == 0){
				while(extras.size() > 0){
					deck.add(extras.remove(0));
				}
			}
			
			Card c = deck.peek();
			boolean added = false;
			Integer rank = c.getRank();
			if(column[0].count() < 6){
				if( !( cardRanks0.contains(c.getRank()) ) ){
						column[0].add(deck.get());
						added = true;
						cardRanks0.add(rank);
				}
			}
			
			else if(column[1].count() < 6){
				if( !( cardRanks1.contains(c.getRank()) ) ){
					column[1].add(deck.get());
					added = true;
					cardRanks1.add(c.getRank());
				}
			}
			
			else if(column[2].count() < 6){
				if( !( cardRanks2.contains(c.getRank()) ) ){
					column[2].add(deck.get());
					added = true;
					cardRanks2.add(c.getRank());
				}
			}
			
			else if(column[3].count() < 6){
				if( !( cardRanks3.contains(c.getRank()) ) ){
					column[3].add(deck.get());
					added = true;
					cardRanks3.add(c.getRank());
				}
			}

			else if(column[4].count() < 6){
				if( !( cardRanks4.contains(c.getRank()) ) ){
					column[4].add(c);
					added = true;
					cardRanks4.add(c.getRank());
				}
			}
			
			else if(column[5].count() < 6){
				if( !( cardRanks5.contains(c.getRank()) ) ){
					column[5].add(deck.get());
					added = true;
					cardRanks5.add(c.getRank());
				}
			}
			else if(column[6].count() < 6){
				if( !( cardRanks6.contains(c.getRank()) ) ){
					column[6].add(deck.get());
					added = true;
					cardRanks6.add(c.getRank());
				}
			}
			
			else if(column[7].count() < 6){
				if( !( cardRanks7.contains(c.getRank()) ) ){
					column[7].add(deck.get());
					added = true;
					cardRanks7.add(c.getRank());
				}
			}
			
			if(added == false){
				extras.add(deck.get());
			}
		}

		
//		for (int colNum=0; colNum <=7; colNum++) {
//			
//			ArrayList<Card> c = new ArrayList<Card>();
//			ArrayList<Integer> cardRanks = new ArrayList<Integer>();
//			int i = 0;
//			//First inside card
//			c.add(deck.get());
//			//Add rank of first card to ArrayList of card ranks
//			cardRanks.add((c.get(i).getRank()));
//			c.get(i).setFaceUp (true);
//			column[colNum].add (c.get(i));
//			
//			ArrayList<Card> cardsAtDeckBottom = new ArrayList<Card>();
//			
//			//next 5 cards leading to the top card
//			while(deck.count()<=4)	{
//			
//				Card c = deck.get();

				
//				if(nextCard == null){
//					while (!(cardsAtDeckBottom.isEmpty())){
//						deck.add(cardsAtDeckBottom.remove(0));
//					}
//					nextCard = deck.get();
//				}
					
//				boolean nextCardUsed = false;
//				Integer tempRank = nextCard.getRank();
//				if (!(cardRanks.contains(tempRank))){		
//					c.add(nextCard);
//					nextCardUsed = true;
//					i+=1;
//				 	c.get(i).setFaceUp (true);
//					column[colNum].add (c.get(i));
//				}
//				else{
//					cardsAtDeckBottom.add(0, nextCard);
//				}
//				if(nextCardUsed == false){
//					
//				}
				
// Reserve Cards
 		//alternative could be, while deck is not empty, deal cards to reserve and then at last iteration, make card faceup
						for (int i =0; i<3; i++)
						{
							Card cR = deck.get();
							
							cR.setFaceUp(false);
							reserve.add (cR);
						}
						// this card is faceup initially
						reserve.add (deck.get());
						
					
	}

	private void initializeControllers() {
		// Now for each BuildablePile.
				for (int i = 0; i <= 7; i++) {
					columnView[i].setMouseAdapter (new NestorColumnController (this, columnView[i]));
					columnView[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
					columnView[i].setUndoAdapter (new SolitaireUndoAdapter(this));
				}
		
				reserveView.setMouseAdapter (new NestorBuildablePileController (this, reserveView));
				reserveView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
				reserveView.setUndoAdapter (new SolitaireUndoAdapter(this));
				
				// same for scoreView
				scoreView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
				scoreView.setMouseAdapter (new SolitaireReleasedAdapter(this));
				scoreView.setUndoAdapter (new SolitaireUndoAdapter(this));

	}

	private void initializeView() {

		CardImages ci = getCardImages();
		
		// create ColumnViews, one after the other with 6 cards in each column
		for (int colNum = 1; colNum <=8; colNum++) {
			columnView[colNum-1] = new ColumnView (column[colNum-1]);
			columnView[colNum-1].setBounds (20+(colNum-1)*10+(colNum-1)*ci.getWidth(), 0, ci.getWidth(), 5*ci.getOverlap()+ci.getHeight());
			container.addWidget (columnView[colNum-1]);
		}
		// create BuildablePileViews, one after the other 
		reserveView = new BuildablePileView(reserve);
		reserveView.setBounds (20, 50+(5*ci.getOverlap())+ci.getHeight(), ci.getWidth(), ci.getOverlap()*4+ci.getHeight());
		container.addWidget (reserveView);
		
		scoreView = new IntegerView (getScore());
		scoreView.setFontSize(16);
		scoreView.setColor(java.awt.Color.white);
		scoreView.setBounds (680,0,100,60);
		container.addWidget (scoreView);
		
		updateScore(0);
				
	} 

	private void initializeModel(int seed) {
		deck = new Deck("deck");
		deck.create(seed);
		model.addElement(deck);   // add to our model (as defined within our superclass).
		
		reserve = new BuildablePile("reserve");
		model.addElement(reserve);
		
		// each of the columns appears here
		for (int i = 0; i<8; i++) {
			column[i] = new Column("column" + i); 
			model.addElement (column[i]);
		} 
		
	}

	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new Nestor(), randomNum(-8,8));
	}
	
	public static int randomNum(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

}

