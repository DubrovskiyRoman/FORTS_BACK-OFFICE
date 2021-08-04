package kz.roma.fortsbackoffice.service.threads;

import kz.roma.fortsbackoffice.dao.deals.DealsDao;
import kz.roma.fortsbackoffice.domain_model.Deals;
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
public class SaveDealsThread implements Runnable {
    private Logger logger = LoggerFactory.getLogger(SaveOrdersThread.class);
    CyclicBarrier cyclicBarrier;
    Deals deals;

    @Autowired
    DealsDao dealsDao;

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public Deals getDeals() {
        return deals;
    }

    public void setDeals(Deals deals) {
        this.deals = deals;
    }

    public void save (Deals deals){
        dealsDao.save(deals);
    }



    @Override
    public void run() {
        logger.debug("Begin SaveDeals thread for deals with rowId: " + deals.getRowId());
        save (deals);
        try {
            cyclicBarrier.await();
            logger.debug("SaveDeals thread for instrument with rowId: " + deals.getRowId() + " is achieved barrier");

        } catch (InterruptedException e) {
            logger.error("SaveDealsThread is interrupted for ", deals.getRowId(),  e);
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            logger.error("SaveDeals threadBarrier is not achieved for ", deals.getRowId(),  e);
            e.printStackTrace();
        }

    }
}
