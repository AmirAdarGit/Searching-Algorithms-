import java.util.Stack;

public class test {

	
	public static void main(String[] args) {
		Stack<String> myStack = new Stack<String>(); // (H) Represent a stack in order do search in "DFS" approach
		myStack.add("1");
		myStack.add("2");
		myStack.add("3");
		myStack.add("4");
		myStack.add("5");
		myStack.add("6");
		System.out.println(myStack.toString());
		myStack.remove("3");
		System.out.println(myStack.toString());

	}
}
