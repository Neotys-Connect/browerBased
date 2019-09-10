package com.neotys.htmlunit.customActions;

import com.neotys.action.argument.ArgumentValidator;
import com.neotys.action.argument.Option;
import com.neotys.extensions.action.ActionParameter;

import static com.neotys.action.argument.DefaultArgumentValidator.BOOLEAN_VALIDATOR;
import static com.neotys.action.argument.DefaultArgumentValidator.NON_EMPTY;
import static com.neotys.action.argument.Option.AppearsByDefault.False;
import static com.neotys.action.argument.Option.AppearsByDefault.True;
import static com.neotys.action.argument.Option.OptionalRequired.Optional;
import static com.neotys.action.argument.Option.OptionalRequired.Required;
import static com.neotys.extensions.action.ActionParameter.Type.TEXT;

enum SelectItemFromListByIdOption  implements Option {

    Elementid("Elementid", Required, True, TEXT,
            "Element id of the dom to set text",
                    "Element id of the dom",
              NON_EMPTY),
    FieldValue("Value", Required, True, TEXT,
            "Text to set in the dom element",
                    "Text to set in the dom element",
          NON_EMPTY),
    Performance("Performance", Optional, True, TEXT,
            "enable the measurement of UX ",
                    "enable the UX measurement : true: enable ; false : Disable",
                BOOLEAN_VALIDATOR),
    TraceMode("TraceMode", Optional, False, TEXT,
            "enable logging ",
                    "enable loggin details  : true: enable ; false : Disable",
              BOOLEAN_VALIDATOR);

    private final String name;
    private final Option.OptionalRequired optionalRequired;
    private final Option.AppearsByDefault appearsByDefault;
    private final ActionParameter.Type type;
    private final String defaultValue;
    private final String description;
    private final ArgumentValidator argumentValidator;

    SelectItemFromListByIdOption(final String name, final Option.OptionalRequired optionalRequired,
                               final Option.AppearsByDefault appearsByDefault,
                               final ActionParameter.Type type, final String defaultValue, final String description,
                               final ArgumentValidator argumentValidator) {
        this.name = name;
        this.optionalRequired = optionalRequired;
        this.appearsByDefault = appearsByDefault;
        this.type = type;
        this.defaultValue = defaultValue;
        this.description = description;
        this.argumentValidator = argumentValidator;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Option.OptionalRequired getOptionalRequired() {
        return optionalRequired;
    }

    @Override
    public Option.AppearsByDefault getAppearsByDefault() {
        return appearsByDefault;
    }

    @Override
    public ActionParameter.Type getType() {
        return type;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ArgumentValidator getArgumentValidator() {
        return argumentValidator;
    }

}