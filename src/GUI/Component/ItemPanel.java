package GUI.Component;

import Model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ItemPanel extends JPanel {
    private List<Item> items;
    private int maxWidth;
    private int maxHeight;

    public ItemPanel(List<Item> items, int maxWidth, int maxHeight) {
        this.items = items;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 5;
        int y = 5;
        int lineOffset = Integer.MIN_VALUE;

        // Pour mettre à l'échelle lorsque certains jeux de données comportent des items aux dimensions
        // extrêmes, nous utilisons les dimensions maximales retrouvées dans le jeu de données.

        // On imagine le cas où tous les items ont la taille maximale et calcule la réduction à effectuer en fonction du
        // nombre de lignes à représenter selon la taille actuelle de la fenêtre

        int maxItemsByLine = getParent().getSize().width / maxWidth;
        int linesNeeded = items.size() / maxItemsByLine;
        int totalMaxHeight = linesNeeded * maxHeight;
        float ratio = Math.min((float) getParent().getSize().height / totalMaxHeight, 1);

//        float widthRatio = Math.min(getParent().getSize().width / (items.size() * maxWidth), 1);
        System.out.println("ratio = " + ratio + " totalMaxHeight = " + totalMaxHeight + " maxItemsByLine = " + maxItemsByLine);
        for (Item item : items) {
            int height = (int) (item.getHeight() * ratio);
            int width = (int) (item.getWidth() * ratio);
            if (x + width + 5 < getParent().getSize().width) {
                drawItem(g, x, y, width, height, item.getColor());
                x += width + 5;
                lineOffset = Math.max(lineOffset, height);
            } else {
                x = 5;
                y += lineOffset + 5;
                lineOffset = 0;
            }
        }
    }

    private void drawItem(Graphics g, int x, int y, int width, int height, Color color) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                g.setColor(color);
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
