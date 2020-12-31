import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class Square implements Cloneable {
	//The number assigned to the square (if it's 0, that means the number has not yet been finalized)
	private int finalNumber;
	
	//The possible numbers that this square could be
	private Set<Integer> options;
	
	//Initializes a Square with no final number and all possible options
	public Square() {
		finalNumber = 0;
		options = new HashSet<Integer>();
		for(int i = 1; i <= 9; i++){
			options.add(i);
		}
	}
	//Returns finalNumber
	public int getNum() {
		return finalNumber;
	}
	//Returns options set
	public Set<Integer> getOptions() {
		return options;
	}
	//Returns size of options set
	public int getSetSize() {
		return options.size();
	}
	//Sets finalNumber, returns true is possible, else returns false
	public boolean setNum(int number) {
		if(finalNumber == 0 && options.contains(number)){
			finalNumber = number;
			options.removeAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
			return true;
		} else{
			return false;
		}
	}
	//Takes away one option
	public void removeOne(int number) {
		options.remove(number);
	}
	//Removes all options in the input collection
	public void removeAll(Collection<Integer> a) {
		options.removeAll(a);
	}
	//Creates a deep clone of the square
	public Object clone() throws CloneNotSupportedException {
		Set<Integer> tempOptions = new HashSet<Integer>();
		tempOptions.addAll(options);
		Square temp = (Square) super.clone(); 
		temp.options = tempOptions;
		return temp;
	}
	
	//Testing method
//	public static void main(String[] args) throws CloneNotSupportedException{
//		Square n1 = new Square();
//		n1.removeOne(9);
//		Square n2 = (Square) n1.clone();
//		n1.removeOne(8);
//		n1.setNum(1);
//		n2.removeOne(7);
//		System.out.println(n1.options);
//		System.out.println(n2.options);
//		
//	}
}
