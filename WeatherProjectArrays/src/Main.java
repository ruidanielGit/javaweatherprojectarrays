import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    static DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {

        //int[][] data = insertTerrainDataInArray();
        int[][] dataTest = new int[][]{{18, 40, -22, 24, 39}, {19, 35, 38, 55, 45}, {30, 35, 38, 39, 31}};
        //exercise B
        System.out.println(displayDataAreaAndTemp(dataTest));
        //exercise C
        displayAllertMap(createAllertMap(dataTest));
        //exercise D
        int alteration = sc.nextInt();
        int[][] alteratedData = temperatureAlterationMatrix(dataTest, alteration);
        System.out.println(printTempAlterationMatrix(alteratedData));
        //exercise E
        char[] allertMap = createAllertMap(alteratedData);
        printAllertRepport(allertPercentReport(createAllertMap(alteratedData)));
        //exercise F
        System.out.println(toAllCatastrophicAllerts(alteratedData));
        //exercise G
        int[][] altDataPlusN = alteratedDataPlusN(alteratedData, 10);
        //exercise H
        catastrophicNorthToSouth(altDataPlusN);
    }


    /**
     * Insert terrain data into Array
     */
    protected static int[][] insertTerrainDataInArray() {

        String data = sc.nextLine();

        String matrixScale = sc.nextLine();
        String numOfRows = "";
        String numOfColumns = "";
        int count = 0;

        while (matrixScale.charAt(count) != ' ') {
            numOfRows += String.valueOf(matrixScale.charAt(count));
            count++;
        }
        //jumps the space between the numbers
        count++;

        while (count < matrixScale.length() && (matrixScale.charAt(count) != ' ' || matrixScale.charAt(count) != '\n')) {
            numOfColumns += String.valueOf(matrixScale.charAt(count));
            count++;
        }

        int[][] temp = new int[Integer.parseInt(numOfRows)][Integer.parseInt(numOfColumns)];

        for (int i = 0; i < Integer.parseInt(numOfRows); i++) {
            for (int j = 0; j < Integer.parseInt(numOfColumns); j++) {
                temp[i][j] = sc.nextInt();
            }
        }
        return temp;
    }

    /**
     * @param data matrix with temperatures for a certain area
     * @return a toString of data
     */
    protected static String displayDataAreaAndTemp(int[][] data) {

        String info = "";
        int count = 0;

        for (int[] row : data) {
            for (int temp : row) {
                //to remove space after last number of each row
                if (count < row.length - 1) {
                    info += temp + " ";
                } else {
                    info += temp;
                }
                count++;
            }
            count = 0;
            info += "\n";
        }

        return info;
    }

    /**
     * @param data matrix with temperatures for a certain area
     * @return the allert map
     */
    protected static char[] createAllertMap(int[][] data) {
        char[] allerts = new char[data.length * data[0].length + (data.length - 1)];
        int j, k = 0;

        for (int i = 0; i < data.length; i++) {
            for (j = 0; j < data[0].length; j++) {
                if (data[i][j] < 20) allerts[k] = 'M';
                else if (data[i][j] < 30) allerts[k] = 'H';
                else if (data[i][j] < 40) allerts[k] = 'E';
                else allerts[k] = 'C';
                k++;
            }
            if (j == data[0].length && i < data.length - 1) {
                allerts[k] = '\n';
                k++;
            }
        }
        return allerts;
    }

    protected static void displayAllertMap(char[] allerts) {
        for (char allert : allerts) {
            if (allert == 'B') {
                System.out.println();
            } else {
                System.out.print(allert);
            }
        }
        System.out.print("\n");
    }

    /**
     * Supports an alteration on the temperature
     *
     * @param initialData initial matrix with temperatures for a certain area
     * @return the alterated data with the new temperatures
     */
    protected static int[][] temperatureAlterationMatrix(int[][] initialData, int alteration) {
        int[][] alteratedData = new int[initialData.length][initialData[0].length];

        for (int i = 0; i < initialData.length; i++) {
            for (int j = 0; j < initialData[0].length; j++) {
                alteratedData[i][j] = initialData[i][j] + alteration;
            }
        }

        return alteratedData;
    }

    protected static String printTempAlterationMatrix(int[][] alteratedData) {

        String info = "";
        info += displayDataAreaAndTemp(alteratedData);
        info += "\n";

        for (char c : createAllertMap(alteratedData)) {
            info += c;
        }
        return info + "\n";
    }

    /**
     * Prints the allert report
     *
     * @param allertPercent allert map with percentages
     */
    protected static void printAllertRepport(float[] allertPercent) {
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

        System.out.println("MODERATE\t\t:\t" + df.format(allertPercent[0]) + "%");
        System.out.println("HIGH\t\t\t:\t" + df.format(allertPercent[1]) + "%");
        System.out.println("EXTREME\t\t\t:\t" + df.format(allertPercent[2]) + "%");
        System.out.println("CATASTROPHIC\t:\t" + "" + df.format(allertPercent[3]) + "%\n");
    }

    /**
     * allertCounter count of occurences of the Allert given by counterPerAllert function
     *
     * @param allerts String representation of the Allerts
     * @return an Array with the percentages of the Allert
     */
    protected static float[] allertPercentReport(char[] allerts) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00");
        int[] allertCounter = counterPerAllert(allerts);

        float[] allertPercent = new float[4];
        int numAllerts = allertCounter[0] + allertCounter[1] + allertCounter[2] + allertCounter[3];

        for (int i = 0; i < allertCounter.length; i++) {
            allertPercent[i] = (float) allertCounter[i] / numAllerts * 100;
        }

        return allertPercent;
    }

    /**
     * @param allerts String representation of the allertMap
     *                calculates a counter per Allert
     */
    protected static int[] counterPerAllert(char[] allerts) {
        int[] allertCounter = new int[]{0, 0, 0, 0};

        for (char allert : allerts) {
            if (allert == 'M') allertCounter[0]++;
            else if (allert == 'H') allertCounter[1]++;
            else if (allert == 'E') allertCounter[2]++;
            else if (allert == 'C') allertCounter[3]++;
        }
        return allertCounter;
    }

    private static String toAllCatastrophicAllerts(int[][] data) {

        int getMin = data[0][0];

        for (int[] row : data) {
            for (int temp : row) {
                if (temp < getMin) getMin = temp;
            }
        }

        return "To get all terrain on CATASTROPHIC alert, the temperature has\n" + "to rise : " + (40 - getMin) + "ºC\n";
    }

    protected static int[][] alteratedDataPlusN(int[][] data, int alteration) {

        int[][] tempPlus10 = temperatureAlterationMatrix(data, alteration);
        int numOfTemps = data.length * data[0].length;
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

        char[] newMap = createAllertMap(tempPlus10);

        float numVariations = countVariationsInTemp(createAllertMap(data), newMap);
        float toPercent = (numVariations / numOfTemps) * 100;

        displayAllertMap(newMap);

        System.out.println("Alert Levels changes due to temperature variations by " + alteration + "ºC : " + df.format(toPercent) + "%\n");

        return tempPlus10;
    }

    protected static float countVariationsInTemp(char[] oldMap, char[] newMap) {

        float count = 0;

        for (int i = 0; i < oldMap.length; i++) {
            if (oldMap[i] != newMap[i]) count++;
        }

        return count;
    }

    protected static void catastrophicNorthToSouth(int[][] data) {

        int[][] newData = new int[data.length][data[0].length];

        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] >= 40) {
                    newData[i][j] = data[i][j];
                    newData[i + 1][j] = 40;
                } else if (newData[i][j] < 40) {
                    newData[i][j] = data[i][j];
                }

            }
        }
        displayAllertMap(createAllertMap(newData));
    }
}


















