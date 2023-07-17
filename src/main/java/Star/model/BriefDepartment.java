package Star.model;

import Star.filter.Filter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BriefDepartment {

    private final School school;
    private final Department department;
    public StringProperty schoolName;
    public StringProperty departmentName;
    public StringProperty valid;
    public StringProperty[] recruits = new StringProperty[7];
    public StringProperty[] ranks = new StringProperty[7];
    public StringProperty[] percents = new StringProperty[7];

    public String[] ranksRaw;

    public BriefDepartment(School school, Department department, int[] admissionAll, String[] requirements, String[] percents) {
        this.school = school;
        this.department = department;
        this.schoolName = new SimpleStringProperty(school.getName());
        this.departmentName = new SimpleStringProperty(department.getName());
        this.ranksRaw = requirements;
        for (int i = 0; i < 7; i++) {
            this.ranks[i] = new SimpleStringProperty(requirements[i]);
        }
        for (int i = 0; i < 7; i++) {
            this.recruits[i] = new SimpleStringProperty(String.valueOf(admissionAll[i]));
            this.percents[i] = new SimpleStringProperty(percents[i]);
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

    public FavoriteIdentifier toFavoriteIdentifier() {
        return new FavoriteIdentifier(school, department);
    }
}
