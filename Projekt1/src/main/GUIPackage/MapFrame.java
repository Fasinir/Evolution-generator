package GUIPackage;

import MapPackage.JungleMap;

import javax.swing.*;

public class MapFrame extends JFrame implements GUIElement {
    private JungleMap map;
    private int SingleElementHeight;
    private int SingleElementWidth;
    private MapPanel MapPanel;
    private RightPanel rightpanel;
    public MapFrame(JungleMap map,StatisticsCounter SC, int initialenergy)
    {

        this.setLayout(null);
        // this.pack();
        this.setVisible(true);
        this.setSize(750,750);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.map=map;

        /*
        float tmpwidth=(float) this.getWidth()/(float) this.map.getSize()[0];
        float tmpheight=(float) this.getHeight()/(float) this.map.getSize()[1];
        this.SingleElementWidth=(int)tmpwidth;
        this.SingleElementHeight=(int)tmpheight;
         */
        //38 seems to be height of titlebar

        this.rightpanel=new RightPanel(this.getWidth()-200,this.getHeight()-38,SC);
        this.MapPanel=new MapPanel(this.map,initialenergy,0,0,this.getWidth()-200,this.getHeight()-38);

        this.add(MapPanel);
        this.add(rightpanel);



    }

    public void setTimer(Timer timer) {
        this.rightpanel.setTimer(timer);
    }

    @Override
    public void refresh() {
        this.rightpanel.refresh();
        this.MapPanel.refresh();
    }

}
