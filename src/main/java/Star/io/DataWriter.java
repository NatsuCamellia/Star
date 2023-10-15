package Star.io;

import Star.model.ObservableBriefDepartment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import idv.natsucamellia.StarAPI.model.FavoriteIdentifier;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DataWriter {
    private final String PATH = "favorite.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void writeFavorite(ObservableList<ObservableBriefDepartment> favorList) {
        // Get identifier list
        List<FavoriteIdentifier> identifierList = favorList.stream().map(x -> x.toFavoriteIdentifier()).toList();

        try {
            Writer writer = new FileWriter(PATH, StandardCharsets.UTF_8);
            gson.toJson(identifierList, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error occurs when writing favorite!");
            e.printStackTrace();
        }

    }
}
