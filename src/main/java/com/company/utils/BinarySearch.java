package com.company.utils;

import java.util.Comparator;

public class BinarySearch {
    public static <T> int getFirstOccurrence(T[] target, T key, int start, int end, Comparator<? super T> comparator) {
        while (start < end - 1) {
            var mid = (start + end) >>> 1;
            if (comparator.compare(target[mid], key) < 0)
                start = mid;
            else
                end = mid;
        }

        return end;
    }

    public static <T> int getLastOccurrence(T[] target, T key, int start, int end, Comparator<? super T> comparator) {
        while (start < end - 1) {
            var mid = (start + end) >>> 1;
            if (comparator.compare(target[mid], key) <= 0)
                start = mid;
            else
                end = mid;
        }

        return end;
    }
}