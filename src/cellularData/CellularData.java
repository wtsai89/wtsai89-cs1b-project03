package cellularData;

public class CellularData {
    private double table[][];
    private int years[];
    private String countries[];
    private int numberOfCountries, numberOfSubs, startingYear, endingYear;
    private int lastFilledRow = 0;

    /**
     * Constructor for CellularData
     * @param rows represents number of countries
     * @param cols represents number of subscriptions
     * @param sYear represents the starting year
     */
    public CellularData(int rows, int cols, int sYear)
    {
        table = new double[rows][cols];
        countries = new String[rows];
        numberOfCountries = rows;
        numberOfSubs = cols;
        startingYear = sYear;
        endingYear = sYear + cols - 1;

        years = new int[cols];
        for(int i = 0; i < years.length; i++)
        {
            years[i] = sYear + i;
        }
    }

    /**
     * Adds a country to the table
     * @param country
     * @param data one-dimensional array of yearly subscriptions for the country
     */
    public void addCountry(String country, double[] data)
    {
        countries[lastFilledRow] = country;
        table[lastFilledRow] = data;
        lastFilledRow++;
    }

    /**
     * The starting year and ending year must fall within the appropriate range of the table other wise an error will be generated.
     * @param country
     * @param start starting year
     * @param end ending year
     * @return the number of subsciptions in the country for the period
     */
    public double getNumSubscriptionsInCountryForPeriod(String country, int start, int end)
    {
        double sum = 0;
        int countryIndex = getCountryIndex(country);

        if(countryIndex == -1)
        {
            System.out.println("Country not found. Returning -1");
            return -1;
        }
        else if(start > end || start > endingYear || end < startingYear)
        {
            System.out.println("Illegal arguments. The range of years is outside the valid period. Returning -1.");
            return -1;
        }
        else {
            int flag = 0;  //only display corrected range if there is a discrepancy
            if (start < startingYear) {
                System.out.printf("Illegal Argument Request of start year %d. ", start);
                start = startingYear;
                flag = 1;
            }
            if (end > endingYear) {
                System.out.printf("Illegal Argument Request of end year %d. ", end);
                end = endingYear;
                flag = 1;
            }
            if (flag == 1)
                System.out.printf("\nValid period for %s is %d to %d. \n", country, start, end);

            int startIndex = start - startingYear;
            int endIndex = end - startingYear;
            for (int i = startIndex; i <= endIndex; i++)
                sum += table[countryIndex][i];

            return sum;
        }
    }

    /**
     * Used to locate the row index for the country in the table
     * @param country
     * @return
     */
    public int getCountryIndex(String country)
    {
        for(int i = 0; i < countries.length; i++)
            if(countries[i].equals(country))
                return i;

        return -1;
    }

    /**
     * overrides the Object class
     * @return
     */
    public String toString()
    {
        String s = "Country       | Year";
        for(int i = 0; i < years.length; i++)
            s += String.format("%7d", years[i]);
        s += "\n";

        for(int i = 0; i < numberOfCountries; i++)
        {
            s += String.format("%-20s", countries[i]);
            for(int j = 0; j < numberOfSubs; j++)
                s += String.format("%7.2f", table[i][j]);
            s += "\n";
        }

        return s;
    }
}
