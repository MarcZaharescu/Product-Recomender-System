package Auxilary;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 

public class ReadSentimentData {

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();

		try {

			BufferedReader bf = new BufferedReader(
					new FileReader(
							"C:/Users/User/Desktop/trainDirectory/Clothing_Shoes_and_Jewelry_5.json"));

			FileWriter fileWriter = new FileWriter(
					"C:/Users/User/Desktop/trainDirectory/ReviewData.csv");

			fileWriter.append("Nr,Review name,Score,\n");

			int stop = 10, i = 0;
			String score = "NAN";
			String review = "NAN";
			Object sCurrentLine;
			while ((sCurrentLine = bf.readLine()) != null) {
				String[] x1 = ((String) sCurrentLine).split("reviewT");
				try {
					String[] x2 = x1[1].split("overall");
					review = (x2[0]).substring(7, x2[0].length() - 4);

					review = review.replaceAll(",", " ");
					String[] x3 = x2[1].split("summary");
					System.out.println(i);
					i++;

					
	
					score = x3[0].substring(3, x3[0].length() - 5);
					
					 
					 
					 if(Integer.parseInt(score)>0 && Integer.parseInt(score)<=3)
					 	score="Negative";
					else
						if(Integer.parseInt(score)>3 && Integer.parseInt(score)<=5)
					 	score="Positive";
					 
					if (!review.equals("NAN") && !score.equals("NAN")
							&& score.length()== 8)
						fileWriter.append(String.valueOf(i) + "," + review
								+ "," + score + "\n");

				} catch (Exception e) {
				}

			}

			fileWriter.flush();
			fileWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
