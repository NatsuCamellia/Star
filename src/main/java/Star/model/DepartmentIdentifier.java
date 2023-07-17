package Star.model;

public class DepartmentIdentifier {
    private String code;
    private String name;

    public DepartmentIdentifier(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return code + " " + name;
    }
}
