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

    public static String[] getDepartmentList(String school, boolean filterEnabled, int[] scores) {
        String schoolCode = school.split(" ")[0];
        try {
            String str = "";

            CSVReader reader = CSVReaderUtil.getSchoolReader("111", schoolCode);

            String[] data;
            while ((data = reader.readNext()) != null) {
                if (filterEnabled) {
                    String[] ranks = data[4].split("\n");
                    if (!FilterUtil.filter(ranks, scores)) continue;
                }
                String code = data[0];
                String name = data[1];
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
