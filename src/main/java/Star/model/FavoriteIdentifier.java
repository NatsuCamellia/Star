package Star.model;

public class FavoriteIdentifier {
    private final String schoolCode, schoolName, departmentCode, departmentName;

    public FavoriteIdentifier(School school, Department department) {
        this.schoolCode = school.getCode();
        this.schoolName = school.getName();
        this.departmentCode = department.getCode();
        this.departmentName = department.getName();
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
