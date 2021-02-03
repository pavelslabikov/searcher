package com.company.core;

import com.company.core.extractors.DataManager;
import com.company.core.searchers.Searcher;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class CsvSearchEngine {
    private String[] target;

    private final Searcher searcher;
    private final DataManager dataManager;
    private final StopWatch timer;

    public CsvSearchEngine(Searcher searcher, DataManager dataManager) {
        this.searcher = searcher;
        this.dataManager = dataManager;
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
        var result = dataManager.transformData(searchResults);
        timer.stop();
        return result;
    }

    @PostConstruct
    public void prepareData() {
        target = dataManager.extractData();
    }
}
