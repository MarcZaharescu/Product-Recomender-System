import java.util.HashSet;
import java.util.Set;

public class PorterAlgorithm {

	static char[] vowels = { 'a', 'e', 'i', 'o', 'u' };

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		
		RULE1("possess");

	}

	private static boolean checkIfVowel(char vl) {

		boolean ok = false;
		for (char c : vowels) {
			if (c == vl) {
				ok = true;
				break;
			}
		}

		return ok;
	}

	public static int ConsonantFrequency(String para_word) {
		int frequency = 0, j = 0;
		;
		int length = para_word.length();

		for (int i = length - 1; i >= 0; i--) {
			int nr = 0;
			System.out.println(i + " - " + j);
			if (i - j >= 5) {
				if (checkIfVowel(para_word.charAt(i))
						&& !checkIfVowel(para_word.charAt(i - 1))
						&& checkIfVowel(para_word.charAt(i - 2))
						&& !checkIfVowel(para_word.charAt(i - 3))
						&& checkIfVowel(para_word.charAt(i - 4))
						&& !checkIfVowel(para_word.charAt(i - 5))) {
					return 2;
				}

			} else if (i - j >= 3) {
				if (checkIfVowel(para_word.charAt(i))
						&& !checkIfVowel(para_word.charAt(i - 1))
						&& checkIfVowel(para_word.charAt(i - 2))
						&& !checkIfVowel(para_word.charAt(i - 3))) {
					return 1;
				}

				else
					return 0;
			}

			// if C-V return 0
			// if C VC V return 1
			// if C VCVC V return 2
			j++;
		}
		return frequency;
	}

	public static String r(String s) {
		if (ConsonantFrequency(s) > 0)
			return "str";

		return "";
	}

	public static boolean CVC(int pos, String para_word) {
		// checks wherer the previous 3 characters before pos were c-v-c

		if (para_word.length() >= pos) {
			if (!checkIfVowel(para_word.charAt(pos - 1))
					&& checkIfVowel(para_word.charAt(pos - 2))
					&& !checkIfVowel(para_word.charAt(pos - 3)))
				return true;
		}

		return false;
	}

	public static void RULE1(String para_word) {
		int last = para_word.length() - 1;
		// if it ends in s
		if (para_word.charAt(last) == 's')
			if (last + 1 >= 5){ 
				// if it ends in ssess -> ss
				if (para_word.substring(last - 5, last).equals("ssess")) {
					para_word=para_word.substring(0,last-4);System.out.println("ok");
				}
			}

		
		System.out.println(para_word.substring(last - 5, last));
		// if it ends in ies -> i

		// else -- last char

		// if it ends in eed or ends in ing and vowel in stea,
		// k=j
		// if ends in at set to ate
		// if ends in bl
		// set to ble
		// if ends in ix
		// set to ize
		// else if doublex(k)
		// k--
		// int ch = b[k]
		// if ch=='1' or ch=='s' or ch=='z'
		// k++
		// else if M()==1 && cvc(k)
		// set to "e"

	}
}
