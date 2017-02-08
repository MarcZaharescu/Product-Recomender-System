package General;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import NaturalLanguageProcessing.Tokenizer;

public class DatabaseQuery {

	public static void main(String args[]) throws SQLException, ParseException {

		// read the user details from a property file
		String value[] = ReadUserDetails();

		// connect to the data base
		Connection dbConn = setUpConnection(value);
		
		// get the words input
		Tokenizer token = new Tokenizer();
		String test = "Hello, this is a test. I want a green ,red  dress";
		// lower the case
		test = token.toLowerCase(test);
		// separate into words
		// remove stop words
		ArrayList<String> words=token.removeStopWords(token.separateWords(test));
		FetchImages(dbConn,words);
	}

	public static Connection setUpConnection(String value[]) {

		System.setProperty("jdbc.drivers", "org.gjt.mm.mysql.Driver");
		String dbName = "jdbc:mysql://localhost:3306/productimages";
		Connection dbConn = null;

		try {
			dbConn = DriverManager.getConnection(dbName, value[1], value[0]);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		}

		catch (ClassNotFoundException ex) {
			System.out.println("Driver not found");
		}

		System.out.println("PostgreSQL driver registered.");

		if (dbConn != null) {
			System.out.println("Database accessed!");
		}

		else {
			System.out.println("Failed to make connection");
		}
		return dbConn;
	}

	/**
	 * Reads the user`s details from a property file
	 * 
	 * @return a array of string containing the details
	 */
	public static String[] ReadUserDetails() {
		String[] key = new String[2];
		String[] value = new String[2];
		int i = 0;
		int j = 0;

		try {
			File file = new File("src/userDetails");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration<Object> enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				key[i] = (String) enuKeys.nextElement();
				value[j] = properties.getProperty(key[i]);

				i++;
				j++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void FetchImages(Connection dbConn, ArrayList<String> words) throws SQLException,
			ParseException {
       
		String operator="";
		for(int i=0;i<words.size();i++)
			{operator=operator+"Like '" +words.get(i)+"%' ";
			if(i>=0 && i<words.size()-1)
				operator = operator +"OR description ";
			}
		 
		 
		// create the sql query using the operator
		String queryValue = "Select * from images where description "+operator+";";
		PreparedStatement Query1 = dbConn.prepareStatement(queryValue);

		ResultSet imageResults;
		imageResults = Query1.executeQuery();

		
		String location,name,id,description;
		
		while ((imageResults).next()) {
             location=imageResults.getString("location");
             name=imageResults.getString("name");
             id= imageResults.getString("idimages");
             description= imageResults.getString("description");
             
             System.out.println(name + ", " + location + ", " + description + ", " + id);
		}

	}
}
