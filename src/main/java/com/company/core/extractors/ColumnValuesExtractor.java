package com.company.core.extractors;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@Primary
public class ColumnValuesExtractor implements DataManager {
    private final Multimap<String, String> fileContent;
    private final int columnNumber;

    public ColumnValuesExtractor(int columnNumber) {
        this.columnNumber = columnNumber;
        fileContent = TreeMultimap.create();
    }

    @Override
    public String[] extractData(String filename) {
        try {
            var reader = new CSVReader(new FileReader("src/main/resources/airports.dat"), ',' , '"' , 1);
            String[] nextLine;
            while (true) {
                nextLine = reader.readNext();
                if (nextLine != null) {
                    fileContent.put(nextLine[columnNumber], Arrays.toString(nextLine).intern());
                } else
                    break;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent.keySet().toArray(new String[0]);
    }

    @Override
    public String[] transformData(String[] data) {
        var result = new ArrayList<String>();
        for (String datum : data)
            result.addAll(fileContent.get(datum));
        return result.toArray(new String[0]);
    }
}
