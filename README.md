## Java Sudoku Solver

This is an algorithm-based Sudoku solver that will always find at least one right solution if one exists. It's modular with respect to the algorithms, and the time complexity with respect to the number of algorithms is linear, assuming all algorithms have the same inherent complexity. 

### Algorithm Overview
Currently, the code stores not only the board, but the possible digits that each cell can take and the number of possible places a digit can be placed in each row/column/three by three grid. Then, the code first checks to see if any digit can be placed instantly, for example if there is only one possible place to put the digit 1 in column 1. After each placement, all the stored data of the board is updated appropriately. 

After a point where no more digits can be placed, it goes through a set of algorithms (more of which can easily be added to) that will eliminate possible digit placements. For example, if the only place where we can place the digit 1 in row 1 is the three squares in the top left three by three, then no other square in that three by three can contain the digit 1. 

Once it gets completely stuck, I have implemented an optimal branching algorithm, which finds the square with the least possibilities, and creates a deep copy of the current board with a guess for that square as well as pushing the current board onto a stack. If that guess turns out to be right, then we are done! If it leads to a contradiction, then we load up the previous board on the stack and remove that guess as a possibility. This algorithm is able to solve the famous 17 digit boards with an unique solution with only a maximum of 3 guesses. 

### Inputs
I am planning on adding a Java Graphics UI someday, but for now, I have a main method at the bottom of the boards.java file where you can manually fill in the board into a 2D array as input.  
