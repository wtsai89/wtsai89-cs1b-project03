package cellularData;

import java.util.Scanner;
import java.io.*;

/**
 * Reads a csv file containing cellular data and stores it in a table
 */
public class CSVReader {
    private String[] countryNames;
    private int[] yearLabels;
    private double[][] cellularDataTable;

    /**
     *
     * @param filename
     */
    public CSVReader(String filename)
    {
        File file = new File(filename);
        try {
            Scanner input = new Scanner(file);

            input.nextLine();
            int numberOfCountries = Integer.parseInt(((input.nextLine()).split(","))[1]);
            countryNames = new String[numberOfCountries];

            String[] yearString = ((input.nextLine()).split("Country Name,"))[1].split(",");
            yearLabels = new int[yearString.length];
            for (int i = 0; i < yearString.length; i++)
                yearLabels[i] = Integer.parseInt(yearString[i]);

            cellularDataTable = new double[countryNames.length][yearLabels.length];

            int count = 0;
            while(input.hasNextLine())
            {
                String[] countryString = (input.nextLine()).split(",");   //each line is split into the country name and the list of subscriptions

                int nameIndex = 1;                    //If there are commas in the country name, have to keep track of where country name ends and numbers begin
                boolean flag = true;
                while(flag)
                {
                    try{
                        Double.parseDouble(countryString[nameIndex]);
                        flag = false;
                    }
                    catch(NumberFormatException ex){
                        nameIndex++;
                    }
                }
                String name = "";
                for(int i = 0; i < nameIndex; i++) {
                    if(i != 0)
                        name += ",";
                    name += countryString[i];
                }
                countryNames[count] = name;

                for(int i = nameIndex; i < countryString.length; i ++)
                    cellularDataTable[count][i-nameIndex] = Double.parseDouble(countryString[i]);

                count++;
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found");
        }

    }

    public String[] getCountryNames()
    {
        return countryNames;
    }

    public int[] getYearLabels()
    {
        return yearLabels;
    }

    public double[][] getParsedTable()
    {
        return cellularDataTable;
    }

    public int getNumberOfYears()
    {
        return yearLabels.length;
    }

}
