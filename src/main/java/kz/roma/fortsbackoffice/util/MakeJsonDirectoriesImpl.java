package kz.roma.fortsbackoffice.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;


@Service
public class MakeJsonDirectoriesImpl implements MakeJsonDirectories {
    private static Logger logger = LoggerFactory.getLogger(MakeJsonDirectories.class);
    Boolean existJsonDir;
    List<String> exportJsonDirsList = new ArrayList<>();

    public MakeJsonDirectoriesImpl(@Value("${jsondir.instruments}") String instrumentsJsonExports,
                                   @Value("${jsondir.orders}") String ordersJsonExports,
                                   @Value("${jsondir.deals}") String dealsJsonExports) {

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String data = df.format(new Date());
        exportJsonDirsList.add(instrumentsJsonExports + data);
        exportJsonDirsList.add(ordersJsonExports + data);
        exportJsonDirsList.add(dealsJsonExports + data);
    }

    @PostConstruct
    private void makeDir() {
        Stream<String> dirListStream = exportJsonDirsList.stream();
        Iterator<String> dirListItr = dirListStream.iterator();
        while (dirListItr.hasNext()) {
            Path dirJsonPath = Paths.get(dirListItr.next());
            existJsonDir = checkJsonDirectories((pathJson) -> Files.exists(pathJson), dirJsonPath);
            if (!existJsonDir) {
                logger.debug("Directory: " + dirJsonPath + " is not existed");
                makeJsonDirectory((directories) -> {
                    try {
                        Files.createDirectories(directories);
                        //System.out.println("Directory: " + directories + " is made");
                        logger.debug("Directory: " + directories + " is made");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return directories;
                }, dirJsonPath);
            } else {
                //System.out.println("Directory: " + dirJsonPath + " is already existed");
                logger.debug("Directory: " + dirJsonPath + " is already existed");
            }
        }
    }


    private Path makeJsonDirectory(UnaryOperator<Path> makeExportJsonDir, Path filepath) {
        return makeExportJsonDir.apply(filepath);
    }

    private boolean checkJsonDirectories(Predicate<Path> existJson, Path filePath) {
        return existJson.test(filePath);
    }

    public List<String> getExportJsonDirsList() {
        return exportJsonDirsList;
    }
}
