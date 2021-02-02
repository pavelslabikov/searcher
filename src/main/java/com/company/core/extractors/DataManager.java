package com.company.core.extractors;

public interface DataManager {
    String[] extractData(String filename);
    String[] transformData(String[] data);
}
