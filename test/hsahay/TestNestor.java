package hsahay;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.view.CardImages;
import ks.common.view.CardView;
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
	
	public void testNestor(){
		//testing board initialization
		assertTrue(nestor.deck.empty());
		for(int i=0; i<7; i++)
		{
			assertEquals(nestor.column[i].count(), 6);
		}
		assertEquals(nestor.reserve.count(), 4);
	}
	
	public void testPairColumnsMove() {
		
		ModelFactory.init(nestor.column[0], "5H 6H");
		ModelFactory.init(nestor.column[1],	"5C 6C");
		
		assertEquals(2, nestor.column[0].count());
		assertEquals(2, nestor.column[1].count());
		assertEquals(0, nestor.getScoreValue());
		assertEquals(0, nestor.deck.count());
		
		PairColumnsMove pcm = new PairColumnsMove(nestor.column[0], nestor.column[1], nestor.column[0].get());
		
		assertTrue(pcm.valid(nestor));
		
		pcm.doMove(nestor);
		
		assertEquals(1, nestor.column[0].count());
		assertEquals(1, nestor.column[1].count());
		assertEquals(2, nestor.getScoreValue());
		
		
		pcm.undo(nestor);
		assertEquals(2, nestor.column[0].count());
		assertEquals(2, nestor.column[1].count());
		assertEquals(0, nestor.getScoreValue());
	
		assertEquals("6H", nestor.column[0].peek().toString());
		assertEquals("6C", nestor.column[1].peek().toString());
	}
	
public void testPairColumnReserveMove() {
		
		ModelFactory.init(nestor.column[0], "5H 6H");
		ModelFactory.init(nestor.reserve, "5C KC AS 6S");
		
		assertEquals(2, nestor.column[0].count());
		assertEquals(4, nestor.reserve.count());
		assertEquals(0, nestor.getScoreValue());
		assertEquals(0, nestor.deck.count());
		
		PairColumnReserveMove pcrm = new PairColumnReserveMove(nestor.column[0], nestor.reserve, nestor.column[0].get());
		
		assertTrue(pcrm.valid(nestor));
		
		pcrm.doMove(nestor);
		
		assertEquals(1, nestor.column[0].count());
		assertEquals(3, nestor.reserve.count());
		assertEquals(2, nestor.getScoreValue());
		
		
		pcrm.undo(nestor);
		assertEquals(2, nestor.column[0].count());
		assertEquals(4, nestor.reserve.count());
		assertEquals(0, nestor.getScoreValue());
	
		assertEquals("6H", nestor.column[0].peek().toString());
		assertEquals("6S", nestor.reserve.peek().toString());
	}
	
public void testPairReserveColumnMove() {
	
	ModelFactory.init(nestor.reserve, "5C KC AS 8C");
	ModelFactory.init(nestor.column[0], "5H 8S");
	
	assertEquals(4, nestor.reserve.count());
	assertEquals(2, nestor.column[0].count());
	assertEquals(0, nestor.getScoreValue());
	assertEquals(0, nestor.deck.count());
	
	PairReserveColumnMove prcm = new PairReserveColumnMove(nestor.reserve, nestor.column[0], nestor.reserve.get());
	
	assertTrue(prcm.valid(nestor));
	
	prcm.doMove(nestor);
	
	assertEquals(3, nestor.reserve.count());
	assertEquals(1, nestor.column[0].count());
	assertEquals(2, nestor.getScoreValue());
	
	prcm.undo(nestor);
	assertEquals(4, nestor.reserve.count());
	assertEquals(2, nestor.column[0].count());
	assertEquals(0, nestor.getScoreValue());
	
	assertEquals("8C", nestor.reserve.peek().toString());
	assertEquals("8S", nestor.column[0].peek().toString());
}

	public void testColumnController(){
		ModelFactory.init(nestor.column[0], "3H 4C AD KH");
		ModelFactory.init(nestor.column[1], "3D 7S 5C KC");
		
		assertEquals(4, nestor.column[0].count());
		assertEquals(4, nestor.column[1].count());
		assertEquals(0, nestor.getScoreValue());
		assertEquals(0, nestor.deck.count());
		
		assertEquals ("KH", nestor.column[0].peek().toString());
		assertEquals ("KC", nestor.column[1].peek().toString());
		
		//press to pick up card from first column
		MouseEvent press = this.createPressed(nestor, nestor.columnView[0], 1, 66+80);
		nestor.columnView[0].getMouseManager().handleMouseEvent(press);
		
		// drop on the second column
		MouseEvent release = this.createReleased(nestor, nestor.columnView[1], 100, 66+80);
		nestor.columnView[1].getMouseManager().handleMouseEvent(release);

		assertEquals (3, nestor.column[0].count());
		assertEquals (3, nestor.column[1].count());
		assertEquals ("AD", nestor.column[0].peek().toString());
		assertEquals ("5C", nestor.column[1].peek().toString());
		
		assertTrue (nestor.undoMove());
		assertEquals (4, nestor.column[0].count());
		assertEquals (4, nestor.column[1].count());	
		assertEquals ("KH", nestor.column[0].peek().toString());
		assertEquals ("KC", nestor.column[1].peek().toString());
		
	}


//	public void testBuildablePileController() {
//		// first create a mouse event
//		MouseEvent pr = createPressed (nestor, nestor.columnView[7], 0, 6*nestor.columnView[7].getHeight());
//		nestor.columnView[7].getMouseManager().handleMouseEvent(pr);
//		
//		// drop on the first column
//		MouseEvent rel = createReleased (nestor, nestor.columnView[1], 0, 0);
//		nestor.columnView[1].getMouseManager().handleMouseEvent(rel);
//
//		assertEquals (2, nestor.column[1].count());
//		
////	assertFalse (nestor.column[7].peek().isFaceUp());
//
//		// go ahead and flip card by re-executing move
////		nestor.columnView[7].getMouseManager().handleMouseEvent(pr);
////		assertTrue (nestor.column[7].peek().isFaceUp());
//
//	}

}
