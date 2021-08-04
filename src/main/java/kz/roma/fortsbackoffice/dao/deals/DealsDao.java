package kz.roma.fortsbackoffice.dao.deals;

import kz.roma.fortsbackoffice.domain_model.Deals;

import java.util.Date;
import java.util.List;

public interface DealsDao {
    void save (Deals deals);
    List<Deals> findCountDealsByDate(Date currentDate);
}
