import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

import com.sun.imageio.plugins.common.I18N;

import java.*;


public class Run {

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {


		init_file init = new init_file();
		Board myBoard = new Board(init); 
 		//Bfs bfs = new Bfs (myBoard);
		//Dfid dfid = new Dfid(myBoard);
  		//Astar astar = new Astar(myBoard);
  		IdAstar idastar = new IdAstar(myBoard);
	}

}
