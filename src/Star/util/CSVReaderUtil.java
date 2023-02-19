package Star.util;

import java.io.InputStreamReader;
import java.util.Objects;

import Star.MainApp;
import com.opencsv.CSVReader;

public class CSVReaderUtil {
    
    public static CSVReader getSchoolReader(String year, String schoolCode) {
        try {
            CSVReader reader = getReader(year, schoolCode);
            reader.readNext();
            return reader;
        } catch (Exception e) {
            System.out.println("Error occurs when getting school reader!");
            e.printStackTrace();
            return null;
        }
    }

    public static CSVReader getIdReader(String year) {
        return getReader(year, "id");
    }

    private static CSVReader getReader(String year, String code) {
        String csv_path = "csv/%s/%s_%s.csv";
        String path = String.format(csv_path, year, year, code);
        InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(MainApp.class.getResourceAsStream(path)));
        return new CSVReader(isr);
    }
}
