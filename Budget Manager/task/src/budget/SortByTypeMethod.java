package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SortByTypeMethod implements Sorter {

    public void sort (List<String[]> list) {
        double foodTotal = 0;
        double clothesTotal = 0;
        double entertainmentTotal = 0;
        double otherTotal = 0;
        double sumTotal = 0;
        for (String[] item : list) {
            switch (item[0]) {
                case "Food": foodTotal += Double.parseDouble(item[2]); break;
                case "Clothes": clothesTotal += Double.parseDouble(item[2]); break;
                case "Entertainment": entertainmentTotal += Double.parseDouble(item[2]); break;
                case "Other": otherTotal += Double.parseDouble(item[2]); break;
                default: System.out.println("Invalid sort type: " + item[0]);
            }
        }
        sumTotal = foodTotal + clothesTotal + entertainmentTotal + otherTotal;
        List<String[]> sortedList = new ArrayList<String[]>();
        sortedList.add(new String[]{"Food", "" + foodTotal});
        sortedList.add(new String[]{"Clothes", "" + clothesTotal});
        sortedList.add(new String[]{"Entertainment", "" + entertainmentTotal});
        sortedList.add(new String[]{"Other", "" + otherTotal});
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Double.parseDouble(sortedList.get(j)[1]) < Double.parseDouble(sortedList.get(j + 1)[1])) {
                    String[] temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }
            }
        }
        System.out.println("\nTypes:");
        sortedList.forEach(item -> System.out.printf("%s - $%.2f\n", item[0], Double.parseDouble(item[1])));
        System.out.printf("Total sum: $%.2f\n", sumTotal);
    }
}
