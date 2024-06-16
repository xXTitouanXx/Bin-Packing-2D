package Algorithms.Metaheuristic.TabuSearch;

import Algorithms.Heuristic.FirstFitFirst;
import Algorithms.Metaheuristic.Metaheuristic;
import GUI.Component.BinPanel;
import GUI.Component.ControlPanel;
import Model.Bin;
import Model.DataSet;
import Model.Item;

import java.util.*;

public class DriverTabu implements Metaheuristic {
    private BinPanel binPanel;

    public DriverTabu(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet, ControlPanel controlPanel) {
        List<Item> items = dataSet.getItems();
        int binWidth = dataSet.getBinWidth() /*/ 10*/;
        int binHeight = dataSet.getBinHeight() /*/ 10*/;
        binPanel.setBins(tabuSearch(items, binWidth, binHeight));
        binPanel.repaint();
//        System.out.println("Tabu search algorithm finished.");
    }

    private List<Bin> tabuSearch(List<Item> items, int binWidth, int binHeight) {
        int maxIterations = 1000;
        int tabuListSize = 50;

        FirstFitFirst fffAlgorithm = new FirstFitFirst();
        List<Bin> currentSolution = fffAlgorithm.FFF(items, binWidth, binHeight);
        List<Bin> bestSolution = new ArrayList<>(currentSolution);
        int bestFitness = calculateFitness(bestSolution);

        List<Move> tabuList = new LinkedList<>();

        for (int i = 0; i < maxIterations; i++) {
            List<Move> neighbors = generateNeighbors(currentSolution);
            Move bestMove = null;
            int bestNeighborFitness = Integer.MAX_VALUE;
//            System.out.println("list neighbors: " + neighbors);
            for (Move neighbor : neighbors) {
//                System.out.println(neighbor);
                if (!tabuList.contains(neighbor) || neighbor.fitness < bestFitness) {
                    int neighborFitness = calculateFitness(neighbor.solution);
//                    System.out.println("neighbor fitness: " + neighborFitness);

                    if (neighborFitness < bestNeighborFitness) {
//                        System.out.println("Iteration " + i + ", neighbor fitness: " + neighborFitness+ ", best neighbor fitness: " + bestNeighborFitness );

                        bestNeighborFitness = neighborFitness;
                        bestMove = neighbor;
                    }
                }
            }

            if (bestMove != null) {
                currentSolution = bestMove.solution;
                if (bestNeighborFitness < bestFitness) {
                    bestFitness = bestNeighborFitness;
                    bestSolution = new ArrayList<>(currentSolution);
                }

                tabuList.add(bestMove);
                if (tabuList.size() > tabuListSize) {
                    tabuList.remove(0); // Remove the oldest move
                }
            }
            //System.out.println("Iteration " + i + ", best fitness: " + calculateFitness(currentSolution) + ", current bins: " + currentSolution.size() + ", best bins: " + bestSolution.size());
        }

        return bestSolution;
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
                        // Essayer d'échanger item1 et item2
                        if (bin1.canFit(item2, item1.getX(), item1.getY()) && bin2.canFit(item1, item2.getX(), item2.getY())) {
                            // Créer une nouvelle solution avec l'échange
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
//    private List<Bin> tabuSearch(List<Item> items, int binWidth, int binHeight) {
//        int maxIterations = 1000;
//        int tabuListSize = 50;
//
//        FirstFitFirst fffAlgorithm = new FirstFitFirst();
//        List<Bin> currentSolution = fffAlgorithm.FFF(items, binWidth, binHeight);
//        List<Bin> bestSolution = new ArrayList<>(currentSolution);
//        List<List<Bin>> tabuList = new ArrayList<>();
//
//        for (int iteration = 0; iteration < maxIterations; iteration++) {
//            List<List<Bin>> neighbors = generateNeighbors(currentSolution, items, binWidth, binHeight);
//            List<Bin> bestNeighbor = null;
//            double bestNeighborScore = Double.MAX_VALUE;
//            for (List<Bin> neighbor : neighbors) {
//                double neighborScore = evaluateSolution(neighbor);
//                if (!tabuList.contains(neighbor) && neighborScore < bestNeighborScore) {
//                    bestNeighbor = neighbor;
//                    bestNeighborScore = neighborScore;
//                }
//            }
//
//            if (bestNeighbor == null) {
//                break; // Aucun voisin valide trouvé, arrêtez l'algorithme
//            }
//
//            currentSolution = bestNeighbor;
//            if (evaluateSolution(currentSolution) < evaluateSolution(bestSolution)) {
//                bestSolution = new ArrayList<>(currentSolution);
//            }
//
//            tabuList.add(currentSolution);
//            if (tabuList.size() > tabuListSize) {
//                tabuList.remove(0);
//            }
//            updateBinPanel(currentSolution);
//
//            System.out.println("Iteration " + iteration + ", current bins: " + currentSolution.size() + ", best bins: " + bestSolution.size());
//        }
//        System.out.println("Contenu de chaque bin : " + bestSolution);
//        return bestSolution;
//    }
//
//    private List<List<Bin>> generateNeighbors(List<Bin> currentSolution, List<Item> items, int binWidth, int binHeight) {
//        List<List<Bin>> neighbors = new ArrayList<>();
//        Random random = new Random();
//
//        for (int i = 0; i < 10; i++) { // Générer 10 voisins
//            // Copie en profondeur de la solution actuelle
//            List<Bin> neighbor = new ArrayList<>(currentSolution);
//
//            // Appliquer une transformation locale
//            if (random.nextBoolean()) {
//                // Effectuer un swap
//                performSwap(neighbor, random);
//            } else {
//                // Effectuer un relocate
//                performRelocate(neighbor, random);
//            }
//
//            neighbors.add(neighbor);
//        }
//
//        return neighbors;
//    }
//
//    private void performSwap(List<Bin> bins, Random random) {
//        // Choisir deux bins aléatoires
//        int binIndex1 = random.nextInt(bins.size());
//        int binIndex2 = random.nextInt(bins.size());
//
//        // Assurez-vous qu'ils ne sont pas les mêmes
//        while (binIndex2 == binIndex1) {
//            binIndex2 = random.nextInt(bins.size());
//        }
//
//        Bin bin1 = bins.get(binIndex1);
//        Bin bin2 = bins.get(binIndex2);
//
//        // Choisir un item aléatoire dans chaque bin
//        if (!bin1.getItems().isEmpty() && !bin2.getItems().isEmpty()) {
//            int itemIndex1 = random.nextInt(bin1.getItems().size());
//            int itemIndex2 = random.nextInt(bin2.getItems().size());
//
//            Item item1 = bin1.getItems().get(itemIndex1);
//            Item item2 = bin2.getItems().get(itemIndex2);
//
//            // Échanger les items
//            bin1.removeItem(item1);
//            bin2.removeItem(item2);
//            if (bin1.tryAddItem(item2) && bin2.tryAddItem(item1)) {
//                // Échange réussi
//            } else {
//                // Rétablir si l'échange échoue
//                bin1.addItem(item1);
//                bin2.addItem(item2);
//            }
//        }
//    }
//
//    private void performRelocate(List<Bin> bins, Random random) {
//        // Choisir un bin source et un bin cible aléatoires
//        int sourceBinIndex = random.nextInt(bins.size());
//        int targetBinIndex = random.nextInt(bins.size());
//
//        // Assurez-vous qu'ils ne sont pas les mêmes
//        while (targetBinIndex == sourceBinIndex) {
//            targetBinIndex = random.nextInt(bins.size());
//        }
//
//        Bin sourceBin = bins.get(sourceBinIndex);
//        Bin targetBin = bins.get(targetBinIndex);
//
//        // Choisir un item aléatoire dans le bin source
//        if (!sourceBin.getItems().isEmpty()) {
//            int itemIndex = random.nextInt(sourceBin.getItems().size());
//            Item item = sourceBin.getItems().get(itemIndex);
//
//            // Déplacer l'item
//            sourceBin.removeItem(item);
//            if (!targetBin.tryAddItem(item)) {
//                // Rétablir si le déplacement échoue
//                sourceBin.addItem(item);
//            }
//        }
//    }
//
//    private double evaluateSolution(List<Bin> solution) {
//        double score = 0.0;
//        for (Bin bin : solution) {
//            double binUtilization = calculateBinUtilization(bin);
//            score += 1.0 / binUtilization; // Moins l'utilisation est efficace, plus la pénalité est élevée
//        }
//        score += solution.size(); // Pénalité pour le nombre de bins
//        return score;
//    }
//
//    private double calculateBinUtilization(Bin bin) {
//        double totalArea = bin.getWidth() * bin.getHeight();
//        double usedArea = 0.0;
//        for (Item item : bin.getItems()) {
//            usedArea += item.getWidth() * item.getHeight();
//        }
//        return usedArea / totalArea;
//    }
//
//    private void updateBinPanel(List<Bin> solution) {
//        // Efface le panneau
//        binPanel.clearBins();
//
//        // Ajoute chaque bin avec ses items au panneau
//        binPanel.setBins(solution);
//        binPanel.repaint();
//    }
}
