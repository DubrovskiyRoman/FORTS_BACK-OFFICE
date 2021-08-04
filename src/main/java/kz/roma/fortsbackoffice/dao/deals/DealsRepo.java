package kz.roma.fortsbackoffice.dao.deals;

import kz.roma.fortsbackoffice.domain_model.Deals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DealsRepo extends JpaRepository<Deals, Long> {
    @Query(value = "select d from Deals d where d.downloadDate=:downloadDate")
    List<Deals> findCountDealsByDate(@Param("downloadDate") Date currentDate);
}
