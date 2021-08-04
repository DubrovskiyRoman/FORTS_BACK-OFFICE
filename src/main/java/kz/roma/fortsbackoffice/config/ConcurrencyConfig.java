package kz.roma.fortsbackoffice.config;


import kz.roma.fortsbackoffice.service.threads.ShutdownExecutorServiceThreads;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
public class ConcurrencyConfig {
    @Bean
    @Scope("prototype")
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(2);
    }

    @Bean
    @Scope("prototype")
    public CyclicBarrier cyclicBarrier() {
        return new CyclicBarrier(2, new ShutdownExecutorServiceThreads(executorService()));
    }

}
