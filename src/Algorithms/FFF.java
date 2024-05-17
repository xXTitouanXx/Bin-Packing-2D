package Algorithms;

import GUI.Component.BinPanel;
import Model.DataSet;
import Model.Item;
import Model.Bin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FFF implements Metaheuristic {
    private BinPanel binPanel;

    public FFF(BinPanel binPanel) {
        this.binPanel = binPanel;
    }

    @Override
    public void solveBinPacking2D(DataSet dataSet) {
        List<Item> items = dataSet.getItems();
        int binWidth = dataSet.getBinWidth() /*/ 10*/;
        int binHeight = dataSet.getBinHeight() /*/ 10*/;
        binPanel.setBins(FFF(items, binWidth, binHeight));
        binPanel.repaint();
        System.out.println("Nombre de bins : " + FFF(items, binWidth, binHeight).size());
        System.out.println("FFF algorithm finished.");
    }

    //    private List<Bin> FFF(List<Item> items, int binWidth, int binHeight) {
//        List<Bin> bins = new ArrayList<>();
//        Random random = new Random();
//
//        List<Item> shuffledItems = new ArrayList<>(items);
//        Collections.shuffle(shuffledItems, random);
//
//        Bin currentBin = new Bin(binWidth, binHeight);
//        bins.add(currentBin);
//
//        for (int i = 0; i < shuffledItems.size(); i++) {
//            boolean placed = false;
//            System.out.println("Nombre d'itérations : " + i);
//
//            // Parcourir les positions disponibles dans le bin, en commençant par le coin inférieur gauche
//            if (currentBin.findPositionForItem(shuffledItems.get(i))) {
//                System.out.println("item ajouté");
//                currentBin.addItem(shuffledItems.get(i));
//                placed = true;
//                break;
//            }
//            if (!placed) {
//                Item rotatedItem = shuffledItems.get(i).rotate();
//                if (currentBin.findPositionForItem(rotatedItem)) {
//                    currentBin.addItem(rotatedItem);
//                    placed = true;
//                    break;
//                }
//            }
//            // Créer un nouveau bin si nécessaire
//            if (!placed) {
//                currentBin = new Bin(binWidth, binHeight);
//                bins.add(currentBin);
//                i--;
//            }
//        }
//        return bins;
//    }
    private List<Bin> FFF(List<Item> items, int binWidth, int binHeight) {
        List<Bin> bins = new ArrayList<>();
        Random random = new Random();

        List<Item> shuffledItems = new ArrayList<>(items);
        //Collections.shuffle(shuffledItems, random);
        for (Item item : shuffledItems) {
            System.out.println("ordre : " + item.getId());
        }
        Bin currentBin = new Bin(binWidth, binHeight);
        bins.add(currentBin);
        for (Item item : shuffledItems) {
            System.out.println("ordre inté for : " + item.getId());
            boolean placed = false;
            System.out.println("Find position : " + currentBin.findPositionForItem(item));
            if (currentBin.findPositionForItem(item)) {
                currentBin.addItem(item);
                System.out.println("item ajouté");
                placed = true;
            }
            if (!placed) {
                Item rotatedItem = item.rotate();
                if (currentBin.findPositionForItem(rotatedItem)) {
                    currentBin.addItem(rotatedItem);
                    System.out.println("item rotate ajouté");
                    break;
                }
            }
            if (!placed) {
                currentBin = new Bin(binWidth, binHeight);
                bins.add(currentBin);
                System.out.println("item ajouté dans le bin");
                currentBin.addItem(item);
            }
        }
        return bins;
    }
}
