package Algorithms.Heuristic;

import GUI.Component.BinPanel;
import Model.Bin;
import Model.DataSet;
import Model.Item;

import java.util.ArrayList;
import java.util.List;

public class NextFitDecreasingHeight implements Heuristic {
    private BinPanel binPanel;

    public NextFitDecreasingHeight(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet) {
        List<Item> items = dataSet.getItems();

        // Tri des items par hauteur décroissante, en tenant compte de la rotation
        items.sort((item1, item2) -> {
            int maxHeight1 = Math.max(item1.getHeight(), item1.getWidth());
            int maxHeight2 = Math.max(item2.getHeight(), item2.getWidth());
            return Integer.compare(maxHeight2, maxHeight1);
        });

        List<Bin> bins = new ArrayList<>();
        Bin openBin = new Bin(dataSet.getBinWidth(), dataSet.getBinHeight());
        bins.add(openBin);

        for (Item item : items) {
            if (!tryAddItemWithRotation(openBin, item)) {
                // Si l'item ne rentre pas dans le conteneur ouvert, on en ouvre un nouveau
                openBin = new Bin(dataSet.getBinWidth(), dataSet.getBinHeight());
                bins.add(openBin);
                // On essaie d'ajouter l'item au nouveau conteneur
                tryAddItemWithRotation(openBin, item);
            }
        }
        System.out.println("Nombre de conteneurs utilisés: " + bins.size());
        binPanel.setBins(bins);
    }

    public static boolean tryAddItemWithRotation(Bin bin, Item item) {
        if (bin.tryAddItem(item)) {
            return true;
        }
        item.rotate();
        boolean added = bin.tryAddItem(item);
        if (!added) {
            item.rotate();
        }
        return added;
    }
}