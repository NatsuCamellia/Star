package Star.model;

import Star.io.DataReader;

import java.util.ArrayList;
import java.util.List;

public class School {
    private String code;
    private String name;
    private List<DepartmentIdentifier> departments;

    public School(String code, String name, List<DepartmentIdentifier> departments) {
        this.code = code;
        this.name = name;
        this.departments = departments;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<DepartmentIdentifier> getDepartments() {
        return departments;
    }

    public List<DepartmentIdentifier> getDepartmentsWithFilter(int[] scales) {
        List<DepartmentIdentifier> toReturn = new ArrayList<>();
        DataReader dataReader = new DataReader();
        for (DepartmentIdentifier identifier : departments) {
            Department department = dataReader.getDepartmentFromIdentifier(identifier);
            BriefDepartment briefDepartment = dataReader.getBriefDepartment(this, department);
            if (briefDepartment.validate(scales)) {
                toReturn.add(identifier);
            }
        }

        return toReturn;
    }

    public String toString() {
        return code + " " + name;
    }
}
