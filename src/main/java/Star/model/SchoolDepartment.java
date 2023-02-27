package Star.model;

import java.io.Serializable;

public class SchoolDepartment implements Serializable {
    private String schoolCode;
    private String schoolName;
    private String departmentCode;
    private String departmentName;


    public String getSchoolCode() {
        return schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setSchool(String school) {
        String[] codeName = school.split(" ");
        setSchool(codeName[0], codeName[1]);
    }

    public void setSchool(String code, String name) {
        schoolCode = code;
        schoolName = name;
    }

    public void setDepartment(String department) {
        String[] codeName = department.split(" ");
        setDepartment(codeName[0], codeName[1]);
    }

    public void setDepartment(String code, String name) {
        departmentCode = code;
        departmentName = name;
    }

    public static SchoolDepartment copyOf(SchoolDepartment schoolDepartment) {
        SchoolDepartment toReturn = new SchoolDepartment();
        toReturn.setSchool(schoolDepartment.getSchoolCode(), schoolDepartment.getSchoolName());
        toReturn.setDepartment(schoolDepartment.getDepartmentCode(), schoolDepartment.getDepartmentName());
        return toReturn;
    }

    public String toReminderString() {
        return String.format("%s %s > %s", departmentCode, schoolName, departmentName);
    }
}
