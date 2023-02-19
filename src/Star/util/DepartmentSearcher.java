package Star.util;

import Star.model.BriefDepartment;
import Star.model.SchoolDepartment;
import com.opencsv.CSVReader;

import Star.model.Department;

public class DepartmentSearcher {

    public Department search(String year, SchoolDepartment schoolDepartment) {

        String schoolCode = schoolDepartment.getSchoolCode();
        String departmentName = schoolDepartment.getDepartmentName();
        CSVReader reader = CSVReaderUtil.getSchoolReader(year, schoolCode);

        // Get department data
        String[] data = searchByDepartment(departmentName, reader);

        // 陽明（025）科系已併入陽明交通（013），舊資料需從 025 找
        if (data == null && schoolCode.equals("013")) {
            reader = CSVReaderUtil.getSchoolReader(year, "025");
            data = searchByDepartment(departmentName, reader);
        }

        if (data == null)
            return new Department();
        else
            return arrayToDepartment(data, year);

        
    }

    // 根據校系名稱取得校系資料
    private static String[] searchByDepartment(String department, CSVReader reader) {
        
        String[] data = null;
        try {
            while ((data = reader.readNext()) != null) {
                String resultName = data[1];
                if (resultName.equals(department)) break;
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

        String[] recruits = new String[7];
        String[] ranks = new String[7];
        String[] percents = new String[7];

        for (int i = 105; i <= 112; i++) {
            CSVReader reader = CSVReaderUtil.getSchoolReader(String.valueOf(i), schoolCode);
            String[] data = searchByDepartment(departmentName, reader);

            if (i == 112) {
                ranks = data[4].split("\n");
            } else if (data != null) {
                recruits[i - 105] = data[3];
                percents[i - 105] = data[7].split("\n")[0];
            }
        }

        return new BriefDepartment(schoolDepartment, recruits, ranks, percents);

    }

    private Department arrayToDepartment(String[] data, String year) {
        String scale, fil1, fil2;
        StringBuilder builder = new StringBuilder();

        // Recruit
        builder.append(String.format("%s / %s", data[2], data[3]));

        // Subjects
        String[] subjects;
        String[] scales = data[4].split("\n");

        // Math A, B is added since 111
        if (Integer.parseInt(year) >= 111)
            subjects = new String[] {"國文", "英文", "數學Ａ", "數學Ｂ", "社會", "自然", "英聽"};
        else
            subjects = new String[] {"國文", "英文", "數學", "社會", "自然", "英聽"};

        for (int i = 0; i < subjects.length; i++) {
            builder.append(String.format("\n%-4s%s", subjects[i], scales[i]));
        }

        scale = builder.toString().replace(' ', '　');

        // Filters
        String[] filters = data[5].split("\n");

        // Stage 1
        builder = new StringBuilder();
        builder.append(String.format("%s / %s", data[6], data[2]));
        String[] fil_1 = data[7].split("\n");
        for (int i = 0; i < filters.length; i++) {
            builder.append(String.format("\n%-7s%s", filters[i], fil_1[i]));
        }
        fil1 = builder.toString().replace("A", "Ａ").replace("B", "Ｂ").replace(' ', '　');
        
        // Stage 2
        if (year.equals("112")) {
            fil2 = fil1;
        } else if (!data[8].equals("--")) {
            builder = new StringBuilder();
            builder.append(String.format("%s / %s", data[8], data[2]));
            String[] fil_2 = data[9].split("\n");
            for (int i = 0; i < filters.length; i++) {
                builder.append(String.format("\n%-7s%s", filters[i], fil_2[i]));
            }
            fil2 = builder.toString().replace("A", "Ａ").replace("B", "Ｂ").replace(' ', '　');
        // 無第二輪篩選
        } else {
            fil2 = "無第二輪";
        }

        return new Department(scale, fil1, fil2);
    }
}
