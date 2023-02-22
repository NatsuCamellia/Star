package Star.model;

public class Department {

    private final String scale;
    private final String stage1;
    private final String stage2;

    public Department(String scale, String stage1, String stage2) {
        this.scale = scale;
        this.stage1 = stage1;
        this.stage2 = stage2;
    }

    public Department() {
        this.scale = "無資料";
        this.stage1 = "無資料";
        this.stage2 = "無資料";
    }

    public String getScale() {
        return scale;
    }

    public String getStage1() {
        return stage1;
    }

    public String getStage2() {
        return stage2;
    }


}
