package start;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class start {
    public static void main(String[] args)
    {
        //loading parameters from json
        //recommendend number of maps is 1 or 2
        JSONParser jsonParser=new JSONParser();
        try(FileReader reader=new FileReader("src/parameters.json")) {

            Object obj=jsonParser.parse(reader);
            org.json.simple.JSONObject jsonObject=(org.json.simple.JSONObject) obj;
            Long temporaryLong= (Long) jsonObject.get("NumberOfMaps");
            int number=temporaryLong.intValue();

            for (int i=0;i<number;i++)
            {
                MainVoid.main(new String[]{});
            }



        }catch (FileNotFoundException e)
        {
            System.out.println("PARAMETERS FILE NOT FOUND");
        }
        catch (IOException e)
        {
            System.out.println("IO error");
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            System.out.println("Parse exception");
            e.printStackTrace();
        }

    }
}
