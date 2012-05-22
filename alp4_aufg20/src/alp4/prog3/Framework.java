/*
Copyright (C) 2012 Christoph Husse

Permission is hereby granted, free of charge, to any person obtaining a copy of this 
software and associated documentation files (the "Software"), to deal in the Software 
without restriction, including without limitation the rights to use, copy, modify, merge, 
publish, distribute, sublicense, and/or sell copies of the Software, and to permit 
persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or 
substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
DEALINGS IN THE SOFTWARE. 
 */
package alp4.prog3;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import javax.imageio.ImageIO;

public class Framework {

	static void processLabeling(String inputFile, String outputFile, ILabeler labeler) throws Exception {
			File src = new File(inputFile);
			
			if(!src.exists())
			{
				System.out.println("[ERROR]: File \"" + src.getCanonicalPath() + "\" does not exist!");
				return;
			}
			
			File dst = new File(outputFile);
			dst.delete();
			
			// read input image...
			BufferedImage img = ImageIO.read(src);
			float[] pixels = img.getData().getPixels(0, 0, img.getWidth(), img.getHeight(), (float[])null);
			int[][] image = new int[img.getWidth()][img.getHeight()];
			int[][] label = new int[image.length][image[0].length];
			boolean[][] visited = new boolean[image.length][image[0].length];
			
			for(int y = 0, i = 0; y < img.getHeight(); y++){
				for(int x = 0; x < img.getWidth(); x++){
					label[x][y] = i;
					image[x][y] = (int)pixels[i++];
				}
			}
			
			// apply labeling...
			labeler.process(image, label);
			
			// hacky greedy algorithm for minimal graph coloring
			HashMap<Integer,Integer> distinct = new HashMap<Integer,Integer>();
			for(int x = 0, i = 0; x < img.getWidth(); x++){
				for(int y = 0; y < img.getHeight(); y++){
					if(!distinct.containsKey(label[x][y]))
						distinct.put(label[x][y], ++i);
				}
			}
		
			Arrays.fill(pixels, 0);
			float lumFactor = (distinct.size() <= 1) ? 0 : 255.0f / (distinct.size() - 1);
			
			for(int y = 0, i = 0; y < img.getHeight(); y++){
				for(int x = 0; x < img.getWidth(); x++){
					pixels[i++] = (distinct.get(label[x][y])-1) * lumFactor;
				}
			}
			
			// write final grayscale label map to file...
			img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
			img.getRaster().setPixels(0, 0, img.getWidth(), img.getHeight(), pixels);
			ImageIO.write(img, "PNG", dst);
			
			
			System.out.println("[SUCCEEDED]");
	}

}
