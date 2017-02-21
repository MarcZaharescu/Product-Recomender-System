package SentimentAnalysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import General.DatabaseQuery;
import NaturalLanguageProcessing.Tokenizer;

public class ReadSAModel {

	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		ReadSAModel();

	}

	public static void ReadSAModel() throws IOException, SQLException {
		// build the model																
		// save the results

		SentimentClassifier sc = new SentimentClassifier();

		// save model to db
		DatabaseQuery db = new DatabaseQuery();
		// read the user details from a property file
		String value[] = db.ReadUserDetails();

		// connect to the data base
		Connection dbConn = db.setUpConnection(value);

		String[] db_model = new String[5001];
		int db_index = 0;

	 
		// finish and execute the query

		// delete previous models
		 
 
		// write data to the db
		String queryValue = "Select * from productimages.sentiment_analysis_model ;";
		PreparedStatement Query1 = dbConn.prepareStatement(queryValue);

		ResultSet rs= 	Query1.executeQuery();
		System.out.println(rs);
		String[] pos_word = new String[5001],neg_word =   new String[5001];
		int [] pos_freq =    new int[5001],neg_freq =   new int[5001];
		int index=0;
		
		while ((rs).next()) {
			pos_word[index]=rs.getString("pos_word");
			pos_freq[index]=rs.getInt("pos_freq");
			neg_word[index]=rs.getString("neg_word");
			neg_freq[index]=rs.getInt("neg_freq");
            
            System.out.println(pos_word[index] + ", " + pos_freq[index] + ", " + neg_word[index] + ", " + neg_freq[index]);
            index++;
		}
		
		
		/*// USING THE TOKENIZER HELPER CLASS
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
  */
	}


}
