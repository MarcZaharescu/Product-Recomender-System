package NaturalLanguageProcessing;

import java.util.HashSet;
import java.util.Set;

public class PorterAlgorithm {

	static char[] vowels = { 'a', 'e', 'i', 'o', 'u' };

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		System.out.println(Rule4("realize"));
		
		System.out.println(ConsonantFrequency("re"));
	 

		 

	}

	public static String Stem(String word) {
		word = Rule1(word);
		word = Rule2(word);
		word = Rule3(word);
		word = Rule4(word);
		word = Rule5(word);
		word = Rule6(word);

		return word;
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

	public static String turnIntoCV(String para_word) {
		// *helper function* - changing a word into a [V]CV[C] stream -
		String temp = "";
		for (int i = 0; i < para_word.length() - 1; i++) {

			// beginning special cases
			if (i == 0) {
				if (para_word.charAt(i) == 'y'
						&& !checkIfVowel(para_word.charAt(i + 1)))
					temp = temp + "V";
			}

			if (i > 0) {
				if (para_word.charAt(i) == 'y'
						&& !checkIfVowel(para_word.charAt(i + 1))
						&& !(checkIfVowel(para_word.charAt(i + -1)))) {
					temp = temp + "V";
				}
			}

			// normal cases
			if ((checkIfVowel(para_word.charAt(i)))
					&& !checkIfVowel(para_word.charAt(i + 1))) {
				temp = temp + "V";
			} else if (!checkIfVowel(para_word.charAt(i))
					&& (para_word.charAt(i + 1) == 'y' || checkIfVowel(para_word
							.charAt(i + 1)))) {
				temp = temp + "C";

			}

			// ending special cases
			if (para_word.length() >= 3) {
				if (i == para_word.length() - 2) {
					if (para_word.charAt(i + 1) == 'y'
							&& !checkIfVowel(para_word.charAt(i - 1)))
						temp = temp + "V";

				}
			}
		
			if (para_word.length() >= 2) {
				if (i == para_word.length() - 2) {
					if (checkIfVowel(para_word.charAt(i + 1)))
						temp = temp + "V";
					else
						temp = temp + "C";
				}
			}

		}

		return temp;
	}

	public static int ConsonantFrequency(String para_word) {
		// if C-V return 0
		// if C VC V return 1
		// if C VCVC V return 2

		int frequency = 0;
		String temp = turnIntoCV(para_word);
 
		for (int i = 0; i < temp.length() - 1; i++) {
			if (temp.charAt(i) == 'V' && temp.charAt(i + 1) == 'C')
				frequency++;
		}

		return frequency;
	}

	public static String transformS(String s1, String s2, String s) {
		if (s.length() >= s.length() - s1.length()) {
			String root = s.substring(0, s.length() - s1.length());

			if (ConsonantFrequency(root) > 0)
				return root + s2;
		}

		return s;
	}

	public static boolean endsWith(String sub, String s) {
		if (s.length() >= sub.length())
			return s.substring(s.length() - sub.length(), s.length()).equals(
					sub);

		return false;
	}

	public static boolean CVC(int pos, String para_word) {
		// checks wherer the previous 3 characters before pos were c-v-c

		if (para_word.length() >= pos && para_word.length()>3) {
			if (!checkIfVowel(para_word.charAt(pos - 1))
					&& checkIfVowel(para_word.charAt(pos - 2))
					&& !checkIfVowel(para_word.charAt(pos - 3)))
				return true;
		}

		return false;
	}

	public static String Rule1(String para_word) {
		 
		
		int check = 0;
		boolean flag_1 = false, flag_2 = false;
		int last = para_word.length();
		
		// if it ends in s
		if (para_word.charAt(last - 1) == 's') {

			if (last >= 5) {
				// if it ends in ssess -> ss
				if (para_word.substring(last - 5, last).equals("ssess")) {
					para_word = para_word.substring(0, last - 3);
					flag_1 = true;
				}
			}
			if (last >= 3 && flag_1 == false) {
				// if it ends in ies -> i
				if (para_word.substring(last - 3, last).equals("ies")) {
					para_word = para_word.substring(0, last - 2);
					flag_1 = true;
				}

				else if (para_word.charAt(last - 2) != 's') {
					para_word = para_word.substring(0, last - 1);
					flag_1 = true;
				}
			}
		}

		if (last >= 3 && flag_1 == false) {
			// if it ends in eed
			if (para_word.substring(last - 3, last).equals("eed")) {
				if (ConsonantFrequency(para_word.substring(0, last - 3)) > 0) {
					para_word = para_word.substring(0, last - 2);
					flag_2 = true;
					last = para_word.length();
				}
			} else {
				// if the word ends in ed and vowel in steam get to the root
				if ((para_word.substring(last - 2, last).equals("ed") && wordSteamHasVowel(para_word
						.substring(last - 2, last)))) {

					para_word = para_word.substring(0, last - 2);
					flag_2 = true;
					last = para_word.length();
				}
				// if the word ends in ing and vowel in steam get to the root
				else if (para_word.substring(last - 3, last).equals("ing")
						&& wordSteamHasVowel(para_word
								.substring(0, last-3))) {

					para_word = para_word.substring(0, last - 3);
					flag_2 = true;
					last = para_word.length();

				}
				// if it ends in at or bl or iz -> add and e
				if (flag_2 == true
						&& (para_word.substring(last - 2, last).equals("at")
								|| para_word.substring(last - 2, last).equals(
										"bl") || para_word.substring(last - 2,
								last).equals("iz"))) {
					para_word = para_word + "e";
				} else
				// double consonant ending
				if (flag_2 == false && checkIfVowel(para_word.charAt(last - 1))
						&& checkIfVowel(para_word.charAt(last - 2))) {
					// remove last letter if its not 'l' or 's' or 'z'
					check = 0;
					if (!((para_word.charAt(last - 1) == 'l'
							|| para_word.charAt(last - 1) == 's' || para_word
								.charAt(last - 1) == 'z'))) {
						// para_word = para_word.substring(0, last - 1);

						check = 1;
					}
				} else
				// if consonant frequency ==0 and cvc(word) = true set to e
				if (flag_2 == false && ConsonantFrequency(para_word) == 0
						&& CVC(last - check - 1, para_word)) {
					para_word = para_word + "e";
				}
			}
		}

		return para_word;

	}

	public static String Rule2(String para_word) {
		// turns terminal y in i if there is another vowel in steam
		int last = para_word.length();

		// if it ends in y and there is a vowel in steam change it to i
		if ((para_word.charAt(last - 1) == 'y') && wordSteamHasVowel(para_word)) {
			para_word = para_word.substring(0, last - 1) + "i";
		}

		return para_word;
	}

	public static String Rule3(String para_word) {
		// maps double suffixes to single ones
		int last = para_word.length();

		switch (para_word.charAt(last - 2)) {
		case 'a':
			if (endsWith("ational", para_word)) {
				para_word = transformS("ational", "ate", para_word);
				break;
			}
			if (endsWith("tional", para_word)) {
				para_word = transformS("tional", "tion", para_word);
				break;
			}

		case 'c':

			if (endsWith("enci", para_word)) {
				para_word = transformS("enci", "ence", para_word);
				break;
			}
			if (endsWith("anci", para_word)) {
				para_word = transformS("anci", "ance", para_word);
				break;
			}
		case 'e':
			if (endsWith("izer", para_word)) {
				para_word = transformS("izer", "ize", para_word);
				break;
			}

		case 'l':
			if (endsWith("bli", para_word)) {
				para_word = transformS("bli", "ble", para_word);
				break;
			}
			if (endsWith("alli", para_word)) {
				para_word = transformS("alli", "al", para_word);
				break;
			}
			if (endsWith("entli", para_word)) {
				para_word = transformS("entli", "ent", para_word);
				break;
			}
			if (endsWith("eli", para_word)) {
				para_word = transformS("eli", "e", para_word);
				break;
			}
			if (endsWith("ousli", para_word)) {
				para_word = transformS("ousli", "ous", para_word);
				break;
			}

		case 'o':
			if (endsWith("ization", para_word)) {
				para_word = transformS("ization", "ize", para_word);
				break;
			}
			if (endsWith("ation", para_word)) {
				para_word = transformS("ation", "ate", para_word);
				break;
			}
			if (endsWith("ator", para_word)) {
				para_word = transformS("ator", "ate", para_word);
				break;
			}
		case 's':
			if (endsWith("alism", para_word)) {
				para_word = transformS("alism", "al", para_word);
				break;
			}
			if (endsWith("iveness", para_word)) {
				para_word = transformS("iveness", "ive", para_word);
				break;
			}
			if (endsWith("fulness", para_word)) {
				para_word = transformS("fulness", "ful", para_word);
				break;
			}
			if (endsWith("ousness", para_word)) {
				para_word = transformS("ousness", "ous", para_word);
				break;
			}
		case 't':
			if (endsWith("aliti", para_word)) {
				para_word = transformS("aliti", "al", para_word);
				break;
			}
			if (endsWith("iviti", para_word)) {
				para_word = transformS("iviti", "ive", para_word);
				break;
			}
			if (endsWith("biliti", para_word)) {
				para_word = transformS("biliti", "ble", para_word);
				break;
			}
		case 'g':
			if (endsWith("logi", para_word)) {
				para_word = transformS("logi", "log", para_word);
				break;
			}

		}

		return para_word;
	}

	public static String Rule4(String para_word) {
		// deals with -full and -ness
		int last = para_word.length();

		switch (para_word.charAt(last - 1)) {
		case 'e':
			if (endsWith("icate", para_word)) {
				para_word = transformS("icate", "ic", para_word);
				break;
			}
			if (endsWith("ative", para_word)) {
				para_word = transformS("ative", "", para_word);
				break;
			}
			if (endsWith("alize", para_word)) {
				para_word = transformS("alize", "al", para_word);
				break;
			}
		case 'i':
			if (endsWith("iciti", para_word)) {
				para_word = transformS("iciti", "ic", para_word);
				break;
			}
		case 'l':
			if (endsWith("ical", para_word)) {
				para_word = transformS("ical", "ic", para_word);
				break;
			}
			if (endsWith("ful", para_word)) {
				para_word = transformS("ful", "", para_word);
				break;
			}
		case 's':
			if (endsWith("ness", para_word)) {
				para_word = transformS("ness", "", para_word);
				break;
			}
		}

		return para_word;
	}

	public static String Rule5(String para_word) {
		// deals with -ant and -ence
		int last = para_word.length();

		switch (para_word.charAt(last - 2)) {
		case 'a':
			if (endsWith("al", para_word)) {
				break;
			}
		case 'c':
			if (endsWith("ance", para_word)) {
				break;
			}
			if (endsWith("ence", para_word)) {
				break;
			}
		case 'e':
			if (endsWith("er", para_word)) {
				break;
			}
		case 'i':
			if (endsWith("ic", para_word)) {
				break;
			}
		case 'l':
			if (endsWith("able", para_word)) {
				break;
			}
			if (endsWith("ible", para_word)) {
				break;
			}
		}
		return para_word;
	}

	public static String Rule6(String para_word) {
		// removes final e
		int last = para_word.length();
		int x;

		if (para_word.charAt(last - 1) == 'e') {
			x = ConsonantFrequency(para_word.substring(0, last - 1));
			if (x > 1
					|| (x == 1 && !CVC(last - 1, para_word) && para_word
							.length() > 3)) {
				para_word = para_word.substring(0, last - 1);
			}
		} else if (para_word.charAt(last - 1) == 'l'
				&& para_word.charAt(last - 2) == 'l'
				&& ConsonantFrequency(para_word.substring(0, last - 1)) > 1) {

			para_word = para_word.substring(0, last - 1);
		}

		return para_word;

	}
}
