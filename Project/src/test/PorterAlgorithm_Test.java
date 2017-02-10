package test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import NaturalLanguageProcessing.PorterAlgorithm;

public class PorterAlgorithm_Test {

	PorterAlgorithm pa;

	@BeforeClass
	public void setUP() {
		// create a porter algorithm object
		pa = new PorterAlgorithm();
		

	}

	@Test
	public void test_checkRule1() {
		// generate words for rule 1 and expected results for rule 1
		String[] rule1_testWords = { "possessess","ponies","helped","infuriating","interesting","realizes","fables","fated" };
		String[] rule1_expectedResults = { "possess","poni","help","infuriate","interest","realize","fable","fate" };
		// check if the rule 1 satisfies the expected results
		for (int i = 0; i < rule1_testWords.length; i++)
			Assert.assertEquals(pa.Rule1(rule1_testWords[i]),
					rule1_expectedResults[i]);
	}
	
	@Test
	public void test_checkRule2() {
		// generate words for rule 2 and expected results for rule 1
		String[] rule2_testWords = { "colly","furry","fry","grey" };
		String[] rule2_expectedResults = {"colli","furri","fry","grei"  };
		// check if the rule 1 satisfies the expected results
		for (int i = 0; i < rule2_testWords.length; i++)
			Assert.assertEquals(pa.Rule2(rule2_testWords[i]),
					rule2_expectedResults[i]);
	}
	
	@Test
	public void test_checkRule3() {
		// generate words for rule 3 and expected results for rule 1
		String[] rule3_testWords = { "rational","optional","operational","possibli","realli","realization","feudalism", "playfulness","liveness"};
		String[] rule3_expectedResults = {"rational","option","operate","possible","real","realize","feudal", "playful","liveness" };
		// check if the rule 1 satisfies the expected results
		for (int i = 0; i < rule3_testWords.length; i++)
			Assert.assertEquals(pa.Rule3(rule3_testWords[i]),
					rule3_expectedResults[i]);
	}
	
	@Test
	public void test_checkRule4() {
		// generate words for rule 4 and expected results for rule 1
		String[] rule4_testWords = { "playful","practical","authenticate","predicate","realize","feliciti","gleeful","largeness"};
		String[] rule4_expectedResults = { "play","practic","authentic","predic","real","felic","glee","large" };
		// check if the rule 1 satisfies the expected results
		for (int i = 0; i < rule4_testWords.length; i++)
			Assert.assertEquals(pa.Rule4(rule4_testWords[i]),
					rule4_expectedResults[i]);
	}
	
	@Test
	public void test_checkRule5() {
		// generate words for rule 5 and expected results for rule 1
		String[] rule5_testWords = { "playful","practical","authenticate","predicate","realize","feliciti","gleeful","largeness"};
		String[] rule5_expectedResults = { "play","practic","authentic","predic","real","felic","glee","large" };
		// check if the rule 1 satisfies the expected results
		for (int i = 0; i < rule5_testWords.length; i++)
			Assert.assertEquals(pa.Rule4(rule5_testWords[i]),
					rule5_expectedResults[i]);
	}
	
	
	@Test
	public void test_checkRule6() {
		// generate words for rule 1 and expected results for rule 6
		String[] rule6_testWords = { "parable","fate","deflate","bee","controll","petrol","stall","resell"};
		String[] rule6_expectedResults = {  "parabl","fate","deflat","bee","control","petrol","stall","resel" };
		// check if the rule 1 satisfies the expected results
		for (int i = 0; i < rule6_testWords.length; i++)
			Assert.assertEquals(pa.Rule6(rule6_testWords[i]),
					rule6_expectedResults[i]);
	}
}
