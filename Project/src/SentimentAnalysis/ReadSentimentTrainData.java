package SentimentAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import General.DatabaseQuery;
import General.Pair;
import NaturalLanguageProcessing.Tokenizer;

public class ReadSentimentTrainData {
	public static String review;
	public static String sentiment;
	static ArrayList<Pair> positive_train_reviews;
	static ArrayList<Pair> negative_train_reviews;
	private static ArrayList<Pair> test_reviews;

	public static void main(String args[]) throws IOException, SQLException {

		// train the model
		// buildSAModel();

		// calculate the cross validation
		

	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void ReadTrainData() throws IOException {
		// create a new touple object

		// set max index to 18000
		int MaxIndex = 18000;

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

			if (sentiment.equals("Positive") && posIndex < MaxIndex) {
				touple = new Pair(review, sentiment);
				positive_train_reviews.add(touple);
				posIndex++;

			}

			// add negative reviews
			if (sentiment.equals("Negative") && negIndex < MaxIndex) {
				touple = new Pair(review, sentiment);
				negative_train_reviews.add(touple);
				negIndex++;
			}

			// if neg reviews = pos reviews = MaxIndex break
			System.out.println(negIndex + " - " + posIndex);
			if (negIndex >= MaxIndex && posIndex >= MaxIndex)
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
