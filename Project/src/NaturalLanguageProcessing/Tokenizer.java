package NaturalLanguageProcessing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	private final char[] separators = { ' ', '.', ',' };

	public List<String> separateWords(String input) {

		String regex = "("
				+ new String(separators).replaceAll("(.)", "\\\\$1|")
						.replaceAll("\\|$", ")"); // escape every char with \
													// and turn into "OR"

		String[] temp = input.split(regex);

		List<String> words = Arrays.asList(temp);
		return words;

	}

	public ArrayList<String> removeStopWordsandStem(List<String> inputList) {
		PorterAlgorithm pa= new PorterAlgorithm();
		
		ArrayList<String> wordList = new ArrayList<String>();
		Set<String> stopWordsSet = new HashSet<String>();
		Set<String> specialWordSet = new HashSet<String>();
		// create a word list
		String[] temp = { "a", "about", "above", "across", "after", "against",
				"all", "almost", "alone", "along", "already", "also",
				"although", "always", "among", "an", "and", "another", "any",
				"anybody", "anyone", "anything", "anywhere", "are", "area",
				"areas", "around", "as", "ask", "asked", "asking", "asks",
				"at", "away", "b", "back", "backed", "backing", "backs", "be",
				"became", "because", "become", "becomes", "been", "before",
				"began", "behind", "being", "beings", "best", "between", "big",
				"both", "but", "by", "c", "came", "can", "cannot", "case",
				"cases", "certain", "certainly", "clear", "clearly", "come",
				"could", "d", "did", "differ", "different", "differently",
				"do", "does", "done", "down", "down", "downed", "downing",
				"downs", "during", "e", "each", "early", "either", "end",
				"ended", "ending", "ends", "enough", "even", "evenly", "ever",
				"every", "everybody", "everyone", "everything", "everywhere",
				"f", "face", "faces", "fact", "facts", "far", "felt", "few",
				"find", "finds", "first", "for", "four", "from", "full",
				"fully", "further", "furthered", "furthering", "furthers", "g",
				"gave", "general", "generally", "get", "gets", "give", "given",
				"gives", "go", "going", "got", "group", "grouped", "grouping",
				"groups", "h", "had", "has", "have", "having", "he", "her",
				"here", "herself", "high", "high", "him", "himself", "his",
				"how", "however", "i", "if", "in", "interest",
				"interested", "interesting", "interests", "into", "is", "it",
				"its", "itself", "j", "just", "k", "keep", "keeps", "kind",
				"knew", "know", "known", "knows", "l", "large", "largely",
				"last", "later", "latest", "less", "let", "lets", "longer",
				"longest", "m", "made", "make", "making", "man", "many", "may",
				"me", "member", "members", "men", "might", "more", "most",
				"mostly", "mr", "mrs", "much", "must", "my", "myself", "n",
				"necessary", "needing", "needs", "new", "new", "newer",
				"newest", "next", "no", "nobody", "non", "noone", "not",
				"nothing", "now", "nowhere", "number", "numbers", "o", "of",
				"off", "often", "old", "older", "oldest", "on", "once", "one",
				"only", "open", "opened", "opening", "opens", "or", "order",
				"ordered", "ordering", "orders", "other", "others", "our",
				"out", "over", "p", "part", "parted", "parting", "parts",
				"per", "perhaps", "place", "places", "point", "pointed",
				"pointing", "points", "present", "presented", "presenting",
				"presents", "put", "puts", "q", "r",
				"really", "right", "right", "room", "rooms", "s", "said",
				"same", "saw", "say", "says", "second", "seconds", "see",
				"seeming", "seems", "sees", "several", "shall", "she",
				"should", "show", "showed", "showing", "shows", "side",
				"sides", "since", "small", "smaller", "smallest", "so", "some",
				"somebody", "someone", "something", "somewhere", "state",
				"states", "still", "still", "such", "sure", "t", "take",
				"taken", "than", "that", "the", "their", "them", "then",
				"there", "therefore", "these", "they", "thing", "things",
				"think", "thinks", "this", "those", "though", "thought",
				"thoughts", "three", "through", "thus", "to", "today",
				"together", "too", "took", "toward", "turn", "turned",
				"turning", "turns", "two", "u", "under", "until", "up", "upon",
				"us", "use", "used", "uses", "v", "very", "w", "want", "was",
				"way", "ways", "we", "went", "were", "what", "when", "where",
				"whether", "which", "while", "who", "whole", "whose", "why",
				"will", "with", "within", "working", "works", "would", "x",
				"y", "year", "years", "yet", "you", "young", "younger",
				"youngest", "your", "yours", };

		String[] temp1 = { "not", "don't", "didn't", "haven't", "hadn't" };
		// add the stop words into the array list
		for (int i = 0; i < temp.length; i++)
			stopWordsSet.add(temp[i]);

		// add the special words into the array list
		for (int i = 0; i < temp1.length; i++)
			specialWordSet.add(temp1[i]);

		String special = "";
		//main loop
		for (String word : inputList) {

			if (special.length() > 0) {
				wordList.add(special + " " + word);
				special = "";
			}
			if (specialWordSet.contains(word)) {
				special = word;
			}

			else if (!stopWordsSet.contains(word) && word.length() > 2) {
				if(word.length()<4)
				wordList.add(word);
				else
				wordList.add(pa.Stem(word));
			}

		}

		return wordList;

	}
	
	public ArrayList<String> NLP(String inputList)
	{	// lower the case
		inputList =  toLowerCase(inputList);
		//separate the words
		// remove the stop words
		// stem
		return removeStopWordsandStem( separateWords(inputList));
		
	
	
	}

	public String toLowerCase(String input) {

		return input.toLowerCase();
	}

	public static void main(String args[]) {
		Tokenizer token = new Tokenizer();
		String test = "Hello, this not is a testinged.";
		System.out.println( token.NLP(test));

	}
}
