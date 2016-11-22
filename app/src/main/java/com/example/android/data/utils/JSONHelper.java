package com.example.android.data.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.android.data.model.DataItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JSONHelper {

    private static final String FILE_NAME = "menuitems.json";
    private static final String TAG = "JSONHelper";

    public static boolean exportToJSON(Context context, List<DataItem> dataItemList) {
        DataItems menuData = new DataItems();
        menuData.setDataItems(dataItemList);

        Gson gson = new Gson();
        String jsonString = gson.toJson(menuData);
        Log.i(TAG, "exportToJSON: "+jsonString);

        FileOutputStream fileoutputStream = null;
        File file = new File(Environment.getExternalStorageDirectory(),FILE_NAME);

        try {
            fileoutputStream = new FileOutputStream(file);
            fileoutputStream.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileoutputStream!=null)
            {
                try {
                    fileoutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }


    public static List<DataItem> importFromJSON(Context context) {
        DataItems menuData = new DataItems();

        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);

        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(file));
            menuData = gson.fromJson(reader, menuData.getClass());
            return menuData.getDataItems();
        } catch (FileNotFoundException e) {
            Log.d("Error", "FileNotFoundException, file not found.");
        }

        return null;
    }


    static class DataItems{
        List<DataItem> dataItems;

        public List<DataItem> getDataItems() {
            return dataItems;
        }

        public void setDataItems(List<DataItem> dataItems) {
            this.dataItems = dataItems;
        }
    }
}
