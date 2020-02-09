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
		
	}
	
	//Update Options
	private void updateOptionsUtil(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				for(int n=1;n<10;n++){
					if(currBoard.grid[i][j].getOptions().contains(n)&&(currBoard.rows[i][n]==-1||currBoard.columns[j][n]==-1||currBoard.threeByThrees[currBoard.findThreeByThree(i, j)][n]==-1)){
						currBoard.grid[i][j].removeOne(n);
						currBoard.updateCounts(i, j, n);
						System.out.println(i+" "+j+" "+n);
					}
				}
			}
		}
	}
	
//	public static void main(String[] args) throws CloneNotSupportedException{
//		Board b = new Board();
//		b.setSquare(6, 8, 8);
//		Solver s = new Solver(b);
//		s.solveOneStep();
//		b=s.currBoard;
//		for(int i=0;i<9;i++){
//			for(int j=0;j<9;j++){
//				System.out.print(b.grid[i][j].getNum());
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//		for(int i=0;i<9;i++){
//			for(int j=1;j<10;j++){
//				System.out.print(b.rows[i][j]);
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//		for(int i=0;i<9;i++){
//			for(int j=1;j<10;j++){
//				System.out.print(b.columns[i][j]);
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//		for(int i=0;i<9;i++){
//			for(int j=1;j<10;j++){
//				System.out.print(b.threeByThrees[i][j]);
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//	
//	}
}
