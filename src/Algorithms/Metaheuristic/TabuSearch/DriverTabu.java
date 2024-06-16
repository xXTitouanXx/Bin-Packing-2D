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

        int maxIterations = 1000;
        int tabuListSize = 50;
        final List<Bin>[] currentSolution = new List[]{initialize(dataSet.getItems(), dataSet.getBinWidth(), dataSet.getBinHeight())};
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
                    Thread.sleep(100);
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

    private List<Bin> initialize(List<Item> items, int binWidth, int binHeight) {
        List<Bin> bins = new ArrayList<>();
        Random random = new Random();

        for (Item item : items) {
            boolean placed = false;
            while (!placed) {
                // Try to place the item in an existing bin
                if (!bins.isEmpty()) {
                    Bin randomBin = bins.get(random.nextInt(bins.size()));
                    if (randomBin.tryAddItem(item)) {
                        placed = true;
                    }
                }
                // If the item cannot be placed in any existing bin, create a new bin
                if (!placed) {
                    Bin newBin = new Bin(binWidth, binHeight);
                    newBin.tryAddItem(item);
                    bins.add(newBin);
                    placed = true;
                }
            }
        }

        return bins;
    }

    private List<Move> generateNeighbors(List<Bin> currentSolution) {
        List<Move> neighbors = new ArrayList<>();

        for (int i = 0; i < currentSolution.size(); i++) {
            for (int j = i + 1; j < currentSolution.size(); j++) {
                Bin bin1 = currentSolution.get(i);
                Bin bin2 = currentSolution.get(j);

                List<Item> items1 = new ArrayList<>(bin1.getItems());
                List<Item> items2 = new ArrayList<>(bin2.getItems());

                for (Item item1 : items1) {
                    for (Item item2 : items2) {
                        if (bin1.canFit(item2, item1.getX(), item1.getY()) && bin2.canFit(item1, item2.getX(), item2.getY())) {
                            List<Bin> newSolution = deepCopyBins(currentSolution);
                            Bin newBin1 = newSolution.get(i);
                            Bin newBin2 = newSolution.get(j);

                            newBin1.removeItem(item1);
                            newBin2.removeItem(item2);

                            item1.setX(item2.getX());
                            item1.setY(item2.getY());
                            item2.setX(item1.getX());
                            item2.setY(item1.getY());

                            newBin1.addItem(item2);
                            newBin2.addItem(item1);

                            int newFitness = calculateFitness(newSolution);
                            neighbors.add(new Move(newSolution, newFitness));
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
