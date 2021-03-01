package Comparators;

import AnimalAndMoving.Animal;

import java.util.Comparator;

public class AnimalEnergyComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal first,Animal second)
    {
        int e1=first.getEnergy();
        int e2=second.getEnergy();
        if (e1<e2)
        {
            return 1;
        }
        else if(e1==e2)
        {
            Comparator<Animal> tmpcomparator=new AnimalIdComparator();
            return tmpcomparator.compare(first,second);
        }
        return -1;
    }
}
