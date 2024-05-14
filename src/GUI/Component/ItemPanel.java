package GUI.Component;

import Model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ItemPanel extends JPanel {
    private List<Item> items;

    public ItemPanel(List<Item> items) {
        this.items = items;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 5;
        int y = 5;
        int yMax = Integer.MIN_VALUE;
        for (Item item : items) {
            drawItem(g, item, x, y);
            if (x + item.getWidth() < getParent().getSize().width) {
                yMax = Math.max(yMax, item.getHeight());
                System.out.println("Valeur ymax : " + yMax);
                x += item.getWidth() + 5;
            } else {
                x = 5;
                y += yMax + 5;
                yMax = 0;
            }
        }
    }

    private void drawItem(Graphics g, Item item, int x, int y) {
        for (int row = 0; row < item.getHeight(); row++) {
            for (int col = 0; col < item.getWidth(); col++) {
                g.setColor(item.getColor());
                g.drawRect(x + col, y + row, 1, 1);
            }
        }

    }

    public void clearItems() {
        items.clear(); // Efface tous les items
        repaint(); // Redessine le composant pour refléter les changements
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
