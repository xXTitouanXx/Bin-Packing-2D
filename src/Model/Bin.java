package Model;

import java.util.ArrayList;
import java.util.List;

public class Bin {
    private boolean[][] grid;
    private List<Item> items;

    public Bin(int width, int height) {
        grid = new boolean[width][height];
        this.items = new ArrayList<>();
    }

    // Getters et Setters
    public int getWidth() {
        return grid.length;
    }

    // Méthode pour obtenir la hauteur de la grille
    public int getHeight() {
        return grid[0].length;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void setGrid(boolean[][] grid) {
        this.grid = grid;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // Methods
    public boolean contains(Item item) {
        return items.contains(item);
    }

    public void addItem(Item item) {
        for (int x = item.getX(); x < item.getX() + item.getWidth(); x++) {
            for (int y = item.getY(); y < item.getY() + item.getHeight(); y++) {
                setPixelUsed(x, y);
            }
        }
        items.add(item);
    }

    public boolean canFit(Item item, int x, int y) {
        if (x + item.getWidth() > getWidth() || y + item.getHeight() > getHeight()) {
            return false;
        }
        for (int i = x; i < x + item.getWidth(); i++) {
            for (int j = y; j < y + item.getHeight(); j++) {
                if (isPixelUsed(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPixelUsed(int x, int y) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            //une case utilisé renvoie true
            return grid[x][y];
        } else {
            // System.out.println("Coordonnées de pixel non valides");
            return false; // Ou une autre valeur par défaut selon le contexte
        }
    }

    public void setPixelUsed(int x, int y) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            grid[x][y] = true;
        } else {
            //System.out.println("Coordonnées de pixel non valides");
        }
    }
}