package Star.model;

public class FilterElement {
    private String subject;
    private String value;

    public FilterElement(String subject, String value) {
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
