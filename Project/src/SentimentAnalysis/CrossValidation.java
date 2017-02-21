package SentimentAnalysis;

import java.io.IOException;
import java.util.ArrayList;

import General.Pair;
import NaturalLanguageProcessing.Tokenizer;

public class CrossValidation {

	public static void main(String[] args) throws IOException {
		
		kfoldCrossValidation(10);
		// TODO Auto-generated method stub

	}

	public static void kfoldCrossValidation(int para_k) throws IOException {
		int k = para_k;
		SentimentClassifier sc = new SentimentClassifier();
		ReadSentimentTrainData rstd = new ReadSentimentTrainData();

		// read the data
		rstd.ReadTrainData();

		// split the data into k folds and set the train data 1 new fold each
		// time
		ArrayList<ArrayList<Pair>> samplesTrain_pos = new ArrayList<ArrayList<Pair>>();
		ArrayList<ArrayList<Pair>> samplesTrain_neg = new ArrayList<ArrayList<Pair>>();
		ArrayList<ArrayList<Pair>> samplesTest = new ArrayList<ArrayList<Pair>>();

		int step = rstd.negative_train_reviews.size() / k;
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
				sampleTest.add(rstd.positive_train_reviews.get(j));
				sampleTest.add(rstd.negative_train_reviews.get(j));

			}
			samplesTest.add(sampleTest);
			beginningTest += step;
			endTest += step;

			// add train
			for (int x = beginningTrain_a; x < endTrain_a; x++) {
				sampleTrain_pos.add(rstd.positive_train_reviews.get(x));
				sampleTrain_neg.add(rstd.negative_train_reviews.get(x));

			}
			endTrain_a += step;
			for (int x = beginningTrain_b; x < endTrain_b; x++) {
				sampleTrain_pos.add(rstd.positive_train_reviews.get(x));
				sampleTrain_neg.add(rstd.negative_train_reviews.get(x));

			}
			samplesTrain_pos.add(sampleTrain_pos);
			samplesTrain_neg.add(sampleTrain_neg);
			beginningTrain_b += step;

		}
		System.out.println(samplesTrain_pos.size() + " <- train : test ->"
				+ samplesTest.size());
	 
		// the main loop
		int Total_Score = 0;
		int sample_size = 0;
		for (int i = 0; i < k; i++) {
			System.out.println("i: " + i);
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
				 
				if (positivity > negativity && score.equals("Positive")) {
					// good
					Acc_Score++;
				} else if (positivity > negativity && score.equals("Negative")) {
					// bad
				} else if (positivity <= negativity && score.equals("Negative")) {
					// good
					Acc_Score++;
				} else if (positivity <= negativity && score.equals("Positive")) {
					// bad
				}

			}
			Total_Score += Acc_Score;
			System.out.println("Acc score: " + Acc_Score);

		}
		System.out.println("Total score: " + Total_Score);
		System.out.println("Sample score: " + sample_size);
		System.out.println("Average: " + (double) Total_Score/sample_size  );

	}

}
