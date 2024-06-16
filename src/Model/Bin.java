package Model;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

public class Bin {
    public class Rectangle{
        int width;
        int height;
        int x;
        int y;

        Rectangle(int width, int height, int x, int y) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
        }

        boolean fitsIn(Rectangle other) {
            return this.width <= other.width && this.height <= other.height;
        }

        Rectangle cutHorizontal(int cutHeight) {
            return new Rectangle(this.width, cutHeight, this.x, this.y + this.height - cutHeight);
        }

        Rectangle cutVertical(int cutWidth) {
            return new Rectangle(cutWidth, this.height, this.x + this.width - cutWidth, this.y);
        }
    }
    private boolean[][] grid;
    private List<Item> items;
    private List<Rectangle> freeSpace;

    public Bin(int width, int height) {
        grid = new boolean[width][height];
        this.items = new ArrayList<>();
        this.freeSpace = new ArrayList<>();
        this.freeSpace.add(new Rectangle(width, height, 0, 0));
    }

    // Getters et Setters
    public int getWidth() {
        return grid.length;
    }

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
        for (Rectangle rectangle : freeSpace) {
            if (!items.contains(item) && item.getWidth() < rectangle.width && item.getHeight() < rectangle.height) {
                for (int x = item.getX(); x < item.getX() + item.getWidth(); x++) {
                    for (int y = item.getY(); y < item.getY() + item.getHeight(); y++) {
                        setPixelUsed(x, y);
                    }
                }
                items.add(item);
                Rectangle itemRect = new Rectangle(item.getWidth(), item.getHeight(), rectangle.x, rectangle.y);
                splitFreeSpace(rectangle, itemRect);
            }
        }
    }

    private void splitFreeSpace(Rectangle rectangle, Rectangle itemRect) {
        this.freeSpace.remove(rectangle);
        Rectangle rightRectangle = rectangle.cutVertical(rectangle.width - itemRect.width);
        Rectangle topRectangle = rectangle.cutHorizontal(rectangle.height - itemRect.height);
        if (rightRectangle.width > 0 && rightRectangle.height > 0) {
            this.freeSpace.add(rightRectangle);
        }
        if (topRectangle.width > 0 && topRectangle.height > 0) {
            this.freeSpace.add(topRectangle);
        }
    }

    public void removeItem(Item item) {
        if (items.remove(item)) {
            for (int x = item.getX(); x < item.getX() + item.getWidth(); x++) {
                for (int y = item.getY(); y < item.getY() + item.getHeight(); y++) {
                    setPixelUnused(x, y);
                }
            }
            items.remove(item);
        } else {
            System.out.println("Item non trouvé dans ce bin.");
        }
    }

    public boolean canFit(Item item, int x, int y) {
        if (x + item.getWidth() > getWidth() || y + item.getHeight() > getHeight()) {
            return false;
        }
        for (int i = x; i < x + item.getWidth(); i++) {
            for (int j = y; j < y + item.getHeight(); j++) {
                if (isPixelUsed(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean findPositionForItem(Item item) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (canFit(item, x, y)) {
                    item.setX(x);
                    item.setY(y);
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
            return true; // Ou une autre valeur par défaut selon le contexte
        }
    }

    public void setPixelUnused(int x, int y) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            grid[x][y] = false;
        } else {
            System.out.println("Coordonnées de pixel non valides");
        }
    }

    public void setPixelUsed(int x, int y) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            grid[x][y] = true;
        } else {
            System.out.println("Coordonnées de pixel non valides");
        }
    }
}