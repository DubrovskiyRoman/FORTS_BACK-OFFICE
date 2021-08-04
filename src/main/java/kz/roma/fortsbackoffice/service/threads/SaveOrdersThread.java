package kz.roma.fortsbackoffice.service.threads;

import kz.roma.fortsbackoffice.dao.orders.OrdersDao;
import kz.roma.fortsbackoffice.domain_model.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Service
@Scope("prototype")
public class SaveOrdersThread implements Runnable {
    private Logger logger = LoggerFactory.getLogger(SaveOrdersThread.class);
    CyclicBarrier cyclicBarrier;
    Orders orders;

    @Autowired
    OrdersDao ordersDao;

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public void save (Orders orders){
        ordersDao.saveOrders(orders);
    }

    @Override
    public void run() {
        logger.debug("Begin SaveOrders thread for orders with rowId: " + orders.getRowId());
        save(orders);
        try {
            cyclicBarrier.await();
            logger.debug("SaveOrders thread for orders with rowId: " + orders.getRowId() + " is achieved barrier");
        } catch (InterruptedException e) {
            logger.error("SaveOrders thread is interrupted for ", orders.getRowId(), e);
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            logger.error("SaveOrders threadBarrier is not achieved for ", orders.getRowId(), e);
            e.printStackTrace();
        }

    }
}
