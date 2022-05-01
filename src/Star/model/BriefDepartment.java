package Star.model;

import Star.util.FilterUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BriefDepartment {

    public StringProperty schoolDepartment;
    public StringProperty school;
    public StringProperty department;
    public StringProperty valid;
    public StringProperty[] recruits = new StringProperty[7];
    public StringProperty[] ranks = new StringProperty[7];
    public StringProperty[] percents = new StringProperty[7];

    public String[] ranksRaw;

    public BriefDepartment(String schoolDepartment, String[] recruits, String[] ranks, String[] percents) {
        this.schoolDepartment = new SimpleStringProperty(schoolDepartment);
        String[] split = schoolDepartment.split(" ");
        this.school = new SimpleStringProperty(split[1]);
        this.department = new SimpleStringProperty(split[4]);
        this.ranksRaw = ranks;
        for (int i = 0; i < 7; i++) {
            this.ranks[i] = new SimpleStringProperty(ranks[i]);
        }
        for (int i = 0; i < 7; i++) {
            this.recruits[i] = new SimpleStringProperty(recruits[i]);
            this.percents[i] = new SimpleStringProperty(percents[i]);
        }
    }

    public void validate (int[] scores) {
        String s = FilterUtil.filter(ranksRaw, scores) ? "O" : "X";
        valid = new SimpleStringProperty(s);
    }
}
