package Generators;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class GeneGenerator {
    public static int[] RandomGenes()
    {
        int[] result= new int[32];
        for (int i=0;i<32;i++)
        {
            result[i]=ThreadLocalRandom.current().nextInt(0, 8);
        }
        Arrays.sort(result);
        return result;
    }
}
