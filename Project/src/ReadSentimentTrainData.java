import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

		int index1=0;
		for(String wd: sc.negative_word_frequency.keySet())
		{
			System.out.println(wd +" : " +sc.negative_word_frequency.get(wd));
			index1++;
		if(index1>50)
			break;
		}
		
		System.out.println("don't" +" : " +sc.negative_word_frequency.get("don't like"));
		System.out.println("don't" +" : " +sc.positive_word_frequency.get("don't like"));
		
		System.out.println(sc.positive_word_frequency.size() + "    " +sc.negative_word_frequency.size());
		// getPositiveProbabilityForWord
		int numberOfUniqueWords = sc.word_frequency.size();
		double PosP = 1;
		double NegP = 1;

		// USING THE TOKENIZER HELPER CLASS
		System.out.println(negative_train_reviews.size());
		String word_vector1 = "nice soft pretty amazing good";//(String) negative_train_reviews.get(4).getL();
		Tokenizer token = new Tokenizer();
		// lower the case
		word_vector1 = token.toLowerCase(word_vector1);
		// separate into words
		// remove stop words
		ArrayList<String> words = token.removeStopWords(token
				.separateWords(word_vector1));

		for (String word : words) {
			double posP_wi = (double) sc.getPositiveProbabilityForWord(
					sc.positive_word_frequency, word, numberOfUniqueWords);
			double negP_wi = (double) sc.getNegativeProbabilityForWord(
					sc.negative_word_frequency, word, numberOfUniqueWords);

			PosP = PosP * posP_wi;
			NegP = NegP * negP_wi;
		}

		PosP = PosP * sc.PositiveProbability;
		NegP = NegP * sc.NegativeProbability;
		System.out.println("Positive " + PosP);
		System.out.println("Negative " + NegP);

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

	 int negIndex=0, posIndex=0;
		while ((currentLine = br.readLine()) != null) {
			col = currentLine.split(",");
			review = col[1];
			sentiment = col[2];
 
			// add positive reviews
			
			if (sentiment.equals("Positive") && posIndex<18000) {
				touple = new Pair(review, sentiment);
				positive_train_reviews.add(touple);
				posIndex++;
				 

			}

			// add negative reviews
		
			if (sentiment.equals("Negative")&& negIndex<18000) {
				touple = new Pair(review, sentiment);
				negative_train_reviews.add(touple);
			  negIndex++;
			}
			
			
			//if neg reviews = pos reviews = 18000 break
			System.out.println(negIndex + " - "+  posIndex);
			if(negIndex>=18000 && posIndex>=18000)
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
