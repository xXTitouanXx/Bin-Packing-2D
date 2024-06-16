package Algorithms.Heuristic;

import GUI.Component.BinPanel;
import GUI.Component.ControlPanel;
import Model.Bin;
import Model.DataSet;
import Model.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FirstFitDecreasingHeight implements Heuristic {
    private BinPanel binPanel;

    public FirstFitDecreasingHeight(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet, ControlPanel controlPanel) {
        List<Item> items = dataSet.getItems();

        // Tri des items par aire (surface) décroissante
        for (Item i:items){
            System.out.println("Item: "+i.getId());
        }
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                int area1 = item1.getWidth() * item1.getHeight();
                int area2 = item2.getWidth() * item2.getHeight();
                return Integer.compare(area2, area1);
            }
        });
        for (Item i:items){
            System.out.println("Item trié: "+i.getId());
        }

        // Liste des conteneurs utilisés
        List<Bin> bins = new ArrayList<>();

        // Parcours des items triés
        for (Item item : items) {
            boolean placed = false;

            // Parcours des conteneurs existants pour trouver où placer l'item
            for (Bin bin : bins) {
                if (bin.tryAddItem(item)) {
                    placed = true;
                    break;
                }
                if (bin.tryAddItem(item.rotate())) {
                    placed = true;
                    break;
                }
            }

            // Si l'item n'a pas pu être placé dans un conteneur existant, créer un nouveau conteneur
            if (!placed) {
                Bin newBin = new Bin(dataSet.getBinWidth(), dataSet.getBinHeight());
                newBin.tryAddItem(item);
                bins.add(newBin);
            }
        }

        binPanel.setBins(bins);
    }
}
