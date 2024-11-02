package budget;

import java.util.*;

public class SortCertainTypeMethod implements Sorter {

    private String type;

    public SortCertainTypeMethod(String type) {
        this.type = type;
    }

    public void sort(List<String[]> list) {
        if (list.isEmpty()) {
            System.out.println("\nThe purchase list is empty!");
            return;
        }
        List<String[]> newList = new ArrayList<>();
        int counter = 0;
        for (String[] item : list) {
            if (item[0].equals(type)) {
                newList.add(item);
                counter++;
            }
        }
        if (counter == 0) {
            System.out.println("\nThe purchase list is empty!");
            return;
        }
        double total = 0;
        for (int i = 0; i < newList.size() - 1; i++) {
            for (int j = 0; j < newList.size() - 1; j++) {
                if (Double.parseDouble(newList.get(j)[2]) < Double.parseDouble(newList.get(j + 1)[2])) {
                    String[] temp = newList.get(j);
                    newList.set(j, newList.get(j + 1));
                    newList.set(j + 1, temp);
                    total += Double.parseDouble(newList.get(j)[2]);
                }
            }
        }
        System.out.println();
        if (counter == 0) {
            System.out.println("The purchase list is empty!\n");
        }
        newList.forEach(item -> System.out.printf("%s $%.2f\n", item[1], Double.parseDouble(item[2])));
        System.out.printf("Total: $%.2f\n", total);
    }
}
