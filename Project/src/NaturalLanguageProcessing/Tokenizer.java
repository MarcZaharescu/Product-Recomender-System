package NaturalLanguageProcessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	private final char[] separators = { ' ', '.', ',', '(', ')', '?', ';' };

	public List<String> separateWords(String input) {

		String regex = "("
				+ new String(separators).replaceAll("(.)", "\\\\$1|")
						.replaceAll("\\|$", ")"); // escape every char with \
													// and turn into "OR"

		String[] temp = input.split(regex);

		List<String> words = Arrays.asList(temp);
		return words;

	}

	public static boolean isGoodWord(String input) {
		/*
		 * char[] cs = input.toCharArray(); for (char c : cs) {
		 * if(!Character.isLetter(c)) { return false; } } return true;
		 */
		return input.matches("[a-z]+");
	}

	public ArrayList<String> removeStopWordsandStem(List<String> inputList) {
		PorterAlgorithm pa = new PorterAlgorithm();

		ArrayList<String> wordList = new ArrayList<String>();
		Set<String> stopWordsSet = new HashSet<String>();
		Set<String> specialWordSet = new HashSet<String>();
		// create a word list
		String[] temp = { "a", "about", "across", "after", "against", "all",
				"alone", "along", "already", "also", "although", "always",
				"among", "an", "and", "another", "any", "anybody", "anyone",
				"anything", "anywhere", "are", "area", "areas", "around", "as",
				"ask", "asked", "asking", "asks", "at", "away", "back",
				"backed", "backing", "backs", "be", "became", "because",
				"become", "becomes", "been", "before", "began", "behind",
				"being", "beings", "best", "between", "big", "both", "but",
				"by", "came", "can", "cannot", "case", "cases", "certain",
				"have", "certainly", "clear", "clearly", "come", "could", "d",
				"did", "had", "work", "differ", "different", "differently",
				"do", "does", "done", "wear", "watch", "time", "down", "down",
				"downed", "downing", "downs", "during", "each", "early",
				"either", "end", "ended", "ending", "ends", "enough", "even",
				"evenly", "ever", "every", "everybody", "everyone",
				"everything", "everywhere", "f", "face", "faces", "fact",
				"facts", "far", "few", "first", "for", "four", "from", "full",
				"fully", "further", "furthered", "furthering", "furthers", "g",
				"get", "gets", "give", "given", "gives", "go", "have", "going",
				"got", "group", "grouped", "grouping", "groups", "having",
				"he", "her", "here", "herself", "him", "himself", "his", "how",
				"however", "i", "if", "in", "into", "is", "it", "i've", "i'm",
				"its", "itself", "j", "just", "k", "keep", "keeps", "kind",
				"knew", "know", "known", "knows", "l", "last", "later",
				"latest", "less", "let", "lets", "longer", "longest", "m",
				"made", "make", "making", "many", "may", "me", "member",
				"members", "men", "might", "more", "most", "mr", "mrs", "much",
				"my", "myself", "n", "needing", "newer", "newest", "next",
				"nobody", "non", "noone", "now", "number", "numbers", "o",
				"of", "off", "often", "old", "older", "oldest", "on", "once",
				"one", "only", "open", "opened", "opening", "opens", "or",
				"order", "ordered", "ordering", "orders", "other", "others",
				"our", "out", "over", "p", "part", "parted", "parting",
				"parts", "per", "perhaps", "place", "places", "point",
				"pointed", "pointing", "points", "present", "presented",
				"presenting", "presents", "put", "puts", "rooms", "said",
				"same", "saw", "say", "says", "second", "seconds", "see",
				"seeming", "seems", "sees", "several", "shall", "she",
				"should", "show", "showed", "showing", "shows", "side",
				"sides", "since", "so", "some", "somebody", "someone",
				"something", "somewhere", "states", "still", "such", "sure",
				"take", "taken", "than", "that", "the", "their", "them",
				"then", "there", "therefore", "these", "they", "thing",
				"things", "think", "thinks", "this", "those", "though",
				"thought", "thoughts", "through", "thus", "to", "today",
				"together", "took", "toward", "turn", "turned", "turning",
				"turns", "two", "u", "under", "until", "up", "upon", "us",
				"use", "used", "uses", "v", "very", "w", "want", "was", "way",
				"ways", "we", "went", "were", "what", "when", "where",
				"whether", "which", "while", "who", "whole", "whose", "why",
				"will", "with", "within", "would", "year", "years", "amazon",
				"heel", "yet", "you", "young", "younger", "youngest", "your",
				"yours", "size", "jean", "shirt", "color", "material", "pair","has",
				"shoe", "bra", "bought", "sock", "boot", "product", "husband","look","fit","shoe","sock", "price","buy"};

		String[] temp1 = { "not", "don't", "didn't", "haven't", "hadn't","like",
				"too", "really", "won't" };
		// add the stop words into the array list
		for (int i = 0; i < temp.length; i++)
			stopWordsSet.add(temp[i]);

		// add the special words into the array list
		for (int i = 0; i < temp1.length; i++)
			specialWordSet.add(temp1[i]);

		String special = "";
		// main loop
		for (String word : inputList) {

			if (special.length() > 0 && isGoodWord(word)) {
				if(word.length()>3)
				wordList.add(special + " " + pa.Stem(word));
				else
			     wordList.add(special + " " + word );
				special = "";
			}
			if (specialWordSet.contains(word)) {
				special = word;
			}

			else if (!stopWordsSet.contains(word) && word.length() > 2
					&& isGoodWord(word)) {

				if (word.length() < 4)
					wordList.add(word);
				else
					wordList.add(pa.Stem(word));
			}

		}

		return wordList;

	}

	public ArrayList<String> NLP(String inputList) { // lower the case
		inputList = toLowerCase(inputList);
		// separate the words
		// remove the stop words
		// stem
		return removeStopWordsandStem(separateWords(inputList));

	}

	public String toLowerCase(String input) {

		return input.toLowerCase();
	}

	public static void main(String args[]) {
		Tokenizer token = new Tokenizer();
		String test = "Hello, this not is a testinged.   too masculine!'i ";
		System.out.println(token.NLP(test));
		System.out.println(isGoodWord("masculine!'i"));

	}
}
