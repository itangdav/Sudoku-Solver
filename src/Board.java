import java.util.HashSet;
import java.util.Set;

//Represents a 9 by 9 grid of squares


public class Board implements Cloneable{
	//Represents the 9 by 9 grid of squares
	private Square [][] grid = new Square[9][9];
	//Initializes the Board to have all empty squares with no conditions
	public Board(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				grid[i][j]=new Square();
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
		tempGrid[x][y].removeOne(numberToBeRemoved);
		temp.grid=tempGrid;
		return temp;
	}
	//Testing method
	public static void main(String[]args) throws CloneNotSupportedException{
		Board b1 = new Board();
		b1.grid[0][0].removeOne(9);
		Board b2 = (Board) b1.clone(0,0,1);
		b1.grid[0][0].setNum(8);
		b2.grid[0][0].removeOne(7);
		System.out.println(b1.grid[0][0].getOptions());
		System.out.println(b2.grid[0][0].getOptions());
		
	}
}
