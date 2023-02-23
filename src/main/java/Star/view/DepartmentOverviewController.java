package Star.view;

import Star.MainApp;
import Star.model.BriefDepartment;
import Star.model.Department;
import Star.model.SchoolDepartment;
import Star.util.DepartmentSearcher;
import Star.util.IOUtil;
import Star.util.ListViewUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;

public class DepartmentOverviewController {

    // 總覽
    @FXML
    private ListView<String> schoolListView, departmentListView;

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
            rank_109, rank_110, rank_111, rank_112,
            fil1_109, fil1_110, fil1_111, fil1_112,
            fil2_109, fil2_110, fil2_111, fil2_112;
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
    private Label navigationLabel;

    // 單一顯示與多重顯示
    private boolean isSoloView = true;

    // 五標篩選
    @FXML
    private ChoiceBox<String> CNBox, ENBox, MABox, MBBox, SOBox, SCBox, ELBox;
    private List<ChoiceBox<String>> scoreBoxes;
    int[] scales = new int[7];
    @FXML
    private CheckBox filterCheckBox;
    boolean filterEnabled = false;

    SchoolDepartment schoolDepartment = new SchoolDepartment();
    
    public void initialize() {
        rankLabels = new Label[]{rank_109, rank_110, rank_111, rank_112};
        fil1Labels = new Label[]{fil1_109, fil1_110, fil1_111, fil1_112};
        fil2Labels = new Label[]{fil2_109, fil2_110, fil2_111, fil2_112};

        schoolListView.getItems().setAll(ListViewUtil.getSchoolList());
        schoolListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> onSchoolListSelect());
        schoolListView.getSelectionModel().select(0);

        departmentListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> onDepartmentListSelect());
        departmentListView.getSelectionModel().select(0);

        favorList = IOUtil.readFavorite();
        favorTableView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> favorTableViewSelected());
        favorTableView.setItems(favorList);
        favorSchoolCell.setCellValueFactory(b -> b.getValue().schoolName);
        favorDepartmentCell.setCellValueFactory(b -> b.getValue().departmentName);

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

        scoreBoxes = Arrays.asList(CNBox, ENBox, MABox, MBBox, SOBox, SCBox, ELBox);
        for (int i = 0; i < scales.length - 1; i++) {
            scoreBoxes.get(i).getItems().setAll("無標", "底標", "後標", "均標", "前標", "頂標");
            scoreBoxes.get(i).getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> updateFilter());
            scoreBoxes.get(i).getSelectionModel().select(5);
        }
        ELBox.getItems().setAll("無成績", "F", "C", "B", "A");
        ELBox.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> updateFilter());
        ELBox.getSelectionModel().select(4);
    }

    private void onSchoolListSelect() {
        // Update selected school
        schoolDepartment.setSchool(schoolListView.getSelectionModel().getSelectedItem());
        // Clear selected department
        schoolDepartment.setDepartment(null, null);

        updateDepartmentList();
    }

    private void onDepartmentListSelect() {
        if (departmentListView.getSelectionModel().getSelectedItem() == null) return;

        // Update selected department
        schoolDepartment.setDepartment(departmentListView.getSelectionModel().getSelectedItem());

        // Clear favorite list selection, for visual reason
        favorTableView.getSelectionModel().clearSelection();
        show();
    }

    private void favorTableViewSelected() {
        BriefDepartment selectedItem = favorTableView.getSelectionModel().getSelectedItem();
        multiView.getSelectionModel().select(selectedItem);
        if (selectedItem == null) return;
        schoolDepartment = selectedItem.getSchoolDepartment();
        departmentListView.getSelectionModel().clearSelection();
        show();
    }

    private void updateDepartmentList() {
        departmentListView.getItems().setAll(ListViewUtil.getDepartmentList(schoolDepartment, filterEnabled, scales));
    }

    private void updateFavoritesList() {
        favorList.forEach(b -> b.validate(scales));
        multiView.refresh();
    }

    @FXML
    private void updateFilter() {
        for (int i = 0; i < scales.length; i++) {
            scales[i] = scoreBoxes.get(i).getSelectionModel().getSelectedIndex();
        }
        filterEnabled = filterCheckBox.isSelected();
        String school = schoolListView.getSelectionModel().getSelectedItem();
        updateDepartmentList();
        updateFavoritesList();
    }

    private void show() {
        DepartmentSearcher searcher = new DepartmentSearcher();
        Department department;
        int startYear = MainApp.SOLO_START_YEAR;
        int endYear = MainApp.SOLO_END_YEAR;
        for (int i = 0; i <= endYear - startYear; i++) {
            department = searcher.search(String.valueOf(startYear + i), schoolDepartment);
            rankLabels[i].setText(department.getScale());
            fil1Labels[i].setText(department.getStage1());
            fil2Labels[i].setText(department.getStage2());
        }

        navigationLabel.setText(schoolDepartment.toReminderString());
    }

    @FXML
    private void addFavorite() {
        if (schoolDepartment.getDepartmentCode() == null) return;
        favorList.add(DepartmentSearcher.getBriefDepartment(schoolDepartment));
        updateFavoritesList();
        IOUtil.writeFavorite(favorList);
    }

    @FXML
    private void deleteFavorite() {
        if (schoolDepartment.getDepartmentCode() == null) return;
        favorList.remove(favorTableView.getSelectionModel().getSelectedItem());
        IOUtil.writeFavorite(favorList);
    }

    @FXML
    private void toggleDisplay() {
        soloView.setVisible(!isSoloView);
        multiView.setVisible(isSoloView);

        isSoloView = !isSoloView;
    }
}
