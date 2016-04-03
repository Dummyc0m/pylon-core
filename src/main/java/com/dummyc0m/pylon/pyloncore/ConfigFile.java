package com.dummyc0m.pylon.pyloncore;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * Created by Dummyc0m on 3/3/15.
 */
public class ConfigFile<T> {
    private File file;
    private final Gson gson;
    private T config;

    public ConfigFile(File dataFolder, String file, Class<T> clazz) {
        this(dataFolder, file, new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .serializeNulls()
                .create(), clazz);
    }

    public ConfigFile(File dataFolder, String file, Gson gson, Class<T> clazz) {
        this.gson = gson;
        try {
            if (!dataFolder.exists() && !dataFolder.mkdir()) {
                throw new IOException("An error occurred when trying to create configuration folder");
            }
            this.file = new File(dataFolder.getAbsolutePath(), file);
            if (this.file.exists() || this.file.createNewFile()) {
                BufferedReader bReader = new BufferedReader(new FileReader(this.file));
                this.config = this.gson.fromJson(bReader, clazz);
                if (config == null) {
                    useDefault(clazz);
                    save();
                }
            } else {
                throw new IOException("An error occurred when trying to instantiate a configuration");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void useDefault(Class<T> settingsClass) {
        try {
            Constructor<T> constructor = settingsClass.getConstructor();
            this.config = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T getConfig() {
        return this.config;
    }

    public T save() {
        try {
            FileWriter fWriter = new FileWriter(file, false);
            fWriter.write(this.gson.toJson(config));
            fWriter.flush();
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}
