package Algorithms.Metaheuristic.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Random;
import Model.Item;
import Model.PopMember;

public class Population
{
    private ArrayList<PopMember> popList;
    private int popSize;
    private Random r;

    public Population(int popSize)
    {
        popList = new ArrayList<PopMember>(popSize);
        this.popSize = popSize;
        r = new Random();
    }

    public void insert(PopMember child)
    {
        double currentFitness;
        PopMember currentPerson;
        int prevIndex;

        popList.add(child);

        for(int i = 1; i < popList.size(); i++)
        {
            currentFitness = popList.get(i).getFitness();
            currentPerson = popList.get(i);
            prevIndex = i - 1;

            while(prevIndex >= 0 && popList.get(prevIndex).getFitness() > currentFitness)
            {
                popList.set(prevIndex+1, popList.get(prevIndex));
                prevIndex--;
            }

            popList.set(prevIndex+1, currentPerson);
        }

        if(popList.size() > popSize)
        {
            popList.remove(popList.size()-1);
        }
    }

    public PopMember selectParent()
    {
        int x = r.nextInt(popSize);
        int y = r.nextInt(popSize-1);
        int z = y;

        if(x <= y)
        {
            z = x;
        }
        return popList.get(z);
    }

    public ArrayList<PopMember> getPopulation()
    {
        return popList;
    }
}
