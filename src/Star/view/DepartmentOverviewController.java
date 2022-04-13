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
    private Label rank_108, rank_109, rank_110, rank_111;
    @FXML
    private Label fil1_108, fil1_109, fil1_110, fil1_111;
    @FXML
    private Label fil2_108, fil2_109, fil2_110, fil2_111;

    private Label[] rankLabels, fil1Labels, fil2Labels;

    @FXML
    private ListView<String> favorListView;
    @FXML
    private Button addFavorButton, delFavorButton;
    @FXML
    private Label reminder;

    String[] schoolList;
    String[] departmentList;
    String selectedSchool;
    String selectedSchoolCode;
    String selectedDepartment;
    String selectedDepartmentName;
    String currentSchoolDepartment;

    final int beginYear = 108;
    final int endYear = 111;
    
    public void initialize() {
        rankLabels = new Label[]{rank_108, rank_109, rank_110, rank_111};
        fil1Labels = new Label[]{fil1_108, fil1_109, fil1_110, fil1_111};
        fil2Labels = new Label[]{fil2_108, fil2_109, fil2_110, fil2_111};

        schoolList = ListViewUtil.getSchoolList();
        schoolListView.getItems().addAll(schoolList);
        schoolListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            setSchool(schoolListView.getSelectionModel().getSelectedItem());
            refreshDepartmentList();
        });
        
        departmentListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> departmentViewSelected());

        readFavorite();
        favorListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> favorListViewSelected());
        addFavorButton.setOnAction((arg0) -> addFavorite());
        delFavorButton.setOnAction((arg0) -> deleteFavorite());
    }

    public void refreshDepartmentList() {
        departmentList = ListViewUtil.getDepartmentList(selectedSchoolCode);
        departmentListView.getItems().setAll(departmentList);
    }

    public void show() {
        DepartmentSearcher searcher = new DepartmentSearcher();
        Department department;
        for (int i = 0; i <= endYear - beginYear; i++) {
            department = searcher.search(String.valueOf(beginYear + i), selectedSchoolCode, selectedDepartmentName);
            rankLabels[i].setText(department.getRank());
            fil1Labels[i].setText(department.getFil1());
            fil2Labels[i].setText(department.getFil2());
        }

        currentSchoolDepartment = String.format("%s > %s", selectedSchool, selectedDepartment);
        reminder.setText(currentSchoolDepartment);
    }

    private void addFavorite() {
        if (currentSchoolDepartment == null) return;
        if (favorListView.getItems().contains(currentSchoolDepartment)) return;
        favorListView.getItems().add(currentSchoolDepartment);
        writeFavorite();
    }

    private void deleteFavorite() {
        if (currentSchoolDepartment == null) return;
        favorListView.getItems().remove(currentSchoolDepartment);
        writeFavorite();
    }

    private void writeFavorite() {
        try {
            String[] favorites = favorListView.getItems().toArray(new String[0]);
            FileOutputStream fileOut = new FileOutputStream("Favorite.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(favorites);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFavorite() {
        try {
            FileInputStream fileIn = new FileInputStream("Favorite.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            favorListView.getItems().addAll((String[])in.readObject());
        } catch (Exception ignored) {
        }
    }

    private void favorListViewSelected() {
        if (favorListView.getSelectionModel().getSelectedItem() == null) return;

        String[] selected = favorListView.getSelectionModel().getSelectedItem().split(" > ");
        setSchool(selected[0]);
        setDepartment(selected[1]);

        departmentListView.getSelectionModel().clearSelection();
        show();
    }

    private void departmentViewSelected() {
        if (departmentListView.getSelectionModel().getSelectedItem() == null) return;
        setSchool(schoolListView.getSelectionModel().getSelectedItem());
        setDepartment(departmentListView.getSelectionModel().getSelectedItem());

        favorListView.getSelectionModel().clearSelection();
        show();
    }

    private void setSchool(String school) {
        selectedSchool = school;
        selectedSchoolCode = selectedSchool.substring(0, 3);
    }

    private void setDepartment(String department) {
        selectedDepartment = department;
        selectedDepartmentName = selectedDepartment.substring(6);
    }
}
