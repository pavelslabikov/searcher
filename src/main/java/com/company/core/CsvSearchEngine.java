package com.company.core;

import com.company.core.extractors.DataManager;
import com.company.core.searchers.ISearcher;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class CsvSearchEngine {
    private String[] target;

    private final ISearcher searcher;
    private final DataManager extractor;
    private final StopWatch timer;

    public CsvSearchEngine(ISearcher searcher, DataManager extractor) {
        this.searcher = searcher;
        this.extractor = extractor;
        timer = new StopWatch();
    }

    public long getElapsedSearchTime() {
        return timer.getTime(TimeUnit.MICROSECONDS);
    }

    public String[] execute(String query) {
        if (target == null)
            throw new IllegalStateException();

        timer.reset();
        timer.start();
        var searchResults = searcher.search(target, query);
        var result = extractor.transformData(searchResults);
        timer.stop();
        return result;
    }

    @PostConstruct
    public void prepareData() {
        target = extractor.extractData();
    }
}
