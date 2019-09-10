package com.neotys.htmlunit.customActions.common;

import javax.swing.*;
import java.net.URL;

public class BrowserBasedUtils {
    private static final ImageIcon BROWSERBASED_ICON;

    static {

        final URL iconURL = BrowserBasedUtils.class.getResource("browser.png");
        if (iconURL != null) {
            BROWSERBASED_ICON = new ImageIcon(iconURL);
        } else {
            BROWSERBASED_ICON = null;
        }
    }

    public BrowserBasedUtils() {
    }

    public static ImageIcon getBrowserbasedIcon() {
        return BROWSERBASED_ICON;
    }
}