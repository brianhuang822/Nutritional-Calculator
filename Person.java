import java.util.*;

public class Person implements Iterator<String>  {
    ArrayList <String>arr = new ArrayList ();
    int current = 0;

    public Person(HashMap<String,Food> food, HashMap<String,Integer> amount) {
        for (String foodname:food.keySet()){
            arr.add (foodname + Integer.toString(amount.get(foodname)));
        }
    }

    @Override
    public boolean hasNext() {
        return current < arr.size ();
    }

    @Override
    public String next() {
        if (! hasNext())   return null;
        return arr.get(current++);

    }

    @Override
    public void remove() {

    }
}


