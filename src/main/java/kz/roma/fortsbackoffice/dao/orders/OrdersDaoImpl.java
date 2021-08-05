package kz.roma.fortsbackoffice.dao.orders;

import kz.roma.fortsbackoffice.config.DateConfig;
import kz.roma.fortsbackoffice.domain_model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrdersDaoImpl implements OrdersDao {
    @Autowired
    OrdersRepo ordersRepo;

    @Autowired
    DateConfig dateConfig;

    @Override
    public void saveOrders(Orders orders) {
        ordersRepo.save(orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Orders> findCountOrdersByDate(Date currentDate) {
        return ordersRepo.findCountOrdersByDate(dateConfig.getDate());
    }
}
