package Star.model;

import Star.util.FilterUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BriefDepartment {

    private final SchoolDepartment schoolDepartment;
    public StringProperty schoolName;
    public StringProperty departmentName;
    public StringProperty valid;
    public StringProperty[] recruits = new StringProperty[7];
    public StringProperty[] ranks = new StringProperty[7];
    public StringProperty[] percents = new StringProperty[7];

    public String[] ranksRaw;

    public BriefDepartment(SchoolDepartment schoolDepartment, String[] recruits, String[] ranks, String[] percents) {
        this.schoolDepartment = schoolDepartment;
        this.schoolName = new SimpleStringProperty(schoolDepartment.getSchoolName());
        this.departmentName = new SimpleStringProperty(schoolDepartment.getDepartmentName());
        this.ranksRaw = ranks;
        for (int i = 0; i < 7; i++) {
            this.ranks[i] = new SimpleStringProperty(ranks[i]);
        }
        for (int i = 0; i < 7; i++) {
            this.recruits[i] = new SimpleStringProperty(recruits[i]);
            this.percents[i] = new SimpleStringProperty(percents[i]);
        }
    }

    public SchoolDepartment getSchoolDepartment() {
        return schoolDepartment;
    }

    public void validate (int[] scores) {
        String s = FilterUtil.filter(ranksRaw, scores) ? "O" : "X";
        valid = new SimpleStringProperty(s);
    }
}
