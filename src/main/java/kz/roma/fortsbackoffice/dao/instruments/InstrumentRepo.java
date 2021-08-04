package kz.roma.fortsbackoffice.dao.instruments;

import kz.roma.fortsbackoffice.domain_model.Instruments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface InstrumentRepo extends JpaRepository<Instruments, Long> {
    @Query(value = "select i from Instruments i where i.downloadDate=:downloadDate")
    List<Instruments> findCountInstrumentsByDate(@Param("downloadDate") Date currentDate);

}
