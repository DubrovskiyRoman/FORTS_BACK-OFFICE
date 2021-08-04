package kz.roma.fortsbackoffice.service.consumer;


import kz.roma.fortsbackoffice.domain_model.Orders;
import kz.roma.fortsbackoffice.config.RabbitConfig;
import kz.roma.fortsbackoffice.service.threads.ExportJson;
import kz.roma.fortsbackoffice.service.threads.SaveOrdersThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

@Component
public class OrdersConsumer {
    private static Logger logger = LoggerFactory.getLogger(OrdersConsumer.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private CyclicBarrier cyclicBarrier;

    @RabbitListener(queues = RabbitConfig.ordersQueue)
    public void consumeInstrMessage(Orders orders) {
        logger.debug("Orders received from queue: " + orders);
        SaveOrdersThread saveOrdersThread = applicationContext.getBean(SaveOrdersThread.class);
        saveOrdersThread.setCyclicBarrier(cyclicBarrier);
        saveOrdersThread.setOrders(orders);

        ExportJson exportJson = applicationContext.getBean(ExportJson.class);
        exportJson.setObject(orders);
        exportJson.setCyclicBarrier(cyclicBarrier);

        executorService.execute(saveOrdersThread);
        executorService.execute(exportJson);
    }
}
