import AnimalAndMoving.Animal;
import AnimalAndMoving.Vector2d;
import Generators.GeneGenerator;
import MapPackage.JungleMap;
import org.junit.Assert;
import org.junit.Test;

public class AnimalIDTest {
    @Test
    public void globalidTest()
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
        Animal tester1=new Animal(map,new Vector2d(1,1),100, GeneGenerator.RandomGenes(),0);
        Animal tester2=new Animal(map,new Vector2d(1,1),100, GeneGenerator.RandomGenes(),0);
        Assert.assertEquals(0,tester1.getIdnumber());
        Assert.assertEquals(1,tester2.getIdnumber());
    }
}
