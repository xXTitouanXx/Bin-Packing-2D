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
        int panelWidth = getParent().getSize().width;
        int panelHeight = getParent().getSize().height;

        int totalHeightRequired = calculateTotalHeightRequired(panelWidth);

        // Calculate scaling factor
        double scalingFactor = 1.0;
        if (totalHeightRequired > panelHeight) {
            scalingFactor = (double) panelHeight / totalHeightRequired;
        }
        int x = 5;
        int y = 5;
        int yMax = Integer.MIN_VALUE;
        for (Item item : items) {
            int itemWidth = (int) (item.getWidth() * scalingFactor);
            int itemHeight = (int) (item.getHeight() * scalingFactor);
            if (x + itemWidth + 5 > panelWidth) {
                x = 5;
                y += yMax + 5;
                yMax = 0;
            }

            drawItem(g, item, x, y, itemWidth, itemHeight);
            yMax = Math.max(yMax, itemHeight);
            x += itemWidth + 5;
            if (y + itemHeight + 5 > panelHeight) {
                break;
            }
        }
    }

    private int calculateTotalHeightRequired(int panelWidth) {
        int x = 5;
        int yMax = Integer.MIN_VALUE;
        int totalHeight = 0;

        for (Item item : items) {
            if (x + item.getWidth() < panelWidth) {
                yMax = Math.max(yMax, item.getHeight());
                x += item.getWidth() + 5;
            } else {
                x = 5;
                totalHeight += yMax + 5;
                yMax = item.getHeight();
            }
        }
        totalHeight += yMax + 5;
        return totalHeight;
    }

    private void drawItem(Graphics g, Item item, int x, int y, int width, int height) {
        g.setColor(item.getColor());
        g.fillRect(x, y, width, height);
    }

    public void setItems(List<Item> items) {
        this.items = items;
        repaint();
    }
}