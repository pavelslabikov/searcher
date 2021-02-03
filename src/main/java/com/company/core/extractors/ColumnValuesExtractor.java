package com.company.core.extractors;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@Scope("prototype")
@PropertySource("classpath:application.yml")
public class ColumnValuesExtractor implements DataManager {
    private final Multimap<String, String> fileContent;
    private final String filename;
    private final int columnNumber;

    public ColumnValuesExtractor(@Value("${app.filename}") String filename,
                                 @Value("${app.column-number}") int number) {
        this.filename = filename;
        fileContent = TreeMultimap.create();
        if (number <= 0)
            throw new IllegalArgumentException("Номер колонки должен быть > 0");

        columnNumber = number - 1;
    }

    @Override
    public String[] extractData() {
        try (var reader = new CSVReader(new FileReader(filename))) {
            String[] nextLine;
            while (true) {
                nextLine = reader.readNext();
                if (nextLine == null)
                    break;

                if (columnNumber >= nextLine.length)
                    continue;

                fileContent.put(nextLine[columnNumber], Arrays.toString(nextLine).intern());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileContent.keySet().toArray(new String[0]);
    }

    @Override
    public String[] transformData(String[] data) {
        var result = new ArrayList<String>();
        for (String column : data)
            result.addAll(fileContent.get(column));
        return result.toArray(new String[0]);
    }
}
