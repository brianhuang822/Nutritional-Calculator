import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Food
{
	public enum Nutrient {
		TOTAL_FAT,
		SATURATED_FAT,
		TRANS_FAT,
		CHOLESTEROL,
		SODIUM,
		TOTAL_CARBOHYDRATES,
		DIETARY_FIBER,
		SUGAR,
		PROTEIN,
		VITAMIN_A,
		VITAMIN_C,
		VITAMIN_D,
		CALCIUM,
		IRON,
		VITAMIN_E,
		THIAMIN,
		RIBOFLAVIN,
		NIACIN,
		VITAMIN_B6,
		VITAMIN_B12,
		FOLATE,
		PHOSPHORUS,
		MAGNESIUM,
		ZINC
	};

	private String name;
	private double servingSize;
	private double totalCalories;
	private double fatCalories;
	private HashMap<Nutrient, Double> nutrientIntake;
	
	public Food(String pPath) throws FileNotFoundException, IOException
	{
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(pPath));
		String line = (new File(pPath)).getName();
		
		line = reader.readLine();
		
		while (line != null) {
			parseLine(line);
			line = reader.readLine();
		}
		
		reader.close();
	}

	public String getName()
	{
		return name;
	}
	
	public double getServingSize()
	{
		return servingSize;
	}

	public double getCalories()
	{
		return totalCalories;
	}

	public double getFatCalories()
	{
		return fatCalories;
	}

	public double getNutrientIntake(String nutrient)
	{
		if (!nutrientIntake.containsKey(nutrient)) {
			return 0.0;
		}
		
		return nutrientIntake.get(nutrient);
	}

	private void parseLine(String line)
	{
		if (line.compareTo("") == 0 || line.charAt(0) == '#' || line.matches("(\t|\\s)*\n")) {
			return;
		}

		String[] tokens = line.split(" ");
		assert tokens.length == 2;
		
		nutrientIntake = new HashMap<Nutrient, Double>();
		
		boolean foundMatch = false;
		if (tokens[0].compareTo("SERVING_SIZE") == 0) {
			servingSize = Integer.parseInt(tokens[1]);
			foundMatch = true;
		} else if (tokens[0].compareTo("TOTAL_CALORIES") == 0) {
			totalCalories = Integer.parseInt(tokens[1]);
			foundMatch = true;
		} else if (tokens[0].compareTo("FAT_CALORIES") == 0) {
			fatCalories = Integer.parseInt(tokens[1]);
			foundMatch = true;
		} else {
			for (Nutrient n: Nutrient.values()) {
				if (tokens[0].compareTo(n.toString()) == 0) {
					nutrientIntake.put(n, Double.parseDouble(tokens[1]));
					foundMatch = true;
					break;
				}
			}
		}

		if (!foundMatch) {
			System.out.println("Unknown nutrient: " + tokens[0]);
		}
	}
}
