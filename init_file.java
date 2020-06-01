import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList; // import the ArrayList class

 
public class init_file {
	String algorithmName;
	String runTime;
	String openList;
	String [] black;
	String [] red;
	String [] green;
	String boardSize;
	int n;
	int m;
	String [][] startState;
	String [] checkValidInput;

	public init_file() throws FileNotFoundException {
		Scanner inFile = new Scanner(new FileReader("C:\\Users\\amir adar\\eclipse-workspace\\Searching problems\\src\\input1.txt"));
		this.algorithmName = inFile.nextLine();
		this.runTime = inFile.nextLine();
		this.openList = inFile.nextLine();
 		
		boardSize = inFile.nextLine();
		int index = boardSize.indexOf('x');
		this.n = Integer.parseInt(boardSize.substring(0, index));  
		this.m = Integer.parseInt(boardSize.substring(index+1,boardSize.length()));

		if(n == 0 || m == 0) { //input edge cases 
			System.err.println("n or m cannot be equall to 0!");
			return;
		}	
		String blackCells = inFile.nextLine();
		int blackIndex = blackCells.indexOf(":");
		blackCells = blackCells.substring(blackIndex+1);
		this.black = blackCells.split(",");
		
		String redCells = inFile.nextLine();
		int redIndex = redCells.indexOf(":");
		redCells = redCells.substring(redIndex+2);
 		this.red = redCells.split(",");
		
 
	    this.green = findGreenCells(black,red,n,m);

		this.checkValidInput = new String[n*m]; //[1,2,3,4,5....n-1,_]
		this.startState  = new String[n][m];
 
		int i = 0;
		while(inFile.hasNext()) {
			String[] values = inFile.nextLine().split(",");	
			for(int j=0 ; j<values.length ; j++) {
				if(values[j].matches("[0-9]+")||(values[j].matches("_"))){
					if(values[j].matches("[0-9]+")) {
						int temp = Integer.parseInt(values[j]);
						if(temp > checkValidInput.length-1) {
							System.err.println("Invalid input, values of "+ values[j]);
							return;
						}
						if(checkValidInput[temp-1] == null) {
							checkValidInput[temp-1]=values[j];
						}
						else {
							System.err.println("Invalid input, there duplicates values of "+ values[j]);
							return;
						}
					}
					else {// the char is "_" input to the last cell at checkValidInput array
						if(checkValidInput[checkValidInput.length-1] == null) {
							checkValidInput[checkValidInput.length-1]=values[j];
						}
						else {
 							System.err.println("Invalid input, there duplicates values of " + values[j]);
							return;
						}
					}
				}
				else {
					System.out.println(values[j]);
					System.err.println("Invalid input, the input can be only numbers and _");
					return;
				}
			}

			if(values.length != m) { //input edge cases 
				System.err.println("Invalid input, n/m does'nt equal to the board size");
				return;
			}
			startState[i] = values;
			i++;
		}
	}
	
	public String[] findGreenCells(String[] black, String[] red,int n,int m) {
		//String [] green = new String[n*m -1];
		ArrayList<String> array = new ArrayList<String>(); // Create an ArrayList object
 		for(int i=1; i<(n*m) ; i++) {
 			array.add(String.valueOf(i));
		}
		for(int i=0 ; i<black.length ; i++) {
			if(array.contains(black[i])) {
				array.remove(black[i]);
			}
		}
		for(int i=0 ; i<red.length ; i++) {
			if(array.contains(red[i])) {
				array.remove(red[i]);
			}
		}
		String[] green = array.toArray(new String[0]);
		return green;
 	}
	
	
	
	public void printInitBoard() {
		for(int i = 0 ; i < startState.length ; i++) {
			for(int j = 0 ; j < startState[0].length; j++) {
				System.out.print (startState[i][j]+ " " );
			}
			System.out.println();
		}
	}




	public static void main(String[] args) throws FileNotFoundException {
		init_file init = new init_file();
 	}
}
