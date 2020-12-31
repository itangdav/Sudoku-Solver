import java.util.Stack;


public class Solver {
	//Set up a queue of possible boards
	Stack<Board> boardStates;
	Board currBoard;
	
	public Solver(Board b) throws CloneNotSupportedException {
		//Construct new Solver by adding the cloned board
		currBoard = (Board) b.clone();
		
		boardStates = new Stack<Board>();
	}
	//returns if it is done or not
	public boolean solveOneStep() throws CloneNotSupportedException {
		
		//Update the Options Array
		updateOptionsUtil();

		//If a guess is necessary
		boolean guess = true;
		
		//If we fail
		boolean fail = false;
		
		//If we are done
		boolean done = true;
		
		//Check all single cells for contradictions or naked singles
		for(int i = 0; i < 9; i++){
			for(int n = 1; n <= 9; n++){
				if(currBoard.rows[i][n] == 0 || currBoard.columns[i][n] == 0 || currBoard.threeByThrees[i][n] == 0){
					guess = false;
					fail = true;
				}
			}
			
			for(int j = 0; j < 9; j++){
				if(currBoard.grid[i][j].getNum() == 0 && currBoard.grid[i][j].getSetSize() == 0){
					guess = false;
					fail = true;
				} else if(currBoard.grid[i][j].getSetSize() == 1){
					guess = false;
					currBoard.setSquare(i, j, currBoard.grid[i][j].getOptions().iterator().next());
					currBoard.printBoard();
				}
				if(done && currBoard.grid[i][j].getNum() == 0) {
					done = false;
				}
			}
		}
		
		//Do some algorithms regarding rows, columns, 3x3s
		if(guess){
			if(this.singleInRow()){
				guess = false;
				currBoard.printBoard();
			} else if(this.singleInColumn()){
				guess = false;
				currBoard.printBoard();
			} else if(this.singleInThreeByThree()){
				guess = false;
				currBoard.printBoard();
			}
			
		}
		
		//Make Guess
		if(!done && guess){
			int min = 10;
			int x = -1, y = -1;
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
					if(currBoard.grid[i][j].getNum() == 0 && currBoard.grid[i][j].getSetSize() < min){
						min = currBoard.grid[i][j].getSetSize();
						x = i;
						y = j;
					}
				}
			}
			int numGuessed = currBoard.grid[x][y].getOptions().iterator().next();
					
			//Add new board into boardStates which we use if false, so we remove the guess as an option
			Board tempBoard = (Board) currBoard.clone(x, y, numGuessed); 
			boardStates.add(tempBoard);
			
			//Now make guess
			currBoard.setSquare(x, y, numGuessed);
			currBoard.numberOfGuesses++;
			currBoard.printBoard();
			System.out.println("guess " + x + " " + y + " " + numGuessed);
			
		}
		
		//If it fails, we reset to last board state before guess
		if(fail){
			if(boardStates.size() >= 1){
				currBoard = boardStates.pop();
			} else{
				System.out.println("Impossible");
				done = true;
			}
		}
		
		return done;
	}
	
	//Update Options
	private void updateOptionsUtil() {
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				for(int n = 1; n < 10; n++){
					if(currBoard.grid[i][j].getOptions().contains(n) && (currBoard.rows[i][n] == -1 || currBoard.columns[j][n] == -1 || currBoard.threeByThrees[currBoard.findThreeByThree(i, j)][n] == -1)){
						currBoard.grid[i][j].removeOne(n);
						currBoard.updateCounts(i, j, n);
					}
				}
			}
		}
	}
	
	//*****************************
	//ALGORITHMS BEGIN
	//*****************************
	
	//Check if only single place number can go in row, return true if true
	private boolean singleInRow() {
		for(int i = 0; i < 9; i++){
			for(int j = 1; j < 10; j++){
				if(currBoard.rows[i][j] == 1){
					for(int y = 0; y < 9; y++){
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
	
	//Check for single in Column
	private boolean singleInColumn() {
		for(int i = 0; i < 9; i++){
			for(int j = 1; j < 10; j++){
				if(currBoard.columns[i][j] == 1){
					for(int x = 0; x < 9; x++){
						if(currBoard.grid[x][i].getOptions().contains(j)){
							currBoard.setSquare(x, i, j);
							return true;
						}
						
					}
				}
			}
		}
		return false; 
	}
	
	//Check for single in Three by Three
	private boolean singleInThreeByThree() {
		for(int i = 0; i < 9; i++){
			for(int j = 1; j < 10; j++){
				if(currBoard.threeByThrees[i][j] == 1){
					for(int x = (i / 3) * 3; x < (i / 3) * 3 + 3; x++){
						for(int y = (i % 3) * 3; y < (i % 3) * 3 + 3; y++){
							if(currBoard.grid[x][y].getOptions().contains(j)){
								currBoard.setSquare(x, y, j);
								return true;
							}
						}
						
						
					}
				}
			}
		}
		return false; 
	}
	
	//*****************************
	//ALGORITHMS STOP
	//*****************************
	
	//testing method
	public static void main(String[] args) throws CloneNotSupportedException {
		
		
		int [][] grid = {
				{0,0,0,8,0,1,0,0,0},
				{0,0,0,0,0,0,0,4,3},
				{5,0,0,0,0,0,0,0,0},
				{0,0,0,0,7,0,8,0,0},
				{0,0,0,0,0,0,1,0,0},
				{0,2,0,0,3,0,0,0,0},
				{6,0,0,0,0,0,0,7,5},
				{0,0,3,4,0,0,0,0,0},
				{0,0,0,2,0,0,6,0,0}	
		};
		
		Board b = new Board(grid);
		
		Solver s = new Solver(b);
		for(int i = 0; i < 450; i++){

			boolean done = s.solveOneStep();
			
			if(done){
				i = 450;
				System.out.println("Done");
				System.out.println("Number of guesses: " + s.currBoard.numberOfGuesses);
			}
		}
		
	}
}
