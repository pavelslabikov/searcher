package com.company.core.searchers;

import com.company.utils.BinarySearch;
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
        comparator.setEndIndex(query.length());
        var leftIndex = BinarySearch.getFirstOccurrence(target, query, 0, target.length, comparator);  // TODO: check indices
        var rightIndex = BinarySearch.getLastOccurrence(target, query, leftIndex, target.length, comparator);
        return Arrays.copyOfRange(target, leftIndex, rightIndex);
    }

    public static class PrefixComparator implements Comparator<String> {
        private int endIndex;

        public void setEndIndex(int index) {
            this.endIndex = index;
        }

        @Override
        public int compare(String o1, String o2) {
            var firstIndex = Math.min(o1.length(), endIndex);
            var secondIndex = Math.min(o2.length(), endIndex);
            return o1.substring(0, firstIndex).compareTo(o2.substring(0, secondIndex));
        }
    }
}
