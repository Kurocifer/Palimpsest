package com.palimpsest.service;

import java.awt.*;
import java.util.logging.Logger;

public class TrayManager {
    private static final Logger LOGGER = Logger.getLogger(TrayManager.class.getName());
    private final SystemTray systemTray;
    private final TrayIcon trayIcon;
    private final Image activeIcon;
    private final Image pausedIcon;
    private boolean isPaused;

    public TrayManager(Runnable quitAction, Runnable restoreAction) throws AWTException {
        if (!SystemTray.isSupported()) {
            throw new AWTException("System tray is not supported on this platform");
        }
        systemTray = SystemTray.getSystemTray();
        activeIcon = Toolkit.getDefaultToolkit().getImage(
                TrayManager.class.getResource("/com/palimpsest/images/quill.png")
        );
        pausedIcon = Toolkit.getDefaultToolkit().getImage(
                TrayManager.class.getResource("/com/palimpsest/images/quill_paused.png")
        );
        if (activeIcon == null || pausedIcon == null) {
            LOGGER.severe("Failed to load icon(s): quill.png=" + (activeIcon == null) + ", quill_paused.png=" + (pausedIcon == null));
            throw new AWTException("Icon resource not found");
        }
        trayIcon = new TrayIcon(activeIcon, "Palimpsest");
        trayIcon.setImageAutoSize(true);
        isPaused = false;

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

        pauseItem.addActionListener(e -> {
            if (isPaused) {
                LOGGER.info("Pause clicked - already paused");
            } else {
                LOGGER.info("Pause clicked - swapping to paused icon");
                trayIcon.setImage(pausedIcon);
                isPaused = true;
            }
        });

        resumeItem.addActionListener(e -> {
            if (isPaused) {
                LOGGER.info("Resume clicked - swapping to active icon");
                trayIcon.setImage(activeIcon);
                isPaused = false;
            } else {
                LOGGER.info("Resume clicked - already active");
            }
        });

        quitItem.addActionListener(e -> {
            LOGGER.info("Quit clicked - exiting app with last icon state");
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

    public boolean getIsPaused() {
        return isPaused;
    }
}