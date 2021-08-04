package kz.roma.fortsbackoffice.util;

import kz.roma.fortsbackoffice.config.DateConfig;
import kz.roma.fortsbackoffice.dao.deals.DealsDao;
import kz.roma.fortsbackoffice.dao.instruments.InstrumentDao;
import kz.roma.fortsbackoffice.dao.orders.OrdersDao;
import kz.roma.fortsbackoffice.service.threads.ExportJson;
import kz.roma.fortsbackoffice.service.threads.ExportJsonImplThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SyncJsonWithDB {
    private static Logger logger = LoggerFactory.getLogger(SyncJsonWithDB.class);

    @Autowired
    MakeJsonDirectories makeJsonDirectories;

    @Autowired
    DateConfig dateConfig;

    @Autowired
    ExportJson exportJson;

    @Autowired
    OrdersDao ordersDao;

    @Autowired
    DealsDao dealsDao;

    @Autowired
    InstrumentDao instrumentDao;

    private String jsonFile;
    ExportJsonImplThread exportJsonImplThread;

    private List<Object> instrJsonList = new ArrayList<>();
    private List<Object> orderJsonList = new ArrayList<>();
    private List<Object> dealJsonList = new ArrayList<>();

    // При запуске приложения производим синхронизацию JSON файлов и данных в БД, делается только на старте приложения
    @PostConstruct
    public void syncJsonWithDb() throws IOException {
        System.out.println("Begin synchronization with DB");
        if (!ordersDao.findCountOrdersByDate(dateConfig.getDate()).isEmpty()) {
            System.out.println("We have orders!!!");
            logger.debug("There are orders in DB on date: " + dateConfig.getDate());
            orderJsonList.add(ordersDao.findCountOrdersByDate(dateConfig.getDate()));
            jsonFile = makeJsonDirectories.getExportJsonDirsList().get(1) + "\\JSON_Orders_" + dateConfig.getCurrentDate() + ".json";
            exportJson.exportObjectsToJson(orderJsonList, jsonFile);
        } else {
            System.out.println("We don't have any orders in DB");
            logger.debug("There aren't any orders in DB on date: " + dateConfig.getDate());
        }
        if (!dealsDao.findCountDealsByDate(dateConfig.getDate()).isEmpty()) {
            System.out.println("We have deals!!!");
            logger.debug("There are deals in DB on date: " + dateConfig.getDate());
            dealJsonList.add(dealsDao.findCountDealsByDate(dateConfig.getDate()));
            jsonFile = makeJsonDirectories.getExportJsonDirsList().get(2) + "\\JSON_Deals_" + dateConfig.getCurrentDate() + ".json";
            exportJson.exportObjectsToJson(dealJsonList, jsonFile);
        } else {
            System.out.println("We don't have any deals in DB");
            logger.debug("There aren't any deals in DB on date: " + dateConfig.getDate());
        }
        if (!instrumentDao.findCountInstrumentsByDate(dateConfig.getDate()).isEmpty()) {
            System.out.println("We have Instruments!!!");
            logger.debug("There are instruments in DB on date: " + dateConfig.getDate());
            instrJsonList.add(instrumentDao.findCountInstrumentsByDate(dateConfig.getDate()));
            jsonFile = makeJsonDirectories.getExportJsonDirsList().get(0) + "\\JSON_Instruments_" + dateConfig.getCurrentDate() + ".json";
            exportJson.exportObjectsToJson(instrJsonList, jsonFile);
        } else {
            System.out.println("We don't have any instruments in DB");
            logger.debug("There aren't any instruments in DB on date: " + dateConfig.getDate());
        }
    }
}
