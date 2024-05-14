package Algorithms;

import Algorithms.Metaheuristic;
import GUI.Component.BinPanel;
import Model.Bin;
import Model.DataSet;
import Model.Item;

import java.util.List;
import java.util.Random;

public class GRASP implements Metaheuristic {
    private BinPanel binPanel;

    public GRASP(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet) {
        List<Item> items = dataSet.getItems();
        int binWidth = dataSet.getBinWidth() ;
        int binHeight = dataSet.getBinHeight();
        int maxIterations = 50;

        // Utilisez Algorithms.GRASP pour résoudre le problème de binpacking 2D
        List<Bin> solution = null;
        for (int i = 0; i < maxIterations; i++) {
            solution = graspBinPacking(items, binWidth, binHeight, maxIterations);
          //  binPanel.updateBins(solution); // Met à jour les bins dans le composant Draw
        }
        // Mettez à jour l'affichage avec la solution trouvée
//        binPanel.setBins(solution);
//        binPanel.repaint();
        System.out.println("Algorithms.GRASP algorithm finished.");
    }


    private List<Bin> graspBinPacking(List<Item> items, int binWidth, int binHeight, int maxIterations) {
        List<Bin> bestSolution = null;
        int bestFitness = Integer.MAX_VALUE;
        Random random = new Random();
        for (int i = 0; i < maxIterations; i++) {
//            List<Model.Bin> initialSolution = greedyRandomizedConstruction(items, binWidth, binHeight);
//            List<Model.Bin> improvedSolution = localSearch(initialSolution, binWidth, binHeight, random);

            //int fitness = calculateFitness(improvedSolution);

//            if (fitness < bestFitness) {
//                bestSolution = improvedSolution;
//                bestFitness = fitness;
//            }
        }

        return bestSolution;
    }
}

