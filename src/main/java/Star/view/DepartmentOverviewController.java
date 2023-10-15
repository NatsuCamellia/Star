package Star.view;

import Star.StarTelescope;
import Star.model.ObservableBriefDepartment;
import Star.io.*;
import idv.natsucamellia.StarAPI.core.StarAPI;
import idv.natsucamellia.StarAPI.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepartmentOverviewController {

    // 總覽
    @FXML
    private ListView<DepartmentIdentifier> departmentListView;

    @FXML
    private ListView<School> schoolListView;
    // 最愛清單
    @FXML
    private TableView<ObservableBriefDepartment> favorTableView;
    private ObservableList<ObservableBriefDepartment> observableFavorList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<ObservableBriefDepartment, String> favorSchoolCell, favorDepartmentCell;

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
    private TableView<ObservableBriefDepartment> multiView;
    @FXML
    private TableColumn<ObservableBriefDepartment, String>
            validCell,
            CNCell, ENCell, MACell, MBCell, SOCell, SCCell, ELCell,
            rec106Cell, rec107Cell, rec108Cell, rec109Cell, rec110Cell, rec111Cell, rec112Cell,
            per106Cell, per107Cell, per108Cell, per109Cell, per110Cell, per111Cell, per112Cell;

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

    // Fields
    StarAPI starAPI = new StarAPI(
            StarTelescope.SOLO_START_YEAR,
            StarTelescope.MULTI_END_YEAR,
            StarTelescope.MULTI_START_YEAR,
            StarTelescope.SOLO_END_YEAR);
    DataWriter dataWriter = new DataWriter();
    
    public void initialize() {
        // Solo view
        rankLabels = new Label[]{rank_109, rank_110, rank_111, rank_112};
        fil1Labels = new Label[]{fil1_109, fil1_110, fil1_111, fil1_112};
        fil2Labels = new Label[]{fil2_109, fil2_110, fil2_111, fil2_112};

        // School List
        schoolListView.getItems().setAll(starAPI.getSchoolList());
        schoolListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> onSchoolListSelect());
        schoolListView.getSelectionModel().select(0);

        // Department List
        departmentListView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> onDepartmentListSelect());
        departmentListView.getSelectionModel().select(0);

        // Favorite List
        List<BriefDepartment> favorList = starAPI.getFavoriteList(new File(StarTelescope.FAVORITE_PATH));
        observableFavorList = FXCollections.observableList(favorList.stream().map(ObservableBriefDepartment::new).toList());
        favorTableView.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> favorTableViewSelected());
        favorTableView.setItems(observableFavorList);
        favorSchoolCell.setCellValueFactory(b -> b.getValue().schoolName);
        favorDepartmentCell.setCellValueFactory(b -> b.getValue().departmentName);

        // Multi view
        multiView.setItems(observableFavorList);
        validCell.setCellValueFactory(b -> b.getValue().valid);
        CNCell.setCellValueFactory(b -> b.getValue().ranks[0]);
        ENCell.setCellValueFactory(b -> b.getValue().ranks[1]);
        MACell.setCellValueFactory(b -> b.getValue().ranks[2]);
        MBCell.setCellValueFactory(b -> b.getValue().ranks[3]);
        SOCell.setCellValueFactory(b -> b.getValue().ranks[4]);
        SCCell.setCellValueFactory(b -> b.getValue().ranks[5]);
        ELCell.setCellValueFactory(b -> b.getValue().ranks[6]);
        per106Cell.setCellValueFactory(b -> b.getValue().percents[0]);
        per107Cell.setCellValueFactory(b -> b.getValue().percents[1]);
        per108Cell.setCellValueFactory(b -> b.getValue().percents[2]);
        per109Cell.setCellValueFactory(b -> b.getValue().percents[3]);
        per110Cell.setCellValueFactory(b -> b.getValue().percents[4]);
        per111Cell.setCellValueFactory(b -> b.getValue().percents[5]);
        per112Cell.setCellValueFactory(b -> b.getValue().percents[6]);
        rec106Cell.setCellValueFactory(b -> b.getValue().recruits[0]);
        rec107Cell.setCellValueFactory(b -> b.getValue().recruits[1]);
        rec108Cell.setCellValueFactory(b -> b.getValue().recruits[2]);
        rec109Cell.setCellValueFactory(b -> b.getValue().recruits[3]);
        rec110Cell.setCellValueFactory(b -> b.getValue().recruits[4]);
        rec111Cell.setCellValueFactory(b -> b.getValue().recruits[5]);
        rec112Cell.setCellValueFactory(b -> b.getValue().recruits[6]);

        scoreBoxes = Arrays.asList(CNBox, ENBox, MABox, MBBox, SOBox, SCBox, ELBox);
        // Primary subjects
        for (int i = 0; i < scales.length - 1; i++) {
            scoreBoxes.get(i).getItems().setAll("頂標", "前標", "均標", "後標", "底標", "無標");
            scoreBoxes.get(i).getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> updateFilter());
            scoreBoxes.get(i).getSelectionModel().select(0);
        }
        // English listening
        ELBox.getItems().setAll("A", "B", "C", "F", "無成績");
        ELBox.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> updateFilter());
        ELBox.getSelectionModel().select(0);
    }

    private void onSchoolListSelect() {
        School school = schoolListView.getSelectionModel().getSelectedItem();
        refreshDepartmentList();
    }

    private void onDepartmentListSelect() {
        if (departmentListView.getSelectionModel().getSelectedItem() == null) return;

        School school = schoolListView.getSelectionModel().getSelectedItem();
        DepartmentIdentifier identifier = departmentListView.getSelectionModel().getSelectedItem();
        Department department = starAPI.getDepartment(identifier);

        // Clear favorite list selection, for visual reason
        favorTableView.getSelectionModel().clearSelection();
        show(school, department);
    }

    private void favorTableViewSelected() {
        ObservableBriefDepartment selectedItem = favorTableView.getSelectionModel().getSelectedItem();
        multiView.getSelectionModel().select(selectedItem);
        if (selectedItem == null) return;

        School school = selectedItem.getSchool();
        Department department = selectedItem.getDepartment();
        departmentListView.getSelectionModel().clearSelection();
        show(school, department);
    }

    private void updateFavoritesList() {
        observableFavorList.forEach(b -> b.validate(scales));
        multiView.refresh();
    }

    @FXML
    private void updateFilter() {
        for (int i = 0; i < scales.length; i++) {
            scales[i] = 5 - scoreBoxes.get(i).getSelectionModel().getSelectedIndex();
        }
        filterEnabled = filterCheckBox.isSelected();
        refreshDepartmentList();
        updateFavoritesList();
    }

    private List<DepartmentIdentifier> getDepartmentsWithFilter(int[] scales, School school) {
        List<DepartmentIdentifier> departments = school.getDepartmentIdentifiers();
        List<DepartmentIdentifier> toReturn = new ArrayList<>();
        for (DepartmentIdentifier identifier : departments) {
            Department department = starAPI.getDepartment(identifier);
            BriefDepartment briefDepartment = starAPI.getBriefDepartment(school, department);
            if (ObservableBriefDepartment.validate(scales, briefDepartment)) {
                toReturn.add(identifier);
            }
        }

        return toReturn;
    }

    private void refreshDepartmentList() {
        if (filterEnabled) {
            departmentListView.getItems().setAll(getDepartmentsWithFilter(scales, getSelectedSchool()));
        } else {
            departmentListView.getItems().setAll(getSelectedSchool().getDepartmentIdentifiers());
        }
    }

    private void show(School school, Department department) {
        int startYear = StarTelescope.SOLO_START_YEAR;
        int endYear = StarTelescope.SOLO_END_YEAR;
        for (int year = startYear; year <= endYear; year++) {
            Result result = department.getResultOfYear(year);
            if (result == null) continue;
            rankLabels[year - startYear].setText(result.getRequirementsString());
            fil1Labels[year - startYear].setText(result.getFilterOneString());
            fil2Labels[year - startYear].setText(result.getFilterTwoString());
        }

        // Navigation bar
        DepartmentIdentifier departmentIdentifier = department.getIdentifier();
        navigationLabel.setText(school.toString() + " > " + departmentIdentifier.toString());
    }

    @FXML
    private void addFavorite() {
        if (getSelectedDepartment() == null) return;
        School school = getSelectedSchool();
        Department department = getSelectedDepartment();
        observableFavorList.add(new ObservableBriefDepartment(starAPI.getBriefDepartment(school, department)));
        updateFavoritesList();
        dataWriter.writeFavorite(observableFavorList);
    }

    @FXML
    private void deleteFavorite() {
        observableFavorList.remove(favorTableView.getSelectionModel().getSelectedItem());
        dataWriter.writeFavorite(observableFavorList);
    }

    @FXML
    private void toggleDisplay() {
        isSoloView = !isSoloView;

        soloView.setVisible(isSoloView);
        multiView.setVisible(!isSoloView);
    }

    private School getSelectedSchool() {
        return schoolListView.getSelectionModel().getSelectedItem();
    }

    private Department getSelectedDepartment() {
        DepartmentIdentifier identifier = departmentListView.getSelectionModel().getSelectedItem();
        if (identifier == null) return null;
        return starAPI.getDepartment(identifier);
    }
}
