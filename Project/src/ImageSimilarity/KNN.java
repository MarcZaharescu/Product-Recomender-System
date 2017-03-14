package ImageSimilarity;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import DeepLearning.Matrix;

public class KNN {

	public static void main(String[] args) throws IOException {
		String dir = "C:/Users/User/Desktop/dresses";
		readImages(dir);
	}

	public static void readImages(String directory_path) throws IOException {

		ArrayList<Matrix> all_images = new ArrayList<Matrix>();
		BufferedImage image = null;
		File img_directory = new File(directory_path);

		if (img_directory.isDirectory()) {
			for (File f : img_directory.listFiles()) {

				image = ImageIO.read(f);

				System.out.println("image: " + f.getName());
				System.out.println(" width : " + image.getWidth());
				System.out.println(" height: " + image.getHeight());
				System.out.println(" size  : " + f.length());
				int Height = image.getHeight();
				int Width = image.getWidth();
				double data[][] = new double[Width][Height];

				for (int i = 0; i < Width ; i++) {
					for (int j = 0; j < Height ; j++) {
						data[i][j] = image.getRGB(i, j);
						System.out.println(data[i][j]);
						 Matrix m = new Matrix(data);
						 all_images.add(m);
						 System.out.println("d" + (i*j));
					}
				}
				
				break;
			 
			}
			 

		}
		 
	}

}
