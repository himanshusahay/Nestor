package hsahay;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.view.CardImages;
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
		
		ModelFactory.init(nestor.column[0], "5H 6H");
		ModelFactory.init(nestor.column[1],	"5C 6C");
		
		assertEquals(2, nestor.column[0].count());
		assertEquals(2, nestor.column[1].count());
		assertEquals(0, nestor.getScoreValue());
		
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
		
		assertEquals ("KH", nestor.column[0].peek().toString());
		assertEquals ("KC", nestor.column[1].peek().toString());
		
		CardImages ci = nestor.getCardImages();
		
		//press to pick up card from first column
		MouseEvent press = this.createPressed(nestor, nestor.columnView[0], 25, 3*ci.getOverlap()+ci.getHeight()-20);
		nestor.columnView[0].getMouseManager().handleMouseEvent(press);
		
		// drop on the second column
		MouseEvent release = this.createReleased(nestor, nestor.columnView[1], 25+10+ci.getWidth(), 3*ci.getOverlap()+ci.getHeight()-20);
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


	public void testBuildablePileController() {
		ModelFactory.init(nestor.reserve, "3H 4C 3C QH");
		ModelFactory.init(nestor.column[0], "3D 3S 9H QD 2H QS");
		
		assertEquals(6, nestor.column[0].count());
		assertEquals(4, nestor.reserve.count());
		assertEquals(0, nestor.getScoreValue());
		
		assertEquals ("QS", nestor.column[0].peek().toString());
		assertEquals ("QH", nestor.reserve.peek().toString());
		
		CardImages ci = nestor.getCardImages();
		
		// Pick up card from the column
		MouseEvent press1 = createPressed (nestor, nestor.columnView[0], 25, 5*ci.getOverlap()+ci.getHeight()-20);
		nestor.columnView[0].getMouseManager().handleMouseEvent(press1);		
		
		// Release on the reserve
		MouseEvent release1 = this.createReleased(nestor, nestor.reserveView, 25, 5*ci.getOverlap()+ci.getHeight()+30+ci.getOverlap()*4+ci.getHeight());
		nestor.reserveView.getMouseManager().handleMouseEvent(release1);

		assertEquals (5, nestor.column[0].count());
		assertEquals (3, nestor.reserve.count());
		
		assertEquals ("2H", nestor.column[0].peek().toString());
		assertEquals ("[3C]", nestor.reserve.peek().toString());

		assertTrue (nestor.undoMove());
		assertEquals (6, nestor.column[0].count());
		assertEquals (4, nestor.reserve.count());	
		assertEquals ("QS", nestor.column[0].peek().toString());
		assertEquals ("QH", nestor.reserve.peek().toString());

//		// Pick up card from the column
//		MouseEvent press2 = createPressed (nestor, nestor.reserveView, 25, 5*ci.getOverlap()+ci.getHeight()+ci.getOverlap()*4+ci.getHeight()+30);
//		nestor.reserveView.getMouseManager().handleMouseEvent(press2);		 
//		
//		assertEquals ("QH", nestor.reserve.peek().toString());
//			
//		// Release on the reserve
//		MouseEvent release2 = this.createReleased(nestor, nestor.columnView[0], 25, 5*ci.getOverlap()+ci.getHeight()-20);
//		nestor.columnView[0].getMouseManager().handleMouseEvent(release2);
//
//		assertEquals (5, nestor.column[0].count());
//		assertEquals (3, nestor.reserve.count());
//		
//		assertEquals ("2H", nestor.column[0].peek().toString());
//		assertEquals ("[3C]", nestor.reserve.peek().toString());
//
//		assertTrue (nestor.undoMove());
//		assertEquals (6, nestor.column[0].count());
//		assertEquals (4, nestor.reserve.count());	
//		assertEquals ("QS", nestor.column[0].peek().toString());
//		assertEquals ("QH", nestor.reserve.peek().toString());

	}
}
