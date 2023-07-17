package Star.io;

import Star.StarTelescope;
import Star.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private final Gson gson;
    private List<School> schoolList;
    public DataReader() {
        this.gson = new Gson();
    }

    public List<School> getSchoolList() {
        try {
            InputStream is = StarTelescope.class.getResourceAsStream("/json/schools.json");
            assert is != null;
            Reader reader = new InputStreamReader(is);
            schoolList = gson.fromJson(reader, new TypeToken<List<School>>(){}.getType());
            reader.close();
            return schoolList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private School getSchoolFromCode(String code) {
        for (School s : schoolList) {
            if (s.getCode().equals(code)) return s;
        }

        return null;
    }

    public Department getDepartmentFromIdentifier(DepartmentIdentifier identifier) {
        try {
            String schoolCode = identifier.getCode().substring(0, 3);
            String path = String.format("/json/%s/%s.json", schoolCode, identifier.toString().replace('/', '-'));
            InputStream is = StarTelescope.class.getResourceAsStream(path);
            assert is != null;
            Reader reader = new InputStreamReader(is);

            Department department = gson.fromJson(reader, Department.class);
            reader.close();
            return department;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BriefDepartment getBriefDepartment(School school, Department department) {

        int startYear = StarTelescope.MULTI_START_YEAR;
        int endYear = StarTelescope.MULTI_END_YEAR;
        int length = endYear - startYear + 1;

        String[] requirements = new String[length];
        String[] percents = new String[length];
        int[] admissionAll = new int[length];
        for (int year = startYear; year <= endYear; year++) {
            Result result = department.getResultOfYear(year);
            if (result == null) continue;

            int index = year - startYear;
            percents[index] = result.getPercentOne();
            admissionAll[index] = result.getAdmissionsAll();
        }

        requirements = department.getResultOfYear(StarTelescope.SCALE_YEAR).getRequirementsArray();

        return new BriefDepartment(school, department, admissionAll, requirements, percents);
    }

    public ObservableList<BriefDepartment> getFavoriteList() {
        ObservableList<BriefDepartment> favoriteList = FXCollections.observableArrayList();
        try {
            Reader reader = new FileReader(StarTelescope.FAVORITE_PATH);
            List<FavoriteIdentifier> identifierList
                    = gson.fromJson(reader, new TypeToken<ArrayList<FavoriteIdentifier>>() {
            }.getType());

            for (FavoriteIdentifier identifier : identifierList) {
                School school = getSchoolFromCode(identifier.getSchoolCode());
                if (school == null) continue;

                Department department = getDepartmentFromIdentifier(
                        new DepartmentIdentifier(identifier.getDepartmentCode(), identifier.getDepartmentName()));
                BriefDepartment briefDepartment = getBriefDepartment(school, department);
                favoriteList.add(briefDepartment);
            }
        } catch (FileNotFoundException ignored) {
            System.out.println("File not exist.");
        } catch (Exception e) {
            System.out.println("Error occurs when reading favorite");
            e.printStackTrace();
        }

        return favoriteList;
    }
}
