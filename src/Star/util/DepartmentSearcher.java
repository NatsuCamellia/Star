package Star.util;

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
    private String[] searchByDepartment(String department, CSVReader reader) {
        
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

    private Department arrayToDepartment(String[] data, String year) {
        String rank, fil1, fil2;

        // 名額
        rank = String.format("%s / %s\n", data[2], data[3]);
        // 檢定門檻
        String[] ranks = data[4].split("\n");
        String rank_template;
        if (year.equals("111")) {
            rank_template = """
                    國文\t%s
                    英文\t%s
                    數學A\t%s
                    數學B\t%s
                    自然\t%s
                    社會\t%s
                    英聽\t%s""";
            rank += String.format(rank_template, ranks[0], ranks[1], ranks[2], ranks[3], ranks[4], ranks[5], ranks[6]);
        } else {
            rank_template = """
                    國文\t%s
                    英文\t%s
                    數學\t%s
                    自然\t%s
                    社會\t%s
                    英聽\t%s""";
            rank += String.format(rank_template, ranks[0], ranks[1], ranks[2], ranks[3], ranks[4], ranks[6]);
        }
        
        // 篩選比序順序
        String[] filters = data[5].split("\n");
        // 第一輪篩選
        String[] fil_1 = data[7].split("\n");
        // 名額(第一輪錄取 / 錄取)
        fil1 = data[6] + " / " + data[2];
        for (int i = 0; i < filters.length; i++) {
            fil1 += "\n" + filters[i] + "\t" + fil_1[i];
        }
        fil1 = fil1.replace("學測數A", "學測數Ａ").replace("學測數B", "學測數Ｂ");
        
        // 有第二輪篩選
        if (!data[8].equals("--")) {
            // 名額(第二輪錄取 / 錄取)
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
