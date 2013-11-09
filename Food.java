import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Food
{
	public Food(String pPath)
	{
		File folder = new File(pPath);
		File[] foods = folder.listFiles();
		
		for (File food: foods) {
			BufferedReader reader = null;
			
			if (food.isFile()) {
				try {
					reader = new BufferedReader(new FileReader(food));
				} catch (FileNotFoundException e) {
					System.out.println("Unable to open: " + food.getAbsolutePath());
					continue;
				}
				
				String line;
				try {
					line = reader.readLine();
				} catch (IOException e) {
					System.out.println("Unable to read: " + food.getAbsolutePath());
					continue;
				}
				
				while (line != null) {
					parseLine(line);
				}
			}
		}
	}
	
	public double getServingSize()
	{
		return nutritional_facts.serving_size;
	}
	
	public double getTotalCalories()
	{
		return nutritional_facts.total_calories;
	}
	
	public double getFatCalories()
	{
		return nutritional_facts.fat_calories;
	}
	
	public enum Format { ABSOLUTE, PERCENTAGE };
	
	public double getTotalFat(Format f)
	{
		return nutritional_facts.total_fat[(f == Format.ABSOLUTE) ? 0 : 1];
	}
	
	public double getSaturatedFat(Format f)
	{
		return nutritional_facts.saturated_fat[(f == Format.ABSOLUTE) ? 0 : 1];
	}

	public double getTransFat(Format f)
	{
		return nutritional_facts.trans_fat[(f == Format.ABSOLUTE) ? 0 : 1];
	}
	
	public double getCholesterol(Format f)
	{
		return nutritional_facts.cholesterol[(f == Format.ABSOLUTE) ? 0 : 1];
	}
	
	public double getSodium(Format f)
	{
		return nutritional_facts.sodium[(f == Format.ABSOLUTE) ? 0 : 1];
	}
	
	public double getTotalCarbohydrates(Format f)
	{
		return nutritional_facts.total_carbohydrates[(f == Format.ABSOLUTE) ? 0 : 1];
	}
	
	public double getDietaryFiber(Format f)
	{
		return nutritional_facts.dietary_fiber[(f == Format.ABSOLUTE) ? 0 : 1];
	}
	
	public double getSugar(Format f)
	{
		return nutritional_facts.sugar[(f == Format.ABSOLUTE) ? 0 : 1];
	}
	
	public double getProtein(Format f)
	{
		return nutritional_facts.protein[(f == Format.ABSOLUTE) ? 0 : 1];
	}
	
	public double getVitaminA()
	{
		return nutritional_facts.vitamin_A;
	}

	public double getVitaminC()
	{
		return nutritional_facts.vitamin_C;
	}
	
	public double getCalcium()
	{
		return nutritional_facts.calcium;
	}

	public double getIron()
	{
		return nutritional_facts.iron;
	}

	public double getVitaminE()
	{
		return nutritional_facts.vitamin_E;
	}

	public double getThiamin()
	{
		return nutritional_facts.thiamin;
	}

	public double getRiboflavin()
	{
		return nutritional_facts.riboflavin;
	}

	public double getNiacin()
	{
		return nutritional_facts.niacin;
	}

	public double getVitaminB6()
	{
		return nutritional_facts.vitamin_B6;
	}

	public double getVitaminB12()
	{
		return nutritional_facts.vitamin_B12;
	}

	public double getFolate()
	{
		return nutritional_facts.folate;
	}

	public double getPhosphorus()
	{
		return nutritional_facts.phosphorus;
	}
	
	public double getMagnesium()
	{
		return nutritional_facts.magnesium;
	}

	public double getZinc()
	{
		return nutritional_facts.zinc;
	}

	private class NutritionalFacts
	{
		public double serving_size;
		
		public double total_calories;
		public double fat_calories;
		
		public double[] total_fat;
		public double[] saturated_fat;
		public double[] trans_fat;
		public double[] cholesterol;
		public double[] sodium;
		public double[] total_carbohydrates;
		public double[] dietary_fiber;
		public double[] sugar;
		public double[] protein;
		
		public double vitamin_A;
		public double vitamin_C;
		public double calcium;
		public double iron;
		public double vitamin_E;
		public double thiamin;
		public double riboflavin;
		public double niacin;
		public double vitamin_B6;
		public double vitamin_B12;
		public double folate;
		public double phosphorus;
		public double magnesium;
		public double zinc;
	}
	
	private NutritionalFacts nutritional_facts;
	
	private void parseLine(String line)
	{
		String[] tokens = line.split(" ");
		
		if (tokens[0].compareTo("serving_size") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.serving_size = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("total_calories") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.total_calories = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("fat_calories") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.fat_calories = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("total_fat") == 0) {
			assert(tokens.length == 2 || tokens.length == 3);
			
			if (tokens.length == 2) {
				nutritional_facts.total_fat[1] = -1;
			} else {
				nutritional_facts.total_fat[1] = Integer.parseInt(tokens[2]);
			}
			
			nutritional_facts.total_fat[0] = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("saturated_fat") == 0) {
			assert(tokens.length == 2 || tokens.length == 3);
			
			if (tokens.length == 2) {
				nutritional_facts.saturated_fat[1] = -1;
			} else {
				nutritional_facts.saturated_fat[1] = Integer.parseInt(tokens[2]);
			}
			
			nutritional_facts.saturated_fat[0] = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("trans_fat") == 0) {
			assert(tokens.length == 2 || tokens.length == 3);
			
			if (tokens.length == 2) {
				nutritional_facts.trans_fat[1] = -1;
			} else {
				nutritional_facts.trans_fat[1] = Integer.parseInt(tokens[2]);
			}
		
			nutritional_facts.trans_fat[0] = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("cholesterol") == 0) {
			assert(tokens.length == 2 || tokens.length == 3);
			
			if (tokens.length == 2) {
				nutritional_facts.cholesterol[1] = -1;
			} else {
				nutritional_facts.cholesterol[1] = Integer.parseInt(tokens[2]);
			}
			
			nutritional_facts.cholesterol[0] = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("sodium") == 0) {
			assert(tokens.length == 2 || tokens.length == 3);
			
			if (tokens.length == 2) {
				nutritional_facts.sodium[1] = -1;
			} else {
				nutritional_facts.sodium[1] = Integer.parseInt(tokens[2]);
			}
			
			nutritional_facts.sodium[0] = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("total_carbohydrates") == 0) {
			assert(tokens.length == 2 || tokens.length == 3);
			
			if (tokens.length == 2) {
				nutritional_facts.total_carbohydrates[1] = -1;
			} else {
				nutritional_facts.total_carbohydrates[1] = Integer.parseInt(tokens[2]);
			}
			
			nutritional_facts.total_carbohydrates[0] = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("dietary_fiber") == 0) {
			assert(tokens.length == 2 || tokens.length == 3);
			
			if (tokens.length == 2) {
				nutritional_facts.dietary_fiber[1] = -1;
			} else {
				nutritional_facts.dietary_fiber[1] = Integer.parseInt(tokens[2]);
			}
			
			nutritional_facts.dietary_fiber[0] = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("sugar") == 0) {
			assert(tokens.length == 2 || tokens.length == 3);
			
			if (tokens.length == 2) {
				nutritional_facts.sugar[1] = -1;
			} else {
				nutritional_facts.sugar[1] = Integer.parseInt(tokens[2]);
			}
			
			nutritional_facts.sugar[0] = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("protein") == 0) {
			assert(tokens.length == 2 || tokens.length == 3);
			
			if (tokens.length == 2) {
				nutritional_facts.protein[1] = -1;
			} else {
				nutritional_facts.protein[1] = Integer.parseInt(tokens[2]);
			}
			
			nutritional_facts.protein[0] = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("vitamin_A") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.vitamin_A = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("vitamin_C") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.vitamin_C = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("calcium") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.calcium = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("iron") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.iron = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("vitamin_E") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.vitamin_E = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("thiamin") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.thiamin = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("riboflavin") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.riboflavin = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("niacin") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.niacin = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("vitamin_B6") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.vitamin_B6 = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("vitamin_B12") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.vitamin_B12 = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("folate") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.folate = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("phosphorus") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.phosphorus = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("magnesium") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.magnesium = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("zinc") == 0) {
			assert(tokens.length == 2);
			nutritional_facts.zinc = Integer.parseInt(tokens[1]);
		} else {
			System.out.println("Unable to retrieve value(s) of: " + tokens[0]);
		}
	}
}
