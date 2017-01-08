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

	public ArrayList<String> removeStopWords(List<String> inputList)
	{
		 
	    ArrayList<String> wordList = new ArrayList<String>();
	    Set<String> stopWordsSet = new HashSet<String>();
	    // TO DO 
	    // create a word list
	    stopWordsSet.add("i");
	    stopWordsSet.add("this");
	    stopWordsSet.add("and");
 

	    for(String word : inputList)
	    {
	        
	        if(!stopWordsSet.contains(word))
	        {
	            wordList.add(word);
	        }
	    }
 
		return wordList;
		
	}

	public String toLowerCase(String input) {

		return input.toLowerCase();
	}

	public static void main(String args[]) {
		Tokenizer token = new Tokenizer();
		String test = "Hello, this is a test.";
		// lower the case
		test = token.toLowerCase(test);
		// separate into words
		System.out.println(token.separateWords(test));
		// remove stop words
		System.out.println(token.removeStopWords(token.separateWords(test)));

	}
}
