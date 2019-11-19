package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CommandTest.class, CommandWordTest.class, FoxTest.class, MushroomTest.class, ParserTest.class,
		RabbitTest.class })
public class AllTests {

}
