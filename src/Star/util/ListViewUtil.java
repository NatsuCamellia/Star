package Star.util;

import com.opencsv.CSVReader;

public class ListViewUtil {
    
    public static String[] getSchoolList() {
        try {
            String str = "";

            CSVReader reader = CSVReaderUtil.getIdReader("111");

            String[] data;
            while ((data = reader.readNext()) != null) {
                str += String.format("%s %s,", data[0], data[1]);
            }
            reader.close();

            return str.split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getDepartmentList(String schoolCode) {
        try {
            String str = "";

            CSVReader reader = CSVReaderUtil.getSchoolReader("111", schoolCode);

            String[] data;
            while ((data = reader.readNext()) != null) {
                String code = data[0];
                String name = data[1].replace("\n", "");
                str += String.format("%s %s,", code, name);
            }
            reader.close();

            return str.split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
