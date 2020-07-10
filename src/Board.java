import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//Represents a 9 by 9 grid of squares


public class Board implements Cloneable{
	//Represents the 9 by 9 grid of squares
	public Square [][] grid = new Square[9][9];
	
	//Set up a 2D array for number of squares for which a number is an option in each row/column, i.e. rows[1][2] queries number of 2 options in row 1, set to -1 if a row already has a finalized number of that number
	public int[][] rows = new int[9][10];
	public int[][] columns = new int[9][10];
	//Same as above except for the 9 3x3 grids, labeled: 
	/*
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8 
	 */
	public int[][] threeByThrees= new int[9][10];
	
	//Initializes the Board to have all empty squares with no conditions
	public Board(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				grid[i][j]=new Square();
			}
		}
		for(int i=0;i<9;i++){
			Arrays.fill(rows[i], 9);
			Arrays.fill(columns[i], 9);
			Arrays.fill(threeByThrees[i], 9);
		}
	}
	
	//Initializes the Board to have all squares be the ones in the array, with 0 being empty squares
	public Board(int[][]nums){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				grid[i][j]=new Square();
			}
		}
		for(int i=0;i<9;i++){
			Arrays.fill(rows[i], 9);
			Arrays.fill(columns[i], 9);
			Arrays.fill(threeByThrees[i], 9);
		}
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(nums[i][j]!=0){
					this.setSquare(i, j, nums[i][j]);
				}
			}
		}
	}
	
	//Creates a deep clone of the Board 
	public Object clone() throws CloneNotSupportedException{
		Board temp = (Board) super.clone();
		Square[][] tempGrid = new Square[9][9];
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				tempGrid[i][j] = (Square)grid[i][j].clone();
			}
		}
		temp.grid=tempGrid;
		
		int [][] tempRows = new int[9][10];
		int [][] tempColumns = new int[9][10];
		int [][] tempThreebyThree = new int[9][10];
		for(int i=0;i<9;i++){
			for(int j=0;j<10;j++){
				tempRows[i][j] = rows[i][j];
				tempColumns[i][j] = columns[i][j];
				tempThreebyThree[i][j]= threeByThrees[i][j];
			}
		}
		rows = tempRows;
		columns = tempColumns;
		threeByThrees= tempThreebyThree;
		
		return temp;
	}
	//Creates a deep clone of the Board missing one option
	public Object clone(int x, int y, int numberToBeRemoved) throws CloneNotSupportedException{
		Board temp = (Board) super.clone();
		Square[][] tempGrid = new Square[9][9];
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				tempGrid[i][j] = (Square)grid[i][j].clone();
			}
		}
		
		int [][] tempRows = new int[9][10];
		int [][] tempColumns = new int[9][10];
		int [][] tempThreebyThree = new int[9][10];
		for(int i=0;i<9;i++){
			for(int j=0;j<10;j++){
				tempRows[i][j] = rows[i][j];
				tempColumns[i][j] = columns[i][j];
				tempThreebyThree[i][j]= threeByThrees[i][j];
			}
		}
		rows = tempRows;
		columns = tempColumns;
		threeByThrees= tempThreebyThree;
		
		
		
		tempGrid[x][y].removeOne(numberToBeRemoved);
		temp.grid=tempGrid;
		
		temp.updateCounts(x,y,numberToBeRemoved);
		
		return temp;
	}
	//setSquare at x,y to be number n and return true if possible
	public boolean setSquare(int x, int y, int n){
		Set<Integer> lostOptions = new HashSet<Integer>();
		lostOptions.addAll(grid[x][y].getOptions());
		
		if(grid[x][y].setNum(n)){
			rows[x][n]=-1;
			columns[y][n]=-1;
			threeByThrees[findThreeByThree(x, y)][n]=-1;
			
			updateCounts(x,y,lostOptions);
			return true;
		}
		else{
			return false;
		}
	}
	//updates the count for the rows/columns arrays when removing set of options
	public void updateCounts(int x, int y, Set<Integer> lostOptions){
		for(int i:lostOptions){
			rows[x][i]=Integer.max(-1, rows[x][i]-1);
			columns[y][i]=Integer.max(-1, columns[y][i]-1);
			threeByThrees[findThreeByThree(x, y)][i]=Integer.max(-1, threeByThrees[findThreeByThree(x, y)][i]-1);
		}
	}
	//updates the count for the rows/columns arrays when removing single option
	public void updateCounts(int x, int y, int i){
		rows[x][i]=Integer.max(-1, rows[x][i]-1);
		columns[y][i]=Integer.max(-1, columns[y][i]-1);
		threeByThrees[findThreeByThree(x, y)][i]=Integer.max(-1, threeByThrees[findThreeByThree(x, y)][i]-1);
	}
	
	//Finds the Three by Three label
	public int findThreeByThree(int x, int y){
		return (x/3)*3 + y/3;
	}
	
	//Print out current board with known values
	public void printBoard(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(this.grid[i][j].getNum()+" ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	//Print out current board rows values for debugging purposes
	public void printrows(){
		for(int i=0;i<9;i++){
			for(int j=1;j<=9;j++){
				System.out.print(this.rows[i][j]);
			}
			System.out.println("");
		}
	}
	
	//Testing method
//	public static void main(String[]args) throws CloneNotSupportedException{
//		Board b1 = new Board();
//		Board b2 = (Board) b1.clone(6,8,2);
//		b1.grid[6][8].setNum(8);
//		System.out.println(b1.grid[0][0].getOptions());
//		System.out.println(b2.grid[0][0].getOptions());
//		b2.printBoard();
//		System.out.println("");
//		for(int i=0;i<9;i++){
//			for(int j=1;j<10;j++){
//				System.out.print(b2.rows[i][j]);
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//		for(int i=0;i<9;i++){
//			for(int j=1;j<10;j++){
//				System.out.print(b2.columns[i][j]);
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//		for(int i=0;i<9;i++){
//			for(int j=1;j<10;j++){
//				System.out.print(b2.threeByThrees[i][j]);
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//	}
}
