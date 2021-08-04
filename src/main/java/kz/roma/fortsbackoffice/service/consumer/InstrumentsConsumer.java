package kz.roma.fortsbackoffice.service.consumer;

import kz.roma.fortsbackoffice.config.RabbitConfig;
import kz.roma.fortsbackoffice.domain_model.Instruments;
import kz.roma.fortsbackoffice.service.threads.ExportJson;
import kz.roma.fortsbackoffice.service.threads.SaveInstrumentThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;


@Component
public class InstrumentsConsumer {
    private static Logger logger = LoggerFactory.getLogger(InstrumentsConsumer.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private CyclicBarrier cyclicBarrier;

    @RabbitListener(queues = RabbitConfig.instrQueue)
    public void consumeInstrMessage(Instruments instruments) {
        logger.debug("Instruments received from queue: " + instruments);

        SaveInstrumentThread saveInstrumentThread = applicationContext.getBean(SaveInstrumentThread.class);
        saveInstrumentThread.setCyclicBarrier(cyclicBarrier);
        saveInstrumentThread.setInstruments(instruments);

        ExportJson exportJson = applicationContext.getBean(ExportJson.class);
        exportJson.setObject(instruments);
        exportJson.setCyclicBarrier(cyclicBarrier);


        executorService.execute(saveInstrumentThread);
        executorService.execute(exportJson);
    }
}

