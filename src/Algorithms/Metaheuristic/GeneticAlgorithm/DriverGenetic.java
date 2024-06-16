package Algorithms.Metaheuristic.GeneticAlgorithm;

import Algorithms.Metaheuristic.Metaheuristic;
import GUI.Component.BinPanel;
import GUI.Component.ControlPanel;
import Model.Bin;
import Model.DataSet;
import Model.Item;
import Model.PopMember;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DriverGenetic implements Metaheuristic {
    private BinPanel binPanel;
    private Random random = new Random();

    public DriverGenetic(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet, ControlPanel controlPanel) {
        long startTime = System.currentTimeMillis();

        int populationSize = 10; // Taille de la population
        int generations = 100; // Nombre de générations
        double mutationRate = 0.1; // Taux de mutation

        final List<PopMember>[] population = new List[]{initializePopulation(dataSet.getItems(), populationSize)};
        evaluatePopulation(population[0], dataSet.getBinWidth(), dataSet.getBinHeight());

        SwingWorker<Void, List<Bin>> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int generation = 0; generation < generations; generation++) {
                    long generationStartTime = System.currentTimeMillis();

                    List<PopMember> intermediateSolutions = selectIntermediateSolutions(population[0], populationSize / 3);
                    population[0] = crossover(intermediateSolutions, populationSize, dataSet.getBinWidth(), dataSet.getBinHeight());

                    for (PopMember member : population[0]) {
                        mutate(member, mutationRate, dataSet.getBinWidth(), dataSet.getBinHeight());
                    }

                    PopMember bestSolution = findBestSolution(population[0]);
                    publish(bestSolution.getBins());

                    long generationEndTime = System.currentTimeMillis();
                    long generationElapsedTime = generationEndTime - generationStartTime;
                    System.out.println("Generation: " + generation + " - Best Solution Fitness: " + bestSolution.getFitness() +
                            " - Elapsed Time: " + generationElapsedTime + " ms");
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
                    PopMember bestSolution = findBestSolution(population[0]);
                    binPanel.setBins(bestSolution.getBins());
                    binPanel.repaint();
                    controlPanel.enableButtons();
                    long totalTime = System.currentTimeMillis() - startTime;
                    System.out.println("Total elapsed time: " + totalTime + " ms");
                    System.out.println("Finished genetic algorithm with best solution of " + bestSolution.getBins().size() + " bins.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    @Override
    public void setBinPanel(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    private List<PopMember> initializePopulation(List<Item> items, int populationSize) {
        long startTime = System.currentTimeMillis();

        List<PopMember> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            List<Item> shuffledItems = new ArrayList<>(items);
            java.util.Collections.shuffle(shuffledItems);
            for (Item item : shuffledItems) {
                if (random.nextBoolean()) {
                    item.rotate();
                }
            }
            population.add(new PopMember(shuffledItems.toArray(new Item[0])));
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken for initializePopulation(): " + elapsedTime + " ms");

        return population;
    }

    private void evaluatePopulation(List<PopMember> population, int binWidth, int binHeight) {
        long startTime = System.currentTimeMillis();

        for (PopMember member : population) {
            member.evaluate(binWidth, binHeight);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken for evaluatePopulation(): " + elapsedTime + " ms");
    }

    private List<PopMember> selectIntermediateSolutions(List<PopMember> population, int numSelections) {
        long startTime = System.currentTimeMillis();

        List<PopMember> intermediateSolutions = new ArrayList<>();
        int tournamentSize = 5; // Taille du tournoi

        for (int i = 0; i < numSelections; i++) {
            PopMember best = null;
            for (int j = 0; j < tournamentSize; j++) {
                PopMember candidate = population.get(random.nextInt(population.size()));
                if (best == null || candidate.getFitness() < best.getFitness()) {
                    best = candidate;
                }
            }
            intermediateSolutions.add(best);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken for selectIntermediateSolutions(): " + elapsedTime + " ms");

        return intermediateSolutions;
    }

    private List<PopMember> crossover(List<PopMember> intermediateSolutions, int populationSize, int binWidth, int binHeight) {
        long startTime = System.currentTimeMillis();

        List<PopMember> newPopulation = new ArrayList<>();

        while (newPopulation.size() < populationSize) {
            PopMember parent1 = intermediateSolutions.get(random.nextInt(intermediateSolutions.size()));
            PopMember parent2 = intermediateSolutions.get(random.nextInt(intermediateSolutions.size()));

            PopMember child = new PopMember(parent1.getOrder()); // Initialiser avec l'ordre du parent 1

            for (Item item : parent2.getOrder()) {
                if (!child.containsItem(item)) {
                    Bin suitableBin = findSuitableBin(child, item, binWidth, binHeight);
                    if (suitableBin != null) {
                        suitableBin.addItem(item);
                    } else {
                        addRandomly(child, item, binWidth, binHeight);
                    }
                }
            }

            child.evaluate(binWidth, binHeight);
            newPopulation.add(child);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken for crossover(): " + elapsedTime + " ms");

        return newPopulation;
    }

    private void mutate(PopMember member, double mutationRate, int binWidth, int binHeight) {
        long startTime = System.currentTimeMillis();

        if (random.nextDouble() < mutationRate) {
            int nb = geometricDistribution(mutationRate);

            for (int i = 0; i < nb; i++) {
                int index1 = random.nextInt(member.getOrder().length);
                Item item = member.getOrder()[index1];
                if (random.nextBoolean()) {
                    item.rotate();
                }

                int index2 = random.nextInt(member.getBins().size());
                Bin bin = member.getBins().get(index2);
                if (!canPlaceItem(item, bin, binWidth, binHeight)) {
                    item.rotate(); // Revert rotation if placement failed
                }
            }
            member.evaluate(binWidth, binHeight);
            //hillClimb(member, binWidth, binHeight); // Appeler Hill Climbing après mutation
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken for mutate(): " + elapsedTime + " ms");
    }

    private int geometricDistribution(double p) {
        return (int) Math.floor(Math.log(random.nextDouble()) / Math.log(1.0 - p));

    }

    private boolean canPlaceItem(Item item, Bin bin, int binWidth, int binHeight) {
        return bin.tryAddItem(item);
    }

    private Bin findSuitableBin(PopMember member, Item item, int binWidth, int binHeight) {
        for (Bin bin : member.getBins()) {
            if (bin.tryAddItem(item)) {
                return bin;
            }
        }
        return null;
    }

    private void addRandomly(PopMember member, Item item, int binWidth, int binHeight) {
        Bin newBin = new Bin(binWidth, binHeight);
        newBin.addItem(item);
        member.getBins().add(newBin);
    }

    private PopMember findBestSolution(List<PopMember> population) {
        return population.stream()
                .min(Comparator.comparingDouble(PopMember::getFitness))
                .orElseThrow(() -> new IllegalArgumentException("Population is empty"));
    }

    private void hillClimb(PopMember member, int binWidth, int binHeight) {
        long startTime = System.currentTimeMillis();

        boolean improvement = true;

        while (improvement) {
            improvement = false;

            for (int i = 0; i < member.getOrder().length; i++) {
                // Essayez de déplacer ou de faire pivoter l'article i
                Item originalItem = member.getOrder()[i];
                Item mutatedItem = new Item(originalItem);
                if (random.nextBoolean()) {
                    mutatedItem.rotate();
                }
                // Essayez de déplacer l'article vers un autre bac
                int originalBinIndex = findBinIndexContainingItem(member, originalItem);
                for (Bin bin : member.getBins()) {
                    if (bin.tryAddItem(mutatedItem)) {
                        member.getBins().get(originalBinIndex).removeItem(originalItem);
                        member.evaluate(binWidth, binHeight);
                        if (member.getFitness() < member.getFitness()) {
                            improvement = true;
                        } else {
                            bin.removeItem(mutatedItem);
                            member.getBins().get(originalBinIndex).addItem(originalItem);
                            member.evaluate(binWidth, binHeight);
                        }
                    }
                }
            }
        }
    }

    private int findBinIndexContainingItem(PopMember member, Item item) {
        for (int i = 0; i < member.getBins().size(); i++) {
            if (member.getBins().get(i).containsItem(item)) {
                return i;
            }
        }
        return -1; // Should never happen if item is in bins
    }
}


