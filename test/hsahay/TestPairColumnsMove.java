package hsahay;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import ks.tests.model.ModelFactory;

public class TestPairColumnsMove extends KSTestCase {
	
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
	
	public void testPairFromColumnToColumn() {
		PairColumnsMove pcm = new PairColumnsMove(nestor.column[1], nestor.column[3], nestor.column[1].peek());
		
		Card cardMovedFirst = nestor.column[1].peek();
		
		assertTrue(pcm.valid(nestor));
		assertTrue(pcm.doMove(nestor));
		//assertFalse(nestor.column[1].peek() == cardMovedFirst);
		//assertTrue(pcm.undo(nestor));
		
	
	}

}
