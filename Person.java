import java.util.*;

public class Person implements Iterable<String>  {
	private ArrayList arr;
	
	public static class MyIterator implements Iterator<String> {

        private final Person person;
        private int current;

        MyIterator(Person person,Hash) {
            this.person = person;
            this.current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < arr.size ();
        }

        @Override
        public String next() {
            if (! hasNext())   return null;
            return person.arr.get(current++);
			
        }
	}
	public Iterator<String> iterator(HashMap food, HashMap amount) {
		for (String foodname:foodDatabase.keySet()){
			arr = foodname + (foodDatabase.get(foodname)).toString();
		}
        return new MyIterator();
    }

}
