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
        for (Bin bin : bins) {
            if (x + (int) (bin.getWidth() * scalingFactor) + 5 > panelWidth) {
                x = 5;
                y += (int) (bin.getHeight() * scalingFactor) + 5;
            }
            drawBin(g, bin, x, y, scalingFactor);
            x += (int) (bin.getWidth() * scalingFactor) + 5;
            i++;
            if (y + (int) (bin.getHeight() * scalingFactor) + 5 > panelHeight) {
                break;

            }
        }
    }

    private int calculateTotalHeightRequired(int panelWidth) {
        int x = 5;
        int totalHeight = 0;
        int rowHeight = 0; // Hauteur maximale d'une ligne courante

        for (Bin bin : bins) {
            int binWidth = (int) (bin.getWidth() + 5); // Largeur du bin avec espace
            int binHeight = bin.getHeight() + 5; // Hauteur du bin avec espace

            if (x + binWidth < panelWidth) {
                // Ajouter le bin à la ligne courante
                x += binWidth;
                rowHeight = Math.max(rowHeight, binHeight); // Mettre à jour la hauteur maximale de la ligne
            } else {
                // Nouvelle ligne
                x = 5; // Réinitialiser la position x
                totalHeight += rowHeight; // Ajouter la hauteur de la ligne précédente
                rowHeight = binHeight; // Commencer une nouvelle ligne
            }
        }

        // Ajouter la hauteur de la dernière ligne
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
        bins.clear(); // Efface tous les bins
        repaint(); // Redessine le composant pour refléter les changements
    }

    public void setBins(List<Bin> bins) {
        this.bins = bins != null ? bins : new ArrayList<>();
        repaint(); // Redessine le composant pour refléter les changements
    }

    public List<Bin> getBins() {
        return bins;
    }
}
