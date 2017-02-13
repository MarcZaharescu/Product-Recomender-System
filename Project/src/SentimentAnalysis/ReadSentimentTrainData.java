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
	private static ArrayList<Pair> positive_train_reviews;
	private static ArrayList<Pair> negative_train_reviews;
	private static ArrayList<Pair> test_reviews;

	public static void main(String args[]) throws IOException, SQLException {

		// train the model
		// buildSAModel();

		// calculate the cross validation
		kfoldCrossValidation(10);

	}

	public static void kfoldCrossValidation(int para_k) throws IOException {
		int k = para_k;
		SentimentClassifier sc = new SentimentClassifier();

		// read the data
		ReadTrainData();

		// split the data into k folds and set the train data 1 new fold each
		// time
		ArrayList<ArrayList<Pair>> samplesTrain_pos = new ArrayList<ArrayList<Pair>>();
		ArrayList<ArrayList<Pair>> samplesTrain_neg = new ArrayList<ArrayList<Pair>>();
		ArrayList<ArrayList<Pair>> samplesTest = new ArrayList<ArrayList<Pair>>();

		int step = negative_train_reviews.size() / k;
		int beginningTest = 0;
		int endTest = step;

		int beginningTrain_a = 0;
		int endTrain_a = 0;
		int beginningTrain_b = step;
		int endTrain_b = step * k;

		ArrayList<Pair> sampleTest = new ArrayList<Pair>();
		ArrayList<Pair> sampleTrain_pos = new ArrayList<Pair>();
		ArrayList<Pair> sampleTrain_neg = new ArrayList<Pair>();

		for (int i = 0; i < k; i++) {
			// add test
			sampleTest = new ArrayList<Pair>();
			for (int j = beginningTest; j < endTest; j++) {
				sampleTest.add(positive_train_reviews.get(j));
				sampleTest.add(negative_train_reviews.get(j));

			}
			samplesTest.add(sampleTest);
			beginningTest += step;
			endTest += step;

			// add train
			for (int x = beginningTrain_a; x < endTrain_a; x++) {
				sampleTrain_pos.add(positive_train_reviews.get(x));
				sampleTrain_neg.add(negative_train_reviews.get(x));

			}
			endTrain_a += step;
			for (int x = beginningTrain_b; x < endTrain_b; x++) {
				sampleTrain_pos.add(positive_train_reviews.get(x));
				sampleTrain_neg.add(negative_train_reviews.get(x));

			}
			samplesTrain_pos.add(sampleTest);
			beginningTrain_b += step;

		}
		System.out.println(samplesTrain_pos.size() + " <- train : test ->"
				+ samplesTest.size());
		System.out.println(samplesTrain_pos.get(1));
		// the main loop
		int Total_Score = 0;
		int sample_size = 0;
		for (int i = 0; i < k; i++) {
			System.out.println("k: " + k);
			ArrayList<Pair> trainData_pos = samplesTrain_pos.get(i);
			ArrayList<Pair> trainData_neg = samplesTrain_neg.get(i);

			ArrayList<Pair> testData = samplesTest.get(i);
			sample_size += testData.size();
			// compute frequency list
			sc.computeWordFrequencyList(trainData_pos, trainData_neg);
			// getOverallProbability
			sc.computeTotalPositiveandNegativeProbability(trainData_pos,
					trainData_neg);

			// USING THE TOKENIZER HELPER CLASS
			int Acc_Score = 0;

			for (Pair t : testData) {
				Tokenizer token = new Tokenizer();

				String review = (String) t.getL();
				String score = (String) t.getR();

				ArrayList<String> words = token.NLP(review);

				Double positivity = (double) sc
						.getTotalPositiveProbability(words);
				Double negativity = (double) sc
						.getTotalNegativeProbability(words);
				if (positivity > negativity && score.equals("positive")) {
					// good
					Acc_Score++;
				} else if (positivity > negativity && score.equals("negative")) {
					// bad
				} else if (positivity <= negativity && score.equals("negative")) {
					// good
					Acc_Score++;
				} else if (positivity <= negativity && score.equals("positive")) {
					// bad
				}

			}
			Total_Score += Acc_Score;
			System.out.println("Acc score: " + Acc_Score);

		}
		System.out.println("Total score: " + Total_Score);
		System.out.println("Sample score: " + sample_size);
		System.out.println("Average: " + (double) sample_size / Total_Score);

	}

	public static void buildSAModel() throws IOException, SQLException {// build
																		// the
																		// model
																		// and

		// save the results

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

		// save model to db
		DatabaseQuery db = new DatabaseQuery();
		// read the user details from a property file
		String value[] = db.ReadUserDetails();

		// connect to the data base
		Connection dbConn = db.setUpConnection(value);

		String[] db_model = new String[5001];
		int db_index = 0;

		br_pos.write("word,freq,\n ");
		for (String wd : sc.positive_word_frequency.keySet()) {
			br_pos.write(wd + "," + sc.positive_word_frequency.get(wd) + "\n ");
			db_model[db_index] = db_index + "," + wd + ","
					+ sc.positive_word_frequency.get(wd) + ",";
			index1++;
			db_index++;

		}

		db_index = 0;
		br_neg.write("word,freq,\n ");
		for (String wd : sc.negative_word_frequency.keySet()) {
			br_neg.write(wd + "," + sc.negative_word_frequency.get(wd) + "\n ");
			db_model[db_index] += wd + "," + sc.negative_word_frequency.get(wd);
			index1++;
			db_index++;

		}
		br_neg.flush();
		br_neg.close();
		br_pos.flush();
		br_pos.close();

		// finish and execute the query

		// delete previous models
		String deleteQuery = "truncate sentiment_analysis_model;";
		PreparedStatement Query0 = dbConn.prepareStatement(deleteQuery);
		Query0.executeUpdate();
		// write data to the db
		String queryValue = "Insert INTO sentiment_analysis_model (id,pos_word, pos_freq, neg_word, neg_freq) VALUES (?,?,?,?,?);";
		PreparedStatement Query1 = dbConn.prepareStatement(queryValue);

		for (int i = 0; i < db_model.length; i++) {
			String[] wds = db_model[i].split(",");
			System.out.println(wds[0] + " " + wds[1] + " " + wds[2] + " "
					+ wds[3] + " " + wds[4]);
			Query1.setInt(1, Integer.valueOf(wds[0]));
			Query1.setString(2, wds[1]);
			Query1.setInt(3, Integer.valueOf(wds[2]));
			Query1.setString(4, wds[3]);
			Query1.setInt(5, Integer.valueOf(wds[4]));

			Query1.addBatch();
		}

		// execute the query
		Query1.executeBatch();

		// USING THE TOKENIZER HELPER CLASS
		System.out.println(negative_train_reviews.size());
		String word_vector1 = " fit great look comfortable dsadf well good love nice"; // (String)
		negative_train_reviews.get(4).getL();
		Tokenizer token = new Tokenizer();
		// lower the case word_vector1 =
		token.toLowerCase(word_vector1);
		// separate into words // remove stop words
		ArrayList<String> words = token.NLP(word_vector1);

		System.out.println("Positive " + sc.getTotalPositiveProbability(words));
		System.out.println("Negative " + sc.getTotalNegativeProbability(words));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void ReadTrainData() throws IOException {
		// create a new touple object

		// set max index to 18000
		int MaxIndex = 1000;

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
