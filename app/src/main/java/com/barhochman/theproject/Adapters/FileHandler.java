package com.barhochman.theproject.Adapters;

import com.barhochman.theproject.Nodes.Transfers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileHandler {

    //writing list into JSON file
    public static void write(LinkedList<Transfers> lst, String FileName){

        File f = new File(FileName);

        try (Writer writer = new FileWriter(f)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(lst, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //reading from JSON and returning list
    public static List<Transfers> read(String FileName){

        File  f = new File(FileName);

        try (Reader reader = new FileReader(f)){
            Gson gson = new GsonBuilder().create();
            Type listType = new TypeToken<ArrayList<Transfers>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
