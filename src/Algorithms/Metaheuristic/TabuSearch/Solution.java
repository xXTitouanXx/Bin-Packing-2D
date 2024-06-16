package Algorithms.Metaheuristic.TabuSearch;

import Model.Bin;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<Bin> bins;
    public int fitness;

    Solution(List<Bin> bins, int fitness) {
        this.bins = new ArrayList<>(bins);
        this.fitness = fitness;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Solution solution = (Solution) obj;
        // Comparer les solutions pour vérifier si elles sont identiques
        return this.bins.equals(solution.bins);
    }

    @Override
    public int hashCode() {
        // Générer un code de hachage basé sur la solution
        return this.bins.hashCode();
    }
}
