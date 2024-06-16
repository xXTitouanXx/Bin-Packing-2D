package Algorithms;

import Algorithms.Heuristic.FirstFitFirst;
import Algorithms.Metaheuristic.GeneticAlgorithm.DriverGenetic;
import Algorithms.Metaheuristic.Metaheuristic;
import Algorithms.Metaheuristic.TabuSearch.DriverTabu;
import GUI.Component.BinPanel;
import Model.DataSet;
import Model.Item;

public class Solver {
    private BinPanel binPanel;

    public Solver(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    public void solve(DataSet dataSet, Metaheuristic metaheuristic) {
        if (dataSet != null) {
            // On affiche la borne minimale théorique pour le nombre de bins
            System.out.println("Il faut théoriquement au moins " + lowerBound(dataSet)+ " bins");
            // Solve bin packing with selected metaheuristic
            if (metaheuristic != null) {
                metaheuristic.solveBinPacking2D(dataSet);
            } else {
                System.out.println("Unknown metaheuristic");
            }
        }
    }

    public Metaheuristic getMetaheuristic(String metaheuristicName) {
        switch (metaheuristicName.toLowerCase()) {
            case "test":
                return new Test(binPanel);
            case "tabu search":
                return new DriverTabu(binPanel);
            case "genetic algorithm":
                return new DriverGenetic(binPanel);
            default:
                return null;
        }
    }

    public int lowerBound(DataSet dataSet){
        double area = 0;
        for (Item item : dataSet.getItems()){
            area += item.getWidth() * item.getHeight();
        }
        return (int) Math.ceil(area / (dataSet.getBinWidth() * dataSet.getBinHeight()));
    }

    public BinPanel getBinPanel() {
        return binPanel;
    }

    public void setBinPanel(BinPanel binPanel) {
        this.binPanel = binPanel;
    }
}
