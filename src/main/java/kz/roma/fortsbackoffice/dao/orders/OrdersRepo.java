package kz.roma.fortsbackoffice.dao.orders;

import kz.roma.fortsbackoffice.domain_model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrdersRepo extends JpaRepository<Orders, Long> {
    @Query(value = "select o from Orders o where o.downloadDate=:downloadDate")
    List<Orders> findCountOrdersByDate(@Param("downloadDate") Date currentDate);
}
