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
		int start =   rand.nextInt(inputMatrix.I);
		int end = inputMatrix.I;
		
		Matrix [] outputs = FeedForwardFunction(Matrix.line(inputMatrix, start), weightMatrix,  Matrix.element(biasVector,start));
		 Matrix error = Matrix.difference( Matrix.line(targetOutput, start ) , outputs[1] );
		 
		Matrix B = new Matrix (activateFunctionDerivative(outputs[0]).data);
		Matrix delta = Matrix.hadamardProduct(error, B);
	 
		Matrix weights_delta= Matrix.krocknerMultiplication(Matrix.transposition( Matrix.horizontalConcatenation(Matrix.line(inputMatrix, start ),Matrix.element(biasVector, start))),delta  ).multiplyScalar(learningRate);
		//weights_delta.print();
		//System.out.println();
		//weightMatrix.print();
		weightMatrix = Matrix.sum(weightMatrix, weights_delta);
		
		return weightMatrix;

	}

	public static double[] trainFunction(DataSet DtrainingSet,
			DataSet DvalidationSet, DataSet DtestSet) {

		Matrix trainingSet = new Matrix(DtrainingSet.input.data);
		Matrix validationSet = new Matrix(DvalidationSet.input.data);
		Matrix testSet = new Matrix(DtestSet.input.data);
		 
		 
		Matrix weightMatrix = weightInitialization(0.5, DtrainingSet.input_count+1, DtrainingSet.output_count);
		
		int number_of_iterations=5001;
 
 
		double learning_rate = 0.1;
		double[] training_error, validation_error, test_error;
		double[] error_train = new double[number_of_iterations], error_validation = new double[number_of_iterations], error_test = new double[number_of_iterations];
		double[] classification_error_train = new double[number_of_iterations], classification_error_validation = new double[number_of_iterations], classification_error_test = new double[number_of_iterations];

		int index = 0;
		int classCount =3;
		
		//validation treshold
		double validation_stop_threshold=0.005;

 
		for (int i = 0; i < number_of_iterations-1; i++) {
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
			
			  if (error_validation[index] < validation_stop_threshold )
			 	break;
		       
		

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
		return new double[] { error_train[index],
				classification_error_train[index], error_validation[index],
				classification_error_validation[index], error_test[index],
				classification_error_test[index] };
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
				C.data[i][j] = tanh(C.data[i][j]);

		return C;
	}

	public static Matrix activateFunctionDerivative(Matrix A) {
		double[][] a = A.data;
		Matrix C = new Matrix(a);

		for (int i = 0; i < C.I; i++)
			for (int j = 0; j < C.J; j++)
				C.data[i][j] = tanh_derivative(C.data[i][j]);

		return C;
	}

	public static Matrix[] FeedForwardFunction(Matrix inputMatrix,
			Matrix weightMatrix, Matrix biasnodeMatrix) {

		Matrix horcat = Matrix
				.horizontalConcatenation( inputMatrix , biasnodeMatrix);
		
		//System.out.println("weightMatrix " + weightMatrix.data.length +" " + weightMatrix.data[0].length);
		//System.out.println("horcat " +horcat.data.length +" " +horcat.data[0].length);
		
		Matrix net = Matrix.multiply(horcat, weightMatrix);
		 
		Matrix output = activateFunction(net);
 
		return new Matrix[] { net, output };
		// return output matrix, net matrix
	}

	public static Matrix weightInitialization(double max,   int height, int width) {
		Matrix C = new Matrix(height , width); 
		Random rand= new Random();
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				C.data[i][j] = ThreadLocalRandom.current().nextDouble(-max,
						max  ) ;

		return C;
	}

	
	public static double tanh(double x)
	{
		return (Math.tanh(x)+1)/2;
	}
	
	public static double tanh_derivative(double x)
	{
		return (1- (Math.tanh(x)* Math.tanh(x)) )/2;
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
		
		Matrix classes = Matrix.outputToClass (outputs[1]);
 
		 
	 
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
		
		 
		double [] results =trainFunction(training,validation,test) ;
		
		 
		System.out.println("traing regression " + results[0]);
		System.out.println("traing classification " + results[1]);
		
		System.out.println("validation regression " + results[2]);
		System.out.println("validation classification " + results[3]);
		
		System.out.println("test regression " + results[4]);
		System.out.println("test classification " + results[5]);
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
