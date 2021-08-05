package kz.roma.fortsbackoffice.dao.instruments;

import kz.roma.fortsbackoffice.config.DateConfig;
import kz.roma.fortsbackoffice.domain_model.Instruments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InstrumentDaoImpl implements InstrumentDao {
    @Autowired
    InstrumentRepo instrumentRepo;

    @Autowired
    DateConfig dateConfig;

    public void save(Instruments instruments) {
        instrumentRepo.save(instruments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instruments> findCountInstrumentsByDate(Date currentDate) {
        return instrumentRepo.findCountInstrumentsByDate(dateConfig.getDate());
    }


}
