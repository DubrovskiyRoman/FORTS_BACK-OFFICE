package kz.roma.fortsbackoffice.dao.orders;

import kz.roma.fortsbackoffice.domain_model.Orders;

import java.util.Date;
import java.util.List;

public interface OrdersDao {
    void saveOrders(Orders orders);

    List<Orders> findCountOrdersByDate(Date currentDate);

}
