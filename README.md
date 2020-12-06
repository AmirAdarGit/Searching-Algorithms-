# Searching-Algorithms-
Implementation of "Tail Puzzle" game using 5 searching algorithms (Bfs, Dfid, A*, IdA*, DfbNb).

In order to run the code you must download all the files to your computer.
You are required to enter the input1.txt file where you can understand how to set up the gaming board in order to run one of the algorithms and see if there is a solution for the input tile puzzle board.

example:

DFBnB               //Here you tipe the name of the requier algorithm - Bfs, Dfid, A*, IdA*, DfbNb
no time             //If you want to see the runtime of the algorithm until it finds the solution write down "with time" otherwise leave "no time".
with open           //If it is registered in the input file is "no open" all the output will be written to the output.txt file and nothing will be printed in the console.
                      If "with open" is written, the algorithm will run on the console, in each row you will see the "open list" of the search graph, until the solution is found. 
3x4                 //In this row you enter the size of the board (NxM)
Black: 3,4,5        //Here you can set the black cells, the ones that will not be able to move during the game.
Red: 1,2            //Here you can set the red cells when moving this cell the "cost" of moving will be 30 points.
                    //For all the cells that not selected as red or black, will set to the green collor automatically, And for each move of green cell will coast 10 points. 
1,2,3,4             
5,6,11,7            //Example of input board
9,10,8,_












enjoy.
