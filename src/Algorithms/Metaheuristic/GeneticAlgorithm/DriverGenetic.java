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
        int populationSize = 10; // Taille de la population
        int generations = 100; // Nombre de générations
        double mutationRate = 0.1; // Taux de mutation

        final List<PopMember>[] population = new List[]{initializePopulation(dataSet.getItems(), populationSize)};
        evaluatePopulation(population[0], dataSet.getBinWidth(), dataSet.getBinHeight());

        SwingWorker<Void, List<Bin>> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int generation = 0; generation < generations; generation++) {
                    List<PopMember> intermediateSolutions = selectIntermediateSolutions(population[0], populationSize / 3);
                    population[0] = crossover(intermediateSolutions, populationSize, dataSet.getBinWidth(), dataSet.getBinHeight());

                    for (PopMember member : population[0]) {
                        mutate(member, mutationRate, dataSet.getBinWidth(), dataSet.getBinHeight());
                    }

                    PopMember bestSolution = findBestSolution(population[0]);
                    publish(bestSolution.getBins());

                    System.out.println("Generation: " + generation + " - Best Solution Fitness: " + bestSolution.getFitness());
                    // Affichage des détails si nécessaire

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
            for (Item item : shuffledItems) {
                if (random.nextBoolean()) {
                    item.rotate();
                }
            }
            population.add(new PopMember(shuffledItems.toArray(new Item[0])));
        }
        return population;
    }

    private void evaluatePopulation(List<PopMember> population, int binWidth, int binHeight) {
        for (PopMember member : population) {
            member.evaluate(binWidth, binHeight);
        }
    }

    private List<PopMember> selectIntermediateSolutions(List<PopMember> population, int numSelections) {
        population.sort(Comparator.comparingInt(PopMember::getFitness));

        List<PopMember> intermediateSolutions = new ArrayList<>();
        int totalFitness = population.stream().mapToInt(PopMember::getFitness).sum();

        for (int i = 0; i < numSelections; i++) {
            int rouletteSpin = random.nextInt(totalFitness);
            int cumulativeFitness = 0;

            for (PopMember member : population) {
                cumulativeFitness += member.getFitness();
                if (cumulativeFitness > rouletteSpin) {
                    intermediateSolutions.add(member);
                    break;
                }
            }
        }
        return intermediateSolutions;
    }

    private List<PopMember> crossover(List<PopMember> intermediateSolutions, int populationSize, int binWidth, int binHeight) {
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
                        // Méthode d'ajout aléatoire si aucune bin convenable n'est trouvée
                        addRandomly(child, item, binWidth, binHeight);
                    }
                }
            }

            child.evaluate(binWidth, binHeight);
            newPopulation.add(child);
        }
        return newPopulation;
    }

    private void mutate(PopMember member, double mutationRate, int binWidth, int binHeight) {
        if (random.nextDouble() < mutationRate) {
            int nb = geometricDistribution(mutationRate);

            for (int i = 0; i < nb; i++) {
                int index1 = random.nextInt(member.getOrder().length);
                Item item = member.getOrder()[index1];
                if (random.nextBoolean()) {
                    item.rotate();
                }

                // Find a new random position for the item in the bins
                int index2 = random.nextInt(member.getBins().size());
                Bin bin = member.getBins().get(index2);
                if (!canPlaceItem(item, bin, binWidth, binHeight)) {
                    item.rotate(); // Revert rotation if placement failed
                }
            }
            member.evaluate(binWidth, binHeight); // Réévaluer la fitness après mutation
        }
    }


    // Méthode pour une distribution géométrique
    private int geometricDistribution(double p) {
        return (int) Math.floor(Math.log(random.nextDouble()) / Math.log(1.0 - p));
    }

    // Vérifier si un article peut être placé dans un bac
    private boolean canPlaceItem(Item item, Bin bin, int binWidth, int binHeight) {
        // Implémentez vos conditions spécifiques ici, par exemple, vérifiez les dimensions restantes du bac
        return bin.tryAddItem(item);
    }

    // Méthode pour trouver un bac adapté pour un article dans un membre de population
    private Bin findSuitableBin(PopMember member, Item item, int binWidth, int binHeight) {
        for (Bin bin : member.getBins()) {
            if (bin.tryAddItem(item)) {
                return bin;
            }
        }
        return null;
    }

    // Méthode pour ajouter un article aléatoirement dans un membre de population
    private void addRandomly(PopMember member, Item item, int binWidth, int binHeight) {
        // Implémentez votre méthode d'ajout aléatoire ici
    }

    private PopMember findBestSolution(List<PopMember> population) {
        return population.stream()
                .min(Comparator.comparingInt(PopMember::getFitness))
                .orElseThrow(() -> new IllegalArgumentException("Population is empty"));
    }
}
