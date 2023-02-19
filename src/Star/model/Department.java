package Star.model;

public class Department {

    private final String rank;
    private final String fil1;
    private final String fil2;

    public Department(String rank, String fil1, String fil2) {
        this.rank = rank;
        this.fil1 = fil1;
        this.fil2 = fil2;
    }

    public Department() {
        this.rank = "無資料";
        this.fil1 = "無資料";
        this.fil2 = "無資料";
    }

    public String getRank() {
        return rank;
    }

    public String getFil1() {
        return fil1;
    }

    public String getFil2() {
        return fil2;
    }


}
