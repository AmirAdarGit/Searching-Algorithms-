import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class Dfid {
	Board root;//Represent the board that we get from the user
	Board lastSearch;
	String goalPath = "";
	int nodes = 1;
	public Dfid(Board myBoard) throws InterruptedException{
		this.root = myBoard;	

		if(root.checkIfWin()) {
			System.out.println("the root is the goal!!! ");
			return;
			//check if the root is the goal, 
		}
		else
			runDfid(myBoard);
	}

	public void runDfid(Board myBoard) throws InterruptedException{
		long startTime = System.nanoTime();
		for(int i=1; i<Integer.MAX_VALUE; i++) {
			System.out.println(i);	
			Set<String> myCurrentPath = new HashSet<String>();  
			Board result = lmitedDfs(myBoard, i, myCurrentPath); //action function			
			if (result.isCutOFF != true){//check if the serch "cut off" on his way to the goal, True by defult
				if(result.findTheGoal) {
					System.out.println("win!!!! ");
					System.out.println("The number od nodes are: "+ nodes);
					System.out.println("G_cost_to_choose: " + result.G_cost_to_choose);
					System.out.println(goalPath.substring(0,goalPath.length()-1));
					long endTime = System.nanoTime();
					System.out.println((endTime-startTime)*Math.pow(10, -9) + " sec");
					break;
				}
				else {
					System.out.println("no path");
					break;
				}
			}
		}
	}

	public Board lmitedDfs(Board myBoard,int  i, Set<String> myCurrentPath) throws InterruptedException {
		if(myBoard.checkIfWin()) {
			myBoard.setFindTheGoal(true);
			this.goalPath = myBoard.path;//when we find the goal i save the path.
			return myBoard;
		}

		if(i == 0) {//Untill we find the solution Or 
 			myBoard.isCutOFF = true;
			return myBoard;
		}
		else {
 			myCurrentPath.add(myBoard.searchForBoardId());//Add the id board to the hash table
			myBoard.isCutOFF = false;
			ArrayList<Board> childrens =  createChildrens(myBoard,myCurrentPath);//create all the allowd operators fron the node	
			if(childrens.isEmpty()) {
				return myBoard;
			}
			//Thread s = new Thread();
			//s.sleep(1000);
			i--;
			for (Board b : childrens) {
				nodes++;
				this.lastSearch = lmitedDfs(b, i, myCurrentPath);//recursion call
				
 				if(lastSearch.isCutOFF) {
 					lastSearch.isCutOFF=true;
				}
				else if(lastSearch.findTheGoal){//if find win 
					lastSearch.setFindTheGoal(true);
					return lastSearch;//didnt send CUTOFF
				}			
			}
			myCurrentPath.remove(myBoard.getId());
			
			if(lastSearch.isCutOFF)
				return lastSearch;
			else
			return myBoard;
		}
	}
	public ArrayList<Board> createChildrens(Board node, Set<String> myCurrentPath) {
		//node.printBoard();
		ArrayList<Board> childrens = new ArrayList<Board>( );
		if(node.y_whiteT < node.mystate[0].length-1 && node.mystate[node.x_whiteT][node.y_whiteT+1].color != 'B') {
			Board copyOfNodeL = new Board(node);
			Board L = swapTile(copyOfNodeL,'L');
			if(!myCurrentPath.contains(L.boardId)){	
				childrens.add(L);
				if(node.mystate[node.x_whiteT][node.y_whiteT+1].color == 'G')
					L.G_cost_to_choose +=1;//green
				else
					L.G_cost_to_choose += 30;//red
				L.path += node.mystate[node.x_whiteT][node.y_whiteT+1].cellNumber + "L-" ;
			}
		} 

		if(node.x_whiteT < node.mystate.length-1 && node.mystate[node.x_whiteT+1][node.y_whiteT].color != 'B'){
			//				&& !openList.contains(node.getBoardId()))  {//the block cannot be black
			Board copyOfNodeU = new Board(node);
			Board U = swapTile(copyOfNodeU,'U');
			if(!myCurrentPath.contains(U.boardId)){	
				childrens.add(U);
				if(node.mystate[node.x_whiteT+1][node.y_whiteT].color == 'G')
					U.G_cost_to_choose +=1;//green
				else
					U.G_cost_to_choose += 30;//red
				U.path += node.mystate[node.x_whiteT+1][node.y_whiteT].cellNumber + "U-" ;
			}
		}
		if(node.y_whiteT > 0 && node.mystate[node.x_whiteT][node.y_whiteT-1].color != 'B') {
			//				&& !openList.contains(node.getBoardId()))  {//the block cannot be black
			Board copyOfNodeR = new Board(node);
			Board R = swapTile(copyOfNodeR,'R');	 
			if(!myCurrentPath.contains(R.boardId)){	
				childrens.add(R);
				if(node.mystate[node.x_whiteT][node.y_whiteT-1].color == 'G')
					R.G_cost_to_choose +=1;//green
				else
					R.G_cost_to_choose += 30;//red
				R.path += node.mystate[node.x_whiteT][node.y_whiteT-1].cellNumber + "R-" ;
			}
		}
		if(node.x_whiteT > 0 && node.mystate[node.x_whiteT-1][node.y_whiteT].color != 'B') {
			//				&& !openList.contains(node.getBoardId()))  {//the block cannot be black
			Board copyOfNodeD = new Board(node);
			Board D = swapTile(copyOfNodeD,'D');
			if(!myCurrentPath.contains(D.boardId)){	
				childrens.add(D);
				if(node.mystate[node.x_whiteT-1][node.y_whiteT].color == 'G')
					D.G_cost_to_choose +=1;//green
				else
					D.G_cost_to_choose += 30;//red
				D.path += node.mystate[node.x_whiteT-1][node.y_whiteT].cellNumber + "D-";
			}
		}
		return childrens;
	}

	public Board swapTile(Board node, char direction) {
		if(direction == 'L') { 
			Cell tempWiteTile = new Cell (); //temporary cell for swapping in order to find the solotion
			tempWiteTile.cellNumber = node.mystate[node.x_whiteT][node.y_whiteT].cellNumber;
			node.mystate[node.x_whiteT][node.y_whiteT] = node.mystate[node.x_whiteT][node.y_whiteT+1];
			node.mystate[node.x_whiteT][node.y_whiteT+1] = tempWiteTile;
			node.findWhiteXY(node.mystate);
			node.searchForBoardId();
			return node;
		}

		else if(direction == 'U') { 
			Cell tempWiteTile = new Cell ();
			tempWiteTile = node.mystate[node.x_whiteT][node.y_whiteT]; //temporary cell for swapping in order to find the solotion
			node.mystate[node.x_whiteT][node.y_whiteT] = node.mystate[node.x_whiteT+1][node.y_whiteT];
			node.mystate[node.x_whiteT+1][node.y_whiteT] = tempWiteTile;
			node.findWhiteXY(node.mystate);
			node.searchForBoardId();
			return node;
		}
		else if(direction == 'R') { 
			Cell tempWiteTile = new Cell ();
			tempWiteTile = node.mystate[node.x_whiteT][node.y_whiteT]; //temporary cell for swapping in order to find the solotion
			node.mystate[node.x_whiteT][node.y_whiteT] = node.mystate[node.x_whiteT][node.y_whiteT-1];
			node.mystate[node.x_whiteT][node.y_whiteT-1] = tempWiteTile;
			node.findWhiteXY(node.mystate);
			node.searchForBoardId();	
			return node;
		}
		else if(direction == 'D') { 
			Cell tempWiteTile = new Cell ();
			tempWiteTile = node.mystate[node.x_whiteT][node.y_whiteT]; //temporary cell for swapping in order to find the solotion
			node.mystate[node.x_whiteT][node.y_whiteT] = node.mystate[node.x_whiteT-1][node.y_whiteT];
			node.mystate[node.x_whiteT-1][node.y_whiteT] = tempWiteTile;
			node.findWhiteXY(node.mystate);
			node.searchForBoardId();
			return node;
		}

		else
			return node;
	}
















}

