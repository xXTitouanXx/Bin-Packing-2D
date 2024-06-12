package Algorithms.Heuristic;

import Algorithms.Metaheuristic.Metaheuristic;
import GUI.Component.BinPanel;
import Model.DataSet;
import Model.Item;
import Model.Bin;

import java.util.ArrayList;
import java.util.List;

public class FirstFitFirst {

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
    public List<Bin> FFF(List<Item> items, int binWidth, int binHeight) {
        List<Bin> bins = new ArrayList<>();

        for (Item item : items) {
            System.out.println("ordre : " + item.getId());
        }
        Bin currentBin = new Bin(binWidth, binHeight);
        bins.add(currentBin);
        for (Item item : items) {
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
