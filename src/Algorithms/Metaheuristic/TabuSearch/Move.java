package Algorithms.Metaheuristic.TabuSearch;

import Model.Bin;

import java.util.ArrayList;
import java.util.List;

public class Move {
    List<Bin> solution;
    int fitness;

    // Constructeur pour initialiser une instance de Move avec une solution et son fitness
    Move(List<Bin> solution, int fitness) {
        this.solution = new ArrayList<>(solution);
        this.solution.removeIf(e -> e.getItems().isEmpty());
        this.fitness = fitness;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Move move = (Move) obj;
        // Comparer les solutions pour vérifier si elles sont identiques
        return this.solution.equals(move.solution);
    }

    @Override
    public int hashCode() {
        // Générer un code de hachage basé sur la solution
        return this.solution.hashCode();
    }
}
