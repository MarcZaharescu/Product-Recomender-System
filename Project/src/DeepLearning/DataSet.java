package DeepLearning;

public class DataSet {
	
	  int count;
	  Matrix input;
	  Matrix output;
	  Matrix classes;
	  Matrix bias;
	
	
	public DataSet(Matrix input, Matrix output, int nrOfClasses)
	{
	
	this.input = new Matrix(input.data);
	this.output = new Matrix(output.data);
	this.count = input.data.length;
	this.bias = new Matrix(count, 1);
	Matrix.initialiseOne(bias);
	this.classes = new Matrix(count, nrOfClasses);
	classes = Matrix.outputToClass(output);
	
	}
	
	 

}
