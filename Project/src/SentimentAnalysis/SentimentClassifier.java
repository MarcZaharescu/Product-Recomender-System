package SentimentAnalysis;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import General.MapUtil;
import General.Pair;
import NaturalLanguageProcessing.Tokenizer;

public class SentimentClassifier {

	static ArrayList<Pair> train_reviews;
	static Map<String, Integer> word_frequency;
	static Set<String> word_list;
	static ArrayList<Pair> test_reviews;
	static double PositiveProbability;
	static double NegativeProbability;
	static Map<String, Integer> positive_word_frequency;
	static Map<String, Integer> negative_word_frequency;
	static ArrayList<Pair> positive_train_reviews;
	static ArrayList<Pair> negative_train_reviews;

	public static void main(String[] args) {

	}


	public static double getTotalPositiveProbability(ArrayList<String> words) {
		int numberOfUniqueWords = word_list.size();
		double PosP = 1.0;

		for (String word : words) {
			double posP_wi = (double) getPositiveProbabilityForWord(
					positive_word_frequency, word, numberOfUniqueWords);
			PosP = PosP * posP_wi;
		}
		return PositiveProbability*PosP;
	}
	
	public static double getTotalNegativeProbability(ArrayList<String> words) {
		int numberOfUniqueWords = word_list.size();
		double NegP = 1.0;

		for (String word : words) {
			double posP_wi = (double) getNegativeProbabilityForWord(
					negative_word_frequency, word, numberOfUniqueWords);
			NegP = NegP * posP_wi;
		}
		return NegativeProbability*NegP;
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
			nk = 10;

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
		word_list = new HashSet<String>();
		// total unique words frequency from reviews
		word_frequency = new HashMap<>();

		//
		//
		// Compute the frequency for positive reviews
		//
		//

		// go through each review
		for (int i = 0; i < positive.size(); i++) {
			System.out.println("pos " + i);
			String word_vector = (String) positive.get(i).getL();

			// USING THE TOKENIZER HELPER CLASS
			Tokenizer token = new Tokenizer();
			// lower the case
			word_vector = token.toLowerCase(word_vector);
			// separate into words
			// remove stop words
			ArrayList<String> words = token.removeStopWordsandStem(token
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

		for (int i = 0; i < negative.size(); i++) {
			System.out.println("neg " + i);
			String word_vector = (String) negative.get(i).getL();

			// USING THE TOKENIZER HELPER CLASS
			Tokenizer token = new Tokenizer();
			// lower the case
			word_vector = token.toLowerCase(word_vector);
			// separate into words
			// remove stop words
			ArrayList<String> words = token.removeStopWordsandStem(token
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
		// sort the list - keep only the first 5000
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
		ArrayList<String> words = token.removeStopWordsandStem(token
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