package Algorithms.Heuristic;

import GUI.Component.BinPanel;
import Model.Bin;
import Model.DataSet;
import Model.Item;

import java.util.ArrayList;
import java.util.List;

public class FirstFitDecreasingArea implements Heuristic {
    private BinPanel binPanel;

    public FirstFitDecreasingArea(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet) {
        List<Item> items = dataSet.getItems();

        // Tri des items par aire décroissante
        items.sort((item1, item2) -> {
            int area1 = item1.getWidth() * item1.getHeight();
            int area2 = item2.getWidth() * item2.getHeight();
            return Integer.compare(area2, area1);
        });

        List<Bin> bins = new ArrayList<>();

        for (Item item : items) {
            boolean placed = false;
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

            if (!placed) {
                Bin newBin = new Bin(dataSet.getBinWidth(), dataSet.getBinHeight());
                newBin.tryAddItem(item);
                bins.add(newBin);
            }
        }
        binPanel.setBins(bins);
    }
}
