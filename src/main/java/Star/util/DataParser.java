package Star.util;

import Star.model.Department;

public class DataParser {
    public static Department parse(String[] data, String year) {
        String scale, stage1, stage2;
        StringBuilder builder;

        String recruit = data[2];
        String recruitMax = data[3];
        String recruitStage1 = data[6];
        String recruitStage2 = data[8];
        String[] scales = data[4].split("\n");
        String[] filters = data[5].split("\n");
        String[] resultStage1 = data[7].split("\n");
        String[] resultStage2 = data[9].split("\n");

        // Subjects
        String[] subjects;

        // Math A, B is added since 111
        if (Integer.parseInt(year) >= 111)
            subjects = new String[] {"國文", "英文", "數學Ａ", "數學Ｂ", "社會", "自然", "英聽"};
        else
            subjects = new String[] {"國文", "英文", "數學", "社會", "自然", "英聽"};


        // 1. Scale requirement
        builder = new StringBuilder();
        builder.append(String.format("%s / %s", recruit, recruitMax));
        for (int i = 0; i < subjects.length; i++) {
            builder.append(String.format("\n%-4s%s", subjects[i], scales[i]));
        }
        scale = builder.toString().replace(' ', '　');

        // 2. Stage 1
        stage1 = getStage(recruitMax, recruitStage1, filters, resultStage1);

        // 3. Stage 2
        // Not published yet
        if (year.equals("112")) {
            stage2 = stage1;
        // Recruited some students
        } else if (!data[8].equals("--")) {
            stage2 = getStage(recruitMax, recruitStage2, filters, resultStage2);
            // No stage 2
        } else {
            stage2 = "無第二輪";
        }

        return new Department(scale, stage1, stage2);
    }

    private static String getStage(String recruitMax, String stageRecruit, String[] filters, String[] stageResult) {
        StringBuilder builder;
        String stage2;
        builder = new StringBuilder();
        builder.append(String.format("%s / %s", stageRecruit, recruitMax));
        for (int i = 0; i < filters.length; i++) {
            builder.append(String.format("\n%-7s%s", filters[i], stageResult[i]));
        }
        stage2 = builder.toString().replace("A", "Ａ").replace("B", "Ｂ").replace(' ', '　');
        return stage2;
    }
}
