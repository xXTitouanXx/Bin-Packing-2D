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

        // Calculate the total height required
        int totalHeightRequired = calculateTotalHeightRequired(panelWidth);

        // Calculate scaling factor
        double scalingFactor = 1.0;
        if (totalHeightRequired > panelHeight) {
            scalingFactor = (double) panelHeight / totalHeightRequired;
        }

        int x = 5;
        int y = 5;
        int i = 0;
        int nb = 0;
        if (bins != null) {
            for (Bin bin : bins) {
                nb++;
                if (x + bin.getWidth() + 5 > panelWidth) {
                    x = 5;
                    y += bin.getHeight() + 5;
                }
                if (y + bin.getHeight() + 5 <= panelHeight) {
                    drawBin(g, bin, x, y, scalingFactor);
                    x += bin.getWidth() + 5;
                    i++;

                } else {
                    break;
                }
            }
            System.out.println("Nb bin dessiné: " + i + ", nb iteration dans la boucle bin: " + nb);

        }
    }

    private int calculateTotalHeightRequired(int panelWidth) {
        int x = 5;
        int totalHeight = 0;
        for (Bin bin : bins) {
            if (x + bin.getWidth() < panelWidth) {
                x += bin.getWidth() + 5;
            } else {
                x = 5;
                totalHeight += bin.getHeight();
            }
        }
        return totalHeight;
    }

    private void drawBin(Graphics g, Bin bin, int x, int y, double scalingFactor) {

        g.setColor(Color.BLACK);
        g.drawRect(x, y, (int) (bin.getWidth() * scalingFactor), (int) (bin.getHeight() * scalingFactor));

        // Dessiner les cellules du Bin
        for (int row = 0; row < (int) (bin.getHeight() * scalingFactor); row++) {
            for (int col = 0; col < (int) (bin.getWidth() * scalingFactor); col++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x + col, y + row, 1, 1); // Remplir chaque cellule du bin
            }
        }
        for (Item item : bin.getItems()) {
            g.setColor(item.getColor());
            //System.out.println("couleur de l'item : " + item.getColor());
            g.fillRect(x + item.getX(), y + item.getY(), (int) (item.getWidth() * scalingFactor), (int) (item.getHeight() * scalingFactor));
            g.setColor(Color.BLACK);
            g.drawRect(x + item.getX(), y + item.getY(), (int) (item.getWidth() * scalingFactor), (int) (item.getHeight() * scalingFactor));
        }
    }

    public void clearBins() {
        bins.clear(); // Efface tous les items
    }

    public void setBins(List<Bin> bins) {
        this.bins = bins != null ? bins : new ArrayList<>();
        repaint();
    }

    public List<Bin> getBins() {
        return bins;
    }
}