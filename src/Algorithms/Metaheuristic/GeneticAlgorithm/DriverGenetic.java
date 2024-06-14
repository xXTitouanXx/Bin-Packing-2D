package Algorithms.Metaheuristic.GeneticAlgorithm;

import Algorithms.Metaheuristic.Metaheuristic;
import GUI.Component.BinPanel;
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
    public void solveBinPacking2D(DataSet dataSet) {
        int populationSize = 50;
        int generations = 100;
        double mutationRate = 0.1;

        final List<PopMember>[] population = new List[]{initializePopulation(dataSet.getItems(), populationSize)};
        evaluatePopulation(population[0], dataSet.getBinWidth(), dataSet.getBinHeight());

        SwingWorker<Void, List<Bin>> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int generation = 0; generation < generations; generation++) {
                    population[0] = evolvePopulation(population[0], mutationRate, dataSet.getBinWidth(), dataSet.getBinHeight());
                    PopMember bestSolution = findBestSolution(population[0]);
                    publish(bestSolution.getBins());

                    // Pause pour permettre l'affichage des étapes
                    Thread.sleep(100);
                }
                return null;
            }

            @Override
            protected void process(List<List<Bin>> chunks) {
                List<Bin> latestBins = chunks.get(chunks.size() - 1);
                binPanel.setBins(latestBins);
                binPanel.repaint();
            }

            @Override
            protected void done() {
                try {
                    PopMember bestSolution = findBestSolution(population[0]);
                    binPanel.setBins(bestSolution.getBins());
                    binPanel.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    private List<PopMember> initializePopulation(List<Item> items, int populationSize) {
        List<PopMember> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            List<Item> shuffledItems = new ArrayList<>(items);
            java.util.Collections.shuffle(shuffledItems);
            population.add(new PopMember(shuffledItems.toArray(new Item[0])));
        }
        return population;
    }

    private void evaluatePopulation(List<PopMember> population, int binWidth, int binHeight) {
        for (PopMember member : population) {
            member.evaluate(binWidth, binHeight);
        }
    }

    private List<PopMember> evolvePopulation(List<PopMember> population, double mutationRate, int binWidth, int binHeight) {
        List<PopMember> newPopulation = new ArrayList<>();
        // Conserver les meilleurs individus (élitisme)
        int elitismCount = 2;
        population.sort(Comparator.comparingInt(PopMember::getFitness));
        for (int i = 0; i < elitismCount; i++) {
            newPopulation.add(population.get(i));
        }
        for (int i = elitismCount; i < population.size(); i++) {
            PopMember parent1 = selectParent(population);
            PopMember parent2 = selectParent(population);
            PopMember child = crossover(parent1, parent2, binWidth, binHeight);
            mutate(child, mutationRate);
            newPopulation.add(child);
        }
        return newPopulation;
    }

    private PopMember selectParent(List<PopMember> population) {
        // Sélection par roulette
        int totalFitness = population.stream().mapToInt(PopMember::getFitness).sum();
        int rouletteSpin = random.nextInt(totalFitness);
        int cumulativeFitness = 0;
        for (PopMember member : population) {
            cumulativeFitness += member.getFitness();
            if (cumulativeFitness > rouletteSpin) {
                return member;
            }
        }
        return population.get(population.size() - 1); // Fallback
    }

    private PopMember crossover(PopMember parent1, PopMember parent2, int binWidth, int binHeight) {
        Item[] order1 = parent1.getOrder();
        Item[] order2 = parent2.getOrder();
        Item[] childOrder = new Item[order1.length];

        int crossoverPoint = random.nextInt(order1.length);
        for (int i = 0; i < crossoverPoint; i++) {
            childOrder[i] = order1[i];
        }
        int index = crossoverPoint;
        for (Item item : order2) {
            if (!contains(childOrder, item)) {
                childOrder[index++] = item;
            }
        }

        PopMember child = new PopMember(childOrder);
        child.evaluate(binWidth, binHeight);
        return child;
    }

    private void mutate(PopMember member, double mutationRate) {
        if (random.nextDouble() < mutationRate) {
            int index1 = random.nextInt(member.getOrder().length);
            int index2 = random.nextInt(member.getOrder().length);
            Item temp = member.getOrder()[index1];
            member.getOrder()[index1] = member.getOrder()[index2];
            member.getOrder()[index2] = temp;
            member.evaluate(member.getBins().get(0).getWidth(), member.getBins().get(0).getHeight());
        }
    }

    private boolean contains(Item[] array, Item item) {
        for (Item i : array) {
            if (i != null && i.equals(item)) {
                return true;
            }
        }
        return false;
    }

    private PopMember findBestSolution(List<PopMember> population) {
        PopMember bestSolution = population.get(0);
        for (PopMember member : population) {
            if (member.getFitness() < bestSolution.getFitness()) {
                bestSolution = member;
            }
        }
        return bestSolution;
    }
}
