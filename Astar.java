import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import sun.awt.image.PixelConverter.Bgrx;


public class Astar {

	Set<String> closedList = new HashSet<String>(); //Represent an hash table thet hold the node that we extend alredy
	Set<Board> openList = new HashSet<Board>(); //Represent an hash table thet hold the node that we find but nod extend alredy
	PriorityQueue<Board> pQueue = new PriorityQueue<Board>(); 

	Board root;

	public Astar(Board myBoard) throws InterruptedException{
		this.root = myBoard;	
		if(root.checkIfWin()) {
			System.out.println("the root is the goal!!! ");
			return;
			//check if the root is the goal, 
		}
		else
			Astar(myBoard);
	}

	public void Astar(Board myBoard) throws InterruptedException{
		long startTime = System.nanoTime();
		pQueue.add(myBoard);
		int i=0;
		while(!pQueue.isEmpty()) {
			Board currentBoard = pQueue.poll();
			System.out.println(closedList.size());
			if(currentBoard.checkIfWin()) {
				System.out.println(currentBoard.path.substring(0,currentBoard.path.length()-1));
				System.out.println("The total nobes are: " + (openList.size() + closedList.size()));
				System.out.println("Cost: " + currentBoard.G_cost_to_choose);
				long endTime = System.nanoTime();
				System.out.println((endTime-startTime)*Math.pow(10, -9) + " sec");		
				return;
			}
			else {
				openList.remove(currentBoard);
				closedList.add(currentBoard.boardId);
				ArrayList<Board> childrens =  createChildrens(currentBoard);//create all the allowd operators fron the node	
				for (Board b : childrens) {
//					System.out.println("G: "+ b.G_cost_to_choose);
//					System.out.println("H: "+ b.H_cost_to_choose);

					if(!closedList.contains(b.getId())&&(!openList.contains(b.getId()))) {
						pQueue.add(b);
					}
					else if(openList.contains(b.getId())) {
						Board temp = findBoard(b);
						int b_prince = b.F_cost_to_choose;
						int temp_price = temp.F_cost_to_choose;
						if(temp_price > b_prince) {
							openList.remove(temp);
							openList.add(b);
						}
					}
				}

			}



		}



	}


	public Board findBoard(Board b) {
		for (Iterator<Board> it = openList.iterator(); it.hasNext(); ) {
			Board f = it.next();
			if (f.getId().equals(b.getId()))
				return f;
		}
		return null;
	}








	public ArrayList<Board> createChildrens(Board node) {
		//node.printBoard();
		ArrayList<Board> childrens = new ArrayList<Board>( );
		if(node.y_whiteT < node.mystate[0].length-1 && node.mystate[node.x_whiteT][node.y_whiteT+1].color != 'B') {
			//				&& !openList.contains(node.getBoardId()))  {//the block cannot be black			
			Board copyOfNodeL = new Board(node);
			Board L = swapTile(copyOfNodeL,'L');
			if(!closedList.contains(L.boardId)&&!openList.contains(L.boardId)){	
				childrens.add(L);
				if(node.mystate[node.x_whiteT][node.y_whiteT+1].color == 'G')
					L.G_cost_to_choose +=1;//green
				else
					L.G_cost_to_choose += 30;//red

				L.H_cost_to_choose = L.getHuristicCost(L);
				L.F_cost_to_choose = L.H_cost_to_choose + L.G_cost_to_choose;
				L.path += node.mystate[node.x_whiteT][node.y_whiteT+1].cellNumber + "L-" ;
				openList.add(L);
			}
		} 


		if(node.x_whiteT < node.mystate.length-1 && node.mystate[node.x_whiteT+1][node.y_whiteT].color != 'B'){
			//				&& !openList.contains(node.getBoardId()))  {//the block cannot be black
			Board copyOfNodeU = new Board(node);
			Board U = swapTile(copyOfNodeU,'U');
			if(!closedList.contains(U.boardId)&&!openList.contains(U.boardId)){	
				childrens.add(U);
				if(node.mystate[node.x_whiteT+1][node.y_whiteT].color == 'G')
					U.G_cost_to_choose +=1;//green
				else
					U.G_cost_to_choose += 30;//red
				U.H_cost_to_choose = U.getHuristicCost(U);
				U.F_cost_to_choose = U.H_cost_to_choose + U.G_cost_to_choose;
				U.path += node.mystate[node.x_whiteT+1][node.y_whiteT].cellNumber + "U-" ;
				openList.add(U);
			}
		}
		if(node.y_whiteT > 0 && node.mystate[node.x_whiteT][node.y_whiteT-1].color != 'B') {
			//				&& !openList.contains(node.getBoardId()))  {//the block cannot be black
			Board copyOfNodeR = new Board(node);
			Board R = swapTile(copyOfNodeR,'R');	 
			if(!closedList.contains(R.boardId)&&!openList.contains(R.boardId)){	
				childrens.add(R);
				if(node.mystate[node.x_whiteT][node.y_whiteT-1].color == 'G')
					R.G_cost_to_choose +=1;//green
				else
					R.G_cost_to_choose += 30;//red
				R.H_cost_to_choose = R.getHuristicCost(R);
				R.F_cost_to_choose = R.H_cost_to_choose + R.G_cost_to_choose;
				R.path += node.mystate[node.x_whiteT][node.y_whiteT-1].cellNumber + "R-" ;
				openList.add(R);
			}
		}
		if(node.x_whiteT > 0 && node.mystate[node.x_whiteT-1][node.y_whiteT].color != 'B') {
			//				&& !openList.contains(node.getBoardId()))  {//the block cannot be black
			Board copyOfNodeD = new Board(node);
			Board D = swapTile(copyOfNodeD,'D');
			if(!closedList.contains(D.boardId)&&!openList.contains(D.boardId)){	
				childrens.add(D);
				if(node.mystate[node.x_whiteT-1][node.y_whiteT].color == 'G')
					D.G_cost_to_choose +=1;//green
				else
					D.G_cost_to_choose += 30;//red
				D.H_cost_to_choose = D.getHuristicCost(D);
				D.F_cost_to_choose = D.H_cost_to_choose + D.G_cost_to_choose;
				D.path += node.mystate[node.x_whiteT-1][node.y_whiteT].cellNumber + "D-";
				openList.add(D);
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
