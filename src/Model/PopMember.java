package Model;

import java.util.ArrayList;
import java.util.List;

public class PopMember {
    private Item[] order;
    private double fitness;
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

    public void evaluate(double binWidth, double binHeight) {
        bins = new ArrayList<>();
        Bin currentBin = new Bin((int) binWidth, (int) binHeight);
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
                Bin newBin = new Bin((int) binWidth, (int) binHeight);
                newBin.tryAddItem(item);
                bins.add(newBin);
            }
        }

        double freeSpace;
        double occupiedSpace = 0;
        double ratioSpace;
        double totalRatioSpace = 0;
        for (Bin b : bins) {
            freeSpace = calculateFreeSpace(b);
            for (Item i : b.getItems()) {
                occupiedSpace += (i.getHeight() * i.getWidth());
            }
            ratioSpace = freeSpace / occupiedSpace;
            totalRatioSpace += ratioSpace;

        }
        // Ajuster les coefficients de pondération
        double binWeight = 100.0;
        double spaceWeight = 2.0; // Coefficient pour l'efficacité de placement

        fitness = (binWeight * bins.size() + spaceWeight * totalRatioSpace);
    }

    private int calculateFreeSpace(Bin bin) {
        boolean[][] grid = bin.getGrid();
        int freeSpace = 0;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (!grid[x][y]) {
                    freeSpace++;
                }
            }
        }
        return freeSpace;
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