package kz.roma.fortsbackoffice.config;

import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DateConfig {
    public String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String date = df.format(new Date());
        return date;
    }

    public Date getDate() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String date = df.format(new Date());
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy/MM/dd");
        Date currentDate = null;
        try {
            currentDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate;
    }
}
