## Java Sudoku Solver

This is an algorithm-based Sudoku solver that will always find at least one right solution if one exists. It's modular with respect to the algorithms, and the time complexity with respect to the number of algorithms is linear, assuming all algorithms have the same inherent complexity. 

### Algorithm Overview
Currently, the code stores not only the board, but the possible digits that each cell can take and the number of possible places a digit can be placed in each row/column/three by three grid. Then, the code first checks to see if any digit can be placed instantly, for example if there is only one possible place to put the digit 1 in column 1. After each placement, all the stored data of the board is updated appropriately. 

After a point where no more digits can be placed, it goes through a set of algorithms (more of which can easily be added to) that will eliminate possible digit placements. For example, if the only place where we can place the digit 1 in row 1 is the three squares in the top left three by three, then no other square in that three by three can contain the digit 1. 

Once it gets completely stuck, I have implemented an optimal branching algorithm, which finds the square with the least possibilities, and creates a deep copy of the current board with a guess for that square as well as pushing the current board onto a stack. If that guess turns out to be right, then we are done! If it leads to a contradiction, then we load up the previous board on the stack and remove that guess as a possibility. This algorithm is able to solve the famous 17 digit boards with an unique solution with only a maximum of 3 guesses. 

### Documentation for Code
There are 3 important files for this project:
#### Square.java defines the square class which has the following properties
1. finalNumber: the number of that square, which is 0 if it's still blank
2. options: A set of possible valid finalNumbers this square can take, is empty if finalNumber is not 0.
(All the functions should be self-explanatory based on its name and comments)

#### Board.java defines the board class which has the following properties
1. grid: a 9 x 9 array of squares representing the sudoku board
2. numberOfGuesses: an integer tracking the number of times we have to make an optimized guess (which leads to branching)
3. rows: a 2-d array where rows[i][j] represents the number of squares in row i (counting from the top, indexed starting at zero) has j as an option, note that j=0 is never used.
4. columns: same as rows except for columns that are zero-indexed and starting from the left.
5. threeByThrees: same as rows but for each three by three region of the grid which is zero indexed and starts from the top left and labelled in the way that we would read a book (towards the right and wrapping around to next line).
(All the functions should be self-explanatory based on its name and comments)

#### Solver.java defines the solver class which has teh following properties
1. boardStates: a stack of boards that represent the "other branch" we could have head down. For example, when we guess a square is a 1, we push onto the stack a board where this square is not a 1 (remove 1 from the options) which allows for us to backtrack.
2. currBoard: the current board that we are working on solving
3. solveOneStep(): this is the function that solves one more step of the board by either making a guess or placing a number. First, it calls updateOptionsUtil() which is a utility function that updates the options sets of all the squares. Then, it checks all single cells for contradictions (no options left but finalNumber is 0) or naked singles (only one option left). As well, if a single cell doesn't have a finalNumber not equal to 0, we would set done (which is initialized as true) as false. Afterwards, if no contradictions or naked singles are found, we can run through a set of algorithms to try to solve for one of the squares. If these algorithms all fail, we have to make a guess, which finds the square with the least number of options and guesses one of these options. Now, lastly, if we actually got a contradiction, we would backtrack down into another branch and if there is no more backtracking possible, the board has no solutions so we would set done as true. This function returns the boolean done of whether the board is solved or has no solution.
(All the other functions should be self-explanatory based on its name and comments)

### Documentation for Inputs
I am planning on adding a Java Graphics UI someday, but for now, in the main method at the bottom of the solver.java file, you can manually fill in the board into a 2D array as input and run this file to get an output in the terminal.  
