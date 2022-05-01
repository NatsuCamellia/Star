package Star.view;

import Star.model.BriefDepartment;
import Star.model.Department;
import Star.util.DepartmentSearcher;
import Star.util.ListViewUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.*;

public class DepartmentOverviewController {

    // 總覽
    @FXML
    private ListView<String> schoolListView;
    @FXML
    private ListView<String> departmentListView;

    // 最愛清單
    @FXML
    private TableView<BriefDepartment> favorTableView;
    private ObservableList<BriefDepartment> favorList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<BriefDepartment, String> favorSchoolCell, favorDepartmentCell;

    // 單一檢視
    @FXML
    private GridPane soloView;
    @FXML
    private Label
            rank_108, rank_109, rank_110,
            rank_111, fil1_108, fil1_109, fil1_110, fil1_111,
            fil2_108, fil2_109, fil2_110, fil2_111;
    private Label[] rankLabels;
    private Label[] fil1Labels;
    private Label[] fil2Labels;


    // 多重顯示
    @FXML
    private TableView<BriefDepartment> multiView;
    @FXML
    private TableColumn<BriefDepartment, String>
            validCell,
            CNCell, ENCell, MACell, MBCell, SOCell, SCCell, ELCell,
            rec105Cell, rec106Cell, rec107Cell, rec108Cell, rec109Cell, rec110Cell, rec111Cell,
            per105Cell, per106Cell, per107Cell, per108Cell, per109Cell, per110Cell, per111Cell;

    // 導航列
    @FXML
    private Label reminder;
    String currentSchoolDepartment;

    // 單一顯示與多重顯示
    private boolean isSoloView;

    // 五標篩選
    @FXML
    private ChoiceBox<String> CNBox, ENBox, MABox, MBBox, SOBox, SCBox, ELBox;
    private ChoiceBox<String>[] scoreBoxes;

    // 篩選勾取
    @FXML
    private CheckBox filterCheckBox;
    String[] schoolList;
    String[] departmentList;
    String selectedSchool;
    String selectedSchoolCode;
    String selectedDepartment;
    String selectedDepartmentName;

    int[] scores = new int[7];

    boolean filterEnabled;

    final int BEGIN_YEAR = 108;
    final int END_YEAR = 111;
    
    public void initialize() {

        isSoloView = true;
        filterEnabled = filterCheckBox.isSelected();

        rankLabels = new Label[]{rank_108, rank_109, rank_110, rank_111};
        fil1Labels = new Label[]{fil1_108, fil1_109, fil1_110, fil1_111};
        fil2Labels = new Label[]{fil2_108, fil2_109, fil2_110, fil2_111};

        schoolList = ListViewUtil.getSchoolList();
        schoolListView.getItems().addAll(schoolList);
        schoolListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> schoolListViewSelected());
        schoolListView.getSelectionModel().select(0);

        departmentListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> departmentViewSelected());
        departmentListView.getSelectionModel().select(0);

        readFavorite();
        favorTableView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> favorTableViewSelected());
        favorTableView.setItems(favorList);
        favorSchoolCell.setCellValueFactory(b -> b.getValue().school);
        favorDepartmentCell.setCellValueFactory(b -> b.getValue().department);

        multiView.setItems(favorList);
        validCell.setCellValueFactory(b -> b.getValue().valid);
        CNCell.setCellValueFactory(b -> b.getValue().ranks[0]);
        ENCell.setCellValueFactory(b -> b.getValue().ranks[1]);
        MACell.setCellValueFactory(b -> b.getValue().ranks[2]);
        MBCell.setCellValueFactory(b -> b.getValue().ranks[3]);
        SOCell.setCellValueFactory(b -> b.getValue().ranks[4]);
        SCCell.setCellValueFactory(b -> b.getValue().ranks[5]);
        ELCell.setCellValueFactory(b -> b.getValue().ranks[6]);
        per105Cell.setCellValueFactory(b -> b.getValue().percents[0]);
        per106Cell.setCellValueFactory(b -> b.getValue().percents[1]);
        per107Cell.setCellValueFactory(b -> b.getValue().percents[2]);
        per108Cell.setCellValueFactory(b -> b.getValue().percents[3]);
        per109Cell.setCellValueFactory(b -> b.getValue().percents[4]);
        per110Cell.setCellValueFactory(b -> b.getValue().percents[5]);
        per111Cell.setCellValueFactory(b -> b.getValue().percents[6]);
        rec105Cell.setCellValueFactory(b -> b.getValue().recruits[0]);
        rec106Cell.setCellValueFactory(b -> b.getValue().recruits[1]);
        rec107Cell.setCellValueFactory(b -> b.getValue().recruits[2]);
        rec108Cell.setCellValueFactory(b -> b.getValue().recruits[3]);
        rec109Cell.setCellValueFactory(b -> b.getValue().recruits[4]);
        rec110Cell.setCellValueFactory(b -> b.getValue().recruits[5]);
        rec111Cell.setCellValueFactory(b -> b.getValue().recruits[6]);

        scoreBoxes = new ChoiceBox[]{CNBox, ENBox, MABox, MBBox, SOBox, SCBox, ELBox};
        for (int i = 0; i < scores.length - 1; i++) {
            scoreBoxes[i].getItems().setAll("無標", "底標", "後標", "均標", "前標", "頂標");
            scoreBoxes[i].getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> setScores());
            scoreBoxes[i].getSelectionModel().select(5);
        }
        ELBox.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> setScores());
        ELBox.getItems().setAll("無成績", "F", "C", "B", "A");
        ELBox.getSelectionModel().select(4);

    }

    public void schoolListViewSelected() {
        setSchool(schoolListView.getSelectionModel().getSelectedItem());
        updateDepartmentList();
    }

    private void updateDepartmentList() {
        departmentList = ListViewUtil.getDepartmentList(selectedSchoolCode, filterEnabled, scores);
        departmentListView.getItems().setAll(departmentList);
    }

    private void updateFavoritesList() {
        favorList.forEach(b -> b.validate(scores));
        multiView.refresh();
    }

    public void show() {
        DepartmentSearcher searcher = new DepartmentSearcher();
        Department department;
        for (int i = 0; i <= END_YEAR - BEGIN_YEAR; i++) {
            department = searcher.search(String.valueOf(BEGIN_YEAR + i), selectedSchoolCode, selectedDepartmentName);
            rankLabels[i].setText(department.getRank());
            fil1Labels[i].setText(department.getFil1());
            fil2Labels[i].setText(department.getFil2());
        }

        currentSchoolDepartment = String.format("%s > %s", selectedSchool, selectedDepartment);
        reminder.setText(currentSchoolDepartment);
    }

    @FXML
    private void addFavorite() {
        if (currentSchoolDepartment == null) return;
        favorList.add(DepartmentSearcher.getBriefDepartment(currentSchoolDepartment));
        writeFavorite();
    }

    @FXML
    private void deleteFavorite() {
        if (currentSchoolDepartment == null) return;
        favorList.remove(favorTableView.getSelectionModel().getSelectedItem());
        writeFavorite();
    }

    @FXML
    private void toggleDisplay() {
        soloView.setVisible(!isSoloView);
        multiView.setVisible(isSoloView);

        isSoloView = !isSoloView;
    }

    @FXML
    private void setScores() {
        for (int i = 0; i < scores.length; i++) {
            scores[i] = scoreBoxes[i].getSelectionModel().getSelectedIndex();
        }
        updateDepartmentList();
        updateFavoritesList();
    }

    @FXML
    private void updateFilter() {
        filterEnabled = filterCheckBox.isSelected();
        updateDepartmentList();
        updateFavoritesList();
    }

    private void writeFavorite() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Favorite.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            StringBuilder sb = new StringBuilder();
            favorList.forEach(b -> sb.append(b.schoolDepartment.getValue()).append(","));
            String[] data = sb.toString().split(",");
            out.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFavorite() {
        try {
            FileInputStream fileIn = new FileInputStream("Favorite.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            String[] data = (String[])in.readObject();
            favorList = FXCollections.observableArrayList();
            for (String s : data) {
                favorList.add(DepartmentSearcher.getBriefDepartment(s));
            }
        } catch (Exception ignored) {
        }
    }

    private void favorTableViewSelected() {
        BriefDepartment selectedItem = favorTableView.getSelectionModel().getSelectedItem();
        multiView.getSelectionModel().select(selectedItem);
        if (selectedItem == null) return;

        String[] selected = favorTableView.getSelectionModel().getSelectedItem().schoolDepartment.getValue().split(" > ");
        setSchool(selected[0]);
        setDepartment(selected[1]);

        departmentListView.getSelectionModel().clearSelection();
        show();
    }

    private void departmentViewSelected() {
        if (departmentListView.getSelectionModel().getSelectedItem() == null) return;
        setSchool(schoolListView.getSelectionModel().getSelectedItem());
        setDepartment(departmentListView.getSelectionModel().getSelectedItem());

        favorTableView.getSelectionModel().clearSelection();
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
