package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CommandTest.class,  FoxTest.class, MushroomTest.class, 
		RabbitTest.class })
public class AllTests {

}
