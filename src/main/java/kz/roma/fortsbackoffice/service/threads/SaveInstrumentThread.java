package kz.roma.fortsbackoffice.service.threads;

import kz.roma.fortsbackoffice.dao.instruments.InstrumentDao;
import kz.roma.fortsbackoffice.domain_model.Instruments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.BrokenBarrierException;

import java.util.concurrent.CyclicBarrier;

@Service
@Scope("prototype")
public class SaveInstrumentThread implements Runnable  {
    private static Logger logger = LoggerFactory.getLogger(SaveInstrumentThread.class);
    CyclicBarrier cyclicBarrier;
    Instruments instruments;

    @Autowired
    InstrumentDao instrumentDao;

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public Instruments getInstruments() {
        return instruments;
    }

    public void setInstruments(Instruments instruments) {
        this.instruments = instruments;
    }

    public void save (Instruments instruments){
        instrumentDao.save(instruments);
    }

    @Override
    public void run() {
        logger.debug("Begin SaveInstruments thread for instrument with rowId: " + instruments.getRowId());
        save(instruments);
        try {
            cyclicBarrier.await();
            logger.debug("SaveInstruments thread for instrument with rowId: " + instruments.getRowId() + " is achieved barrier");
        } catch (InterruptedException e) {
            logger.error("SaveInstruments thread is interrupted for ", instruments.getRowId(), e);
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            logger.error("SaveInstrument threadBarrier is not achieved for ", instruments.getRowId(), e);
            e.printStackTrace();
        }
    }
}
