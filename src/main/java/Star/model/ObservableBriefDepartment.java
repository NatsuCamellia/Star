package Star.model;

import Star.StarTelescope;
import idv.natsucamellia.StarAPI.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class ObservableBriefDepartment {

    private final School school;
    private final Department department;
    public StringProperty schoolName;
    public StringProperty departmentName;
    public StringProperty valid;
    private final Map<Integer, Integer> admissions = new HashMap<>();
    private final Map<Integer, Integer> percents = new HashMap<>();
    private final Map<Subject, Scale> scales = new HashMap<>();


    public ObservableBriefDepartment(BriefDepartment briefDepartment) {
        this.school = briefDepartment.getSchool();
        this.department = briefDepartment.getDepartment();
        this.schoolName = new SimpleStringProperty(school.getName());
        this.departmentName = new SimpleStringProperty(department.getName());
        for (int year = StarTelescope.MULTI_START_YEAR; year <= StarTelescope.MULTI_END_YEAR; year++) {
            admissions.put(year, briefDepartment.getRecruitOfYear(year));
            percents.put(year, briefDepartment.getPercentOfYear(year));
        }
        for (Subject subject : Subject.values()) {
            Scale scale = briefDepartment.getScale(subject);
            if (scale != Scale.NONE) scales.put(subject, scale);
        }
    }

    public School getSchool() {
        return school;
    }

    public Department getDepartment() {
        return department;
    }

    public void validate (Map<Subject, Scale> userScales) {
        boolean pass = true;

        for (Subject subject : userScales.keySet()) {
            Scale scale = scales.get(subject);
            if (scale == null) scale = Scale.NONE;
            Scale userScale = userScales.get(subject);
            if (userScale == null) userScale = Scale.NONE;

            if (scale.getScore() > userScale.getScore()) {
                pass = false;
                break;
            }
        }

        valid = new SimpleStringProperty(pass ? "O" : "X");
    }

    public StringProperty getPercentOfYear(int year) {
        return new SimpleStringProperty(percents.get(year).toString() + '%');
    }

    public StringProperty getAdmissionsOfYear(int year) {
        return new SimpleStringProperty(admissions.get(year).toString());
    }

    public StringProperty getScaleProperty(Subject subject) {
        Scale scale = scales.get(subject);
        return scale == null ? new SimpleStringProperty() : new SimpleStringProperty(scale.toString());
    }

    public FavoriteIdentifier toFavoriteIdentifier() {
        return new FavoriteIdentifier(school, department);
    }
}
