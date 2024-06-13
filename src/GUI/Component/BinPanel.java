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
        int x = 5;
        int y = 5;
        if (bins != null) {
            for (Bin bin : bins) {
                drawBin(g, bin, x, y);
                if (x + bin.getWidth() < getParent().getSize().width) {
                    x += bin.getWidth() + 5;
                } else {
                    x = 5;
                    y += bin.getHeight() + 5;
                }
            }
        }
    }

    private void drawBin(Graphics g, Bin bin, int x, int y) {

        g.setColor(Color.BLACK);
        g.drawRect(x, y, bin.getWidth(), bin.getHeight());

        // Dessiner les cellules du Bin
        for (int row = 0; row < bin.getHeight(); row++) {
            for (int col = 0; col < bin.getWidth(); col++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x + col, y + row, 1, 1); // Remplir chaque cellule du bin
            }
        }
        for (Item item : bin.getItems()) {
            g.setColor(item.getColor());
            //System.out.println("couleur de l'item : " + item.getColor());
            g.fillRect(x + item.getX(), y + item.getY(), item.getWidth(), item.getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(x + item.getX(), y + item.getY(), item.getWidth(), item.getHeight());
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