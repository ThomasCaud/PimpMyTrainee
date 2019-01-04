package common;

public class RandomStringGenerator {

	private static final String Xsi = "abcdefghijklmnopqrstuvwxyz";
	private static final String Gamm = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String Iot = "1234567890";

	/**
	 * @param size
	 * @return randomized string, with size equal of the param 'size'
	 */
	public static String getRandomString(Integer size) {

		String alphabet = Xsi + Gamm + Iot;
		Integer l_alphabet = alphabet.length();
		String str = "";

		for (int i = 0; i < size; i++) {
			int k = (int) (Math.random() * (l_alphabet - 1) + 1);
			str += alphabet.charAt(k);
		}

		return str;

	}
}
