package kz.roma.fortsbackoffice.dao.instruments;

import kz.roma.fortsbackoffice.domain_model.Instruments;


import java.util.Date;
import java.util.List;

public interface InstrumentDao {
    void save(Instruments instruments);

    List<Instruments> findCountInstrumentsByDate(Date currentDate);

}
