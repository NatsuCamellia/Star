package Star.model;

import Star.filter.Filter;
import idv.natsucamellia.StarAPI.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObservableBriefDepartment {

    private final School school;
    private final Department department;
    public StringProperty schoolName;
    public StringProperty departmentName;
    public StringProperty valid;
    public StringProperty[] recruits = new StringProperty[7];
    public StringProperty[] ranks = new StringProperty[7];
    public StringProperty[] percents = new StringProperty[7];

    public String[] ranksRaw;

    public ObservableBriefDepartment(BriefDepartment briefDepartment) {
        this.school = briefDepartment.getSchool();
        this.department = briefDepartment.getDepartment();
        this.schoolName = new SimpleStringProperty(school.getName());
        this.departmentName = new SimpleStringProperty(department.getName());
        this.ranksRaw = briefDepartment.ranksRaw;
        for (int i = 0; i < 7; i++) {
            this.ranks[i] = new SimpleStringProperty(ranksRaw[i]);
        }
        for (int i = 0; i < 7; i++) {
            this.recruits[i] = new SimpleStringProperty(String.valueOf(briefDepartment.recruits[i]));
            this.percents[i] = new SimpleStringProperty(briefDepartment.percents[i]);
        }
    }

    public School getSchool() {
        return school;
    }

    public Department getDepartment() {
        return department;
    }

    public boolean validate (int[] scores) {
        String s = Filter.filter(ranksRaw, scores) ? "O" : "X";
        valid = new SimpleStringProperty(s);
        return s.equals("O");
    }

    public static boolean validate (int[] scores, BriefDepartment briefDepartment) {
        return Filter.filter(briefDepartment.ranksRaw, scores);
    }

    public FavoriteIdentifier toFavoriteIdentifier() {
        return new FavoriteIdentifier(school, department);
    }
}
