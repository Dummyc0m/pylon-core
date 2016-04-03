package com.dummyc0m.pylon.pyloncore;


/**
 * Created by Dummyc0m on 3/9/16.
 */
public class I18N {
    private static StringTranslator translator = StringTranslator.getTranslator();

    public static synchronized String getLocalizedString(String key) {
        return translator.translateKey(key);
    }

    public static synchronized String getLocalizedStringFormatted(String key, Object... varargs) {
        return translator.translateKeyFormat(key, varargs);
    }

    public static synchronized boolean isLocalized(String key) {
        return translator.isKeyTranslated(key);
    }
}
