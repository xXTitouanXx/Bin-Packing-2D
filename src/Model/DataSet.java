package Model;

import java.util.List;

public class DataSet {
    private String name;
    private String comment;
    private int nbItems;
    private final int binWidth;
    private final int binHeight;
    private List<Item> items;

    public DataSet(String name, String comment, int nbItems, int binWidth, int binHeight, List<Item> items) {
        this.name = name;
        this.comment = comment;
        this.nbItems = nbItems;
        this.binWidth = binWidth;
        this.binHeight = binHeight;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNbItems() {
        return nbItems;
    }

    public void setNbItems(int nbItems) {
        this.nbItems = nbItems;
    }

    public int getBinWidth() {
        return binWidth;
    }

    public int getBinHeight() {
        return binHeight;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
