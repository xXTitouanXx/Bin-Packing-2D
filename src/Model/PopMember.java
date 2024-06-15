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
        Bin currentBin = new Bin((int) binWidth, (int) binHeight);
        bins.add(currentBin);
        int i = 0;
        int bi = 1;
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
                bi += 1;
                newBin.tryAddItem(item);
                bins.add(newBin);
            }
            i += 1;
            System.out.println("item: " + i + ", item id: " + item.getId() + ", position: (" + item.getX() + ", " + item.getY() + ")" + ", bin: " + bi);
        }

        // Calculer l'espace libre total
        int totalFreeSpace = 0;
        for (Bin b : bins) {
            totalFreeSpace += calculateFreeSpace(b);
        }

        // Pondérer le nombre de bacs plus lourdement que l'espace libre
        double binWeight = 100.0;  // Le coefficient de pondération pour le nombre de bins
        double freeSpaceWeight = 1.0;  // Le coefficient de pondération pour l'espace libre

        // Combiner le nombre de bacs et l'espace libre dans la fitness
        fitness = (int) (binWeight * bins.size());
        System.out.println("Fitness: " + fitness);
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
    public boolean containsItem(Item item) {
        for (Item i : order) {
            if (i.equals(item)) {
                return true;
            }
        }
        return false;
    }
}
