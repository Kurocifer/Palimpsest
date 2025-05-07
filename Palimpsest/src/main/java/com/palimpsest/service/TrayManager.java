package com.palimpsest.service;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class TrayManager {
    private static final Logger LOGGER = Logger.getLogger(TrayManager.class.getName());
    private final SystemTray systemTray;
    private final TrayIcon trayIcon;

    public TrayManager() throws AWTException {
        if (!SystemTray.isSupported()) {
            throw new AWTException("System tray is not supported on this platform");
        }
        systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage(
                TrayManager.class.getResource("/com/palimpsest/quill.png")
        );
        if (image == null) {
            LOGGER.severe("Failed to load quill.png - resource not found");
            throw new AWTException("Image resource not found");
        }
        trayIcon = new TrayIcon(image, "Palimpsest");
        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LOGGER.info("Tray icon clicked - placeholder action");
            }
        });
        systemTray.add(trayIcon);
    }

    public void removeTrayIcon() {
        systemTray.remove(trayIcon);
    }
}