package Algorithms.Heuristic;

import GUI.Component.BinPanel;
import Model.Bin;
import Model.DataSet;
import Model.Item;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FiniteFirstFit implements Heuristic {
    private BinPanel binPanel;

    public FiniteFirstFit(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet) {
        int binWidth = dataSet.getBinWidth();
        int binHeight = dataSet.getBinHeight();
        List<Item> items = dataSet.getItems();
        List<Bin> bins = new ArrayList<>();
        Bin currentBin = new Bin(binWidth, binHeight);
        bins.add(currentBin);
        for (Item item : items) {
            boolean placed = false;
            if (currentBin.findPositionForItem(item)) {
                currentBin.addItem(item);
                placed = true;
            }
            if (!placed) {
                Item rotatedItem = item.rotate();
                if (currentBin.findPositionForItem(rotatedItem)) {
                    currentBin.addItem(rotatedItem);
                    placed = true;
                }
            }
            if (!placed) {
                currentBin = new Bin(binWidth, binHeight);
                bins.add(currentBin);
                currentBin.addItem(item);
            }
        }
        // Finally, set the bins in BinPanel after all items are added
        binPanel.setBins(bins);
    }
}
