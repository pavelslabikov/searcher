package com.company.core;

import com.company.core.extractors.CvsDataExtractor;
import com.company.core.searchers.ISearcher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("prototype")
public class CvsSearchEngine {
    private String[] target;
    private final ISearcher searcher;
    private final CvsDataExtractor extractor;

    public CvsSearchEngine(ISearcher searcher, CvsDataExtractor extractor) {
        this.searcher = searcher;
        this.extractor = extractor;
    }

    public String[] execute(String query) {
        return searcher.search(target, query);
    }

    @PostConstruct
    public void prepareData() {
        target = extractor.extractData("src/main/resources/airports.dat");
    }
}
