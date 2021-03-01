import AnimalAndMoving.Animal;
import AnimalAndMoving.Vector2d;
import Generators.GeneGenerator;
import MapPackage.JungleMap;
import MapPackage.JungleMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AnimalTest {

    @Test
    public void copulateTest()
    {
        JungleMap map= new JungleMap(
                3,
                3,
                0,
                0,
                0,
                0,
                0
                );
        Animal parent1=new Animal(map,new Vector2d(1,1),100, GeneGenerator.RandomGenes(),0);
        Animal parent2=new Animal(map,new Vector2d(1,1),100, GeneGenerator.RandomGenes(),0);
        System.out.println(map.toString());
        Animal child=parent1.copulate(parent2,map.findSuitablePlace(parent1.getPosition()),0);
        System.out.println(map.toString());
        Assert.assertEquals(50,child.getEnergy());
        System.out.println(Arrays.toString(child.getGenes()));
        Assert.assertFalse(AnyZero(child.getGenes()));



    }
    private boolean AnyZero(int[] genes)
    {
        int[] count = new int[8];
        for (int i=0;i< genes.length;i++)
        {
            count[genes[i]]+=1;
        }
        boolean anyzero=false;
        for (int i=0;i<8;i++)
        {
            if (count[i]==0)
            {
                anyzero=true;
            }
        }
        return anyzero;
    }

    @Test
    public void SubtractEnergyTest()
    {
        JungleMap map= new JungleMap(
                3,
                3,
                0,
                0,
                0,
                0,
                0);
        Animal tester1=new Animal(map,new Vector2d(1,1),100, GeneGenerator.RandomGenes(),0);
        Animal tester2=new Animal(map,new Vector2d(1,1),100, GeneGenerator.RandomGenes(),0);
        tester1.SubtractEnergy(100);
        tester2.SubtractEnergy(50);
        Assert.assertEquals(0,tester1.getEnergy());
        Assert.assertEquals(50,tester2.getEnergy());
    }

}
