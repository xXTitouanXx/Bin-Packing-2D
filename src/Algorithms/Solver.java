package Algorithms;

import Algorithms.Heuristic.FiniteFirstFit;
import Algorithms.Heuristic.FirstFitDecreasingHeight;
import Algorithms.Heuristic.Heuristic;
import Algorithms.Heuristic.NextFitDecreasingHeight;
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

    public void solveMetaheuristic(DataSet dataSet, Metaheuristic metaheuristic) {
        if (dataSet != null) {
            // Solve bin packing with selected metaheuristic
            if (metaheuristic != null) {
                metaheuristic.solveBinPacking2D(dataSet);
            } else {
                System.out.println("Unknown metaheuristic");
            }
        }
    }public void solveHeuristic(DataSet dataSet, Heuristic heuristic) {
        if (dataSet != null) {
            // Solve bin packing with selected metaheuristic
            if (heuristic != null) {
                heuristic.solveBinPacking2D(dataSet);
            } else {
                System.out.println("Unknown heuristic");
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
    }public Heuristic getHeuristic(String heuristicName) {
        switch (heuristicName.toLowerCase()) {
            case "finite first fit":
                return new FiniteFirstFit(binPanel);
            case "first fit decreasing height":
                return new FirstFitDecreasingHeight(binPanel);
            case "next fit decreasing height":
                return new NextFitDecreasingHeight(binPanel);
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
