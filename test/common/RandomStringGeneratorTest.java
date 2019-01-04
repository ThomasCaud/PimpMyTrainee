package common;

import junit.framework.TestCase;

public class RandomStringGeneratorTest extends TestCase {
	public RandomStringGeneratorTest(String testName) {
		super(testName);
	}

	public void testGetRandomString() {
		String res = RandomStringGenerator.getRandomString(17);
		assert (res.length() == 17);

		String res2 = RandomStringGenerator.getRandomString(121);
		assert (res2.length() == 121);

		String res3 = RandomStringGenerator.getRandomString(17);
		assert (!res.equals(res3));
	}
}