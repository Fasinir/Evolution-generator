package EnginePackage;

import AnimalAndMoving.Animal;
import AnimalAndMoving.Vector2d;
import Comparators.AnimalEnergyComparator;
import GUIPackage.GUIElement;
import GUIPackage.RightPanel;
import GUIPackage.StatisticsCounter;
import Generators.GeneGenerator;
import MapPackage.JungleMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class JungleEngine implements IEngine, ActionListener {
    private JungleMap map;
    private int InitialEnergy;
    private int SteppeChance;
    private int JungleChance;
    private long TimeBetweenStages;
    private int DailyLoss;
    private int days=0;
    private StatisticsCounter SC;

    private GUIElement gui;
    public JungleEngine(JungleMap map,
                        int InitialAnimals,
                        int InitialEnergy,
                        int SteppeChance,
                        int JungleChance,
                        int DailyLoss,
                        GUIElement gui,
                        long TimeBetweenStages,
                        StatisticsCounter SC)
    {
        this.SC=SC;
        this.gui=gui;
        this.DailyLoss=DailyLoss;
        this.TimeBetweenStages=TimeBetweenStages;
        this.map=map;
        this.InitialEnergy=InitialEnergy;
        int[] Bounds=map.getBounds();
        this.SteppeChance=SteppeChance;
        this.JungleChance=JungleChance;
        //placing new animals
        for (int i=0;i<InitialAnimals;i++)
        {
            int RandomX= ThreadLocalRandom.current().nextInt(0,Bounds[0]);
            int RandomY= ThreadLocalRandom.current().nextInt(0,Bounds[1]);
            new Animal(this.map, new Vector2d(RandomX,RandomY),InitialEnergy, GeneGenerator.RandomGenes(),0);
        }
    }
    public void run() {
                SleepForAWhile();
                //getting occupied positions
                List<Vector2d> AnimalPositions = new ArrayList<>();
                AnimalPositions.addAll(this.map.getAnimalMap().keySet());

                //getting places where both animals and grass are
                List<Vector2d> GrassPositions = new ArrayList<>();
                GrassPositions.addAll(this.map.getGrassMap().keySet());
                List<Vector2d> CommonKeys = new ArrayList<>(AnimalPositions);
                CommonKeys.retainAll(GrassPositions);
                //eating
                //System.out.println("EATING");
                Eating(CommonKeys);
                //System.out.println(this.map.toString());
                gui.refresh();
                SleepForAWhile();

                //reducing energy of all animals
                //System.out.println("GETTING TIRED");
                ReducingEnergy(AnimalPositions);
                // System.out.println(this.map.toString());
                gui.refresh();
                SleepForAWhile();

                //removing dead
                //System.out.println("REMOVING THE DEAD");
                RemoveTheDead(AnimalPositions);
                AnimalPositions.clear();
                AnimalPositions.addAll(this.map.getAnimalMap().keySet());
                // System.out.println(this.map.toString());
                gui.refresh();
                SleepForAWhile();

                //more animals!
                //System.out.println("NEW ANIMALS");
                MakeLove(AnimalPositions);
                // System.out.println(this.map.toString());
                gui.refresh();
                SleepForAWhile();

                //more grass!
                //.out.println("PLANTING");
                Plant();
                // System.out.println(this.map.toString());
                gui.refresh();
                SleepForAWhile();

                //Rotating and moving
                //System.out.println("MOVING");
                MoveAll(AnimalPositions);
                //  System.out.println(this.map.toString());
                gui.refresh();
                SleepForAWhile();



                days += 1;
                SC.setDay(days);
                SC.statCount();
                //  System.out.println(this.map.toString());
                //System.out.println("DAY: " + String.valueOf(days));
                gui.refresh();
                SleepForAWhile();

    }
    private void SleepForAWhile()
    {
        try {
            Thread.sleep(TimeBetweenStages);
        } catch (InterruptedException e) {};
    }
    private void ReducingEnergy(List<Vector2d> AnimalPositions)
    {
        for (Vector2d Position:AnimalPositions)
        {
            ArrayList<Animal> AnimalsAt=this.map.getAnimalMap().get(Position);
            for (Animal an: AnimalsAt)
            {
                an.SubtractEnergy(DailyLoss);
            }
        }
    }
    private void RemoveTheDead(List<Vector2d> AnimalPositions)
    {
        for(Vector2d Position:AnimalPositions)
        {
            this.map.RemoveTheDead(Position,SC);
        }
    }
    private void Eating(List<Vector2d> CommonKeys)
    {

        for (Vector2d Position:CommonKeys)
        {
            this.map.FeedAnimals(Position);
        }
    }
    private void MakeLove(List<Vector2d> AnimalPositions)
    {
        for( Vector2d Position:AnimalPositions)
        {
            ArrayList<Animal> AnimalsAt=this.map.getAnimalMap().get(Position);
            if(AnimalsAt.size()>=2)
            {
                Collections.sort(AnimalsAt, new AnimalEnergyComparator());
                //the 2 strongest animals will produce a new one
                if (AnimalsAt.get(1).getEnergy()>=InitialEnergy/2)
                {
                    AnimalsAt.get(0).copulate(AnimalsAt.get(1),this.map.findSuitablePlace(Position),this.days);
                }
            }
        }
    }
    private void Plant()
    {
        this.map.GenerateGrass(SteppeChance,JungleChance);
    }
    private void MoveAll(List<Vector2d> AnimalPositions)
    {
        for(Vector2d Position:AnimalPositions)
        {
            this.map.RotateAnimalsOnPosition(Position);
            this.map.MoveAnimalsOnPosition(Position);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.run();
    }
    private void updateStats()
    {

    }
}
