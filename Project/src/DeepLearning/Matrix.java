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

	public static Matrix multiply(Matrix A, Matrix B) {
		int I = A.data.length;
		int J = A.data[0].length;
		int I1 = B.data.length;
		int J1 = B.data[0].length;
		if (J != I1)
			throw new RuntimeException("Error: Wrong matrix dimentions");

		Matrix C = new Matrix(J, I1);

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
	public static double sumComponents(Matrix A)
	{
		double sum=0;
		for(int i=0;i<A.I;i++)
			for(int j=0;j<A.J;j++)
				sum+=A.data[i][j];
		
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
						C.data[i + i1 + y][j + j1 + k] = A.data[i][j]
								* B.data[i1][j1];

					}

			}
		}
		return C;
	}

	public static Matrix horizontalConcatenation(Matrix A, Matrix B) {
		int I = A.data.length;
		int J = A.data[0].length;
		int I1 = B.data.length;
		int J1 = B.data[0].length;

		Matrix C = new Matrix(I, J + J1);

		for (int i = 0; i < I; i++)  
			for (int j = 0; j < J; j++) 	
				C.data[i][j]=A.data[i][j];
			 
		for (int i = 0; i < I; i++)  
			for (int j = J; j < J+J1; j++) 	
				C.data[i][j]=B.data[i][j-J];
		 

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
		System.out.println("sum= "+sum);
		
	}

}
