package DeepLearning;

final public class Matrix {
	int I;
	int J;
	double data[][];

	public Matrix(int I, int J) {
		this.I = I;
		this.J = J;
		this.data = new double[I][J];
	}

	public Matrix(double[][] data) {
		this.J = data[0].length;
		this.I = data.length;
		this.data = new double[I][J];

		for (int i = 0; i < I; i++)
			for (int j = 0; j < J; j++) {
				this.data[i][j] = data[i][j];
			}

	}

	public static Matrix split(Matrix A, int start, int end) {
		int I = A.data.length;
		int J = A.data[0].length;
		Matrix temp = new Matrix(end - start, J);

		int k = 0;
		for (int i = start; i < end; i++) {
			{
				for (int j = 0; j < J; j++)
					temp.data[k][j] = A.data[i][j];
				k++;
			}

		}

		return temp;
	}
	
	public static Matrix line(Matrix A, int line) {
		int I = A.data.length;
		int J = A.data[0].length;
		Matrix temp = new Matrix(1, J);

	 
				for (int j = 0; j < J; j++)
					temp.data[0][j] = A.data[line][j];
				 


		return temp;
	}
	public static Matrix element(Matrix A, int  number) {
		int I = A.data.length;
		int J = A.data[0].length;
		Matrix temp = new Matrix(1, 1);

			
				for (int j = 0; j < J; j++)
					for(int i=0;i<I;i++)
						if(i+j==number)
					temp.data[0][0] = A.data[i][j];
				 


		return temp;
	}

	public static double numberOfDifferences(Matrix A, Matrix B) {
		double diff = 0;

		for (int i = 0; i < A.I; i++)
			for (int j = 0; j < A.J; j++)
				if (A.data[i][j] != B.data[i][j])
					diff++;

		return diff;
	}
	
	public static Matrix approximateError(Matrix A)
	{
		Matrix C = new Matrix(A.data);
		
		for (int i = 0; i < A.I; i++)
			for (int j = 0; j < A.J; j++)
			{
				if(C.data[i][j]>0.85)
					C.data[i][j]=1;
				else C.data[i][j]=0;
			}
		
		return C;
	}

	public static Matrix transposition(Matrix A) {
		int I = A.data.length;
		int J = A.data[0].length;
		Matrix temp = new Matrix(J, I);
		for (int i = 0; i < I; i++)
			for (int j = 0; j < J; j++)
				temp.data[j][i] = A.data[i][j];

		return temp;
	}

	public static Matrix sum(Matrix A, Matrix B) {
		int I = A.data.length;
		int J = A.data[0].length;
		int I1 = B.data.length;
		int J1 = B.data[0].length;
		if (I != I1 || J != J1)
			throw new RuntimeException("Error: Wrong matrix dimentions");

		Matrix C = new Matrix(I, J);

		for (int i = 0; i < I; i++)
			for (int j = 0; j < J; j++) {
				C.data[i][j] = A.data[i][j] + B.data[i][j];
			}

		return C;

	}

	public static Matrix outputToClass(Matrix A) {
		Matrix C = new Matrix(A.I, 1);

		for (int i = 0; i < A.I; i++)
		{	double max=0;
			int o_class=0;
			for (int j = 0; j < A.J; j++) {
				if (A.data[i][j] >max)
				{
					max=A.data[i][j];
					o_class=j;
				}
			}
			C.data[i][0] = o_class+1;
		}

		return C;
	}

	public static Matrix classToOutput(Matrix A) {
		int nrOfClasses = 3;

		Matrix C = new Matrix(A.I, nrOfClasses);
		int k = 0;
		for (int i = 0; i < A.I; i++) {
			for (int j = 0; j < A.data[i][0]; j++) {
				k = j;
				C.data[i][j] = 0;
			}
			C.data[i][k] = 1;
			for (int j = (int) (A.data[i][0] + 1); j < nrOfClasses; j++) {
				C.data[i][j] = 0;
			}
		}
		return C;
	}

	 
	public static Matrix multiply(Matrix A, Matrix B) {
		int I = A.data.length;
		int J = A.data[0].length;
		int I1 = B.data.length;
		int J1 = B.data[0].length;
		if (J != I1)
			throw new RuntimeException("Error: Wrong matrix dimentions");

		Matrix C = new Matrix(I, J1);

		for (int i = 0; i < C.I; i++)
			for (int j = 0; j < C.J; j++)
				for (int x = 0; x < J; x++) {
					C.data[i][j] += A.data[i][x] * B.data[x][j];
				}

		return C;

	}

	public static Matrix difference(Matrix A, Matrix B) {
		int I = A.data.length;
		int J = A.data[0].length;
		int I1 = B.data.length;
		int J1 = B.data[0].length;
		if (I != I1 || J != J1)
			throw new RuntimeException("Error: Wrong matrix dimentions");

		Matrix C = new Matrix(I, J);

		for (int i = 0; i < I; i++)
			for (int j = 0; j < J; j++) {
				C.data[i][j] = A.data[i][j] - B.data[i][j];
			}

		return C;

	}

