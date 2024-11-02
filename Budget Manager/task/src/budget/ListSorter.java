package budget;

import java.util.List;

public class ListSorter {

    Sorter sorter;

    public ListSorter(Sorter sorter) {
        this.sorter = sorter;
    }

    public void sort(List<String[]> list) {
        sorter.sort(list);
    }

}
