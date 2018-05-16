import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private int gridSize;
   private int unionSize;
   private int numOpenSites;
   private int[][] grid;
   public WeightedQuickUnionUF quickUnion;
   private int pValue;
   private int qValue;

   public Percolation(int n){
       if(n < 0)
           throw new IndexOutOfBoundsException("n is out of bounds\n");
       
       System.out.printf("Creating grid...\n");
       gridSize = n;
       unionSize = (gridSize + 1) * (gridSize + 1) + 1;
       quickUnion = new WeightedQuickUnionUF(unionSize);
       grid = new int[gridSize+1][gridSize+1];  
       //0 represents a closed site and 1 represents an open site.
       for (int i = 0; i <= n ; i++){
           for (int j = 0; j <= n; j++) {
               grid[i][j] = 0;
           }
       }
       for (int i = 0; i <= n; i++){
           for (int j = 0; j <= n; j++) {
               System.out.printf("%d ", grid[i][j]);
           }
               System.out.printf("\n");
       }    
   }
   public void open(int row, int col){
       outOfBounds(row,col);
       System.out.printf("Row: %d, Col: %d\n", row, col);
       grid[row][col] = 1;
       pValue = xyTo1d(row,col);
       System.out.printf("Pvalue - %d \n", pValue);
       numOpenSites++;    
       if(row - 1  >= 1 && isOpen(row-1, col)){
           qValue = xyTo1d(row-1, col);
           System.out.printf("Inside row -1, qValue = %d\n", qValue);
           quickUnion.union(pValue, qValue);   
       }
       if(col - 1 >= 1 && isOpen(row, col-1)){
           qValue = xyTo1d(row, col-1);
           System.out.printf("Inside col -1, qValue = %d\n", qValue);
           quickUnion.union(pValue, qValue);
       }
       if(row + 1 < gridSize + 1 && isOpen(row+1, col)){
           qValue = xyTo1d(row+1, col);
           System.out.printf("Inside row +1, qValue = %d\n", qValue);
           quickUnion.union(pValue, qValue);
       }
       if(col + 1 < gridSize + 1 && isOpen(row, col+1)){
           qValue = xyTo1d(row, col+1);
           System.out.printf("Inside col +1, qValue = %d\n", qValue);
           quickUnion.union(pValue, qValue);
       }
        //Virtual bottom
       if(row + 1 > gridSize) {
           System.out.printf("virtual bottom union\n");
           quickUnion.union(pValue, unionSize-1);
       }
       //Virtual top
       if(row - 1 < 1){
           System.out.printf("virtual top union\n");
           quickUnion.union(pValue, 0);
       }
   }// open site (row, col) if it is not open already
   private boolean outOfBounds(int row, int col){
       if(row > gridSize + 1 || col > gridSize + 1){
           throw new IndexOutOfBoundsException("Index is out of bounds\n");
       }
       if(col < 1 || row < 1){
           throw new IndexOutOfBoundsException("Index out of bounds\n");
       }
       return true;
   }
   public boolean isOpen(int row, int col){
       return grid[row][col] == 1;
   }
   public boolean isFull(int row, int col){       
       int pValue = xyTo1d(row,col);
       return (isOpen(row, col) && quickUnion.connected(pValue, 0));
   }
   
   public int numberOfOpenSites(){
       return numOpenSites;
   }
   public boolean percolates(){
       return quickUnion.connected(0, unionSize-1);
   }

   private int xyTo1d(int row, int col) {
       return (row * (gridSize + 1)) + col;
   }
       
   public static void main(String[] args){
   }// test client (optional)
}