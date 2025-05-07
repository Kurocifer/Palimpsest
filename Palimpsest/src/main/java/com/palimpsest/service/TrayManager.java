package com.palimpsest.service.tray;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class TrayManager {
    private static final Logger LOGGER = Logger.getLogger(TrayManager.class.getName());
    private final SystemTray systemTray;
    private final TrayIcon trayIcon;

    public TrayManager(Runnable quitAction) throws AWTException {
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

        // Create popup menu
        PopupMenu popup = new PopupMenu();
        MenuItem pauseItem = new MenuItem("Pause");
        MenuItem resumeItem = new MenuItem("Resume");
        MenuItem quitItem = new MenuItem("Quit");

        // Placeholder actions for Pause and Resume
        pauseItem.addActionListener(e -> LOGGER.info("Pause clicked - placeholder"));
        resumeItem.addActionListener(e -> LOGGER.info("Resume clicked - placeholder"));
        quitItem.addActionListener(e -> {
            LOGGER.info("Quit clicked - exiting app");
            quitAction.run();
        });

        popup.add(pauseItem);
        popup.add(resumeItem);
        popup.addSeparator();
        popup.add(quitItem);
        trayIcon.setPopupMenu(popup);

        // Click listener for tray icon
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LOGGER.info("Tray icon clicked - showing menu");
            }
        });

        systemTray.add(trayIcon);
    }

    public void removeTrayIcon() {
        systemTray.remove(trayIcon);
    }
}