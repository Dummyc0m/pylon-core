package com.dummyc0m.pylon.pyloncore;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Dummyc0m on 3/9/16.
 * To Localize Strings
 */
public class StringTranslator {
    private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");

    private static final Splitter equalSignSplitter = Splitter.on('=').limit(2);

    private static StringTranslator localized = new StringTranslator();

    private final Map<String, String> languageList = new HashMap<>();

    private StringTranslator() {
        addLangResource("en_US");
    }

    public void addLangFile(File langFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(langFile));
            addLangReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLangResource(String lang) {
        InputStream langStream = StringTranslator.class.getResourceAsStream(lang + ".lang");
        if (langStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(langStream, Charset.forName("UTF-8")));
            addLangReader(reader);
        }
    }

    private void addLangReader(BufferedReader reader) {
        try {
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (!line.isEmpty() && line.charAt(0) != 35) {
                    String[] keyValPair = Iterables.toArray(equalSignSplitter.split(line), String.class);

                    if (keyValPair != null && keyValPair.length == 2) {
                        String value = pattern.matcher(keyValPair[1]).replaceAll("%$1s");
                        languageList.put(keyValPair[0], value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static StringTranslator getTranslator() {
        return localized;
    }

    synchronized String translateKey(String key) {
        return this.tryTranslateKey(key);
    }

    synchronized String translateKeyFormat(String key, Object... varargs) {
        String translation = tryTranslateKey(key);

        try {
            return String.format(translation, varargs);
        } catch (IllegalFormatException var5) {
            return "Format error: " + translation;
        }
    }

    private String tryTranslateKey(String key) {
        String translation = languageList.get(key);
        return translation == null ? key : translation;
    }

    synchronized boolean isKeyTranslated(String key) {
        return languageList.containsKey(key);
    }
}
