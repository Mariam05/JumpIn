package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CommandTest.class,  FoxTest.class, MushroomTest.class, 
		RabbitTest.class, GameTest.class, NodeTest.class, RedoTest.class, UndoTest.class, SaveLoadTest.class })
public class AllTests {

}
