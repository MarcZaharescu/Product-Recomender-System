package NaturalLanguageProcessing;
import java.util.HashSet;
import java.util.Set;

public class PorterAlgorithm {

	static char[] vowels = { 'a', 'e', 'i', 'o', 'u' };

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		System.out.println(Rule1("ponies"));
		System.out.println(wordSteamHasVowel(""));

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

	private static boolean wordSteamHasVowel(String para_word) {

		for (int i = 0; i < para_word.length(); i++)
			if (checkIfVowel(para_word.charAt(i)))
				return true;

		return false;
	}

	public static int ConsonantFrequency(String para_word) {
		int frequency = 0, j = 0;
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

	public static String Rule1(String para_word) {
		int check = 0;
		boolean flag_1=false,flag_2=false;
		int last = para_word.length();
		
		// if it ends in s
		if (para_word.charAt(last - 1) == 's') {
			
			if (last >= 5) {
				// if it ends in ssess -> ss
				if (para_word.substring(last - 5, last).equals("ssess")) {
					para_word = para_word.substring(0, last - 3);
					flag_1=true;
				}
			}
			if (last >= 3 && flag_1==false) {
				// if it ends in ies -> i
				if (para_word.substring(last - 3, last).equals("ies")) {
					para_word = para_word.substring(0, last - 2);
					flag_1=true;
				}

				else if (para_word.charAt(last - 2) != 's') {
					para_word = para_word.substring(0, last - 1);
					flag_1=true;
				}
			}
		}
		 
		 
		if (last >= 3 && flag_1==false ) {
			// if it ends in eed
			if (para_word.substring(last - 3, last).equals("eed")
					&& (ConsonantFrequency(para_word) > 0)) {
				para_word = para_word.substring(0, last - 2);
				flag_2=true;last = para_word.length();
			} else {
				// if the word ends in ed and vowel in steam get to the root
				if ((para_word.substring(last - 2, last).equals("ed") && wordSteamHasVowel(para_word
						.substring(last - 2, last)))) {

					para_word = para_word.substring(0, last - 2);
					flag_2=true;last = para_word.length();
				}
				// if the word ends in ing and vowel in steam get to the root
				else if (para_word.substring(last - 3, last).equals("ing")
						&& wordSteamHasVowel(para_word
								.substring(last - 3, last))) {

					para_word = para_word.substring(0, last - 3);
					flag_2=true;
					last = para_word.length();

				}
				// if it ends in at or bl or iz -> add and e
				if (flag_2==true &&(para_word.substring(last - 2, last).equals("at")
						|| para_word.substring(last - 2, last).equals("bl")
						|| para_word.substring(last - 2, last).equals("iz"))) {
					para_word = para_word + "e";
				} else
				// double consonant ending
				if (flag_2==false && checkIfVowel(para_word.charAt(last - 1))
						&& checkIfVowel(para_word.charAt(last - 2))) {
					// remove last letter if its not 'l' or 's' or 'z'
					check = 0;
					if (!((para_word.charAt(last - 1) == 'l'
							|| para_word.charAt(last - 1) == 's' || para_word
								.charAt(last - 1) == 'z'))){
					// para_word = para_word.substring(0, last - 1);
					
						check = 1;
					}
				} else
				// if consonant frequency ==0 and cvc(word) = true set to e
				if (flag_2==false &&ConsonantFrequency(para_word) == 0
						&& CVC(last - check - 1, para_word)) {
					 para_word= para_word+"e";
				}
			}
		}

		return para_word;

		//

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
