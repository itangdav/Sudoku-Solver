import java.util.Queue;


public class Solver {
	//Set up a queue of possible boards
	Queue<Board> boardStates;
	Board currBoard;
	
	public Solver(Board b) throws CloneNotSupportedException{
		//Construct new Solver by adding the cloned board
		currBoard = (Board)b.clone();
	}
	
	public void solveOneStep() throws CloneNotSupportedException{
		
		//Update the Options Array
		updateOptionsUtil();

		//If a guess is necessary
		boolean guess = true;
		if(singleInRow()){
			currBoard.printBoard();
		}
		else{
			System.out.println("nope");
		}
		
	}
	
	//Update Options
	private void updateOptionsUtil(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				for(int n=1;n<10;n++){
					if(currBoard.grid[i][j].getOptions().contains(n)&&(currBoard.rows[i][n]==-1||currBoard.columns[j][n]==-1||currBoard.threeByThrees[currBoard.findThreeByThree(i, j)][n]==-1)){
						currBoard.grid[i][j].removeOne(n);
						currBoard.updateCounts(i, j, n);
					}
				}
			}
		}
	}
	
	//Check if only single place number can go in row, return true if true
	private boolean singleInRow(){
		for(int i=0;i<9;i++){
			for(int j=1;j<10;j++){
				if(currBoard.rows[i][j]==1){
					for(int y=0;y<9;y++){
						if(currBoard.grid[i][y].getOptions().contains(j)){
							currBoard.setSquare(i, y, j);
							return true;
						}
						
					}
				}
			}
		}
		return false; 
	}
	
	public static void main(String[] args) throws CloneNotSupportedException{
		
		
		int [][] grid = {
				{0,0,0,2,6,0,7,0,1},
				{6,8,0,0,7,0,0,9,0},
				{1,9,0,0,0,4,5,0,0},
				{8,2,0,1,0,0,0,4,0},
				{0,0,4,6,0,2,9,0,0},
				{0,5,0,0,0,3,0,2,8},
				{0,0,9,3,0,0,0,7,4},
				{0,4,0,0,5,0,0,3,6},
				{7,0,3,0,1,8,0,0,0}	
		};
		
		Board b = new Board(grid);
		
		
		
		Solver s = new Solver(b);
		for(int i=0;i<45;i++){

			s.solveOneStep();
		}
		
	}
}
