package Star.view;

import Star.model.Department;
import Star.util.DepartmentSearcher;
import Star.util.ListViewUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.*;

public class DepartmentOverviewController {
    
    @FXML
    private ListView<String> schoolListView;
    @FXML
    private ListView<String> departmentListView;
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

    @FXML
    private ListView<String> favorListView;
    @FXML
    private Button addFavorButton;
    @FXML
    private Button delFavorButton;
    @FXML
    private Label reminder;

    String[] schoolList;
    String[] departmentList;
    String selectedSchool;
    String selectedSchoolCode;
    String selectedDepartment;
    String selectedDepartmentName;
    String currentSchoolDepartment;
    
    public void initialize() {
        schoolList = ListViewUtil.getSchoolList();
        schoolListView.getItems().addAll(schoolList);
        schoolListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            selectedSchool = schoolListView.getSelectionModel().getSelectedItem();
            schoolSelected();
            updateDepartmentList();
        });
        
        departmentListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (departmentListView.getSelectionModel().getSelectedItem() == null) return;
            selectedDepartment = departmentListView.getSelectionModel().getSelectedItem();
            departmentSelected();

            favorListView.getSelectionModel().clearSelection();
            updateGrid();
        });

        String[] favorites = readFile();
        if (favorites != null) favorListView.getItems().addAll(favorites);
        favorListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {favorListViewSelected();});
        addFavorButton.setOnAction((arg0) -> {addFavorite();});
        delFavorButton.setOnAction((arg0) -> {deleteFavorite();});
    }

    public void updateDepartmentList() {
        departmentList = ListViewUtil.getDepartmentList(selectedSchoolCode);
        departmentListView.getItems().setAll(departmentList);
    }

    public void updateGrid() {
        try {
            DepartmentSearcher searcher = new DepartmentSearcher();
            Department[] departments = new Department[4];
            for (int year = 108; year <= 111; year++) {
                departments[year - 108] = searcher.search(String.valueOf(year), selectedSchoolCode, selectedDepartmentName);
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

            reminder.setText(String.format("%s > %s", selectedSchool, selectedDepartment));
            currentSchoolDepartment = selectedSchool + " > " +  selectedDepartment;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFavorite() {
        if (currentSchoolDepartment == null) return;
        if (favorListView.getItems().contains(currentSchoolDepartment)) return;
        favorListView.getItems().add(currentSchoolDepartment);
        writeFile();
    }

    private void deleteFavorite() {
        if (currentSchoolDepartment == null) return;
        if (favorListView.getItems().contains(currentSchoolDepartment)) {
            favorListView.getItems().remove(currentSchoolDepartment);
        }
        writeFile();
    }

    private void writeFile() {
        try {
            String[] favorites = favorListView.getItems().toArray(new String[0]);
            FileOutputStream fileOut = new FileOutputStream("Favorite.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(favorites);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] readFile() {
        try {
            FileInputStream fileIn = new FileInputStream("Favorite.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            return (String[]) in.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    private void favorListViewSelected() {
        if (favorListView.getSelectionModel().getSelectedItem() == null) return;

        String[] selected = favorListView.getSelectionModel().getSelectedItem().split(" > ");
        selectedSchool = selected[0];
        schoolSelected();
        selectedDepartment = selected[1];
        departmentSelected();

        departmentListView.getSelectionModel().clearSelection();
        updateGrid();
    }

    private void schoolSelected() {
        selectedSchoolCode = selectedSchool.substring(0, 3);
    }

    private void departmentSelected() {
        selectedDepartmentName = selectedDepartment.substring(6);
    }
}
