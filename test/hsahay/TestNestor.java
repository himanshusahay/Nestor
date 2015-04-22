package hsahay;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import ks.tests.model.ModelFactory;

public class TestNestor extends KSTestCase {
	
	Nestor nestor;
	GameWindow gw;
	

	@Override
	protected void setUp() {
		nestor = new Nestor();
		gw = Main.generateWindow(nestor, -3);
	}
	
	@Override
	protected void tearDown() {
		gw.dispose();
	}
	
	public void testPairColumnsMove() {
		PairColumnsMove pcm = new PairColumnsMove(nestor.column[1], nestor.column[3], nestor.column[1].peek());
		
		Card cardMovedFirst = nestor.column[1].peek();
		
		assertTrue(pcm.valid(nestor));
		assertTrue(pcm.doMove(nestor));
		//assertFalse(nestor.column[1].peek() == cardMovedFirst);
		//assertTrue(pcm.undo(nestor));
		
	
	}
	
	public void testBuildablePileController() {
		// first create a mouse event
		MouseEvent pr = createPressed (nestor, nestor.columnView[7], 0, 6*nestor.columnView[7].getHeight());
		nestor.columnView[7].getMouseManager().handleMouseEvent(pr);

		// drop on the first column
		MouseEvent rel = createReleased (nestor, nestor.columnView[1], 0, 0);
		nestor.columnView[1].getMouseManager().handleMouseEvent(rel);

		assertEquals (2, nestor.column[1].count());
		
		assertFalse (nestor.column[7].peek().isFaceUp());

		// go ahead and flip card by re-executing move
		nestor.columnView[7].getMouseManager().handleMouseEvent(pr);
		assertTrue (nestor.column[7].peek().isFaceUp());

//		// undo twice.
//		assertTrue (game.undoMove());
//		assertTrue (game.undoMove());
//		assertEquals (1, game.piles[1].count());
	}

}
