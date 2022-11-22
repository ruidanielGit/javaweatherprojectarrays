import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;

public class Main {
    static String dataTest = "Porto; 2022/10/19; 12:00";

    public static void main(String[] args) {

        int[][] data = insertTerrainDataInArray();
        System.out.println(displayDataAreaAndTemp(data));
        System.out.println(displayAllertsMap(data));
        String MA = displayAllertsMap(data);
        String test = "MEMMH" + "\n" + "MHHCE\n" + "HHHHH";
        printAllertRepport(allertPercentReport(test));
    }

    /**
     * Insert terrain data into Array
     */
    protected static int[][] insertTerrainDataInArray() {

        Scanner sc = new Scanner(System.in);
        //String data = sc.nextLine();
        String data = dataTest;

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

        int temp[][] = new int[Integer.parseInt(numOfRows)][Integer.parseInt(numOfColumns)];

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
    protected static String displayAllertsMap(int[][] data) {
        String allerts = "";

        for (int[] row : data) {
            for (int temp : row) {
                if (temp < 20) allerts += 'M';
                else if (temp < 30) allerts += 'H';
                else if (temp < 40) allerts += 'E';
                else allerts += 'C';
            }
            allerts += "\n";
        }
        return allerts;
    }

    /**
     * Prints the allert report
     *
     * @param allertPercent allert map with percentages
     * @return the percentage of each allert
     */
    protected static void printAllertRepport(float[] allertPercent) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00", dfs);

        System.out.println("MODERATE\t\t:\t" + df.format(allertPercent[0]) + "%");
        System.out.println("HIGH\t\t\t:\t" + df.format(allertPercent[1]) + "%");
        System.out.println("EXTREME\t\t\t:\t" + df.format(allertPercent[2]) + "%");
        System.out.println("CATASTROPHIC\t:\t" + "" + df.format(allertPercent[3]) + "%");
    }

    /**
     * allertCounter count of occurences of the Allert given by counterPerAllert function
     *
     * @param allerts String representation of the Allerts
     * @return an Array with the percentages of the Allert
     */
    protected static float[] allertPercentReport(String allerts) {
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
    protected static int[] counterPerAllert(String allerts) {
        int[] allertCounter = new int[]{0, 0, 0, 0};

        for (char letter : allerts.toCharArray()) {
            if (letter != '\n') {
                switch (letter) {
                    case 'M':
                        allertCounter[0]++;
                        break;
                    case 'H':
                        allertCounter[1]++;
                        break;
                    case 'E':
                        allertCounter[2]++;
                        break;
                    case 'C':
                        allertCounter[3]++;
                }
            }
        }
        return allertCounter;
    }
}


















