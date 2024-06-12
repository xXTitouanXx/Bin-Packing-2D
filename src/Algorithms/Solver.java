package Algorithms;

import Algorithms.Metaheuristic.GeneticAlgorithm.DriverGenetic;
import Algorithms.Metaheuristic.Metaheuristic;
import Algorithms.Metaheuristic.TabuSearch.DriverTabu;
import GUI.Component.BinPanel;
import Model.DataSet;
import Util.DataSetLoader;

public class Solver {
    private BinPanel binPanel;

    public Solver(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    public void solve(String dataSetName, String metaheuristicName) {
        // Load dataset
        //String dataSetPath = "C:/Polytech_ingenieur/OptDiscrete/" + dataSetName + ".bp2d";
        String dataSetPath = "E:/Polytech/4A/OptDiscrete/" + dataSetName + ".bp2d";
        DataSet dataSet = DataSetLoader.loadDataSet(dataSetPath);

        if (dataSet != null) {
            // Print dataset information
            System.out.println("Loaded dataset: " + dataSet.getName());
            System.out.println("Comment: " + dataSet.getComment());
            System.out.println("Number of items: " + dataSet.getNbItems());
            System.out.println("Bin width: " + dataSet.getBinWidth());
            System.out.println("Bin height: " + dataSet.getBinHeight());

            // Solve bin packing with selected metaheuristic
            Metaheuristic metaheuristic = getMetaheuristic(metaheuristicName);
            if (metaheuristic != null) {
                metaheuristic.solveBinPacking2D(dataSet);
            } else {
                System.out.println("Unknown metaheuristic: " + metaheuristicName);
            }
        } else {
            System.out.println("Failed to load dataset: " + dataSetPath);
        }
    }

    private Metaheuristic getMetaheuristic(String metaheuristicName) {
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
}
