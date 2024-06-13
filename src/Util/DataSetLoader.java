package Util;

import Model.DataSet;
import Model.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataSetLoader {
    public static DataSet loadDataSet(String fileName) {
        File file = new File(fileName);
        Scanner scanner;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            return null;
        }

        String name = null;
        String comment = null;
        int nbItems = 0;
        int binWidth = 0;
        int binHeight = 0;
        List<Item> items = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.startsWith("NAME:")) {
                name = line.substring(6).trim();
            } else if (line.startsWith("COMMENT:")) {
                comment = line.substring(9).trim();
            } else if (line.startsWith("NB_ITEMS:")) {
                nbItems = Integer.parseInt(line.substring(9).trim());
            } else if (line.startsWith("BIN_WIDTH:")) {
                binWidth = Integer.parseInt(line.substring(10).trim());
            } else if (line.startsWith("BIN_HEIGHT:")) {
                binHeight = Integer.parseInt(line.substring(11).trim());
            } else if (line.equals("ITEMS [id width height]:")) {
                // Commencer à lire les données des items
                for (int i = 0; i < nbItems; i++) {
                    String[] parts = scanner.nextLine().trim().split("\\s+");
                    int id = Integer.parseInt(parts[0]);
                    int width = Integer.parseInt(parts[1]) /*/ 10*/;
                    int height = Integer.parseInt(parts[2]) /*/ 10*/;
                    items.add(new Item(id, width, height));
                }
            }
        }

        scanner.close();
        return new DataSet(name, comment, nbItems, binWidth, binHeight, items);
    }
}