package Star.view;

import Star.model.Department;
import Star.util.DepartmentSearcher;
import Star.util.ListViewUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DepartmentOverviewController {
    
    @FXML
    private ListView<String> schoolListView;
    @FXML
    private ListView<String> codeListView;
    @FXML
    private Label rank_108;
    @FXML
    private Label rank_109;
    @FXML
    private Label rank_110;
    @FXML
    private Label rank_111;
    @FXML
    private Label fil1_108;
    @FXML
    private Label fil1_109;
    @FXML
    private Label fil1_110;
    @FXML
    private Label fil1_111;
    @FXML
    private Label fil2_108;
    @FXML
    private Label fil2_109;
    @FXML
    private Label fil2_110;
    @FXML
    private Label fil2_111;

    String[] schoolList;
    String[] codeList;
    String selectedSchool;
    String selectedDepartment;
    
    public void initialize() {
        schoolList = ListViewUtil.getSchoolList();
        schoolListView.getItems().addAll(schoolList);
        schoolListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            selectedSchool = schoolListView.getSelectionModel().getSelectedItem().substring(0, 3);
            updateCodeList(selectedSchool);
        });
        
        codeListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            selectedDepartment = codeListView.getSelectionModel().getSelectedItem();
            if (selectedDepartment == null) return;
            updateGrid(selectedSchool, selectedDepartment);
        });
    }

    public void updateCodeList(String school) {
        
        codeList = ListViewUtil.getDepartmentList(school);
        codeListView.getItems().setAll(codeList);
    }

    @FXML
    public void updateGrid(String school, String name) {
        try {
            String department = name.substring(6);
            
            DepartmentSearcher searcher = new DepartmentSearcher();
            Department[] departments = new Department[4];
            for (int year = 108; year <= 111; year++) {
                departments[year - 108] = searcher.search(String.valueOf(year), school, department);
            }
            
            rank_108.setText(departments[0].getRank());
            rank_109.setText(departments[1].getRank());
            rank_110.setText(departments[2].getRank());
            rank_111.setText(departments[3].getRank());
            fil1_108.setText(departments[0].getFil1());
            fil1_109.setText(departments[1].getFil1());
            fil1_110.setText(departments[2].getFil1());
            fil1_111.setText(departments[3].getFil1());
            fil2_108.setText(departments[0].getFil2());
            fil2_109.setText(departments[1].getFil2());
            fil2_110.setText(departments[2].getFil2());
            fil2_111.setText(departments[3].getFil2());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
