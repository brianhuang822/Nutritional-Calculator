import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

public class NutritionalRecommender
{
	public enum FoodItem {
		WHITE_BREAD,
		TUNA,
		SALMON,
		CHOCOLATE,
		CHICKEN,
		CHEESE,
		CARROT,
		BEANS,
		BANANA,
		BAGEL,
		APPLE
	};
	
	public HashMap<Food, Integer> NutrionalRecommender(Person pPerson){
		LinkedList<Food> foodDatabase = new LinkedList<Food>();
		HashMap<String, Double> missingIntake = new HashMap<String, Double>();
		HashMap<String, Integer> recommendedDailyIntake = new HashMap<String, Integer>();
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader("total_daily_nutritional.txt"));
		
		String line;
		line = reader.readLine();
		
		while (line != null) {
			String[] tokens = line.split("[ ]");
			assert(tokens.length == 2);
			recommendedDailyIntake.put(tokens[0], Integer.parseInt(tokens[1]));
		}
		
		reader.close();
		for (String foodAndAmount: pPerson) {
			for (Food.Nutrient n: Food.Nutrient.values()) {
				String[] food = foodAndAmount.split(" ");
				assert(food.length == 2);
			
				double nutrientIntake = (int) ((new Food(food[0])).getNutrientIntake(n) * Integer.parseInt(food[1]));
			
				if (missingIntake.containsKey(n.toString())) {
					nutrientIntake += missingIntake.get(n.toString());
					missingIntake.remove(n.toString());
				}
			
			missingIntake.put(n.toString(), recommendedDailyIntake.get(n.toString())-nutrientIntake);
			}
		}
		HashMap<String, Integer> weights = new HashMap<String, Integer>();
		reader = new BufferedReader(new FileReader("weights.txt"));
			
		String nextLine;
		nextLine = reader.readLine();
			
		while (nextLine != null) {
			String[] tokens = nextLine.split("[ ]");
			assert(tokens.length == 3);
			weights.put(tokens[0], Integer.parseInt(tokens[2]));
		}
		
		// Recommender Algorithm
		// We need to load all the foods in the database
		for (FoodItem foodName : FoodItem.values()){
			Food newFood = new Food(foodName.toString());
			foodDatabase.add(newFood);
		}
		
		HashMap<Food, Integer> recommendedFoods = new HashMap<Food, Integer>();
		boolean addNutrient = false;
		while(missingIntake.size() > 0){
		for (String nutrient : recommendedDailyIntake.keySet()){
			double missingNutrientIntake = missingIntake.get(nutrient);
			if (missingNutrientIntake > 0){
				for (Food food : foodDatabase){
					for (Food.Nutrient n: Food.Nutrient.values()){
						double nutrientIntake = food.getNutrientIntake(n);
						if (missingIntake.get(n) <= 0){
							// not missing this nutrient, but adding this food to the recommended list will OD this nutrient
							break;
						}
					}
					if(addNutrient){
						if (recommendedFoods.containsKey(food)){
							int oldValue = recommendedFoods.get(food);
							recommendedFoods.put(food, oldValue+1);
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
	return recommendedFoods;
}
}
