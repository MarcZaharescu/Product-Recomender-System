import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SentimentClassifier {

	private static ArrayList<Pair> train_reviews;
	private static Map<String, Integer> word_frequency;
	private static ArrayList<String> word_list;
	private static ArrayList<Pair> test_reviews;
	private static double PositiveProbability;
	private static double NegativeProbability;
	private static Map<String, Integer> positive_word_frequency;
	private static Map<String, Integer> negative_word_frequency;
	private static ArrayList<Pair> positive_train_reviews;
	private static ArrayList<Pair> negative_train_reviews;

	public static void main(String[] args) {

		// read the train data
		readTrainData();
		// compute the word frequency list
		computeWordFrequencyList(positive_train_reviews, negative_train_reviews);
		// extract features for test reviews
		for (Pair review : test_reviews) {
			featureExtractor(review);
			break;
		}

		// getOverallProbability
		computeTotalPositiveandNegativeProbability(positive_train_reviews,
				negative_train_reviews);
		// getPositiveProbabilityForWord
		int numberOfUniqueWords = word_frequency.size();
		double PosP = 1;
		double NegP = 1;
		
		
		

		
		//-----------------------------------EDIT-------------------------------------------
		
		
		
		
		// USING THE TOKENIZER HELPER CLASS
		String word_vector1 = (String) negative_train_reviews.get(4).getL();
		Tokenizer token = new Tokenizer();
		// lower the case
		word_vector1 = token.toLowerCase(word_vector1);
		// separate into words
		// remove stop words
		ArrayList<String> words = token.removeStopWords(token
				.separateWords(word_vector1));

		for (String word : words) {
			double posP_wi = (double) getPositiveProbabilityForWord(
					positive_word_frequency, word, numberOfUniqueWords);
			double negP_wi = (double) getNegativeProbabilityForWord(
					negative_word_frequency, word, numberOfUniqueWords);

			PosP = PosP * posP_wi;
			NegP = NegP * negP_wi;
		}

		PosP = PosP * PositiveProbability;
		NegP = NegP * NegativeProbability;
		System.out.println("Positive " + PosP);
		System.out.println("Negative " + NegP);

	}

	public static void readTrainData() {

		// create a new touple object
		Pair<String, String> touple;

		// read pos reviews
		String[] positive_review_list = { "I love this car", "positive",
				"This view is amazing", "positive",
				"I feel great this morning", "positive",
				"I am so excited about the concert", "positive",
				"He is my best friend", "positive" };

		// read negative reviews
		String[] negative_review_list = { "I do not like this car", "negative",
				"This view is horrible", "negative",
				"I feel tired this morning", "negative",
				"I am not looking forward to the concert", "negative",
				"He is my enemy", "negative" };

		positive_train_reviews = new ArrayList<Pair>();
		// add positive train reviews to the array list
		for (int i = 0; i < positive_review_list.length - 1; i++) {
			touple = new Pair(positive_review_list[i],
					positive_review_list[i + 1]);
			positive_train_reviews.add(touple);
			i = i + 1;
		}

		negative_train_reviews = new ArrayList<Pair>();
		// add negative train reviews to the array list
		for (int i = 0; i < negative_review_list.length - 1; i++) {
			touple = new Pair(negative_review_list[i],
					negative_review_list[i + 1]);
			negative_train_reviews.add(touple);
			i = i + 1;
		}

		// read test reviews
		String[] test_review_list = { "feel happy this morning", "positive",
				"larry friend positive not like that man", "negative",
				"house not great", "negative", "your song annoying", "negative" };
		// add test reviews to the array list
		test_reviews = new ArrayList<Pair>();
		for (int i = 0; i < test_review_list.length - 1; i++) {
			touple = new Pair(test_review_list[i], test_review_list[i + 1]);
			test_reviews.add(touple);
			i = i + 1;
		}

	}

	public static void computeTotalPositiveandNegativeProbability(
			ArrayList<Pair> positive, ArrayList<Pair> negative) {
		PositiveProbability = (double) positive.size()
				/ (positive.size() + negative.size());
		NegativeProbability = (double) negative.size()
				/ (positive.size() + negative.size());
	}

	public static double getPositiveProbabilityForWord(
			Map<String, Integer> word_frequency, String word, int vocabularySize) {

		int nk;
		if (word_frequency.get(word) != null)
			nk = word_frequency.get(word);
		else
			nk = 0;

		// nr the words in positive ratings
		int nrPos = word_frequency.size();

		// positive probability for a word
		double positiveP = (double) (nk + 1) / (nrPos + vocabularySize);

		return positiveP;
	}

	public static double getNegativeProbabilityForWord(
			Map<String, Integer> word_frequency, String word, int vocabularySize) {

		int nk;
		if (word_frequency.get(word) != null)
			nk = word_frequency.get(word);
		else
			nk = 0;

		// nr the words in negative ratings
		int nrPos = word_frequency.size();

		// negative probability for a word
		double negativeP = (double) (nk + 1) / (nrPos + vocabularySize);

		return negativeP;
	}

	public static void computeWordFrequencyList(ArrayList<Pair> positive,
			ArrayList<Pair> negative) {

		// compute an frequency occurrence vector for the train reviews
		Pair<String, Integer> touple;
		positive_word_frequency = new HashMap<>();
		negative_word_frequency = new HashMap<>();
		word_list = new ArrayList<String>();
		// total unique words frequency from reviews
		word_frequency = new HashMap<>();

		//
		//
		// Compute the frequency for positive reviews
		//
		//

		// go through each review
		for (int i = 0; i < positive.size(); i++) {

			String word_vector = (String) positive.get(i).getL();

			// USING THE TOKENIZER HELPER CLASS
			Tokenizer token = new Tokenizer();
			// lower the case
			word_vector = token.toLowerCase(word_vector);
			// separate into words
			// remove stop words
			ArrayList<String> words = token.removeStopWords(token
					.separateWords(word_vector));

			Integer count = 0;
			for (String word : words) {
				count = positive_word_frequency.get(word);

				if (count == null)
					count = 1;
				else
					count++;

				positive_word_frequency.put(word, count);
				word_frequency.put(word, count);
			}
		}
		// sort the list
		positive_word_frequency = MapUtil.sortByValue(positive_word_frequency);
		word_list.addAll(positive_word_frequency.keySet());

		//
		//
		// Compute the frequency for negative reviews
		//
		//

		for (int i = 0; i < positive.size(); i++) {

			String word_vector = (String) negative.get(i).getL();

			// USING THE TOKENIZER HELPER CLASS
			Tokenizer token = new Tokenizer();
			// lower the case
			word_vector = token.toLowerCase(word_vector);
			// separate into words
			// remove stop words
			ArrayList<String> words = token.removeStopWords(token
					.separateWords(word_vector));

			Integer count = 0, count_all = 0;
			for (String word : words) {
				count = negative_word_frequency.get(word);
				// for total unique word frequency
				count_all = word_frequency.put(word, count_all);
				if (count == null)
					count = 1;
				else
					count++;
				// for total unique word frequency
				if (count_all == null)
					count_all = 1;
				else
					count_all++;
				// add the word and frequency to the negative word array list
				negative_word_frequency.put(word, count);
				// add the word and frequency to the total unique word array
				// list
				word_frequency.put(word, count_all);
			}
		}
		// sort the list
		negative_word_frequency = MapUtil.sortByValue(negative_word_frequency);
		word_list.addAll(negative_word_frequency.keySet());

	}

	public static void featureExtractor(Pair review) {

		// apply the dictionary of words to see whether or not it contains any
		// of the words

		Pair<String, Integer> touple;
		ArrayList<Pair> review_features = new ArrayList<Pair>();

		String word_vector = (String) review.getL();

		// USING THE TOKENIZER HELPER CLASS
		Tokenizer token = new Tokenizer();
		// lower the case
		word_vector = token.toLowerCase(word_vector);
		// separate into words
		// remove stop words
		ArrayList<String> words = token.removeStopWords(token
				.separateWords(word_vector));

		for (String word : word_list) {
			if (words.contains(word)) {
				touple = new Pair(word, "True");
				review_features.add(touple);
			} else {
				touple = new Pair(word, "False");
				review_features.add(touple);
			}
		}

		// show the reviews
		// for (Pair rf : review_features) {
		// System.out.println(rf.getL() + " " + rf.getR());
		// }
	}

}