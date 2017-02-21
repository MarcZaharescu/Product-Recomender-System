package DeepLearning;

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

	public void forwardPropagation() {
		int weights = this.weights;
		String activate = this.activateFunction;

		// hidden sum = multiply example.input, weights.inputhidden
		// hidden result = hiddenSum transform sigmoid
		// outputSum = multiply hiddenOutput, hidden Result
		// outputResult = outputSum transform sigmoid

	}

	public static Matrix backwardPropagation(Matrix inputMatrix, Matrix weightMatrix, double learningRate, Matrix biasVector) {
		// double errorOutputLayer= substract(examples.Output -
		// results.outputResult)
		// deltaOutputLayer= dor(resultOutputSum.transform(activatePrime),
		// error)

		// hidden sum = multiply example.input, weights.inputhidden
		// hidden result = hiddenSum transform sigmoid
		// outputSum = multiply hiddenOutput, hidden Result
		// outputResult = outputSum transform sigmoid
		
		
		return weightMatrix;

	}
	
	public static Matrix trainFunction(Matrix trainingSet, Matrix validationSet, Matrix testSet)
	{
		Matrix weightMatrix = weightInitialization(1/2,2);
		Matrix bias_training, bias_validation, bias_test;
		
		return weightMatrix;
	}

	public static double sigmoidFunction(double x) {

		return (1 / (1 + Math.exp(-x)));

	}

	public static double sigmoidFunctionDerivative(double x) {

		return sigmoidFunction(x) * (1 - sigmoidFunction(x));

	}

	public static Matrix activateFunction(Matrix A) {
		double[][] a =  A.data;
		Matrix C = new Matrix(a);
		
		for (int i = 0; i < C.I; i++)
			for (int j = 0; j < C.J; j++)
				C.data[i][j] = sigmoidFunction(C.data[i][j]);

		return C;
	}

	public static Matrix activateFunctionDerivative(Matrix A) {
		double[][] a =  A.data;
		Matrix C = new Matrix(a);
		
		for (int i = 0; i < C.I; i++)
			for (int j = 0; j < C.J; j++)
				C.data[i][j] = sigmoidFunctionDerivative(C.data[i][j]);

		return C;
	}

	public  static Matrix[] FeedForwardFunction(Matrix inputMatrix, Matrix weightMatrix,
			Matrix biasnodeMatrix) {

		Matrix net = Matrix
				.horizontalConcatenation(inputMatrix, biasnodeMatrix);
		Matrix output =  activateFunction(net);
		
		return new Matrix[]{net,output};
		// return output matrix, net matrix
	}
	
	public static Matrix weightInitialization(double width, int height)
	{
		Matrix C = new Matrix(height,height);
		for(int i=0;i<height;i++)
			for(int j=0;j<height;j++)
				C.data[i][j]=ThreadLocalRandom.current().nextDouble(-width, width + 1);
				
		return C;
	}

	public static void evaluateNetworkError(Matrix inputMatrix, Matrix weightMatrix, Matrix targetOutputMatrix, Matrix targetClassMatrix, Matrix biasMatrix)
	{
		Matrix[]  outputZ=  FeedForwardFunction(inputMatrix ,weightMatrix,biasMatrix);
		Matrix temp = Matrix.difference(targetOutputMatrix, outputZ[1]);
		Matrix temp1= Matrix.hadamardProduct(temp,temp);
		double sum = Matrix.sumComponents(temp1);
		
		int sample_count = inputMatrix.I;
		int output_count=  outputZ[1].I;
		
		double normalized_sum = sum/(sample_count *output_count);
		
		// nr of classes that correpsond with the target classes and normalise
		double classes = outputZ[1].I/sample_count;
	}
	
	
	public static void main(String[] args) {
		double[][] a = { { 1, 2 }, { 3, 4 } };
		Matrix A = new Matrix(a);
		double[][] b = { { 0, 5 }, { 6, 7 } };
		Matrix B = new Matrix(b);
		
		double [][] c = {{1},{1}};
		Matrix C = new Matrix(c);
		// c is a bias column vector with 1s and the length is the same size as a
		Matrix[] D=  FeedForwardFunction(A,B,C);
		
		D[0].print();
		System.out.println();
		D[1].print();
		System.out.println();
		
		System.out.println("weight initialization");
		Matrix E = weightInitialization(1,2);
		E.print();

	}
	 
}
