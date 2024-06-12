package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopMember {
    private Item[] order;
    private int fitness;
    private List<Bin> bins;
    private Random random = new Random();

    public PopMember(Item[] order) {
        this.order = order;
    }

    public Item[] getOrder() {
        return order;
    }

    public int getFitness() {
        return fitness;
    }

    public List<Bin> getBins() {
        return bins;
    }

    public void evaluate(double binWidth, double binHeight) {
        bins = new ArrayList<>();
        Bin bin = new Bin((int) binWidth, (int) binHeight);
        bins.add(bin);

        for (Item item : order) {
            boolean placed = false;
            for (Bin b : bins) {
                if (b.findPositionForItem(item)) {
                    b.addItem(item);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                Bin newBin = new Bin((int) binWidth, (int) binHeight);
                newBin.findPositionForItem(item);
                newBin.addItem(item);
                bins.add(newBin);
            }
        }

        fitness = bins.size();
    }
}
