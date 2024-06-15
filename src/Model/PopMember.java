package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopMember {
    private Item[] order;
    private double fitness;
    private List<Bin> bins;
    private Random random = new Random();

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

        int totalFreeSpace = 0;
        int totalInefficiency = 0;
        double freeSpace = 0;
        double occupiedSpace = 0;
        double ratioSpace = 0;
        double totalRatioSpace = 0;
        for (Bin b : bins) {
            freeSpace = calculateFreeSpace(b);
            for (Item i : b.getItems()) {
                occupiedSpace += (i.getHeight() * i.getWidth());
            }
            //System.out.println("free space: " + freeSpace + ", occupied space: " + occupiedSpace);

            ratioSpace = freeSpace / occupiedSpace;
            //System.out.println("ration: " + ratioSpace);
            totalRatioSpace += ratioSpace;
            totalFreeSpace += calculateFreeSpace(b);
            totalInefficiency += calculatePlacementEfficiency(b);
        }
        //System.out.println("Total ratio: " + totalRatioSpace);
        // Ajuster les coefficients de pondération
        double binWeight = 100.0;
        double freeSpaceWeight = 0.5;  // Ajusté pour donner plus d'importance à l'espace libre
        double spaceWeight = 2.0; // Coefficient pour l'efficacité de placement

        // Calculer la fitness en combinant le nombre de bins, l'espace libre et l'efficacité de placement
        fitness = (binWeight * bins.size() + spaceWeight * totalRatioSpace/*+ freeSpaceWeight * totalFreeSpace + inefficiencyWeight * totalInefficiency*/);
        //System.out.println("Fitness: " + fitness + ", dont: " + spaceWeight * totalRatioSpace);
    }

    private int calculatePlacementEfficiency(Bin bin) {
        boolean[][] grid = bin.getGrid();
        int inefficientSpaces = 0;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (!grid[x][y] && hasAdjacentItem(grid, x, y)) {
                    inefficientSpaces++;
                }
            }
        }
        return inefficientSpaces;
    }

    private boolean hasAdjacentItem(boolean[][] grid, int x, int y) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && grid[nx][ny]) {
                return true;
            }
        }
        return false;
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
