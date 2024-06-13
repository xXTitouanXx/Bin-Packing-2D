package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopMember {
    private Item[] order;
    private int fitness;
    private List<Bin> bins;
    private Random random = new Random();

    public PopMember(Item[] order) {
        this.order = order;
    }

    public Item[] getOrder() {
        return order;
    }

    public int getFitness() {
        return fitness;
    }

    public List<Bin> getBins() {
        return bins;
    }

    public void evaluate(double binWidth, double binHeight) {
        bins = new ArrayList<>();
        Bin bin = new Bin((int) binWidth, (int) binHeight);
        bins.add(bin);

        for (Item item : order) {
            boolean placed = false;
            for (Bin b : bins) {
                if (b.findPositionForItem(item)) {
                    b.addItem(item);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                Bin newBin = new Bin((int) binWidth, (int) binHeight);
                newBin.findPositionForItem(item);
                newBin.addItem(item);
                bins.add(newBin);
            }
        }

        // Calculer l'espace libre total
        int totalFreeSpace = 0;
        for (Bin b : bins) {
            totalFreeSpace += calculateFreeSpace(b);
        }

        // Pondérer le nombre de bacs plus lourdement que l'espace libre
        double binWeight = 10.0;  // Le coefficient de pondération pour le nombre de bins
        double freeSpaceWeight = 1.0;  // Le coefficient de pondération pour l'espace libre

        // Combiner le nombre de bacs et l'espace libre dans la fitness
        fitness = (int) (binWeight * bins.size() + freeSpaceWeight * (totalFreeSpace / (binWidth * binHeight)));
    }

    // Méthode pour calculer l'espace libre dans un bin
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
}
