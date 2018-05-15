import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private int gridSize;
   private int numOpenSites;
   private int[][] grid;
   public WeightedQuickUnionUF quickUnion;
   private int pValue;
   private int qValue;

   
   public Percolation(int n){
      
       System.out.printf("Creating grid...\n");
       gridSize = n;
       quickUnion = new WeightedQuickUnionUF((gridSize * gridSize) + 2);
       grid = new int[gridSize][gridSize];
      
       //0 represents a closed site and 1 represents an open site.
       for (int i = 0; i < n; i++){
           for (int j = 0; j < n; j++) {
               grid[i][j] = 0;
           }
       }
       for (int i = 0; i < n; i++){
           for (int j = 0; j < n; j++) {
               System.out.printf("%d ", grid[i][j]);
           }
               System.out.printf("\n");
       }
       
   }
   public void open(int row, int col){
       if(row > gridSize-1 || col > gridSize-1){
           System.out.println("index out of range\n");
          
       }
       System.out.printf("Row: %d, Col: %d\n", row, col);
       grid[row][col] = 1;
       pValue = xyTo1d(row,col);
       System.out.printf("Pvalue - %d \n", pValue);
       numOpenSites++;
       //need to check area around it to see if it is open, if it is connect.      
       if(row - 1 >= 0){
           System.out.printf("Inside row -1\n");
           qValue = xyTo1d(row-1, col);
           quickUnion.union(pValue, qValue);   
       }
       if(col - 1 >= 0){
           System.out.printf("Inside col -1\n");
           qValue = xyTo1d(row, col-1);
           quickUnion.union(pValue, qValue);
       }
       if(row + 1 < gridSize){
           System.out.printf("Inside row +1\n");
           qValue = xyTo1d(row+1, col);
           quickUnion.union(pValue, qValue);
       }
       if(col + 1 < gridSize){
           System.out.printf("Inside col +1\n");
           qValue = xyTo1d(row, col+1);
           quickUnion.union(pValue, qValue);
       }
       //Virtual bottom
       if(row + 1 > gridSize) {
           System.out.printf("virtual bottom union\n");
           quickUnion.union(pValue, gridSize + 2);
       }
       //Virtual top
       if(row - 1 < 0){
           System.out.printf("virtual top union\n");
           quickUnion.union(pValue, gridSize + 1);
       }
   }// open site (row, col) if it is not open already

   public boolean isOpen(int row, int col){
       return grid[row][col] == 1;
   }// is site (row, col) open?
   public boolean isFull(int row, int col){
       return false;
   }// is site (row, col) full?
   
   public int numberOfOpenSites(){
       return numOpenSites;
   }// number of open sites
   public boolean percolates(){
       return quickUnion.connected(gridSize+2, gridSize + 1);
   }

   private int xyTo1d(int row, int col) {
//       System.out.printf("%d(row) * %d(gridSize) + %d(col)\n", row, gridSize, col);
       return (row * gridSize) + col;
   }
       
   public static void main(String[] args){
       Percolation test = new Percolation(4);
       System.out.printf("Num Componented %d\n", test.quickUnion.count());
       test.open(1,3);
       test.open(2,3);
       test.open(3,3);
       test.open(0,3);
       System.out.printf("Num Componented %d\n", test.quickUnion.count());
       System.out.printf("%s\n", test.percolates());
       for (int i = 0; i < test.gridSize; i++){
           for (int j = 0; j < test.gridSize; j++) {
               System.out.printf("%d ", test.grid[i][j]);
           }
               System.out.printf("\n");
       }
   }// test client (optional)
}