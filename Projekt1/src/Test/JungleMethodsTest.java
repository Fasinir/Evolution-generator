import AnimalAndMoving.Animal;
import AnimalAndMoving.MoveDirection;
import AnimalAndMoving.Vector2d;
import GUIPackage.StatisticsCounter;
import MapPackage.JungleMap;
import MapPackage.JungleMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JungleMethodsTest {

    @Test
    public void getStrongestTest()
    {
        JungleMap map= new JungleMap(
                10,
                10,
                5,
                5,
                40,
                10,
                0);
        Animal an1=new Animal(map,new Vector2d(1,1),99,new int[]{1,1,1,1},0);
        Animal an2=new Animal(map,new Vector2d(1,1),101,new int[]{2,2,2,2},0);
        Animal an3=new Animal(map,new Vector2d(1,1),100,new int[]{3,3,3,3},0);
        Animal an4=new Animal(map,new Vector2d(1,1),99,new int[]{3,3,3,3},0);
        Animal an5=new Animal(map,new Vector2d(1,1),101,new int[]{3,3,3,3},0);

        List<Animal> Strongest=new ArrayList<>();
        //Strongest.add(an1);
        Strongest.add(an2);
        Strongest.add(an5);

        List<Animal> TestVar=map.getStrongest(new Vector2d(1,1));
        for (int i=0;i<Strongest.size();i++)
        {
            Assert.assertEquals(Strongest.get(i),TestVar.get(i));
        }
    }
    @Test
    public void movingTest()
    {
        JungleMap map= new JungleMap(
                10,
                10,
                5,
                5,
                40,
                10,
                0);
        Animal tester= new Animal(map,new Vector2d(1,1),100,new int[]{1,1,1,1},0);
        Assert.assertEquals(new Vector2d(1,1),tester.getPosition());

        //move method uses moduloAdd method from Vector2d
        //map is a 10x10 square
        //so no matter the initial position
        //after 10 moves forward Animal should land in the same place
        for (int i=0;i<10;i++)
        {
            tester.move(MoveDirection.FORWARD);
        }
        Assert.assertEquals(new Vector2d(1,1),tester.getPosition());
    }
    @Test
    public void RemoveTheDeadTest()
    {
        JungleMap map= new JungleMap(
                10,
                10,
                5,
                5,
                0,
                0,
                0);
        Animal tester= new Animal(map,new Vector2d(1,1),0,new int[]{1,1,1,1},0);
        Animal teste2= new Animal(map,new Vector2d(1,2),-1,new int[]{1,1,1,1},0);
        System.out.println(map.toString());
        map.RemoveTheDead(new Vector2d(1,1), new StatisticsCounter(map));
        map.RemoveTheDead(new Vector2d(1,2),new StatisticsCounter(map));
        Assert.assertEquals(null,map.getAnimalMap().get(new Vector2d(1,1)));
        Assert.assertEquals(null,map.getAnimalMap().get(new Vector2d(1,2)));
        System.out.println(map.toString());
    }

}
