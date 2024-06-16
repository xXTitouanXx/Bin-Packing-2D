package Algorithms.Heuristic;

import Model.Item;
import Model.Bin;

import java.util.ArrayList;
import java.util.List;

public class FirstFitFirst {
    public List<Bin> FFF(List<Item> items, int binWidth, int binHeight) {
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
                    break;
                }
            }
            if (!placed) {
                currentBin = new Bin(binWidth, binHeight);
                bins.add(currentBin);
                currentBin.addItem(item);
            }
        }
        return bins;
    }
}
