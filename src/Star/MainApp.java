package Star;

import Star.util.IOUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    // Global settings
    public static final int SCALE_YEAR = 112;
    public static final int MULTI_START_YEAR = 105;
    public static final int MULTI_END_YEAR = 111;
    public static final int SOLO_START_YEAR = 108;
    public static final int SOLO_END_YEAR = 111;
    
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {     
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("繁星望遠鏡 v1.2.3");
        this.primaryStage.setResizable(true);
        Image icon = new Image(Objects.requireNonNull(MainApp.class.getResourceAsStream("images/icon.png")));
        this.primaryStage.getIcons().add(icon);

        IOUtil.initialize();
        initRootLayout();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MainApp.class.getResource("view/DepartmentOverview.fxml"));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
