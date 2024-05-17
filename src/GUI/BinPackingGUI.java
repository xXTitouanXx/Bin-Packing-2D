package GUI;

import Algorithms.Solver;
import GUI.Component.BinPanel;
import GUI.Component.ControlPanel;
import GUI.Component.ItemPanel;
import Model.DataSet;
import Util.DataSetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BinPackingGUI extends JFrame {
    private JComboBox<String> dataSetComboBox;
    private JComboBox<String> metaheuristicComboBox;
    private BinPanel binPanel;
    private ItemPanel itemPanel;
    private Solver solver;

    public BinPackingGUI() {
        setTitle("Bin Packing 2D Solver");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);

        // DataSet dataSet = DataSetLoader.loadDataSet("C:/Polytech_ingenieur/OptDiscrete/binpacking2d-01.bp2d");
        DataSet dataSet = DataSetLoader.loadDataSet("E:/Polytech/4A/OptDiscrete/binpacking2d-01.bp2d");
        itemPanel = new ItemPanel(dataSet.getItems());
        binPanel = new BinPanel(null);
        ControlPanel controlPanel = new ControlPanel(this, itemPanel);

        solver = new Solver(binPanel);
        System.out.println("On affiche le items");

        add(controlPanel, BorderLayout.NORTH);
        add(itemPanel, BorderLayout.CENTER);
        controlPanel.getDataSetComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(binPanel);
                add(itemPanel);
                revalidate();
                repaint();
            }
        });
    }

    public void solveBinPacking2D(String dataSetName, String metaheuristicName) {
        System.out.println("On commence le Test");
        remove(itemPanel);
        add(binPanel);
        revalidate();
        repaint();
        solver.solve(dataSetName, metaheuristicName);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BinPackingGUI().setVisible(true);
            }
        });
    }
}
