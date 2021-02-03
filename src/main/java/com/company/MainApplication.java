package com.company;

import com.company.core.CsvSearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    private CsvSearchEngine searchEngine;

    public static void main(String [] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Autowired
    public void engine(CsvSearchEngine engine) {
        searchEngine = engine;
    }

    @Override
    public void run(String... args) {
        var inputScanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите строку: ");
            var query = inputScanner.nextLine();
            if (query.equals("")) {
                System.out.println("Завершение программы...");
                break;
            }

            String[] result = searchEngine.execute(query);
            for (var str : result)
                System.out.println(str);

            System.out.printf("\nКоличество найденных строк: %d\n", result.length);
            System.out.printf("Время, затраченное на поиск: %d мкс\n", searchEngine.getElapsedSearchTime());
        }
    }
}
