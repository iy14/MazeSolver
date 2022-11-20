

/** 
 * Implementation of the Union-Find ADT. 
 */ 
public class UnionFind { 
 
   private int up[]; 
   private int weight[]; 
   int numSets; 
 
 
   /** 
    * Constructor - initializes up and weight arrays. 
    * 
    * @param numElements initial number of singleton sets. 
    */ 
   public UnionFind (int numElements) { 
      this.up = new int [numElements+1];
      this.weight = new int [numElements+1];
      this.numSets = numElements;
		for (int i = 1; i<=numElements; i++) {
         this.up[i] = i;
         this.weight[i] = 1;
      }
   }
   
   /** 
    * Unites two sets using weigthed union. 
    * 
    * @param i representative of first set. 
    * @param j representative of second set. 
	* If i or j are not representative elements - throw an IllegalArgumentException
    */ 
   public void union (int i, int j) { 
		if (up[i]!=i || up[j]!=j) {
         throw new IllegalArgumentException(i + " and " + j + " are not representative elements.");
      }
      else {
         int jointWeight = weight[i]+weight[j];
         if (weight[i]<weight[j]) {
            up[i] = j;
            weight[j]=jointWeight;
            numSets--;
         }
         else {
            up[j] = i;
            weight[i] = jointWeight;
            numSets--;
         }
      }
   } 
 
   /** 
    * Finds the set representative, and applies path compression. 
    * 
    * @param i the element to search. 
    * @return the representative of the group that contains i. 
    */ 
   public int find (int i) { 
		if (up[i] != i) {
         up[i] = find(up[i]);
      }
      return up[i];
   } 
 
   /** 
    * Find the current number of sets. 
    * 
    * @return the number of set. 
    */ 
   public int getNumSets() { 
		return numSets;
   } 
 
   /** 
    * Prints the contents of the up array. 
    */ 
   private void debugPrintUp() { 
 
      System.out.print ("up:     "); 
      for (int i = 1; i < up.length; i++) 
         System.out.print (up[i] + " "); 
      System.out.println (""); 
   } 
 
   /** 
    * Prints the results of running find on all elements. 
    */ 
   private void debugPrintFind() { 
 
      System.out.print ("find:   "); 
      for (int i = 1; i < up.length; i++) 
         System.out.print (find (i) + " "); 
      System.out.println (""); 
   } 
 
   /** 
    * Prints the contents of the weight array. 
    */ 
   private void debugPrintWeight() { 
 
      System.out.print ("weight: "); 
      for (int i = 1; i < weight.length; i++) 
         System.out.print (weight[i] + " "); 
      System.out.println (""); 
   } 
 
   /** 
    * Various tests for the Union-Find functionality. 
    * 
    * @param args command line arguments - not used. 
    */ 
   public static void main (String[] args) { 
 
      UnionFind uf = new UnionFind (10); 
 
      uf.debugPrintUp(); 
      uf.debugPrintFind(); 
      uf.debugPrintWeight(); 
      System.out.println ("Number of sets: " + uf.getNumSets()); 
      System.out.println (""); 
 
      uf.union (2, 1); 
      uf.union (3, 2); 
      uf.union (4, 2); 
      uf.union (5, 2); 
 
      uf.debugPrintUp(); 
      uf.debugPrintFind(); 
      uf.debugPrintWeight(); 
      System.out.println ("Number of sets: " + uf.getNumSets()); 
      System.out.println(); 
 
      uf.union (6, 7); 
      uf.union (8, 9); 
      uf.union (6, 8); 
 
      uf.debugPrintUp(); 
      uf.debugPrintFind(); 
      uf.debugPrintWeight(); 
      System.out.println ("Number of sets: " + uf.getNumSets()); 
      System.out.println(); 
 
      uf.find (8); 
 
      uf.debugPrintUp(); 
      uf.debugPrintFind(); 
      uf.debugPrintWeight(); 
      System.out.println ("Number of sets: " + uf.getNumSets()); 
      System.out.println(); 
   } 
} 
