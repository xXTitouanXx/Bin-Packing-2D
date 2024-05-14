package Algorithms;

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
        String dataSetPath = "C:/Polytech_ingenieur/OptDiscrete/" + dataSetName + ".bp2d";
        DataSet dataSet = DataSetLoader.loadDataSet(dataSetPath);

        if (dataSet != null) {
            // Print dataset information
            System.out.println("Loaded dataset: " + dataSet.getName());
            System.out.println("Comment: " + dataSet.getComment());
            System.out.println("Number of items: " + dataSet.getNbItems());
            System.out.println("Model.Bin width: " + dataSet.getBinWidth());
            System.out.println("Model.Bin height: " + dataSet.getBinHeight());

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
            case "grasp":
                return new GRASP(binPanel);
//            case "fff":
//                return new FFF(draw);
//            case "tabu search":
//                return new TabuSearch();
            default:
                return null;
        }
    }
}
