package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopMember {
    private Item[] order;
    private int fitness;
    private List<Bin> bins;

    public PopMember(Item[] order) {
        this.order = order;
    }

    public Item[] getOrder() {
        return order;
    }

    public double getFitness() {
        return fitness;
    }

    public List<Bin> getBins() {
        return bins;
    }

    public void setBins(List<Bin> bins) {
        this.bins = bins;
    }

    public void evaluate(int binWidth, int binHeight) {
        bins = new ArrayList<>();
        Bin currentBin = new Bin(binWidth, binHeight);
        bins.add(currentBin);

        for (Item item : order) {
            boolean placed = false;
            for (Bin b : bins) {
                if (b.tryAddItem(item)) {
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                Bin newBin = new Bin(binWidth, binHeight);
                newBin.tryAddItem(item);
                bins.add(newBin);
            }
        }
        int binPenalty = 1000;
        int remainingSpacePenalty = 1;
        int totalBinsUsed = bins.size();
        int totalRemainingSpace = 0;
        for (Bin bin : bins) {
            int usedSpace = 0;
            for (Item item : bin.getItems()) {
                usedSpace += item.getWidth() * item.getHeight();
            }
            int binTotalSpace = binWidth * binHeight;
            int binRemainingSpace = binTotalSpace - usedSpace;
            totalRemainingSpace += binRemainingSpace;
        }
        fitness = (binPenalty * totalBinsUsed) + (remainingSpacePenalty * totalRemainingSpace);
    }

    public boolean containsItem(Item item) {
        for (Item i : order) {
            if (i.equals(item)) {
                return true;
            }
        }
        return false;
    }
}
