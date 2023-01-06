package pl.first.project.book.store.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CykliczneZadania {

    @Scheduled(cron = "0 0 8,22 * * *")
    public void zadanie() {
        System.out.println("Robi się cykliczne zadanie");
    }
}
