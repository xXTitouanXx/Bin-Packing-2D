package Algorithms;

import Algorithms.Heuristic.FiniteFirstFit;
import Algorithms.Heuristic.FirstFitDecreasingArea;
import Algorithms.Heuristic.Heuristic;
import Algorithms.Heuristic.NextFitDecreasingHeight;
import Algorithms.Metaheuristic.GeneticAlgorithm.DriverGenetic;
import Algorithms.Metaheuristic.Metaheuristic;
import Algorithms.Metaheuristic.TabuSearch.DriverTabu;
import GUI.Component.BinPanel;
import GUI.Component.ControlPanel;
import Model.DataSet;
import Model.Item;

public class Solver {
    private BinPanel binPanel;

    public Solver(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    public void solveMetaheuristic(DataSet dataSet, Metaheuristic metaheuristic, ControlPanel controlPanel) {
        if (dataSet != null) {
            // Solve bin packing with selected metaheuristic
            if (metaheuristic != null) {
                // C'est notre heuristique la plus efficace pour générer une solution originale acceptable

                FirstFitDecreasingArea ffda = new FirstFitDecreasingArea(binPanel);
                ffda.solveBinPacking2D(dataSet);
//                FiniteFirstFit fff = new FiniteFirstFit(binPanel);
//                fff.solveBinPacking2D(dataSet);

                metaheuristic.setBinPanel(binPanel);
                metaheuristic.solveBinPacking2D(dataSet, controlPanel);
            } else {
                System.out.println("Unknown metaheuristic");
            }
        }
    }

    public int lowerBound(DataSet dataSet){
        double area = 0;
        for (Item item : dataSet.getItems()){
            area += item.getWidth() * item.getHeight();
        }
        return (int) Math.ceil(area / (dataSet.getBinWidth() * dataSet.getBinHeight()));
    }

    public void solveHeuristic(DataSet dataSet, Heuristic heuristic, ControlPanel controlPanel) {
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
            case "tabu search":
                return new DriverTabu(binPanel);
            case "genetic algorithm":
                return new DriverGenetic(binPanel);
            default:
                return null;
        }
    }

    public Heuristic getHeuristic(String heuristicName) {
        switch (heuristicName.toLowerCase()) {
            case "finite first fit":
                return new FiniteFirstFit(binPanel);
            case "first fit decreasing area":
                return new FirstFitDecreasingArea(binPanel);
            case "next fit decreasing height":
                return new NextFitDecreasingHeight(binPanel);
            default:
                return null;
        }
    }

    public void setBinPanel(BinPanel binPanel) {
        this.binPanel = binPanel;
    }
}
