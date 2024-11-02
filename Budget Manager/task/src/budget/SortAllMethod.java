package budget;

import java.util.*;

public class SortAllMethod implements Sorter {

    public void sort(List<String[]> list) {
        if (list.isEmpty()) {
            System.out.println("\nThe purchase list is empty!");
            return;
        }
        double total = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if (Double.parseDouble(list.get(j)[2]) < Double.parseDouble(list.get(j + 1)[2])) {
                    String[] temp = list.get(j).clone();
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        System.out.println();
        list.forEach(item -> System.out.printf("%s $%.2f\n", item[1], Double.parseDouble(item[2])));
    }
}
