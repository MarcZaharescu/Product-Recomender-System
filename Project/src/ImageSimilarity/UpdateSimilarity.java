package ImageSimilarity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import General.DatabaseQuery;
import NaturalLanguageProcessing.Tokenizer;

public class UpdateSimilarity {
	
	public static void main(String[] args) throws SQLException, IOException
	{
		writeData();
	}
	
	public static void writeData() throws SQLException, IOException
	{
	FileReader fr = new FileReader(
			"C:/Users/User/Documents/MATLAB/Similarity.csv");
 
	String currentLine;
	String[] col;

	BufferedReader br = new BufferedReader(fr);
	
	int index=0;
	
 
	int number_of_images=64;
	String[] ref_img= new String[number_of_images];
	String[] sim_indx = new String[number_of_images];
	
	//skip the title line
	br.readLine();
	while ((currentLine = br.readLine()) != null) {
		col = currentLine.split(",");
		ref_img[index] = String.valueOf(index+1);
		sim_indx[index] = col[0]+ " "+ col[1] +  " "+ col[2]+ " " + col[3]+ " " + col[4]+ " " + col[5]+ " " + col[6]+ " " + col[7]+ " " + col[8]+ " " + col[9] ;
	
		System.out.println(sim_indx[index]);
		index++;	
	}
 
	/*
	int index1 = 0;

	// save model to db
	DatabaseQuery db = new DatabaseQuery();
	// read the user details from a property file
	String value[] = db.ReadUserDetails();

	// connect to the data base
	Connection dbConn = db.setUpConnection(value);

	String[] db_model = new String[60];
	int db_index = 0;


	// delete previous models
	String deleteQuery = "truncate image_similarity_model;";
	PreparedStatement Query0 = dbConn.prepareStatement(deleteQuery);
	Query0.executeUpdate();
	// write data to the db
	String queryValue = "Insert INTO image_similarity_model (id, similair_inx) VALUES (?,?);";
	PreparedStatement Query1 = dbConn.prepareStatement(queryValue);

	for (int i = 0; i < db_model.length; i++) {
		String[] wds = db_model[i].split(",");
		System.out.println(wds[0] + " " + wds[1] );
		Query1.setInt(1, Integer.valueOf(wds[0]));
		Query1.setString(2, wds[1]);
	 

		Query1.addBatch();
	}

	// execute the query
	Query1.executeBatch();

	 
*/
}


}
