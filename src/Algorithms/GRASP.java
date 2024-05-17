package Algorithms;

import Algorithms.Metaheuristic;
import GUI.Component.BinPanel;
import Model.Bin;
import Model.DataSet;
import Model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

 /*public class GRASP implements Metaheuristic {
   private BinPanel binPanel;

    public GRASP(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet) {
        List<Item> items = dataSet.getItems();
        int binWidth = dataSet.getBinWidth();
        int binHeight = dataSet.getBinHeight();
        int maxIterations = 50;

        List<Bin> solution = null;
        for (int i = 0; i < maxIterations; i++) {
            solution = graspBinPacking(items, binWidth, binHeight, maxIterations);
            binPanel.updateBins(solution);
        }
        binPanel.setBins(solution);
        binPanel.repaint();
        System.out.println("Algorithms.GRASP algorithm finished.");
    }


    private List<Bin> graspBinPacking(List<Item> items, int binWidth, int binHeight, int maxIterations) {
        List<Bin> bestSolution = null;
        int bestFitness = Integer.MAX_VALUE;
        Random random = new Random();
        for (int i = 0; i < maxIterations; i++) {
            List<Bin> initialSolution = greedyRandomizedConstruction(items, binWidth, binHeight);
            List<Bin> improvedSolution = localSearch(initialSolution, binWidth, binHeight, random);

            int fitness = calculateFitness(improvedSolution);

            if (fitness < bestFitness) {
                bestSolution = improvedSolution;
                bestFitness = fitness;
            }
        }

        return bestSolution;
    }

    private List<Bin> greedyRandomizedConstruction(List<Item> items, int binWidth, int binHeight) {
        List<Bin> solution = new ArrayList<>();
        List<Item> candidates = new ArrayList<>(items);
        Random random = new Random();
        while (!candidates.isEmpty()) {
            List<Item> RCL = buildRCL(candidates, solution, binWidth, binHeight);
            if (RCL.isEmpty()) {
                break; // Aucun candidat valide n'est disponible
            }

            Item selected = RCL.get(random.nextInt(RCL.size()));
            solution.add(new Bin(selected)); // Ajoutez l'élément sélectionné à la solution
            candidates.remove(selected); // Retirez l'élément sélectionné de la liste des candidats
        }

        return solution;
    }

    private int calculateFitness(List<Bin> solution) {
        return solution.size();
    }
}*/

