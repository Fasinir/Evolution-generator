package GUIPackage;

import AnimalAndMoving.Animal;
import AnimalAndMoving.Vector2d;
import MapPackage.Grass;
import MapPackage.JungleMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapPanel extends JPanel implements GUIElement {
    JungleMap map;
    private float InitialEnergy;
    public MapPanel(JungleMap map, int initialenrgy, int xcoord, int ycoord, int width, int height)
    {
        this.InitialEnergy=(float) initialenrgy;
        this.map=map;
        this.setVisible(true);
        this.setBounds(xcoord,ycoord,width,height);

    }
    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2D= (Graphics2D) g;

        g2D.setPaint(new Color(200,150,0));
        g2D.fillRect(0,0,this.getWidth(),this.getHeight());
        g2D.setPaint(new Color(0,150,0));
        //[0] is width
        //[1] is height
        int[] JungleSize=this.map.getJungleSize();
        int[] SteppeSize=this.map.getSize();

        //rel - relative
        float JungleXCoord=((float) Math.floor(((float) SteppeSize[0]-(float) JungleSize[0])/2.0f)/(float) SteppeSize[0])*(float)this.getWidth();
        float JungleYCoord=((float) Math.floor(((float) SteppeSize[1]-(float) JungleSize[1])/2.0f)/(float) SteppeSize[1])*this.getHeight();
        float JungleWidth=((float) JungleSize[0]/(float) SteppeSize[0])*this.getWidth();
        float JungleHeight=((float) JungleSize[1]/(float) SteppeSize[1])*this.getHeight();

        g2D.fillRect((int)JungleXCoord,(int)JungleYCoord,(int) JungleWidth,(int)JungleHeight);


        java.util.List<Vector2d> grassKeys=new ArrayList<>();
        grassKeys.addAll(this.map.getGrassMap().keySet());
        //sizes relative to size of the panel
        float relwidth= (float) (this.getWidth())/(float)(SteppeSize[0]);
        float relheight= (float) (this.getHeight())/(float)(SteppeSize[1]);

        g2D.setPaint(new Color(0,100,0));
        for(Vector2d Position: grassKeys)
        {
            //coordinates relative to the size of the panel
            float relx=(((float)Position.x/(float) SteppeSize[0])*this.getWidth());
            float rely=(((float)Position.y/(float) SteppeSize[1])*this.getHeight());

            g2D.fillRect((int)relx,(int)rely,(int)relwidth,(int)relheight);



        }
        //drawing animals
        Map<Vector2d, ArrayList<Animal>> AnimalMap;
        AnimalMap=this.map.getAnimalMap();
        List<Vector2d> animalKeys=new ArrayList<>();
        animalKeys.addAll(this.map.getAnimalMap().keySet());
        g2D.setPaint(new Color(50,20,0));

        //darker ==weaker
        //color is based on initial energy
        //if animal is having 10% of initial energy it's dark
        //if it has more than 100% of initial energy it's light etc.
        for(Vector2d Position: animalKeys)
        {


            Animal atposition=AnimalMap.get(Position).get(0);
            int energylevel=(int)(((float) atposition.getEnergy()*100)/InitialEnergy);
            energylevel/=10;
            energylevel=Math.min(energylevel,10);
            g2D.setPaint(new Color(50+energylevel*15,20+energylevel*6,0));

            float relx=(((float)Position.x/(float) SteppeSize[0])*this.getWidth());
            float rely=(((float)Position.y/(float) SteppeSize[1])*this.getHeight());

            g2D.fillOval((int)relx,(int)rely,(int)relwidth,(int)relheight);



        }


    }


    @Override
    public void refresh() {
        this.revalidate();
        this.repaint();
    }
}
