import java.util.*;

public class Person implements Iterator<String> {
    ArrayList<String> arr = new ArrayList<String>();
    Iterator<String> itor = arr.iterator();

    public Person(HashMap<String, Integer> foodMultipliers)
    {
        for (String foodName: foodMultipliers.keySet()){
            arr.add(foodName + Integer.toString(foodMultipliers.get(foodName)));
        }
    }

    @Override
    public boolean hasNext()
    {
        return itor.hasNext();
    }

    @Override
    public String next()
    {
        return itor.next();
    }

    @Override
    public void remove() {
    	itor.remove();
    }
}
