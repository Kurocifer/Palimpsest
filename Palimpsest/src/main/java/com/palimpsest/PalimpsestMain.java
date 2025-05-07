package com.palimpsest;

import com.palimpsest.service.TrayManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.AWTException;
import java.io.IOException;
import java.util.logging.Logger;

public class PalimpsestMain extends Application {
    private static final Logger LOGGER = Logger.getLogger(PalimpsestMain.class.getName());
    private TrayManager trayManager;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PalimpsestMain.class.getResource("palimpsest.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        scene.getStylesheets().add(getClass().getResource("palimpsest.css").toExternalForm());
        stage.setTitle("Palimpsest");
        stage.setScene(scene);

        try {
            trayManager = new TrayManager();
            LOGGER.info("Tray icon initialized successfully");
        } catch (AWTException e) {
            LOGGER.severe("Failed to initialize tray icon: " + e.getMessage());
        }

        stage.show();
    }

    @Override
    public void stop() {
        if (trayManager != null) {
            trayManager.removeTrayIcon();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}