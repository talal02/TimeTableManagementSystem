import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({PositiveTests.class, NegativeTests.class})
public class TestSuite {



}
