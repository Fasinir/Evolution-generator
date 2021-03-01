package GUIPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPanel extends JPanel implements GUIElement, ActionListener {
    private JPanel StatPanel;
    private JPanel ChosenPanel;
    private StatisticsCounter SC;
    private JButton PauseButton;
    private JLabel AnimalCountLabel;
    private JLabel PlantCountLabel;
    private JLabel AverageEnergy;
    private JLabel AverageLifespan;
    private JLabel AverageChildren;
    private Timer timer;
    public RightPanel(int xcoord,int rootheight,StatisticsCounter SC)
    {
        this.SC=SC;
        this.setSize(200,rootheight);
        this.setBounds(xcoord,0,200,rootheight);
        this.setVisible(true);
        this.setBackground(new Color(255,255,255));
        this.StatPanel=new JPanel();
        this.ChosenPanel=new JPanel();
        this.setLayout(null);
        this.add(StatPanel);
        this.add(ChosenPanel);

        StatPanel.setBounds(0,0,200,this.getHeight()/2);
        StatPanel.setSize(200,this.getHeight()/2);
        ChosenPanel.setBounds(0,this.getHeight()/2,200,this.getHeight()/2);
        //StatPanel.setBackground(new Color(255,0,0));
        //ChosenPanel.setBackground(new Color(0,255,0));
        StatPanel.setLayout(new FlowLayout());

        //labels with statistics
        this.AnimalCountLabel=new JLabel();
        AnimalCountLabel.setText(htmlconcat("Number of Animals:",SC.getNumberOfLivingAnimals()));
        AnimalCountLabel.setSize(200,20);
        AnimalCountLabel.setHorizontalTextPosition(JLabel.LEFT);
        StatPanel.add(AnimalCountLabel);

        this.PlantCountLabel=new JLabel();
        PlantCountLabel.setText(htmlconcat("Number of plants: ",SC.getNumberOfPlants()));
        PlantCountLabel.setSize(200,20);
        PlantCountLabel.setHorizontalTextPosition(JLabel.LEFT);
        StatPanel.add(PlantCountLabel);

        this.AverageEnergy=new JLabel();
        AverageEnergy.setText(htmlconcat("Average Energy:",SC.getAverageEnergy()));
        AverageEnergy.setSize(200,20);
        AverageEnergy.setHorizontalAlignment(JLabel.LEFT);
        StatPanel.add(AverageEnergy);

        this.AverageLifespan=new JLabel();
        AverageLifespan.setText(htmlconcat("Average lifespan (for dead animals):",SC.getAverageLifespan()));
        AverageLifespan.setSize(200,20);
        AverageLifespan.setHorizontalTextPosition(JLabel.LEFT);
        StatPanel.add(AverageLifespan);

        this.AverageChildren=new JLabel();
        AverageChildren.setText(htmlconcat("Average number of children:",SC.getAverageChildAmount()));
        AverageChildren.setSize(200,20);
        AverageChildren.setHorizontalAlignment(JLabel.LEFT);
        StatPanel.add(AverageChildren);


        this.PauseButton= new JButton();
        PauseButton.setText("PAUSE/PLAY");
        PauseButton.addActionListener(this);
        PauseButton.setSize(200,20);
        ChosenPanel.setLayout(new FlowLayout());
        ChosenPanel.add(PauseButton);



    }
    public void updateStats()
    {
        AnimalCountLabel.setText(htmlconcat("Number of Animals:",SC.getNumberOfLivingAnimals()));
        PlantCountLabel.setText(htmlconcat("Number of plants: ",SC.getNumberOfPlants()));
        AverageEnergy.setText(htmlconcat("Average Energy:",SC.getAverageEnergy()));
        AverageLifespan.setText(htmlconcat("Average lifespan:",SC.getAverageLifespan()));
        AverageChildren.setText(htmlconcat("Average number of children:",SC.getAverageChildAmount()));
    }

    @Override
    public void refresh() {
        this.updateStats();
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.PauseButton)
        {

            if (timer.isRunning())
            {
                timer.stop();
            }
            else
            {
                timer.start();
            }
        }
    }
    private String htmlconcat(String start, int val)
    {
        String result="<html>";
        result+=start;
        result+="<br>";
        result+=String.valueOf(val);
        result+="</br>";
        result+="</html>";
        return result;
    }
    private String htmlconcat(String start, float val)
    {
        String result="<html>";
        result+=start;
        result+="<br>";
        result+=String.valueOf(val);
        result+="</br>";
        result+="</html>";
        return result;
    }

}
