package com.palimpsest.service;

import java.awt.*;
import java.util.logging.Logger;

public class TrayManager {
    private static final Logger LOGGER = Logger.getLogger(TrayManager.class.getName());
    private final SystemTray systemTray;
    private final TrayIcon trayIcon;

    public TrayManager(Runnable quitAction, Runnable restoreAction) throws AWTException {
        if (!SystemTray.isSupported()) {
            throw new AWTException("System tray is not supported on this platform");
        }
        systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage(
                TrayManager.class.getResource("/com/palimpsest/images/quill.png")
        );
        if (image == null) {
            LOGGER.severe("Failed to load quill.png - resource not found");
            throw new AWTException("Image resource not found");
        }
        trayIcon = new TrayIcon(image, "Palimpsest");
        trayIcon.setImageAutoSize(true);

        // Create popup menu (right-click)
        PopupMenu popup = new PopupMenu();
        MenuItem restoreItem = new MenuItem("Restore");
        MenuItem pauseItem = new MenuItem("Pause");
        MenuItem resumeItem = new MenuItem("Resume");
        MenuItem quitItem = new MenuItem("Quit");

        restoreItem.addActionListener(e -> {
            LOGGER.info("Restore clicked - triggering restore action");
            restoreAction.run();
        });

        pauseItem.addActionListener(e -> LOGGER.info("Pause clicked - placeholder"));
        resumeItem.addActionListener(e -> LOGGER.info("Resume clicked - placeholder"));
        quitItem.addActionListener(e -> {
            LOGGER.info("Quit clicked - exiting app");
            quitAction.run();
        });

        popup.add(restoreItem);
        popup.add(pauseItem);
        popup.add(resumeItem);
        popup.addSeparator();
        popup.add(quitItem);
        trayIcon.setPopupMenu(popup);

        systemTray.add(trayIcon);
    }

    public void removeTrayIcon() {
        systemTray.remove(trayIcon);
    }
}