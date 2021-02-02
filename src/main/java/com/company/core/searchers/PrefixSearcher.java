package com.company.core.searchers;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;

@Component
@Primary
public class PrefixSearcher implements ISearcher {
    private final PrefixComparator comparator = new PrefixComparator();

    @Override
    public String[] search(String[] target, String query) {
        var leftIndex = getFirstOccurrence(target, query, 0, target.length - 1);
        if (leftIndex == -1)
            return new String[0];

        var rightIndex = getLastOccurrence(target, query, leftIndex, target.length - 1);
        return Arrays.copyOfRange(target, leftIndex, rightIndex + 1);
    }

    private int getFirstOccurrence(String[] target, String key, int start, int end) {
        while (start < end - 1) {
            var mid = (start + end) / 2;
            if (comparator.compare(key, target[mid]) > 0)
                start = mid;
            else
                end = mid;
        }

        if (comparator.compare(key, target[end]) != 0)
            return -1;

        return end;
    }

    private int getLastOccurrence(String[] target, String key, int start, int end) {
        while (start < end - 1) {
            var mid = (start + end) / 2;
            if (comparator.compare(key, target[mid]) >= 0)
                start = mid;
            else
                end = mid;
        }

        if (comparator.compare(key, target[start]) != 0)
            return -1;

        return start;
    }

    private static class PrefixComparator implements Comparator<String> {
        @Override
        public int compare(String prefix, String s) {
            var index = Math.min(prefix.length(), s.length());
            return prefix.compareTo(s.substring(0, index));
        }
    }
}
