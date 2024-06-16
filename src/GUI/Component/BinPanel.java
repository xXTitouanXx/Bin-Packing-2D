package GUI.Component;

import Model.Bin;
import Model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BinPanel extends JPanel {
    private List<Bin> bins;

    public BinPanel(List<Bin> bins) {
        this.bins = bins != null ? bins : new ArrayList<>();
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

        if (bins != null) {
            for (Bin bin : bins) {
                if (x + (int) (bin.getWidth() * scalingFactor) + 5 > panelWidth) {
                    x = 5;
                    y += (int) (bin.getHeight() * scalingFactor) + 5;
                }
                drawBin(g, bin, x, y, scalingFactor);
                x += (int) (bin.getWidth() * scalingFactor) + 5;
                if (y + (int) (bin.getHeight() * scalingFactor) + 5 > panelHeight) {
                    break;
                }
            }
        }
    }

    private int calculateTotalHeightRequired(int panelWidth) {
        int x = 5;
        int totalHeight = 0;
        int rowHeight = 0; // Hauteur maximale d'une ligne courante

        for (Bin bin : bins) {
            int binWidth = (bin.getWidth() + 5);
            int binHeight = bin.getHeight() + 5;

            if (x + binWidth < panelWidth) {
                x += binWidth;
                rowHeight = Math.max(rowHeight, binHeight);
            } else {
                x = 5;
                totalHeight += rowHeight;
                rowHeight = binHeight;
            }
        }
        totalHeight += rowHeight;

        return totalHeight;
    }

    private void drawBin(Graphics g, Bin bin, int x, int y, double scalingFactor) {
        int scaledWidth = (int) (bin.getWidth() * scalingFactor);
        int scaledHeight = (int) (bin.getHeight() * scalingFactor);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, scaledWidth, scaledHeight);

        // Dessiner les cellules du Bin
        for (int row = 0; row < scaledHeight; row++) {
            for (int col = 0; col < scaledWidth; col++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x + col, y + row, 1, 1); // Remplir chaque cellule du bin
            }
        }

        // Dessiner les items dans le Bin
        for (Item item : bin.getItems()) {
            int scaledItemX = (int) (item.getX() * scalingFactor);
            int scaledItemY = (int) (item.getY() * scalingFactor);
            int scaledItemWidth = (int) (item.getWidth() * scalingFactor);
            int scaledItemHeight = (int) (item.getHeight() * scalingFactor);

            g.setColor(item.getColor());
            g.fillRect(x + scaledItemX, y + scaledItemY, scaledItemWidth, scaledItemHeight);
            g.setColor(Color.BLACK);
            g.drawRect(x + scaledItemX, y + scaledItemY, scaledItemWidth, scaledItemHeight);
        }
    }

    public void clearBins() {
        bins.clear();
        repaint();
    }

    public void setBins(List<Bin> bins) {
        this.bins = bins != null ? bins : new ArrayList<>();
        repaint();
    }

    public List<Bin> getBins() {
        return bins;
    }
}