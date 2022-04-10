package Star.util;

import java.io.InputStreamReader;

import Star.MainApp;
import com.opencsv.CSVReader;

public class CSVReaderUtil {
    
    public static CSVReader getSchoolReader(String year, String schoolCode) {
        try {
            String csv_path = "csv/%s/%s_%s.csv";
            String path = String.format(csv_path, year, year, schoolCode);
            InputStreamReader isr = new InputStreamReader(MainApp.class.getResourceAsStream(path));
            CSVReader reader = new CSVReader(isr);
            if (!schoolCode.equals("id")) reader.readNext();
            return reader;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CSVReader getIdReader(String year) {
        return getSchoolReader(year, "id");
    }
}
