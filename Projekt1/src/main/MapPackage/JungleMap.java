package MapPackage;

import AnimalAndMoving.*;
import GUIPackage.StatisticsCounter;

import javax.swing.text.Position;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class JungleMap implements AnimalPositionChangeObserver {
    //private int minrange;
    //private int maxrange;
    private final int SteppeWidth;
    private final int SteppeHeight;
    private final int JungleWidth;
    private final int JungleHeight;

    private final int PlantProfit;

    private final Vector2d JungleUpperLeft;
    private final Vector2d JungleLowerRight;
    private final Vector2d JungleLowerLeft;
    private final Vector2d JungleUpperRight;

    private Map<Vector2d, ArrayList<Animal>> animals = new LinkedHashMap<>();
    private Map<Vector2d, Grass> grass = new LinkedHashMap<>();

    public JungleMap(int SteppeWidth,
                     int SteppeHeight,
                     int JungleWidth,
                     int JungleHeight,
                     int InitialGrass,
                     int InitialJungleGrass,
                     int PlantProfit) {

        this.SteppeWidth = SteppeWidth;
        this.SteppeHeight = SteppeHeight;
        this.JungleWidth = JungleWidth;
        this.JungleHeight = JungleHeight;

        this.JungleUpperLeft = new Vector2d((SteppeWidth - JungleWidth) / 2, (SteppeHeight + JungleHeight) / 2);
        this.JungleLowerRight = new Vector2d((SteppeWidth + JungleWidth) / 2, (SteppeHeight - JungleHeight) / 2);

        this.JungleUpperRight = JungleUpperLeft.upperRight(JungleLowerRight);
        this.JungleLowerLeft = JungleUpperLeft.lowerLeft(JungleLowerRight);

        this.PlantProfit = PlantProfit;

        GenerateGrass(InitialGrass, InitialJungleGrass);

    }

    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    public boolean place(Animal animal) {
        Vector2d animalpos = animal.getPosition();
        this.positionChanged(new Vector2d(-1, -1), animal, animalpos);
        return true;
    }

    public boolean isOccupied(Vector2d position) {
        //chwilowo tak, żeby się dobrze rysowało
        return true;
    }

    public Object objectAt(Vector2d position) {

        if (animals.containsKey(position)) {
            return animals.get(position).get(0);
        } else if (grass.containsKey(position)) {
            return grass.get(position);
        } else {
            //that's too long
            if (inJungle(position)) {
                return new JungleField();
            }
            return new SteppeField();
        }

    }

    public void positionChanged(Vector2d oldPosition, Animal animal, Vector2d newPosition) {
        if (animals.containsKey(oldPosition)) {
            animals.get(oldPosition).remove(animal);
            if (animals.get(oldPosition).isEmpty()) {
                animals.remove(oldPosition);
            }
        }
        if (!animals.containsKey(newPosition)) {
            animals.put(newPosition, new ArrayList<Animal>());
        }
        animals.get(newPosition).add(animal);
    }

    @Override
    public String toString()
    {
        MapVisualizer mv= new MapVisualizer(this);
        String res=mv.draw(new Vector2d(0,0),new Vector2d(this.SteppeWidth-1,this.SteppeHeight-1));
        return res;
    }

    public void GenerateGrass(int GrassChance, int JungleChance) {
        int howmany = GrassChance;
        while (howmany > 0) {
            int x1 = ThreadLocalRandom.current().nextInt(0, SteppeWidth);
            int y1 = ThreadLocalRandom.current().nextInt(0, SteppeHeight);
            Vector2d position = new Vector2d(x1, y1);
            if (animals.containsKey(position) || grass.containsKey(position)) {
                howmany--;
            } else {
                grass.put(position, new Grass(position));
                //boundary.positionChanged(new Vector2d(-1,-1),position);
                //updateDrawValues();
                howmany--;

            }
        }
        howmany = JungleChance;
        while (howmany > 0) {
            int x1 = ThreadLocalRandom.current().nextInt(JungleUpperLeft.x, JungleUpperRight.x );
            int y1 = ThreadLocalRandom.current().nextInt(JungleLowerLeft.y, JungleUpperLeft.y );
            Vector2d position = new Vector2d(x1, y1);
            if (animals.containsKey(position) || grass.containsKey(position)) {
                howmany--;
            } else {
                grass.put(position, new Grass(position));

                howmany--;
            }
        }
    }

    private boolean inJungle(Vector2d position) {

        if (position.x >= JungleUpperLeft.x
                && position.x < JungleUpperRight.x
                && position.y >= JungleLowerLeft.y
                && position.y < JungleUpperLeft.y) {
            return true;
        }
        return false;
    }

    public Map<Vector2d, ArrayList<Animal>> getAnimals() {
        return animals;
    }

    public List<Animal> getStrongest(Vector2d position) {
        List<Animal> result = new ArrayList<>();
        if (objectAt(position) == null) {
            return result;
        }
        int MaxEnergy = -1;
        List<Animal> AtPosition = this.animals.get(position);
        for (int i = 0; i < AtPosition.size(); i++) {
            if (AtPosition.get(i).getEnergy() > MaxEnergy) {
                result.clear();
                result.add(AtPosition.get(i));
                MaxEnergy = (AtPosition.get(i).getEnergy());
            } else if (AtPosition.get(i).getEnergy() == MaxEnergy) {
                result.add(AtPosition.get(i));
            }
        }
        return result;
    }

    public int[] getBounds() {
        return new int[]{this.SteppeWidth, this.SteppeHeight};
    }

    public Vector2d findSuitablePlace(Vector2d position) {
        MapDirection[] val = MapDirection.values();
        Vector2d[] unitvectors = new Vector2d[8];
        for (int i = 0; i < 8; i++) {
            unitvectors[i] = val[i].toUnitVector();

        }
        for (int i = 0; i < 8; i++) {
            Vector2d tmp = position.moduloAdd(unitvectors[i], getBounds()[0], getBounds()[1]);
            if (!animals.containsKey(tmp)) {
                return tmp;
            }
        }
        int randomidx = ThreadLocalRandom.current().nextInt(0, 8);
        return position.moduloAdd(unitvectors[randomidx], getBounds()[0], getBounds()[1]);
    }

    //will occur when GrassList and animals contain same key
    public void FeedAnimals(Vector2d position) {

        List<Animal> StrongestAt = this.getStrongest(position);
        if (StrongestAt.isEmpty()) {
            return;
        }
        for (int i = 0; i < StrongestAt.size(); i++) {
            StrongestAt.get(i).AddEnergy(PlantProfit / StrongestAt.size());
        }
        grass.remove(position);
    }

    public void RemoveTheDead(Vector2d position,StatisticsCounter SC)
    {
        if (animals.get(position) == null) {
            return;
        }
        ArrayList<Animal> AnimalsAt=this.animals.get(position);
        for (int i=AnimalsAt.size()-1; i>=0 ;i--)
        {
            Animal animal = AnimalsAt.get(i);
            if(animal.getEnergy()<=0)
            {
                SC.addDeadAnimal(animal.getDayOfBirth());
                this.animals.get(position).remove(i);
            }
        }
        if(animals.get(position).isEmpty())
        {
            animals.remove(position);
        }
    }

    public void MoveAnimalsOnPosition(Vector2d position) {
        if (animals.get(position) == null) {
            return;
        }
        List<Animal> OnPosition = animals.get(position);
        for (int i = 0; i < OnPosition.size(); i++) {
            OnPosition.get(i).move(MoveDirection.FORWARD);
        }
    }

    public void RotateAnimalsOnPosition(Vector2d position) {
        List<Animal> OnPosition = animals.get(position);
        if (OnPosition == null) {
            return;
        }
        for (int i = 0; i < OnPosition.size(); i++) {
            OnPosition.get(i).RotateByGenes();
        }
    }

    public Map<Vector2d, ArrayList<Animal>> getAnimalMap()
    {
        return this.animals;
    }
    public Map<Vector2d,Grass> getGrassMap()
    {
        return this.grass;
    }
    public int[] getSize()
    {
        return new int[] {this.SteppeWidth,SteppeHeight};
    }
    public int[] getJungleSize()
    {
        return new int[]{this.JungleWidth,this.JungleHeight};
    }


}
