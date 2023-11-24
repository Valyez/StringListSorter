package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{null, "30"});
        data.add(new String[]{"Alice", ""});
        data.add(new String[]{null, "10"});
        data.add(new String[]{"Bob", "Johnson"});

        Thread thread1 = new Thread(() -> {
            printAndSort(data);
        });
        Thread thread2 = new Thread(() -> {
            printAndSort(data);
        });
        thread1.start(); ;
        thread2.start();

    }

    private static void printAndSort(List<String[]> data) {
        System.out.println("Before sorting:");
        printData(data);

        // Сортировка по второму столбцу (индекс 1)
        IStringRowsListSorter sorter = Task1Impl.INSTANCE;
        sorter.sort(data, 1);

        System.out.println("\nAfter sorting:");
        printData(data);
    }

    private static void printData(List<String[]> data) {
        for (String[] row : data) {
            for (String value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }
}
