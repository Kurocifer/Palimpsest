package com.palimpsest;

import com.palimpsest.service.TrayManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.AWTException;
import java.io.IOException;
import java.util.logging.Logger;

public class PalimpsestMain extends Application {
    private static final Logger LOGGER = Logger.getLogger(PalimpsestMain.class.getName());
    private TrayManager trayManager;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        Platform.setImplicitExit(false); // Prevent app exit when window closes
        FXMLLoader fxmlLoader = new FXMLLoader(PalimpsestMain.class.getResource("palimpsest.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        scene.getStylesheets().add(getClass().getResource("palimpsest.css").toExternalForm());
        stage.setTitle("Palimpsest");
        stage.setScene(scene);

        // Hide stage on close instead of exiting
        stage.setOnCloseRequest(event -> {
            event.consume();
            LOGGER.info("Close button clicked - minimizing to tray");
            stage.hide();
        });

        try {
            // Callback to restore UI
            Runnable restoreAction = () -> {
                LOGGER.info("Restoring UI");
                Platform.runLater(() -> {
                    if (!primaryStage.isShowing()) {
                        primaryStage.show();
                        primaryStage.toFront();
                        LOGGER.info("UI restored successfully");
                    } else {
                        LOGGER.info("UI already visible");
                    }
                });
            };
            trayManager = new TrayManager(Platform::exit, restoreAction);
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