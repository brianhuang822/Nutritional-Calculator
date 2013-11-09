import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

public class NutritionalRecommender
{
    static private String foodDirectory = "./Food";
    static private String foodInformationFile = "./info/total_daily_nutritional";
    static private String foodWeightFile = "./info/weights";

    static public HashMap<String, Integer> getDailyNutritionalRecommendation(Person pPerson) {
        HashMap<String, Double> recommendedDailyIntake = retrieveRecommendedDailyIntake();
        HashMap<Food.Nutrient, Double> currentIntake = getCurrentIntake(pPerson);
        Hashtable<String, Double> nutrientsWeights = getNutrientWeights();
        Hashtable<String, Food> foodDatabase = getFoodDatabase();
        HashMap<Food.Nutrient, Double> missingIntake = getMissingIntake(recommendedDailyIntake, currentIntake);
        
        HashMap<String, Integer> recommendedFoods = new HashMap<String, Integer>();
        int count;
        int recommendCount = 0;
        System.out.println(missingIntake.size());
        while (recommendCount < 10){
        System.out.println("rec"+recommendCount);
            for (Food.Nutrient nutrient : missingIntake.keySet()){
            	count = 0;
                if (missingIntake.get(nutrient) > 0){
                    for (Food food : foodDatabase.values()){
                    	//System.out.println("food nutrient" + food.getNutrientIntake(nutrient));
                    	
//                    	for (Food.Nutrient a: Food.Nutrient.values()) {
//                    	System.out.println(a + ":" + food.getNutrientIntake(a));
//                    	}
                    	//System.out.println(missingIntake.get(nutrient));
                        if (missingIntake.get(nutrient) - food.getNutrientIntake(nutrient) >= 0){
                            if (recommendedFoods.containsKey(food)){
                            	//System.out.println(food.getName());
                                recommendedFoods.put(food.getName(), recommendedFoods.get(food)+1);
                                recommendCount++;
                            }
                            else {
                                recommendedFoods.put(food.getName(), 1);
                                recommendCount++;
                            }
                            missingIntake.put(nutrient, missingIntake.get(nutrient) - food.getNutrientIntake(nutrient));
                        }
                        else {
                            count++;
                        }
                        
                    }
                    System.out.println("COUNT: "+count);
                    if (count == foodDatabase.size()){
                        missingIntake.remove(nutrient);
                    }
                }
            }
        }
    
        return recommendedFoods;
    }

    static public Hashtable<String, Food> getFoodDatabase()
    {
        File directory = new File(foodDirectory);
        File[] filesInDirectory = directory.listFiles();

        Hashtable<String, Food> foodDatabase = new Hashtable<String, Food>();
        for (File f: filesInDirectory) {
            if (f.isFile()) {
                Food food = null;
                try {
                    food = new Food(f.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace ();
                }
                foodDatabase.put(f.getName(), food);
            }
        }

        return foodDatabase;
    }

    static private HashMap<String, Double> retrieveRecommendedDailyIntake()
    {
        HashMap<String, Double> recommendedDailyIntake = new HashMap<String, Double>();
    
        BufferedReader foodInformationReader = null;
        
        try {
            foodInformationReader = new BufferedReader(new FileReader(foodInformationFile));
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't open '" + foodInformationFile + "' aborting.");
            System.exit(-1);
        }

        String line = null;
        
        try {
            line = foodInformationReader.readLine();
        } catch (IOException e) {
            System.out.println("Error while reading '" + foodInformationFile + "' aborting.");
            System.exit(-1);
        }

        while (line != null) {
            String[] tokens = line.split(" ");
            assert(tokens.length == 2);
            recommendedDailyIntake.put(tokens[0], Double.parseDouble(tokens[1]));
            
            try {
                line = foodInformationReader.readLine();
            } catch (IOException e) {
                System.out.println("Error while reading '" + foodInformationFile + "' aborting.");
                System.exit(-1);
            }
        }
        
        try {
            foodInformationReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recommendedDailyIntake;
    }

    static private HashMap<Food.Nutrient, Double> getCurrentIntake(Person pPerson)
    {
        HashMap<Food.Nutrient, Double> currentIntake = new HashMap<Food.Nutrient, Double>();

        for (String line : pPerson.foodQuantity) {
        	String[] food = line.split(" ");
            assert food.length == 2;

            Food currentFood = null;
            try {
                currentFood = new Food(foodDirectory + "/" + food[0]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            double currentFoodMultiplier = Double.parseDouble(food[1]);

            for (Food.Nutrient n: Food.Nutrient.values()) {
        		double nutrientIntake = currentFood.getNutrientIntake(n) * currentFoodMultiplier;
            
                if (currentIntake.containsKey(n.toString())) {
                    nutrientIntake += currentIntake.get(n.toString());
                    currentIntake.remove(n.toString());
                }
            
                currentIntake.put(n, nutrientIntake);
            }
        }

        return currentIntake;
    }

    static private Hashtable<String, Double> getNutrientWeights()
    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(foodWeightFile));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open '" + foodWeightFile + "' aborting.");
            System.exit(-1);
        }
            
        String line = null;
        
        try {
            line = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error while reading '" + foodWeightFile + "' aborting.");
            System.exit(-1);
        }

        Hashtable<String, Double> nutrientWeights = new Hashtable<String, Double>();

        while (line != null) {
            String[] tokens = line.split(" ");
            assert(tokens.length == 2);
            nutrientWeights.put(tokens[0], Double.parseDouble(tokens[1]));
            
            try {
                line = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error while reading '" + foodWeightFile + "' aborting.");
                System.exit(-1);
            }
        }
        
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return nutrientWeights;
    }

    static private HashMap<Food.Nutrient, Double> getMissingIntake(HashMap<String, Double> recommendedDailyIntake, HashMap<Food.Nutrient, Double> currentIntake)
    {
        HashMap<Food.Nutrient, Double> missingIntake = new HashMap<Food.Nutrient, Double>();
        for (Food.Nutrient n: Food.Nutrient.values()) {
        	if (!recommendedDailyIntake.containsKey(n.toString())) {
        		assert !currentIntake.containsKey(n);
        		continue;
        	}
            missingIntake.put(n, recommendedDailyIntake.get(n.toString()) - currentIntake.get(n));
        }

        return missingIntake;
    }
}

