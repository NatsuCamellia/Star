package Star.util;

import Star.model.BriefDepartment;
import Star.model.SchoolDepartment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {

    final static String PATH_WIN = "Favorite.dat";
    final static String PATH_MAC = "/Applications/StarTelescope.app/Contents/app/Favorite.dat";
    static String path;

    public static void initialize() {
        String os = System.getProperty("os.name");
        path = os.toLowerCase().startsWith("win") ? PATH_WIN : PATH_MAC;
    }

    public static void writeFavorite(ObservableList<BriefDepartment> favorList) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            List<SchoolDepartment> list = new ArrayList<>();
            favorList.forEach(x -> list.add(x.getSchoolDepartment()));
            out.writeObject(list);
        } catch (Exception e) {
            System.out.println("Error occurs when writing favorite!");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static ObservableList<BriefDepartment> readFavorite() {
        ObservableList<BriefDepartment> favorList = FXCollections.observableArrayList();
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<SchoolDepartment> list = (List<SchoolDepartment>)in.readObject();
            list.forEach(x -> favorList.add(DepartmentSearcher.getBriefDepartment(x)));
            return favorList;
        } catch (Exception ignored) {
            return favorList;
        }
    }

}
