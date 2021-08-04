package kz.roma.fortsbackoffice.service.threads;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public interface ExportJson extends Runnable   {
    void dispatcherJsonObjects(Object object) throws IOException;
    void setObject(Object object);
    void setCyclicBarrier(CyclicBarrier cyclicBarrier);
    void exportObjectsToJson(List<Object> jsonObjects, String jsonFilePath) throws IOException;


    @Override
    default void run() {

    }
}
