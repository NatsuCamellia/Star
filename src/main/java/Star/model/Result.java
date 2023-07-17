package Star.model;

import java.util.List;

public class Result {
    private int year;
    private int admissionsAll, admissionOne, admissionTwo;
    private int quota;
    private List<RequirementElement> requirements;
    private List<FilterElement> filterOne, filterTwo;

    public Result(int year, int admissionsAll, int admissionOne, int admissionTwo, int quota,
                  List<RequirementElement> requirements,
                  List<FilterElement> filterOne,
                  List<FilterElement> filterTwo) {
        this.year = year;
        this.admissionsAll = admissionsAll;
        this.admissionOne = admissionOne;
        this.admissionTwo = admissionTwo;
        this.quota = quota;
        this.requirements = requirements;
        this.filterOne = filterOne;
        this.filterTwo = filterTwo;
    }

    public int getYear() {
        return year;
    }

    public int getAdmissionsAll() {
        return admissionsAll;
    }

    public int getAdmissionOne() {
        return admissionOne;
    }

    public int getAdmissionTwo() {
        return admissionTwo;
    }

    public int getQuota() {
        return quota;
    }

    public String getRequirementsString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s / %s", admissionsAll, quota));
        for (RequirementElement element : requirements) {
            builder.append(String.format("\n%-4s%s", element.getSubject(), element.getValue() == null ? "" : element.getValue()).replace(' ', '　'));
        }
        return builder.toString().replace(' ', '　');
    }

    public String[] getRequirementsArray() {
        String[] array = new String[requirements.size()];
        for (int i = 0; i < requirements.size(); i++) {
            array[i] = requirements.get(i).getValue();
        }
        return array;
    }

    public String getFilterOneString() {
        if (filterOne == null) {
            return "無第一輪";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s / %s", admissionOne, quota));
        for (FilterElement element : filterOne) {
            builder.append(String.format("\n%-7s%s", element.getSubject(), element.getValue() == null ? "" : element.getValue()).replace(' ', '　'));
        }
        return builder.toString().replace(' ', '　').replace('A', 'Ａ').replace('B', 'Ｂ');
    }

    public String getFilterTwoString() {
        if (admissionTwo == 0) {
            return "無第二輪";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s / %s", admissionTwo, quota));
        for (FilterElement element : filterTwo) {
            builder.append(String.format("\n%-7s%s", element.getSubject(), element.getValue() == null ? "" : element.getValue()).replace(' ', '　'));
        }
        return builder.toString().replace(' ', '　').replace('A', 'Ａ').replace('B', 'Ｂ');
    }

    public String getPercentOne() {
        return filterOne.get(0).getValue();
    }
}
