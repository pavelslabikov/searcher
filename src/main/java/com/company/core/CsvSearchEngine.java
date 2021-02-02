package com.company.core;

import com.company.core.extractors.DataManager;
import com.company.core.searchers.ISearcher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("prototype")
public class CsvSearchEngine {
    private String[] target;
    private final ISearcher searcher;
    private final DataManager extractor;

    public CsvSearchEngine(ISearcher searcher, DataManager extractor) {
        this.searcher = searcher;
        this.extractor = extractor;
    }

    public String[] execute(String query) {
        var searchResults = searcher.search(target, query);
        return extractor.transformData(searchResults);
    }

    @PostConstruct
    public void prepareData() {
        target = extractor.extractData("src/main/resources/airports.dat");
    }
}
