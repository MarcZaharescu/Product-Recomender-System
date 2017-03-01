package DeepLearning;

public class DataSet {
	
	  int count;
	  Matrix input;
	  Matrix output;
	  Matrix classes;
	  Matrix bias;
	  int input_count;
	  int output_count;
	
	
	public DataSet(Matrix input, Matrix output, int nrOfClasses)
	{
	
	this.input = new Matrix(input.data);
	this.output = new Matrix(output.data);
	this.count = input.data.length;
	this.input_count=input.data[0].length;
	this.output_count=output.data[0].length;
	
	this.bias = new Matrix(count, 1);
	Matrix.initialiseOne(bias);
	this.classes = new Matrix(count, nrOfClasses);
	classes = Matrix.outputToClass(output);
	
	}
	
	 

}
