package DeepLearning;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class KNN {
	
	public static void main(String[] args) throws IOException
	{
		String dir="C:/Users/User/Desktop/dresses";
		readImages(dir);
	}
	

	public static void readImages(String directory_path) throws IOException {
		
		ArrayList<Matrix> all_images = new ArrayList<Matrix>();
		BufferedImage imgage=null;
		File img_directory = new File(directory_path);

		if ( img_directory.isDirectory()) { 
			for (  File f :  img_directory.listFiles()) {
				 

				imgage = ImageIO.read(f);
 
				System.out.println("image: " + f.getName());
				System.out.println(" width : " + imgage.getWidth());
				System.out.println(" height: " + imgage.getHeight());
				System.out.println(" size  : " + f.length());

			}
		}
	}

}
