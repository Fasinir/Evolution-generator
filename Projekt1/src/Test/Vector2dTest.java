import AnimalAndMoving.Vector2d;
import org.junit.Assert;
import org.junit.Test;

public class Vector2dTest {
    @Test
    public void moduloAddTest()
    {
        Vector2d vec1= new Vector2d(2,2);
        Vector2d vec2= new Vector2d(3,3);
        Vector2d vec3= vec1.moduloAdd(vec2,4,5);
        Assert.assertEquals(vec3,new Vector2d(1,0));
    }
}
