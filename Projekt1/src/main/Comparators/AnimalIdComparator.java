package Comparators;

import AnimalAndMoving.Animal;

import java.util.Comparator;

public class AnimalIdComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal o1, Animal o2) {
        int ID1= o1.getIdnumber();
        int ID2= o2.getIdnumber();
        if (ID1<ID2)
        {
            return 1;
        }
        else if (ID1>ID2)
        {
            return -1;
        }
        return 0;
    }
}
