package SentimentAnalysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import General.DatabaseQuery;
import NaturalLanguageProcessing.Tokenizer;

public class BuildSAModel {
	
public static void main(String[] args) throws IOException, SQLException {
		
		buildSAModel();
		// TODO Auto-generated method stub

	}
	
	public static void buildSAModel() throws IOException, SQLException {
		// build the model																
		// save the results

		SentimentClassifier sc = new SentimentClassifier();
		ReadSentimentTrainData rstd = new ReadSentimentTrainData();
		// read the data
		rstd.ReadTrainData();

		// compute frequency list
		sc.computeWordFrequencyList(rstd.positive_train_reviews,
				rstd.negative_train_reviews);

		// getOverallProbability
		sc.computeTotalPositiveandNegativeProbability(rstd.positive_train_reviews,
				rstd.negative_train_reviews);

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
		System.out.println(rstd.negative_train_reviews.size());
		String word_vector1 = " fit great look comfortable dsadf well good love nice"; // (String)
		rstd.negative_train_reviews.get(4).getL();
		Tokenizer token = new Tokenizer();
		// lower the case word_vector1 =
		token.toLowerCase(word_vector1);
		// separate into words // remove stop words
		ArrayList<String> words = token.NLP(word_vector1);

		System.out.println("Positive " + sc.getTotalPositiveProbability(words));
		System.out.println("Negative " + sc.getTotalNegativeProbability(words));

	}

}
