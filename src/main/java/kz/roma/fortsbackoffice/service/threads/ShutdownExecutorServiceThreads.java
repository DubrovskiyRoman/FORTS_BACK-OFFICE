package kz.roma.fortsbackoffice.service.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class ShutdownExecutorServiceThreads implements Runnable {
    private Logger logger = LoggerFactory.getLogger(ShutdownExecutorServiceThreads.class);

    ExecutorService executorService;

    public ShutdownExecutorServiceThreads(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void run() {
        executorService.shutdown();
        logger.debug("Shutdown ExecutorService threads;");

    }
}
