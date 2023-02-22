package Star.util;

import Star.filter.Filter;
import Star.model.SchoolDepartment;
import com.opencsv.CSVReader;

import java.util.ArrayList;
import java.util.List;

public class ListViewUtil {
    
    public static List<String> getSchoolList() {
        try {
            List<String> list = new ArrayList<>();

            CSVReader reader = CSVReaderUtil.getIdReader("112");

            String[] data;
            while ((data = reader.readNext()) != null) {
                list.add(String.format("%s %s", data[0], data[1]));
            }
            reader.close();

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getDepartmentList(SchoolDepartment schoolDepartment, boolean filterEnabled, int[] scores) {
        try {
            List<String> list = new ArrayList<>();

            CSVReader reader = CSVReaderUtil.getSchoolReader("112", schoolDepartment.getSchoolCode());
            assert reader != null;

            String[] data;
            while ((data = reader.readNext()) != null) {
                if (filterEnabled) {
                    String[] ranks = data[4].split("\n");
                    if (!Filter.filter(ranks, scores)) continue;
                }
                String code = data[0];
                String name = data[1];
                list.add(String.format("%s %s", code, name));
            }
            reader.close();

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
