package kz.roma.fortsbackoffice.dao.deals;

import kz.roma.fortsbackoffice.config.DateConfig;
import kz.roma.fortsbackoffice.domain_model.Deals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DealsDaoImpl implements DealsDao {
    @Autowired
    private DealsRepo dealsRepo;

    @Autowired
    private DateConfig dateConfig;

    @Override
    public void save(Deals deals) {
        dealsRepo.save(deals);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deals> findCountDealsByDate(Date currentDate) {
        return dealsRepo.findCountDealsByDate(dateConfig.getDate());
    }
}
