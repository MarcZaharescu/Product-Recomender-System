package SentimentAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import General.Pair;
import NaturalLanguageProcessing.Tokenizer;

public class ReadSentimentTrainData {
	public static String review;
	public static String sentiment;
	private static ArrayList<Pair> positive_train_reviews;
	private static ArrayList<Pair> negative_train_reviews;
	private static ArrayList<Pair> test_reviews;

	public static void main(String args[]) throws IOException {

		SentimentClassifier sc = new SentimentClassifier();

		// read the data
		ReadTrainData();

		// compute frequency list
		sc.computeWordFrequencyList(positive_train_reviews,
				negative_train_reviews);

		// getOverallProbability
		sc.computeTotalPositiveandNegativeProbability(positive_train_reviews,
				negative_train_reviews);

		// write data to file
		FileWriter fr = new FileWriter(
				"C:/Users/User/Desktop/trainDirectory/Neg.csv");
		FileWriter fr1 = new FileWriter(
				"C:/Users/User/Desktop/trainDirectory/Pos.csv");
		String currentLine;
		String[] col;

		BufferedWriter br_neg = new BufferedWriter(fr);
		BufferedWriter br_pos = new BufferedWriter(fr1);
		int index1 = 0;

		br_neg.write("word,freq,\n ");
		for (String wd : sc.negative_word_frequency.keySet()) {
			br_neg.write(wd + "," + sc.negative_word_frequency.get(wd) + "\n ");
			System.out.println(wd + " : " + sc.negative_word_frequency.get(wd));
			index1++;

		}

		br_pos.write("word,freq,\n ");
		for (String wd : sc.positive_word_frequency.keySet()) {
			br_pos.write(wd + "," + sc.positive_word_frequency.get(wd) + "\n ");
			System.out.println(wd + " : " + sc.positive_word_frequency.get(wd));
			index1++;

		}
		br_neg.flush();
		br_neg.close();
		br_pos.flush();
		br_pos.close();

		System.out.println(sc.positive_word_frequency.size() + "    "
				+ sc.negative_word_frequency.size());
		// getPositiveProbabilityForWord
		int numberOfUniqueWords = sc.word_list.size();
		 
		// USING THE TOKENIZER HELPER CLASS
		System.out.println(negative_train_reviews.size());
		String word_vector1 = " fit great look comfortable well good love nice";
		// (String) negative_train_reviews.get(4).getL();
		Tokenizer token = new Tokenizer();
		// lower the case
		word_vector1 = token.toLowerCase(word_vector1);
		// separate into words
		// remove stop words
		ArrayList<String> words = token.NLP(word_vector1);
		System.out.println(numberOfUniqueWords);
		 
		System.out.println("Positive " + sc.getTotalPositiveProbability(words));
		System.out.println("Negative " + sc.getTotalNegativeProbability(words));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void ReadTrainData() throws IOException {
		// create a new touple object
		Pair<String, String> touple = null;
		negative_train_reviews = new ArrayList<Pair>();
		positive_train_reviews = new ArrayList<Pair>();
		FileReader fr = new FileReader(
				"C:/Users/User/Desktop/trainDirectory/ReviewData.csv");

		String currentLine;
		String[] col;

		BufferedReader br = new BufferedReader(fr);

		int negIndex = 0, posIndex = 0;
		while ((currentLine = br.readLine()) != null) {
			col = currentLine.split(",");
			review = col[1];
			sentiment = col[2];

			// add positive reviews
			if (sentiment.equals("Positive") && posIndex < 18000) {
				touple = new Pair(review, sentiment);
				positive_train_reviews.add(touple);
				posIndex++;

			}

			// add negative reviews
			if (sentiment.equals("Negative") && negIndex < 18000) {
				touple = new Pair(review, sentiment);
				negative_train_reviews.add(touple);
				negIndex++;
			}

			// if neg reviews = pos reviews = 18000 break
			System.out.println(negIndex + " - " + posIndex);
			if (negIndex >= 18000 && posIndex >= 18000)
				break;

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

}
