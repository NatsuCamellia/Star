package Star.model;

public record Department(String rank, String fil1, String fil2) {

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
