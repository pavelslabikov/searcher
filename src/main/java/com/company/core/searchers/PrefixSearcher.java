package com.company.core.searchers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;

@Component
@Scope("prototype")
public class PrefixSearcher implements Searcher {
    private final PrefixComparator comparator = new PrefixComparator();

    @Override
    public String[] search(String[] target, String query) {
        if (target.length == 0)
            return target;

        var leftIndex = getFirstOccurrence(target, query);
        if (leftIndex == -1)
            return new String[0];

        var rightIndex = getLastOccurrence(target, query, leftIndex, target.length - 1);
        return Arrays.copyOfRange(target, leftIndex, rightIndex + 1);
    }

    private int getFirstOccurrence(String[] target, String key) {
        var left = 0;
        var right = target.length - 1;
        while (left < right - 1) {
            var mid = (left + right) / 2;
            if (comparator.compare(key, target[mid]) > 0)
                left = mid;
            else
                right = mid;
        }

        if (comparator.compare(key, target[right]) != 0)
            return -1;

        return right;
    }

    private int getLastOccurrence(String[] target, String key, int left, int right) {
        while (left < right - 1) {
            var mid = (left + right) / 2;
            if (comparator.compare(key, target[mid]) >= 0)
                left = mid;
            else
                right = mid;
        }

        if (comparator.compare(key, target[left]) != 0)
            return -1;

        return left;
    }

    private static class PrefixComparator implements Comparator<String> {
        @Override
        public int compare(String prefix, String s) {
            var index = Math.min(prefix.length(), s.length());
            return prefix.compareTo(s.substring(0, index));
        }
    }
}
