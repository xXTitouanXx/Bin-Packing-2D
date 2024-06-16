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

public class NextFitDecreasingHeight implements Heuristic {
    private BinPanel binPanel;

    public NextFitDecreasingHeight(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet, ControlPanel controlPanel) {
        List<Item> items = dataSet.getItems();

        // Tri des items par hauteur décroissante, en tenant compte de la rotation
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                int maxHeight1 = Math.max(item1.getHeight(), item1.getWidth());
                int maxHeight2 = Math.max(item2.getHeight(), item2.getWidth());
                return Integer.compare(maxHeight2, maxHeight1);
            }
        });

        // Liste des conteneurs utilisés
        List<Bin> bins = new ArrayList<>();
        Bin openBin = new Bin(dataSet.getBinWidth(), dataSet.getBinHeight());
        bins.add(openBin);

        // Parcours des items triés
        for (Item item : items) {
            if (!tryAddItemWithRotation(openBin, item)) {
                // Si l'item ne rentre pas dans le conteneur ouvert, on en ouvre un nouveau
                openBin = new Bin(dataSet.getBinWidth(), dataSet.getBinHeight());
                bins.add(openBin);
                // On essaie d'ajouter l'item au nouveau conteneur
                tryAddItemWithRotation(openBin, item);
            }
        }

        // Affichage des résultats
        System.out.println("Nombre de conteneurs utilisés: " + bins.size());
        binPanel.setBins(bins);
        controlPanel.enableButtons();
    }

    public static boolean tryAddItemWithRotation(Bin bin, Item item) {
        // Essayer d'ajouter l'item sans rotation
        if (bin.tryAddItem(item)) {
            return true;
        }

        // Rotation de l'item et essai d'ajout
        item.rotate();
        boolean added = bin.tryAddItem(item);

        // Si l'item n'a pas été ajouté, on le remet dans son orientation originale
        if (!added) {
            item.rotate();
        }

        return added;
    }
}
