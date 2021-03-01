import AnimalAndMoving.Animal;
import AnimalAndMoving.MapDirection;
import AnimalAndMoving.MoveDirection;
import AnimalAndMoving.Vector2d;
import MapPackage.JungleMap;
import MapPackage.JungleMap;
import org.junit.Test;

public class VisualizingTest {
JungleMap map= new JungleMap(
        3,
        3,
        1,
        1,
        1,
        0,
        0);
@Test
public void DrawTest()
{
    System.out.println(map.toString());
}
@Test
public void AnimalPlacementTest()
{
    Animal swine1=new Animal(map,new Vector2d(1,1),100,new int[]{1,2,3,4,0},0);
    //Animal swine2=new Animal(map,new Vector2d(1,1),100);
    System.out.println(map.toString());
    swine1.move(MoveDirection.FORWARD);
    System.out.println(map.toString());;

}
}
