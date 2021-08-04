package kz.roma.fortsbackoffice.service.threads;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kz.roma.fortsbackoffice.config.DateConfig;
import kz.roma.fortsbackoffice.dao.deals.DealsDao;
import kz.roma.fortsbackoffice.dao.instruments.InstrumentDao;
import kz.roma.fortsbackoffice.dao.orders.OrdersDao;
import kz.roma.fortsbackoffice.util.MakeJsonDirectories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


@Service
@Scope("prototype")
public class ExportJsonImplThread implements Runnable, ExportJson {
    private static Logger logger = LoggerFactory.getLogger(ExportJsonImplThread.class);
    @Autowired
    private MakeJsonDirectories makeJsonDirectories;

    @Autowired
    private DateConfig dateConfig;

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private DealsDao dealsDao;

    @Autowired
    private InstrumentDao instrumentDao;

    private String jsonFile;
    private Object object;
    private CyclicBarrier cyclicBarrier;

    public static List<Object> instrJsonList = new ArrayList<>();
    public static List<Object> orderJsonList = new ArrayList<>();
    public static List<Object> dealJsonList = new ArrayList<>();

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }



    public synchronized void dispatcherJsonObjects(Object object) throws IOException {
        switch (object.getClass().getSimpleName()) {
            case "Orders":
                logger.debug("Begin thread ExportJSON for " + object.getClass().getSimpleName());
                orderJsonList.add(object);
                jsonFile = makeJsonDirectories.getExportJsonDirsList().get(1) + "\\JSON_Orders_" + dateConfig.getCurrentDate() + ".json";
                exportObjectsToJson(orderJsonList, jsonFile);
                break;
            case "Instruments":
                logger.debug("Begin thread ExportJSON for " + object.getClass().getSimpleName());
                instrJsonList.add(object);
                jsonFile = makeJsonDirectories.getExportJsonDirsList().get(0) + "\\JSON_Instruments_" + dateConfig.getCurrentDate() + ".json";
                exportObjectsToJson(instrJsonList, jsonFile);
                break;
            case "Deals":
                logger.debug("Begin thread ExportJSON for " + object.getClass().getSimpleName());
                dealJsonList.add(object);
                jsonFile = makeJsonDirectories.getExportJsonDirsList().get(2) + "\\JSON_Deals_" + dateConfig.getCurrentDate() + ".json";
                exportObjectsToJson(dealJsonList, jsonFile);
                break;
        }
    }

    public synchronized void exportObjectsToJson(List<Object> jsonObjects, String jsonFilePath) throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").setPrettyPrinting().create();
        String jsonObject = gson.toJson(jsonObjects);
        char buffer[] = new char[jsonObject.length()];
        jsonObject.getChars(0, jsonObject.length(), buffer, 0);
        try (FileWriter jsonFileWriter = new FileWriter(jsonFilePath)) {
            jsonFileWriter.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            dispatcherJsonObjects(object);
            cyclicBarrier.await();
            logger.debug("ExportJSON for thread " + object.getClass().getSimpleName() + " is achieved barrier");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
