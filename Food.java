import java.io.BufferedReader;
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

	public Food(String pPath) throws FileNotFoundException, IOException
	{
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(pPath));
		
		String line;
		line = reader.readLine();
		
		while (line != null) {
			parseLine(line);
		}
		
		reader.close();
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

	public double getNutrientIntake(Nutrient n)
	{
		return nutrientIntake.get(n);
	}

	private double servingSize;
	private double totalCalories;
	private double fatCalories;
	private HashMap<Nutrient, Integer> nutrientIntake;

	private void parseLine(String line)
	{
		String[] tokens = line.split(" ");
		assert tokens.length == 2;
		
		if (tokens[0].compareTo("SERVING_SIZE") == 0) {
			servingSize = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("TOTAL_CALORIES") == 0) {
			totalCalories = Integer.parseInt(tokens[1]);
		} else if (tokens[0].compareTo("FAT_CALORIES") == 0) {
			fatCalories = Integer.parseInt(tokens[1]);
		}
		
		boolean foundMatch = false;
		for (Nutrient n: Nutrient.values()) {
			if (tokens[0].compareTo(n.toString()) == 0) {
				nutrientIntake.put(n, Integer.parseInt(tokens[1]));
				foundMatch = true;
				break;
			}
		}

		if (!foundMatch) {
			System.out.println("Unknown nutrient: " + tokens[0]);
		}
	}
}
