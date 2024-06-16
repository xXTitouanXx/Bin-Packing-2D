package Algorithms.Metaheuristic;

import GUI.Component.BinPanel;
import GUI.Component.ControlPanel;
import Model.DataSet;

public interface Metaheuristic {
    void solveBinPacking2D(DataSet dataSet, ControlPanel controlPanel);
    void setBinPanel(BinPanel binPanel);
}
