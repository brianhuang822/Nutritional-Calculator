import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

public class NutritionalRecommender
{
	static private String foodDirectory = "./Food";
	static private String foodInformationFile = foodDirectory + "/info/total_daily_nutritional";
	static private String foodWeightFile = foodDirectory + "/info/weights";

	static public HashMap<Food, Integer> getDailyNutrionalRecommendation(Person pPerson) {
		HashMap<String, Double> recommendedDailyIntake = getRecommendedDailyIntake();
		HashMap<String, Double> currentIntake = getCurrentIntake();
		HashMap<String, Double> nutrientsWeights = getNutrientWeights();
		HashMap<String, Food> foodDatabase = getFoodDatabase();
		HashMap<String, Double> missingIntake = getMissingIntake(recommendedDailyIntake, currentIntake);

		HashMap<Food, Integer> recommendedFoods = new HashMap<Food, Integer>();
		boolean addNutrient = false;

		while (missingIntake.size() > 0) {
			for (String nutrient : recommendedDailyIntake.keySet()){
				double missingNutrientIntake = missingIntake.get(nutrient);

				if (missingNutrientIntake > 0) {
					for (Food food: foodDatabase.values()) {
						double nutrientIntake = 0.0;

						for (Food.Nutrient n: Food.Nutrient.values()) {
							nutrientIntake = currentIntake(n.toString());

							if (missingIntake.get(n.toString()) <= 0) {
								// not missing this nutrient, but adding this food to the recommended list will OD this nutrient
								break;
							}
						}
						
						if (addNutrient) {
							if (recommendedFoods.containsKey(food)) {
								int oldValue = recommendedFoods.get(food);
								recommendedFoods.put(food, oldValue+1);
								double oldIntake = missingIntake.get(nutrient);
								missingIntake.put(nutrient, oldIntake - nutrientIntake);
							} else {
								recommendedFoods.put(food, 1);
							}
						}
					}
				} else {
					// not missing this nutrient, remove from missing intake list
					missingIntake.remove(nutrient);
				}
			}
		}
	}

	return recommendedFoods;
}

	static public HashMap<String, Food> getFoodDatabase()
	{
		File directory = new File(foodDirectory);
		File[] filesInDirectory = directory.listFiles();

		HashMap<String, Food> foodDatabase = new HashMap<String, Food>();
		for (File f: filesInDirectory) {
			if (f.isFile()) {
				Food food = new Food(f.getAbsolutePath());
				foodDatabase.add(f.getName(), food);
			}
		}

		return foodDatabase;
	}

	static private HashMap<String, Double> retrieveRecommendedDailyIntake()
	{
		HashMap<String, Integer> recommendedDailyIntake = new HashMap<String, Integer>();
	
		BufferedReader foodInformationReader = null;
		
		try {
			foodInformationReader = new BufferedReader(new FileReader(foodInformationFile));
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't open '" + foodInformationFile + "' aborting.");
			System.exit(-1);
		}

		String line;
		
		try {
			line = foodInformationReader.readLine();
		} catch (IOException e) {
			System.out.println("Error while reading '" + foodInformationFile + "' aborting.");
			System.exit(-1);
		}
		
		HashMap<String, Integer> recommendedDailyIntake = new HashMap<String, Integer>();
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
			Food currentFoodMultiplier = Double.parseDouble(food[1]);

			for (Food.Nutrient n: Food.Nutrient.values()) {
				double nutrientIntake = currentFood.getNutrientIntake(n) * currentFoodMultiplier;
			
				if (currentIntake.containsKey(n.toString())) {
					nutrientIntake += currentIntake.get(n.toString());
					currentIntake.remove(n.toString());
				}
			
				currentIntake.put(n.toString(), nutrientIntake);
			}
		}

		return currentIntake();
	}

	static private HashMap<String, Double> getNutrientWeights()
	{
		try {
			reader = new BufferedReader(new FileReader(foodWeightFile));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open '" + foodWeightFile + "' aborting.");
			System.exit(-1);
		}
			
		String line;
		
		try {
			line = reader.readLine();
		} catch (IOException e) {
			System.out.println("Error while reading '" + foodWeightFile + "' aborting.");
			System.exit(-1);
		}

		HashMap<String, Integer> nutrientWeights = new HashMap<String, Integer>();

		while (line != null) {
			String[] tokens = nextLine.split(" ");
			assert(tokens.length == 3);
			nutrientWeights.put(tokens[0], Double.parseDouble(tokens[2]));
			
			try {
				line = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error while reading '" + foodWeightFile + "' aborting.");
				System.exit(-1);
			}
		}

		return nutrientWeights;
	}

	static private HashMap<String, Double> getMissingIntake(HashMap<String, Double> recommendedDailyIntake, HashMap<String, Double> currentIntake)
	{
		HashMap<String, Double> missingIntake = new HashMap<String, Double>();

		for (Food.Nutrient n: Food.Nutrient.values()) {
			missingIntake.add(n.toString(), recommendedDailyIntake.get(n.toString()) - currentIntake.get(n.toString()));
		}

		return missingIntake;
	}
}

