package kz.roma.fortsbackoffice.service.consumer;


import kz.roma.fortsbackoffice.config.RabbitConfig;

import kz.roma.fortsbackoffice.domain_model.Deals;
import kz.roma.fortsbackoffice.service.threads.ExportJson;
import kz.roma.fortsbackoffice.service.threads.SaveDealsThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;


@Component
public class DealsConsumer {
    private static Logger logger = LoggerFactory.getLogger(DealsConsumer.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private CyclicBarrier cyclicBarrier;

    @RabbitListener(queues = RabbitConfig.dealsQueue)
    public void consumeInstrMessage(Deals deals) {
        logger.debug("Deals received from queue: " + deals);

        SaveDealsThread saveDealsThread = applicationContext.getBean(SaveDealsThread.class);
        saveDealsThread.setCyclicBarrier(cyclicBarrier);
        saveDealsThread.setDeals(deals);

        ExportJson exportJson = applicationContext.getBean(ExportJson.class);
        exportJson.setCyclicBarrier(cyclicBarrier);
        exportJson.setObject(deals);

        executorService.execute(saveDealsThread);
        executorService.execute(exportJson);

    }
}
