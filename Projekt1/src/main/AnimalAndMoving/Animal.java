package AnimalAndMoving;

import MapPackage.JungleMap;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Animal {
    //this static int is used to distinguish animals
    private static int globalid=0;
    private final int idnumber;
    private Vector2d pos;
    private final int DayOfBirth;
    //MapDirection.getrandom() to własna funkcja
    private MapDirection dir=MapDirection.getrandom();
    private JungleMap map;
    private List<AnimalPositionChangeObserver> observers = new ArrayList<>();
    private int energy;
    private final int[] genes;
    private List<Animal> children= new ArrayList<>();

    public Animal(JungleMap map, Vector2d initialPosition, int initialenergy,int[] genes,int birthdate)
    {
        this.DayOfBirth=birthdate;
        this.idnumber=globalid;
        globalid+=1;
        this.energy=initialenergy;
        this.map=map;
        this.pos=initialPosition;
        this.genes=new int[genes.length];
        for (int i=0;i< genes.length;i++)
        {
            this.genes[i]=genes[i];
        }
        //jeśli nie udało się ustawić zwierzęcia
        if (map instanceof AnimalPositionChangeObserver)
        {
            addObserver((AnimalPositionChangeObserver) map);
        }
        if (!this.map.place(this))
        {
            this.pos=null;
            throw new IllegalArgumentException("ANIMAL WAS NOT PLACED ON THE MAP, INITIAL POSITION IS OCCUPIED");
        }
        else
        {
            //System.out.println("ANIMAL PLACED SUCCESSFULLY");
        }

    }

    public Vector2d getPosition()
    {
        return this.pos;
    }
    public String toString()
    {
        if ((this.energy/10)>=10)
        {
            return "A";
        }
        return String.valueOf(this.energy/10);
    }
    public void move(MoveDirection direction)
    {
        //lab4 zmiany dla backward i forward
        // teraz używają metod canMoveTo i place z RectangularMap

        switch (direction)
        {
            case LEFT:
                this.dir=dir.previous();
                break;
            case RIGHT:
                this.dir=dir.next();
                break;
            case FORWARD:
                Vector2d tmp = this.dir.toUnitVector();
                if (this.pos!=null)
                {
                    int[] bounds=this.map.getBounds();
                    tmp=tmp.moduloAdd(this.pos,bounds[0],bounds[1]);
                }
                if (this.map.canMoveTo(tmp))
                {
                    positionChanged(this.pos,tmp);
                    this.pos=tmp;
                }
                break;
            case BACKWARD:
                Vector2d tmp2=this.dir.toUnitVector();
                tmp2=tmp2.opposite();
                if (this.pos!=null)
                {
                    tmp2=tmp2.add(this.pos);
                }
                if (this.map.canMoveTo(tmp2))
                {
                    positionChanged(this.pos,tmp2);
                    this.pos=tmp2;
                }
                break;
            default:
                break;

        }
    }
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        int n=observers.size();
        for (int i=0;i<n;i++)
        {
            observers.get(i).positionChanged(oldPosition,this,newPosition);
        }
    }
    public void addObserver(AnimalPositionChangeObserver obs)
    {
        observers.add(obs);
    }
    public void removeObserver(AnimalPositionChangeObserver obs)
    {
        observers.remove(obs);
    }

    public void AddEnergy(int value)
    {
        this.energy+=value;
    }
    public void SubtractEnergy(int value)
    {
        this.energy-=value;
    }
    public int getEnergy() {
        return energy;
    }

    //making sure the animal has all the genes
    private void BalanceGenes(int[] genetics)
    {
        int n=genetics.length;
        int[] count=new int[8];
        for (int i=0;i>8;i++)
        {
            count[i]=0;
        }
        for (int i=0;i<n;i++)
        {
            count[genetics[i]]+=1;
        }
        boolean anyzero=true;
        while (anyzero)
        {
            anyzero=false;
            int MaxCount=-1;
            int GeneToBeAdded=-1;
            int MaxGene=-1;
            for (int i=0;i<8;i++)
            {
                if (count[i]>MaxCount)
                {
                    MaxCount=count[i];
                    MaxGene=i;
                }
                if (count[i]==0)
                {
                    anyzero=true;
                    GeneToBeAdded=i;
                }
            }
            if (anyzero)
            {
                for (int j=0;j<n;j++)
                {
                    if (genetics[j]==MaxGene)
                    {
                        count[genetics[j]]-=1;
                        genetics[j]=GeneToBeAdded;
                        count[GeneToBeAdded]+=1;
                        break;
                    }
                }
            }
        }
    }
    private int[] childgenes(Animal other)
    {
        int rand1= ThreadLocalRandom.current().nextInt(1,this.genes.length-1);
        int rand2= ThreadLocalRandom.current().nextInt(1,this.genes.length-1);
        while (rand1==rand2)
        {
            rand2= ThreadLocalRandom.current().nextInt(1,this.genes.length-1);
        }
        int lower=Math.min(rand1,rand2);
        int higher=Math.max(rand1,rand2);

        int[] result = new int[this.genes.length];

        for (int i=0;i<lower;i++)
        {
            result[i]=this.genes[i];
        }
        Animal middleparent=this;
        int rand3=ThreadLocalRandom.current().nextInt(0,2);
        if(rand3>0)
        {
            middleparent=other;
        }
        for (int i=lower;i<higher;i++)
        {
            result[i]=middleparent.genes[i];
        }
        for (int i=higher;i<this.genes.length;i++)
        {
            result[i]=other.genes[i];
        }
        BalanceGenes(result);
        Arrays.sort(result);
        return result;
    }
    public Animal copulate(Animal SignificantOther,Vector2d where,int when)
    {
        int myenergy=(int)((0.25f)*this.energy);
        int otherenergy=(int)((0.25f)*SignificantOther.energy);
        this.SubtractEnergy(myenergy);
        SignificantOther.SubtractEnergy(otherenergy);
        Animal Child=new Animal(this.map,where,myenergy+otherenergy,this.childgenes(SignificantOther),when);
        this.AddChildID(Child);
        SignificantOther.AddChildID(Child);
        return Child;
    }
    public int[] getGenes()
    {
        return this.genes;
    }

    //basically select a random gene and then rotate
    public void RotateByGenes()
    {
        int rotationidx=ThreadLocalRandom.current().nextInt(0,this.genes.length);
        for (int i=0;i<this.genes[rotationidx];i++)
        {
            this.dir=this.dir.next();
        }
    }


    public int getIdnumber() {
        return idnumber;
    }

    public int getDayOfBirth() {
        return DayOfBirth;
    }

    private void AddChildID(Animal child){
        this.children.add(child);
    }

    public List<Animal> getChildren() {
        return children;
    }
}
