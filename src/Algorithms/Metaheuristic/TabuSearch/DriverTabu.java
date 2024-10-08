package Algorithms.Metaheuristic.TabuSearch;

import Algorithms.Metaheuristic.Metaheuristic;
import GUI.Component.BinPanel;
import GUI.Component.ControlPanel;
import Model.Bin;
import Model.DataSet;
import Model.Item;

import javax.swing.*;
import java.util.*;

public class DriverTabu implements Metaheuristic {
    private BinPanel binPanel;

    public DriverTabu(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet, ControlPanel controlPanel) {
        long startTime = System.currentTimeMillis();

        int maxIterations = 2000;
        int tabuListSize = 25;
        final List<Bin>[] currentSolution = new List[]{this.binPanel.getBins()};
        final List<Bin>[] bestSolution = new List[]{new ArrayList<>(currentSolution[0])};
        final int[] bestFitness = {calculateFitness(bestSolution[0])};

        List<Move> tabuList = new LinkedList<>();
        SwingWorker<Void, List<Bin>> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < maxIterations; i++) {
                    List<Move> neighbors = generateNeighbors(currentSolution[0]);
                    Move bestMove = null;
                    int bestNeighborFitness = Integer.MAX_VALUE;

                    for (Move neighbor : neighbors) {
                        if (!tabuList.contains(neighbor) || neighbor.fitness < bestFitness[0]) {
                            int neighborFitness = calculateFitness(neighbor.solution);

                            if (neighborFitness < bestNeighborFitness) {
                                bestNeighborFitness = neighborFitness;
                                bestMove = neighbor;
                            }
                        }
                    }

                    if (bestMove != null) {
                        currentSolution[0] = bestMove.solution;
                        if (bestNeighborFitness < bestFitness[0]) {
                            bestFitness[0] = bestNeighborFitness;
                            bestSolution[0] = new ArrayList<>(currentSolution[0]);
                        }

                        tabuList.add(bestMove);
                        if (tabuList.size() > tabuListSize) {
                            tabuList.remove(0); // Remove the oldest move
                        }
                    }
                    publish(currentSolution[0]);
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;
                    System.out.println("Iteration: " + i + " - Best Solution Fitness: " + bestFitness[0] +
                            " - Elapsed Time: " + elapsedTime + " ms");
                }
                return null;
            }

            @Override
            protected void process(List<List<Bin>> chunks) {
                binPanel.setBins(chunks.get(chunks.size() - 1));
                binPanel.repaint();
            }

            @Override
            protected void done() {
                try {
                    binPanel.setBins(bestSolution[0]);
                    binPanel.repaint();
                    controlPanel.enableButtons();
                    long totalTime = System.currentTimeMillis() - startTime;
                    System.out.println("Total elapsed time: " + totalTime + " ms");
                    System.out.println("Finished tabu search with best solution of " + bestSolution[0].size() + " bins.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    public void setBinPanel(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    private List<Move> generateNeighbors(List<Bin> currentSolution) {
        List<Move> neighbors = new ArrayList<>();

        for (int i = 0; i < currentSolution.size(); i++) {
            for (int j = i + 1; j < currentSolution.size(); j++) {
                Bin bin1 = currentSolution.get(i);
                Bin bin2 = currentSolution.get(j);
                for (Item item1 : bin1.getItems()) {
                    for (Item item2 : bin2.getItems()) {
                        List<Bin> tmp = deepCopyBins(currentSolution);
                        bin1.removeItem(item1);
                        if (bin1.tryAddItem(item2)) {
                            bin2.removeItem(item2);
                            if (bin2.tryAddItem(item1)) {
                                int newFitness = calculateFitness(currentSolution);
                                neighbors.add(new Move(currentSolution, newFitness));
                                // reset solution
                                currentSolution = deepCopyBins(tmp);
                            } else {
                                bin2.addItem(item2);
                            }
                        } else {
                            bin1.addItem(item1);
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    private List<Bin> deepCopyBins(List<Bin> bins) {
        List<Bin> newBins = new ArrayList<>();
        for (Bin bin : bins) {
            Bin newBin = new Bin(bin.getWidth(), bin.getHeight());
            for (Item item : bin.getItems()) {
                Item newItem = new Item(item);
                newBin.addItem(newItem);
            }
            newBins.add(newBin);
        }
        return newBins;
    }

    private int calculateFitness(List<Bin> solution) {
        int binPenalty = 1000;
        int remainingSpacePenalty = 1;
        int totalBinsUsed = solution.size();
        int totalRemainingSpace = 0;
        for (Bin bin : solution) {
            int binWidth = bin.getWidth();
            int binHeight = bin.getHeight();
            int usedSpace = 0;
            for (Item item : bin.getItems()) {
                usedSpace += item.getWidth() * item.getHeight();
            }
            int binTotalSpace = binWidth * binHeight;
            int binRemainingSpace = binTotalSpace - usedSpace;
            totalRemainingSpace += binRemainingSpace;
        }
        return (binPenalty * totalBinsUsed) + (remainingSpacePenalty * totalRemainingSpace);
    }
}