package GUIPackage;

import AnimalAndMoving.Animal;
import AnimalAndMoving.Vector2d;
import MapPackage.Grass;
import MapPackage.JungleMap;


import java.util.ArrayList;

import java.util.Map;

public class StatisticsCounter {
    private JungleMap map;
    private int NumberOfLivingAnimals=0;
    private int NumberOfDeadAnimals=0;
    private long SumOfDeadAnimalsLifespan=0;
    private long SumOfLivingAnimalsEnergy=0;
    private long SumOfLivingAnimalsLifespan=0;
    private int NumberOfChildren;
    private int day=0;
    public StatisticsCounter(JungleMap map)
    {

        this.map=map;
    }
    public void statCount()
    {
        Map<Vector2d, ArrayList<Animal>> AnimalMap=this.map.getAnimalMap();
        ArrayList<Vector2d> Keys=new ArrayList<>();
        Keys.addAll(AnimalMap.keySet());
        int livinganimals=0;
        int energycount=0;
        int dayslived=0;
        int kidscount=0;
        for (Vector2d Position: Keys)
        {
            ArrayList<Animal> AnimalList=AnimalMap.get(Position);
            livinganimals+=AnimalList.size();
            for(Animal animal:AnimalList)
            {

                energycount+=animal.getEnergy();
                dayslived+=(this.day-animal.getDayOfBirth());
                kidscount+=animal.getChildren().size();
            }
        }
        this.NumberOfLivingAnimals=livinganimals;
        this.SumOfLivingAnimalsEnergy=energycount;
        this.SumOfLivingAnimalsLifespan=dayslived;
        this.NumberOfChildren=kidscount;

    }
    public int getNumberOfPlants()
    {
        Map<Vector2d, Grass> GrassMap=this.map.getGrassMap();
        ArrayList<Vector2d> Keys=new ArrayList<>();
        Keys.addAll(GrassMap.keySet());
        return Keys.size();
    }

    public int getNumberOfLivingAnimals() {
        return NumberOfLivingAnimals;
    }
    public float getAverageEnergy()
    {
        if(NumberOfLivingAnimals==0){
            return 0.0f;
        }

        float result;
        result=(float) this.SumOfLivingAnimalsEnergy;
        result/=(float) this.NumberOfLivingAnimals;
        result=Math.round((result*100.0f))/100.0f;

        return result;
    }
    public float getAverageLifespan()
    {
        if(NumberOfDeadAnimals==0)
        {
            return 0.0f;
        }
        float result;
        result=(float) SumOfDeadAnimalsLifespan;
        result/=(float) NumberOfDeadAnimals;
        result=Math.round((result*100.0f))/100.0f;
        return result;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public void addDeadAnimal(long age)
    {
        this.NumberOfDeadAnimals+=1;
        this.SumOfDeadAnimalsLifespan+=(this.day-age);
    }
    public float getAverageChildAmount()
    {
        if(NumberOfLivingAnimals==0)
        {
            return 0.0f;
        }
        float result;
        result=(float) NumberOfChildren;
        result/=(float) NumberOfLivingAnimals;
        result=Math.round((result*100.0f))/100.0f;
        return result;
    }

    public int getDay() {
        return day;
    }
}
