package Star.util;

import Star.model.BriefDepartment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtil {

    public static void writeFavorite(ObservableList<BriefDepartment> favorList) {
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

    public static ObservableList<BriefDepartment> readFavorite() {
        ObservableList<BriefDepartment> favorList = FXCollections.observableArrayList();
        try {
            FileInputStream fileIn = new FileInputStream("Favorite.dat");
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