	public Matrix multiplyScalar(double k) {
		Matrix B = new Matrix(I, J);

		for (int i = 0; i < I; i++)
			for (int j = 0; j < J; j++)
				B.data[i][j] = this.data[i][j] * k;

		return B;

	}

	public static double sumComponents(Matrix A) {
		double sum = 0;
		for (int i = 0; i < A.I; i++)
			for (int j = 0; j < A.J; j++)
				sum += A.data[i][j];

		return sum;
	}

	public static Matrix hadamardProduct(Matrix A, Matrix B) {
		int I = A.data.length;
		int J = A.data[0].length;
		int I1 = B.data.length;
		int J1 = B.data[0].length;
		if (I != I1 || J != J1)
			throw new RuntimeException("Error: Wrong matrix dimentions");

		Matrix C = new Matrix(I, J);

		for (int i = 0; i < I; i++)
			for (int j = 0; j < J; j++)
				C.data[i][j] = A.data[i][j] * B.data[i][j];

		return C;
	}

	public static Matrix krocknerMultiplication(Matrix A, Matrix B) {
		int I = A.data.length;
		int J = A.data[0].length;
		int I1 = B.data.length;
		int J1 = B.data[0].length;
    
 
		Matrix C = new Matrix(I * I1, J * J1);
		 
		int y = -1;
		for (int i = 0; i < I; i++) {
			y++;
			int k = -1;

			for (int j = 0; j < J; j++) {
				k++;
				for (int i1 = 0; i1 < I1; i1++)
					for (int j1 = 0; j1 < J1; j1++) {

						if(I1==1)
							y=0;
						 
						C.data[i + i1 +y ][j + j1 + k] = A.data[i][j]
								* B.data[i1][j1];

					}

			}
		}
		return C;
	}

	public static void initialiseOne(Matrix A) {
		for (int i = 0; i < A.I; i++)
			for (int j = 0; j < A.J; j++)
				A.data[i][j] = 1;

	}

	public static Matrix horizontalConcatenation(Matrix A, Matrix B) {
		int I = A.data.length;
		int J = A.data[0].length;
		int I1 = B.data.length;
		int J1 = B.data[0].length;

		Matrix C = new Matrix(I, J + J1);

		for (int i = 0; i < I; i++)
			for (int j = 0; j < J; j++)
				C.data[i][j] = A.data[i][j];

		for (int i = 0; i < I; i++)
			for (int j = J; j < J + J1; j++)
				C.data[i][j] = B.data[i][j - J];

		return C;
	}

	public void print() {

		for (int i = 0; i < this.I; i++) {
			for (int j = 0; j < this.J; j++)
				System.out.print(" " + this.data[i][j]);
			System.out.println();
		}
	}

	public static void main(String[] args) {
		double[][] a = { { 1, 2 }, { 3, 4 } };
		Matrix A = new Matrix(a);
		A.print();
		System.out.println();

		double[][] b = { { 0, 5 }, { 6, 7 } };
		Matrix B = new Matrix(b);
		B.print();
		System.out.println("Sum");

		Matrix C = sum(A, B);
		C.print();
		System.out.println("Dif");

		Matrix D = difference(A, B);
		D.print();
		System.out.println("Multi");

		Matrix E = multiply(A, B);
		E.print();
		System.out.println("Transposition");

		Matrix F = transposition(A);
		F.print();
		System.out.println("Scalar multiplication");

		double k = 2;
		Matrix G = A.multiplyScalar(k);
		G.print();
		System.out.println();
		System.out.println("hadamardProduct");

		Matrix H = hadamardProduct(A, B);
		H.print();
		System.out.println("krocknerMultiplication");

		Matrix I = krocknerMultiplication(A, B);
		I.print();
		System.out.println("horizontalConcatenation");

		Matrix J = horizontalConcatenation(A, B);
		J.print();
		System.out.println("Sum components");

		double sum = sumComponents(A);
		System.out.println("sum= " + sum);

		System.out.println("Output To Class");
		double[][] f1 = { { 0, 0, 1 }, { 1, 0, 0 }, { 0, 1, 0 } };
		Matrix F1 = new Matrix(f1);
		F1 = outputToClass(F1);
		F1.print();
		System.out.println();

		System.out.println("Class to Output");
		F1 = classToOutput(F1);
		F1.print();
		System.out.println();
		
		System.out.println("krockner product");
		double[][] a1 = { { 1}, { 2}, {3} ,{4}};
		Matrix A1 = new Matrix(a1);
		double[][] b1 = { { 1,2,3 }   };
		Matrix B1 = new Matrix(b1);
		Matrix I1 = krocknerMultiplication(A1, B1);
		I1.print();
		
 
		 
		 
	}

}
