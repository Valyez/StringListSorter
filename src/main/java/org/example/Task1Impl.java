package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <h1>Задание №1</h1>
 * Реализуйте интерфейс {@link IStringRowsListSorter}.
 *
 * <p>Мы будем обращать внимание в первую очередь на структуру кода и владение стандартными средствами java.</p>
 */
public class Task1Impl implements IStringRowsListSorter {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.

    private Task1Impl() {
    }

    public static final IStringRowsListSorter INSTANCE = new Task1Impl();

    @Override
    public void sort(final List<String[]> rows, final int columnIndex) {
        Comparator<String[]> comparator = (row1, row2) -> {
            // Сначала сравниваем null-значения
            String value1 = row1[columnIndex];
            String value2 = row2[columnIndex];
            if (value1 == null && value2 == null) {
                return 0;
            } else if (value1 == null) {
                return -1;
            } else if (value2 == null) {
                return 1;
            }

            // Затем сравниваем пустые значения
            if (value1.isEmpty() && value2.isEmpty()) {
                return 0;
            } else if (value1.isEmpty()) {
                return -1;
            } else if (value2.isEmpty()) {
                return 1;
            }

            // Разбиваем строки на подстроки и сравниваем их
            String[] substrings1 = value1.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            String[] substrings2 = value2.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

            int minLength = Math.min(substrings1.length, substrings2.length);
            for (int i = 0; i < minLength; i++) {
                int comparisonResult = compareSubstrings(substrings1[i], substrings2[i]);
                if (comparisonResult != 0) {
                    return comparisonResult;
                }
            }

            // Если подстроки дошли до конца, но строки различимы по длине
            return Integer.compare(substrings1.length, substrings2.length);
        };

        // Сортируем список строк с использованием компаратора
        synchronized (rows) {
            rows.sort(comparator);
        }
    }

    private int compareSubstrings(String str1, String str2) {
        // Если обе подстроки состоят из цифр, интерпретируем их как целые числа
        if (str1.matches("\\d+") && str2.matches("\\d+")) {
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
        }
        // В противном случае, сравниваем как строки
        return str1.compareTo(str2);
    }

}

