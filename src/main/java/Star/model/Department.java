package Star.model;

import java.util.List;

public class Department {
    private String code;
    private String name;
    private List<Result> results;

    public Department(String code, String name, List<Result> results) {
        this.code = code;
        this.name = name;
        this.results = results;
    }

    public Department() {

    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<Result> getResults() {
        return results;
    }

    public Result getResultOfYear(int year) {
        for (Result result : results) {
            if (result.getYear() == year) return result;
        }

        return null;
    }

    public DepartmentIdentifier getIdentifier() {
        return new DepartmentIdentifier(code, name);
    }
}
