package com.neotys.htmlunit.customActions;

import com.google.common.base.Optional;
import com.neotys.action.argument.Arguments;
import com.neotys.action.argument.Option;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.htmlunit.customActions.common.BrowserBasedUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClickElementByXpathAction implements Action {
    private static final String BUNDLE_NAME = "com.neotys.htmlunit.customActions.ClickElementByXpath.bundle";
    private static final String DISPLAY_NAME = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayName");
    private static final String DISPLAY_PATH = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayPath");

    @Override
    public String getType() {
        return "ClickElementByXpath";
    }

    @Override
    public List<ActionParameter> getDefaultActionParameters() {
        final ArrayList<ActionParameter> parameters = new ArrayList<>();

        for (final ClickElementByXpathOption option : ClickElementByXpathOption.values()) {
            if (Option.AppearsByDefault.True.equals(option.getAppearsByDefault())) {
                parameters.add(new ActionParameter(option.getName(), option.getDefaultValue(),
                        option.getType()));
            }
        }

        return parameters;
    }

    @Override
    public Class<? extends ActionEngine> getEngineClass() {
        return ClickOnElementByIDActionEngine.class;
    }

    @Override
    public Icon getIcon() {
        // TODO Add an icon
        return BrowserBasedUtils.getBrowserbasedIcon();
    }

    @Override
    public boolean getDefaultIsHit() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Click on a Web Component with the of xpath.\n\n" + Arguments.getArgumentDescriptions(ClickElementByXpathOption.values());

    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public String getDisplayPath() {
        return DISPLAY_PATH;
    }

    @Override
    public Optional<String> getMinimumNeoLoadVersion() {
        return Optional.of("6.7");
    }

    @Override
    public Optional<String> getMaximumNeoLoadVersion() {
        return Optional.absent();
    }
}