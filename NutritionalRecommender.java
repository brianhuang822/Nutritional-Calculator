import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

public class NutritionalRecommender
{
	static private String foodDirectory = "./Food";
	static private String foodInformationFile = foodDirectory + "/info/total_daily_nutritional";
	static private String foodWeightFile = foodDirectory + "/info/weights";

	static public HashMap<Food, Integer> getDailyNutrionalRecommendation(Person pPerson) {
		HashMap<String, Double> recommendedDailyIntake = retrieveRecommendedDailyIntake();
		HashMap<String, Double> currentIntake = getCurrentIntake();
		Hashtable<String, Double> nutrientsWeights = getNutrientWeights();
		Hashtable<String, Food> foodDatabase = getFoodDatabase();
		HashMap<String, Double> missingIntake = getMissingIntake(recommendedDailyIntake, currentIntake);

		HashMap<Food, Integer> recommendedFoods = new HashMap<Food, Integer>();
		boolean addNutrient = false;
		int count;
		
		while (missingIntake.size() > 0){
			for (String nutrient : missingIntake.keySet()){
				count = 0;
				if (missingIntake.get(nutrient) > 0){ // Missing this nutrient
					for (Food food : foodDatabase.values()){
						if (missingIntake.get(nutrient) - food.getNutrientIntake(nutrient) >= 0){
							if (recommendedFoods.containsKey(food)){
								recommendedFoods.put(food, recommendedFoods.get(food)+1);
							}
							else {
								recommendedFoods.put(food, 1);
							}
							missingIntake.put(nutrient, missingIntake.get(nutrient) - food.getNutrientIntake(nutrient));
						}
						else {
							count++;
						}
						
					}
					if (count == foodDatabase.size()){
						missingIntake.remove(nutrient);
					}
				}
			}
		}
	
		return recommendedFoods;
	}

	static public Hashtable<String, Food> getFoodDatabase() throws FileNotFoundException, IOException
	{
		File directory = new File(foodDirectory);
		File[] filesInDirectory = directory.listFiles();
//		LinkedList<Food> foodList = new LinkedList<Food>();
//		LinkedList<Integer> weightList = new LinkedList<Integer>();
//		
//		

		Hashtable<String, Food> foodDatabase = new Hashtable<String, Food>();
		for (File f: filesInDirectory) {
			if (f.isFile()) {
				Food food = new Food(f.getAbsolutePath());
				foodDatabase.put(f.getName(), food);
			}
		}

		return foodDatabase;
	}

	static private HashMap<String, Double> retrieveRecommendedDailyIntake() throws IOException
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
		
		foodInformationReader.close();

		return recommendedDailyIntake;
	}

	static private HashMap<String, Double> getCurrentIntake()
	{
		HashMap<String, Double> currentIntake = new HashMap<String, Double>();

		for (String foodAndAmount: pPerson) {
			String[] food = foodAndAmount.split(" ");
			assert food.length == 2;

			Food currentFood = new Food(food[0]);
			double currentFoodMultiplier = Double.parseDouble(food[1]);

			for (Food.Nutrient n: Food.Nutrient.values()) {
				double nutrientIntake = currentFood.getNutrientIntake(n.name()) * currentFoodMultiplier;
			
				if (currentIntake.containsKey(n.toString())) {
					nutrientIntake += currentIntake.get(n.toString());
					currentIntake.remove(n.toString());
				}
			
				currentIntake.put(n.toString(), nutrientIntake);
			}
		}

		return currentIntake;
	}

	static private Hashtable<String, Double> getNutrientWeights() throws IOException
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
		reader.close();
		return nutrientWeights;
	}

	static private HashMap<String, Double> getMissingIntake(HashMap<String, Double> recommendedDailyIntake, HashMap<String, Double> currentIntake)
	{
		HashMap<String, Double> missingIntake = new HashMap<String, Double>();

		for (Food.Nutrient n: Food.Nutrient.values()) {
			missingIntake.put(n.toString(), recommendedDailyIntake.get(n.toString()) - currentIntake.get(n.toString()));
		}

		return missingIntake;
	}
}

