package Star.model;

public class RequirementElement {
    private String subject;
    private String value;

    public RequirementElement(String subject, String value) {
        this.subject = subject;
        this.value = value;
    }

    public String getSubject() {
        return subject;
    }

    public String getValue() {
        return value;
    }
}
