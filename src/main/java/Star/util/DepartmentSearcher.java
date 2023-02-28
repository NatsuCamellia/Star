package Star.util;

import Star.MainApp;
import Star.model.BriefDepartment;
import Star.model.SchoolDepartment;
import com.opencsv.CSVReader;

import Star.model.Department;

public class DepartmentSearcher {

    // Given year, school and department, then search
    public Department search(String year, SchoolDepartment schoolDepartment) {

        String schoolCode = schoolDepartment.getSchoolCode();
        String departmentName = schoolDepartment.getDepartmentName();
        CSVReader reader = CSVReaderUtil.getSchoolReader(year, schoolCode);

        // Get department data
        String[] data = searchByDepartment(departmentName, reader);

        // 陽明（025）科系已併入陽明交通（013），舊資料需從 025 找
        if (data == null && schoolCode.equals("013")) {
            if ((reader = CSVReaderUtil.getSchoolReader(year, "025")) != null) {
                data = searchByDepartment(departmentName, reader);
            }
        }

        if (data == null)
            return new Department();
        else
            return DataParser.parse(data, year);
    }

    // 根據校系名稱取得校系資料
    private static String[] searchByDepartment(String departmentName, CSVReader reader) {
        
        String[] data = null;
        try {
            while ((data = reader.readNext()) != null) {
                String resultName = data[1];
                if (resultName.equals(departmentName)) break;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static BriefDepartment getBriefDepartment(SchoolDepartment schoolDepartment) {
        String schoolCode = schoolDepartment.getSchoolCode();
        String departmentName = schoolDepartment.getDepartmentName();

        // Scale
        CSVReader reader = CSVReaderUtil.getSchoolReader(String.valueOf(MainApp.SCALE_YEAR), schoolCode);
        String[] data = searchByDepartment(departmentName, reader);
        String[] scales = data[4].split("\n");

        // History
        String[] recruits = new String[7];
        String[] percents = new String[7];

        int startYear = MainApp.MULTI_START_YEAR;
        int endYear = MainApp.MULTI_END_YEAR;
        for (int year = startYear; year <= endYear; year++) {
            reader = CSVReaderUtil.getSchoolReader(String.valueOf(year), schoolCode);
            data = searchByDepartment(departmentName, reader);

            int index = year - startYear;
            if (data != null) {
                recruits[index] = data[3];
                percents[index] = data[7].split("\n")[0];
            } else {
                recruits[index] = "--";
                percents[index] = "--";
            }
        }

        return new BriefDepartment(schoolDepartment, recruits, scales, percents);

    }
}
