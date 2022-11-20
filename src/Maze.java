
import javax.swing.JOptionPane;
import java.util.ArrayList; 
import java.awt.Color; 
 
/** 
 * A class for decomposing images into connected components. 
 * The main method initates a Maze Solver which prompts the user to enter the name of a maze image file.
 * The program then outputs whether the given maze has a solution, and displays the maze with its components colored in.
 * A maze with a solution will have the entire maze path (which is the white background) in the same color.
 * A maze without a solution will have the maze path divided into seperate colors, demonstrating where the solver reaches a dead end.
 */ 
public class Maze { 
 
   private UnionFind uf; 
   private DisplayImage image; 
   private int startX;   // x-coordinate for the start pixel
   private int startY;	 // y-coordinate for the start pixel
   private int endX;	 // x-coordinate for the end pixel
   private int endY;	 // y-coordinate for the end pixel
  
  /** 
    * Constructor - reads image, and decomposes it into its connected components.
    * After calling the constructor, the mazeHasSolution() and areConnected() methods
	* are expect to return a correct solution.
    * 
    * @param fileName name of image file to process. 
    */ 
   public Maze (String fileName, Color c) { 
		this.image = new DisplayImage(fileName);
      int height = image.height();
      int width = image.width();
      int numElements = height*width;
      this.uf = new UnionFind(numElements);

      boolean foundStart = false;

      for (int y=0; y<height-1; y++) {
         //In this inner loop we traverse over each pixel in the row, except for the last column.  
         for (int x=0; x<width-1; x++) {

            if (image.isRed(x, y)) {
               if (foundStart) {
                  this.endX = x;
                  this.endY = y;
                  image.set(x, y, c);
               }
               else {
                  this.startX = x;
                  this.startY = y;
                  image.set(x, y, c);
                  foundStart = true;
               }
            }
            //We run connect for each pixel on its right and downside neighbour.
            connect(x, y, x+1, y); 
            connect(x, y, x, y+1);

         }
         //We complete the check for the last column seperately since these pixels only have downside neighbours.
         connect(width-1, y, width-1, y+1);
      }

      //The last row is done seperately because these pixels only have a right neighbour.
      for (int i =0; i<width-1; i++) {
         connect(i, height-1, i+1, height-1);
      }
   } 
 
   /** 
    * Generates a unique integer id from (x, y) coordinates. 
    * It is suggested you implement this function, in order to transform
	* pixels into valid indices for the UnionFind data structure.
    *
	* @param x x-coordinate. 
    * @param y y-coordinate. 
    * @return unique id. 
    */ 
   private int pixelToId (int x, int y) { 
      
      int id = (y*this.image.width())+x+1; //We add one to reach the UnionFind format where the numbering started from 1, not 0.
      return id;
   } 
 
   /** 
    * Connects two pixels if they both belong to the same image 
    * area (background or foreground), and are not already connected. 
    * It is assumed that the pixels are neighbours.
    * 
    * @param x1 x-coordinate of first pixel. 
    * @param y1 y-coordinate of first pixel. 
    * @param x2 x-coordinate of second pixel. 
    * @param y2 y-coordinate of second pixel. 
    */ 
   public void connect (int x1, int y1, int x2, int y2) { 
		if (!areConnected(x1, y1, x2, y2)) {
         //We call union on the representatives of the pixels we want to connect.
         if (image.isOn(x1, y1) == image.isOn(x2, y2)) {
            uf.union(uf.find(pixelToId(x1, y1)), uf.find(pixelToId(x2, y2)));
         }
      }
   } 
 

 
   /** 
    * Checks if two pixels are connected (belong to the same component). 
    * 
    * @param x1 x-coordinate of first pixel. 
    * @param y1 y-coordinate of first pixel. 
    * @param x2 x-coordinate of second pixel. 
    * @param y2 y-coordinate of second pixel. 
    * @return true if the pixels are connected, false otherwise. 
    */ 
   public boolean areConnected (int x1, int y1, int x2, int y2) { 
		//We check if pixels are connected, by comparing their representatives using the find funciton from UnionFind.
      if (uf.find(pixelToId(x1, y1)) == uf.find(pixelToId(x2, y2))) {
         return true;
      }
		return false;
   } 
 
   /** 
    * Finds the number of components in the image. 
    * 
    * @return the number of components in the image 
    */ 
   public int getNumComponents() { 
		return uf.numSets;
   } 
 
   /**
    * Returns true if and only if the maze has a solution.
    * In this case, the start and end pixel will belong to the same connected component.
    * 
    * @return true if and only if the maze has a solution
    */
   public boolean mazeHasSolution(){
		if (uf.find(pixelToId(startX, startY)) == uf.find(pixelToId(endX, endY))) {
         return true;
      }
		return false;
      
   }
      
   /** 
    * Creates a visual representation of the connected components. 
    * 
    * @return a new image with each component colored in a random color. 
    */ 
   public DisplayImage getComponentImage() { 
 
      DisplayImage compImg = new DisplayImage (image.width(), image.height()); 
      ArrayList<Integer> usedIds = new ArrayList<Integer>(); 
      int numComponents = getNumComponents(); 
      final int MAX_COLOR = 0xffffff; 
      Color colors[] = new Color[numComponents]; 
 
      colors[0] = new Color (0, 0, 100); 
      for (int c = 1; c < numComponents; c++) 
         colors[c] = new Color ((int)(Math.random() * MAX_COLOR)); 
 
      for (int x = 0; x < image.width(); x++) 
         for (int y = 0; y < image.height(); y++) { 
            int componentId = uf.find (pixelToId (x, y)); 
            // Check if this id already exists (inefficient for a large number of components). 
            int index = -1; 
            for (int i = 0; i < usedIds.size(); i++) 
               if (usedIds.get (i) == componentId) 
                  index = i; 
            if (index == -1) { 
               usedIds.add (componentId); 
               index = usedIds.size() - 1; 
            } 
            compImg.set (x, y, colors[index]); 
         } 
 
      return compImg; 
   } 
 
   /** 
    * Various tests for the segmentation functionality. 
    * 
    * @param mazeToSolve - name of file to process. 
    */ 
   public static void main (String[] args) { 
         
      String mazeToSolve = JOptionPane.showInputDialog( "Enter name of maze file from Mazes directory (including .PNG extension)" ); 
	   Maze maze = new Maze ("./Mazes/"+mazeToSolve, Color.white); 
      
      DisplayImage compImg = maze.getComponentImage(); 
      compImg.show(); 

      if (maze.mazeHasSolution()) {
         JOptionPane.showMessageDialog( null, "This maze has a solution. Notice that the maze path, which was originally white, is all in the same color.", "Maze Solver", JOptionPane.PLAIN_MESSAGE ); 
      }
      else {JOptionPane.showMessageDialog( null, "This maze has no solution. Notice that the maze path, which was originally white, is divided into different colors. This shows that each possible path reached a dead end.", "Maze Solver", JOptionPane.PLAIN_MESSAGE );} 
      
   } 
} 