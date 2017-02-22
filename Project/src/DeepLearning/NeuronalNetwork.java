package DeepLearning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NeuronalNetwork {

	private String activateFunction;
	private double learningRate;
	private int iterations;
	private int hiddenUnits;
	private int weights;

	public NeuronalNetwork(String activateFunction, double learningRate,
			int iterations, int hiddenUnits) {
		this.activateFunction = activateFunction;
		this.learningRate = learningRate;
		this.iterations = iterations;
		this.hiddenUnits = hiddenUnits;
	}


	public static Matrix backwardPropagation(Matrix inputMatrix,
			Matrix weightMatrix, double learningRate, Matrix biasVector, Matrix targetOutput) {
		
		Random rand = new Random();
		int start = 1+ rand.nextInt(inputMatrix.I);
		int end = inputMatrix.I;
		
		Matrix [] outputs = FeedForwardFunction(Matrix.split(inputMatrix, start, end),Matrix.split(weightMatrix, start ,end), Matrix.split(biasVector,start,end));
		System.out.println(outputs[1].data[0].length);
		System.out.println(Matrix.split(targetOutput, start, end).data[0].length);
		Matrix error = Matrix.difference( Matrix.split(targetOutput, start, end) , outputs[1] );
		
		Matrix B = new Matrix (activateFunctionDerivative(outputs[0]).data);
		Matrix delta = Matrix.hadamardProduct(error, B);
		
		Matrix weights_delta= Matrix.krocknerMultiplication(Matrix.transposition( Matrix.split(inputMatrix, start, end)),delta  ).multiplyScalar(learningRate);
		weightMatrix = Matrix.sum(weightMatrix, weights_delta);
		
		return weightMatrix;

	}

	public static double[] trainFunction(DataSet DtrainingSet,
			DataSet DvalidationSet, DataSet DtestSet) {

		Matrix trainingSet = new Matrix(DtrainingSet.input.data);
		Matrix validationSet = new Matrix(DvalidationSet.input.data);
		Matrix testSet = new Matrix(DtestSet.input.data);
		 
		 
		Matrix weightMatrix = weightInitialization(1 / 2, DtrainingSet.count+1, DtrainingSet.input.J);

 
 
		double learning_rate = 0.1;
		double[] training_error, validation_error, test_error;
		double[] error_train = new double[501], error_validation = new double[501], error_test = new double[501];
		double[] classification_error_train = new double[501], classification_error_validation = new double[501], classification_error_test = new double[501];

		int index = 0;
		int classCount =3;

 
		for (int i = 0; i < 500; i++) {
			// update the weight matrix
			weightMatrix = backwardPropagation(trainingSet, weightMatrix,
					learning_rate, DtrainingSet.bias, DtrainingSet.output);

			// training error
			DataSet trSet = new DataSet(trainingSet,DtrainingSet.output, classCount);
			training_error = evaluateNetworkError(trSet, weightMatrix);
			
			error_train[index] = training_error[0];
			classification_error_train[index] = training_error[1];

			// validation error
			DataSet vaSet = new DataSet(validationSet,DvalidationSet.output, classCount);
			validation_error = evaluateNetworkError(vaSet, weightMatrix);
			
			error_validation[index] = validation_error[0];
			classification_error_validation[index] = validation_error[1];

			// test error
			DataSet teSet = new DataSet(testSet,DtestSet.output, classCount);
			test_error = evaluateNetworkError(teSet, weightMatrix);
			
			error_test[index] = test_error[0];
			classification_error_test[index] = test_error[1];

			// increment the index
			index++;
		}

		// plot the errors

		// training error
		DataSet trSet = new DataSet(trainingSet,DtrainingSet.output, classCount);
		training_error = evaluateNetworkError(trSet, weightMatrix);
		
		error_train[index] = training_error[0];
		classification_error_train[index] = training_error[1];

		// validation error
		DataSet vaSet = new DataSet(validationSet,DvalidationSet.output, classCount);
		validation_error = evaluateNetworkError(vaSet, weightMatrix);
		
		error_validation[index] = validation_error[0];
		classification_error_validation[index] = validation_error[1];

		// test error
		DataSet teSet = new DataSet(testSet,DtestSet.output, classCount);
		test_error = evaluateNetworkError(teSet, weightMatrix);
		
		error_test[index] = test_error[0];
		classification_error_test[index] = test_error[1];
		

		// return the weightMatrix + 6 errors;
		// and weight matrix
		return new double[] { error_train[501],
				classification_error_train[501], error_validation[501],
				classification_error_validation[501], error_test[501],
				classification_error_test[501] };
	}

	public static double sigmoidFunction(double x) {

		return (1 / (1 + Math.exp(-x)));

	}

	public static double sigmoidFunctionDerivative(double x) {

		return sigmoidFunction(x) * (1 - sigmoidFunction(x));

	}

	public static Matrix activateFunction(Matrix A) {
		double[][] a = A.data;
		Matrix C = new Matrix(a);

		for (int i = 0; i < C.I; i++)
			for (int j = 0; j < C.J; j++)
				C.data[i][j] = sigmoidFunction(C.data[i][j]);

		return C;
	}

	public static Matrix activateFunctionDerivative(Matrix A) {
		double[][] a = A.data;
		Matrix C = new Matrix(a);

		for (int i = 0; i < C.I; i++)
			for (int j = 0; j < C.J; j++)
				C.data[i][j] = sigmoidFunctionDerivative(C.data[i][j]);

		return C;
	}

	public static Matrix[] FeedForwardFunction(Matrix inputMatrix,
			Matrix weightMatrix, Matrix biasnodeMatrix) {

		System.out.println("weightMatrix " + weightMatrix.data.length +" " + weightMatrix.data[0].length);
		System.out.println("inputMatrix " + inputMatrix.data.length +" " + inputMatrix.data[0].length);
		System.out.println("horcatInput " + (Matrix.horizontalConcatenation(inputMatrix, biasnodeMatrix)).data.length +" " + (Matrix.horizontalConcatenation(inputMatrix, biasnodeMatrix)).data[0].length);
		
		Matrix net = Matrix
				.horizontalConcatenation(Matrix.multiply(inputMatrix,  weightMatrix) , biasnodeMatrix);
		Matrix output = activateFunction(net);

		return new Matrix[] { net, output };
		// return output matrix, net matrix
	}

	public static Matrix weightInitialization(double max,   int height, int width) {
		Matrix C = new Matrix(height , width);
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				C.data[i][j] = ThreadLocalRandom.current().nextDouble(-max,
						max + 1);

		return C;
	}

	
	
	public static double[] evaluateNetworkError(DataSet ds,
			Matrix weightMatrix) {
		
		Matrix[] outputs = FeedForwardFunction(ds.input, weightMatrix,
				ds.bias);
		
		Matrix temp = Matrix.difference(ds.output, outputs[1]);
		Matrix temp1 = Matrix.hadamardProduct(temp, temp);
		double sum = Matrix.sumComponents(temp1);

		int sample_count = ds.count ;
		int output_count = weightMatrix.J;
		double regression_error = sum / (sample_count * output_count);
		
		Matrix classes = Matrix.outputToClass(outputs[1]);
		double classesification_error = Matrix.numberOfDifferences(classes, ds.classes) / sample_count;

		return new double[] { regression_error, classesification_error };
	}

	public static void readTrainData() throws NumberFormatException, IOException
	{
		
		FileReader fr = new FileReader(
				"C:/Users/User/Desktop/trainDirectory/IRIS.csv");

		String currentLine;
		String[] col;

		BufferedReader br = new BufferedReader(fr);
		double[][] input = new double[148][4];
		double[][] output = new double[148][1];
		int  cIndex = 0,  rIndex = 0;
		while ((currentLine = br.readLine()) != null) {
			col = currentLine.split(",");
			
			input[rIndex][0] = Double.valueOf(col[0]);
			input[rIndex][1] = Double.valueOf(col[1]);
			input[rIndex][2] = Double.valueOf(col[2]);
			input[rIndex][3] = Double.valueOf(col[3]);
			
			output[rIndex][0] = Double.valueOf(col[4]);
			
			rIndex++;
			 
		}
		
		Matrix inputMatrix  = new Matrix(input);
		Matrix classMatrix = new Matrix(output);
		Matrix outputMatrix = new Matrix (classMatrix.I,3);
		outputMatrix = Matrix.classToOutput(classMatrix);
		
		// training 60% validation 20% test 20 %
		
		DataSet training = new DataSet(Matrix.split(inputMatrix,0,100), Matrix.split(outputMatrix,0,100), 3);
		DataSet validation = new DataSet(Matrix.split(inputMatrix,100,120), Matrix.split(outputMatrix,100,120), 3);
		DataSet test = new DataSet(Matrix.split(inputMatrix,120,147), Matrix.split(outputMatrix,120,147), 3);
		
		System.out.println();
		training.output.print();
		trainFunction(training,validation,test);
		
		System.out.println();
		 
		
		
		 
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		double[][] a = { { 1, 2 }, { 3, 4 } };
		Matrix A = new Matrix(a);
		double[][] b = { { 0, 5 }, { 6, 7 } };
		Matrix B = new Matrix(b);

		double[][] c = { { 1 }, { 1 } };
		Matrix C = new Matrix(c);
		// c is a bias column vector with 1s and the length is the same size as
		// a
		//Matrix[] D = FeedForwardFunction(A, B, C);

		//D[0].print();
		//System.out.println();
		//D[1].print();
		//System.out.println();

		System.out.println("weight initialization");
		Matrix E = weightInitialization(1, 2, 2);
		E.print();
		System.out.println();
		
		
		
		
		
		 readTrainData();
	}

}
