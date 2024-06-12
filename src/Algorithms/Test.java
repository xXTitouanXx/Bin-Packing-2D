package Algorithms;

import Algorithms.Metaheuristic.Metaheuristic;
import GUI.Component.BinPanel;
import Model.Bin;
import Model.DataSet;

import java.util.ArrayList;
import java.util.List;

public class Test implements Metaheuristic {
    private BinPanel binPanel;

    public Test(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet) {
        List<Bin> bins = new ArrayList<Bin>();
        Bin bin1 = new Bin(250, 250);
        bins.add(bin1);
        bins.add(bin1);
        binPanel.setBins(bins);
        binPanel.repaint();
        System.out.println("Test finished.");
    }
}
