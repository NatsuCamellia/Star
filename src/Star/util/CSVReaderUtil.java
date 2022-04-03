package Star.util;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.opencsv.CSVReader;

public class CSVReaderUtil {

    private static String csv_path = "app/resources/csv/%s/%s_%s.csv";
    
    public static CSVReader getSchoolReader(String year, String school) {
        try {
            String path = String.format(csv_path, year, year, school);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path));
            CSVReader reader = new CSVReader(isr);
            if (!school.equals("id")) reader.readNext(); 
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
