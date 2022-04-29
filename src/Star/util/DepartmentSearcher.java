package Star.util;

import Star.model.BriefDepartment;
import com.opencsv.CSVReader;

import Star.model.Department;

public class DepartmentSearcher {

    public Department search(String year, String schoolCode, String department) {

        CSVReader reader = CSVReaderUtil.getSchoolReader(year, schoolCode);

        // 取得校系資料
        String[] data = searchByDepartment(department, reader);

        if (data == null && schoolCode.equals("013")) {
            reader = CSVReaderUtil.getSchoolReader(year, "025");
            data = searchByDepartment(department, reader);
        }

        if (data == null) return new Department("無資料", "無資料", "無資料");
        
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

    public static BriefDepartment getBriefDepartment(String schoolDepartment) {
        String[] temp = schoolDepartment.split(" ");
        String schoolCode = temp[0];
        String departmentName = temp[4];

        String[] recruits = new String[7];
        String[] ranks = new String[7];
        String[] percents = new String[7];

        for (int i = 105; i <= 111; i++) {
            CSVReader reader = CSVReaderUtil.getSchoolReader(String.valueOf(i), schoolCode);
            String[] data = searchByDepartment(departmentName, reader);

            if (i == 111) {
                ranks = data[4].split("\n");
            }

            if (data != null) {
                recruits[i - 105] = data[3];
                percents[i - 105] = data[7].split("\n")[0];
            }
        }

        return new BriefDepartment(schoolDepartment, recruits, ranks, percents);

    }

    private Department arrayToDepartment(String[] data, String year) {
        String rank, fil1, fil2;

        // 名額
        rank = String.format("%s / %s", data[2], data[3]);
        String[] subjects;
        String[] ranks = data[4].split("\n");

        if (year.equals("111")) subjects = new String[] {"國文", "英文", "數學A", "數學B", "社會", "自然", "英聽"};
        else subjects = new String[] {"國文", "英文", "數學", "社會", "自然", "英聽"};

        for (int i = 0; i < subjects.length; i++) {
            rank += String.format("\n%s\t%s", subjects[i], ranks[i]);
        }

        String[] filters = data[5].split("\n");
        String[] fil_1 = data[7].split("\n");
        fil1 = data[6] + " / " + data[2];
        for (int i = 0; i < filters.length; i++) {
            fil1 += "\n" + filters[i] + "\t" + fil_1[i];
        }
        fil1 = fil1.replace("學測數A", "學測數Ａ").replace("學測數B", "學測數Ｂ");
        
        // 有第二輪篩選
        if (!data[8].equals("--")) {
            fil2 = data[8] + " / " + data[2];
            String[] fil_2 = data[9].split("\n");
            for (int i = 0; i < filters.length; i++) {
                fil2 += "\n" + filters[i] + "\t" + fil_2[i];
            }
            fil2 = fil2.replace("學測數A", "學測數Ａ").replace("學測數B", "學測數Ｂ");
        // 無第二輪篩選
        } else {
            fil2 = "無第二輪";
        }

        return new Department(rank, fil1, fil2);
    }
}
