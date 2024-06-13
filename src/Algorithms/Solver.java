package Algorithms;

import Algorithms.Metaheuristic.GeneticAlgorithm.DriverGenetic;
import Algorithms.Metaheuristic.Metaheuristic;
import Algorithms.Metaheuristic.TabuSearch.DriverTabu;
import GUI.Component.BinPanel;
import Model.DataSet;

public class Solver {
    private BinPanel binPanel;

    public Solver(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    public void solve(DataSet dataSet, Metaheuristic metaheuristic) {
        if (dataSet != null) {
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

    public BinPanel getBinPanel() {
        return binPanel;
    }

    public void setBinPanel(BinPanel binPanel) {
        this.binPanel = binPanel;
    }
}
