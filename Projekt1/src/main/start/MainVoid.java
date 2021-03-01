package start;


import EnginePackage.IEngine;
import EnginePackage.JungleEngine;
import GUIPackage.MapFrame;
import GUIPackage.StatisticsCounter;
import MapPackage.JungleMap;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class MainVoid {
    public static void main (String[] args)
    {
        JSONParser jsonParser=new JSONParser();
        try(FileReader reader=new FileReader("src/parameters.json")) {
            //loading parameters from json
            Object obj=jsonParser.parse(reader);
            org.json.simple.JSONObject jsonObject=(org.json.simple.JSONObject) obj;
            Long temporaryLong= (Long) jsonObject.get("SteppeWidth");
            int SteppeWidth= temporaryLong.intValue();

            //rest is the same as above
            int SteppeHeight=((Long) jsonObject.get("SteppeHeight")).intValue();
            int JungleWidth=((Long) jsonObject.get("JungleWidth")).intValue();
            int JungleHeight=((Long) jsonObject.get("JungleHeight")).intValue();
            int InitialGrass=((Long) jsonObject.get("InitialGrass")).intValue();
            int InitialJungleGrass=((Long) jsonObject.get("InitialJungleGrass")).intValue();
            int GrassChance=((Long) jsonObject.get("GrassChance")).intValue();
            int JungleChance=((Long) jsonObject.get("JungleChance")).intValue();
            int PlantProfit=((Long) jsonObject.get("PlantProfit")).intValue();
            int InitialAnimals=((Long) jsonObject.get("InitialAnimals")).intValue();
            int InitialEnergy=((Long) jsonObject.get("InitialEnergy")).intValue();
            int DailyLoss=((Long) jsonObject.get("DailyLoss")).intValue();
            int TimeBetweenStages=((Long) jsonObject.get("TimeBetweenStages")).intValue();
            int delay=((Long) jsonObject.get("delay")).intValue();

            JungleMap map = new JungleMap(
                    SteppeWidth,
                    SteppeHeight,
                    JungleWidth,
                    JungleHeight,
                    InitialGrass,
                    InitialJungleGrass,
                    PlantProfit);
            StatisticsCounter SC = new StatisticsCounter(map);
            MapFrame MF = new MapFrame(map,SC,InitialEnergy);

            IEngine engine = new JungleEngine(map,
                    InitialAnimals,
                    InitialEnergy,
                    GrassChance,
                    JungleChance,
                    //GrassChance and JungleChance are integer values
                    //they represent how many times a program will try to find a suitable place
                    //for a plant
                    //same goes with InitialGrass and InitialJungleGrass for map
                    //it is possible that a plant may not spawn on some days
                    DailyLoss,
                    MF,
                    TimeBetweenStages,
                    SC);
            javax.swing.Timer timer = new javax.swing.Timer(delay, engine);
            MF.setTimer(timer);
            timer.start();

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
