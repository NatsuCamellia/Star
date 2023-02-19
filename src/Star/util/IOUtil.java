package Star.util;

import Star.model.BriefDepartment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtil {

    final static String PATH_WIN = "Favorite.dat";
    final static String PATH_MAC = "/Applications/繁星望遠鏡.app/Contents/app/Favorite.dat";
    static String path;

    public static void initialize() {
        String os = System.getProperty("os.name");
        path = os.toLowerCase().startsWith("win") ? PATH_WIN : PATH_MAC;
    }

    public static void writeFavorite(ObservableList<BriefDepartment> favorList) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            StringBuilder sb = new StringBuilder();
            favorList.forEach(b -> sb.append(b.schoolDepartment.getValue()).append(","));
            String[] data = sb.toString().split(",");
            out.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<BriefDepartment> readFavorite() {
        ObservableList<BriefDepartment> favorList = FXCollections.observableArrayList();
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            String[] data = (String[])in.readObject();
            for (String s : data) {
                favorList.add(DepartmentSearcher.getBriefDepartment(s));
            }
            return favorList;
        } catch (Exception ignored) {
            return favorList;
        }
    }

}
