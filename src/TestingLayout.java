import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by David on 25/01/2017.
 */
class TestingLayout extends JFrame implements MouseListener, MouseMotionListener, ActionListener{
    //Background
    private JFrame w = new JFrame();
    private JPanel panel1 = new JPanel(new GridLayout(4,2),false);
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JSlider population;
    private JButton popButton;
    private JLabel label4;

    //input country List
    private HashMap<Integer,Country> list;

    //components (Flags
    private Component[] add = new Component[8];

    //correct spot & points
    int cPosition;
    int points;
    Country correctPop;
    long g1Points;
    long g2Points;
    int g2iter=0;
    int mode;
    boolean thousand = false;
    long started;

    public TestingLayout(HashMap<Integer,Country> countryList){
        //Mouse Listeners
        panel1.addMouseListener(this);
        panel1.addMouseMotionListener(this);
        popButton.addActionListener(this);

        //Initialize Country list
        list = countryList;

        //Initializing Gui box
        BoxLayout boxLayout = new BoxLayout(w.getContentPane(),BoxLayout.Y_AXIS);
        w.setLayout(boxLayout);
        w.add(label1);
        w.add(label2);
        w.add(label3);
        w.add(label4);
        w.add(panel1);
        w.setSize(800,600);

        //start game
        started = System.currentTimeMillis();
        mode = 1;
        placePictures();
        w.setVisible(true);

        gameOn();
    }

    private void placePictures(){
        //Hold # of country, Labels of country
        Integer[] numbers = new Integer[8];
        JLabel[] pics = new JLabel[8];

        //Start with Country and show name
        int correct = ThreadLocalRandom.current().nextInt(1, list.size()+1);
        Country t = list.get(correct);
        File temp = new File("C:\\Users\\David\\IdeaProjects\\Geo\\src\\png250px\\" + t.getCode() + ".png");
        while(!temp.exists()){
            correct = ThreadLocalRandom.current().nextInt(1, list.size()+1);
            t = list.get(correct);
            temp = new File("C:\\Users\\David\\IdeaProjects\\Geo\\src\\png250px\\" + t.getCode() + ".png");
        }
        label1.setText(t.getName());
        label2.setText(String.valueOf(points));
        System.out.println(t.getName());

        //Obtain 8 countries.
        numbers[0]=correct;
        for(int i=0;i<8;i++){
            //Gets 2 digit & get file

            String data = t.getCode();
            String id = "C:\\Users\\David\\IdeaProjects\\Geo\\src\\png250px\\" + data + ".png";

            //Pictures
            ImageIcon imTemp = new ImageIcon(id);
            pics[i] = new JLabel(imTemp);
            pics[i].setPreferredSize(new Dimension(40,18));
            pics[i].setSize(40,18);
            //obtain new country

            int rand = ThreadLocalRandom.current().nextInt(1, list.size()+1);
            while(true){
                if(Arrays.asList(numbers).contains(rand)){rand = ThreadLocalRandom.current().nextInt(1, list.size()+1);}
                else{numbers[i] = rand; break;}
            }
            t = list.get(rand);
            temp = new File("C:\\Users\\David\\IdeaProjects\\Geo\\src\\png250px\\" + t.getCode() + ".png");
            while(!temp.exists()){
                rand = ThreadLocalRandom.current().nextInt(1, list.size()+1);
                t = list.get(rand);
                temp = new File("C:\\Users\\David\\IdeaProjects\\Geo\\src\\png250px\\" + t.getCode() + ".png");
            }
            t = list.get(rand);
        }
        cPosition = ThreadLocalRandom.current().nextInt(0, 8);
        add[0]=panel1.add(pics[cPosition]);
        for(int i=1; i<8;i++){
            if(i==cPosition){add[i] = panel1.add(pics[0]);}
            else{add[i] = panel1.add(pics[i]);}
        }
    }

    public void gameOn(){}

    public void game2(){
        g2iter++;
        if(g2iter>20){
            label1.setText("Game 1 points:" + Long.toString(g1Points));
            label2.setText("Game 2 points:" + Long.toString(g2Points));
            label3.setText("");
            label4.setText("");
            return;
        }
        int rand;
        Country randC;
        while (true) {
            rand = ThreadLocalRandom.current().nextInt(1, list.size() + 1);
            randC = list.get(rand);
            if(randC.getPop()!=null){break;}
        }
        label1.setText(randC.getName());
        label2.setText("Round 1 score " + g1Points);
        label3.setText("Get the country population");
        correctPop = randC;
        int hold;
        int maxVal;
        if(randC.getPop()>(2147483640/3)){maxVal = 2147483640;}
        else{maxVal = randC.getPop() + ThreadLocalRandom.current().nextInt(1, 3* randC.getPop() + 1);}
        int minVal = randC.getPop() - ThreadLocalRandom.current().nextInt(1, randC.getPop() + 1);
        System.out.println(randC.getName());
        population.setModel(new DefaultBoundedRangeModel(minVal,(maxVal-minVal)/5000,minVal,maxVal));
        population.setLabelTable(null);
        population.createStandardLabels((maxVal-minVal)/10);
        population.setMajorTickSpacing((maxVal-minVal)/10);
        population.setPaintTicks(true);
        population.setPaintLabels(true);
        panel1.add(population);
        panel1.add(popButton);
    }
    public void correctReset(){
        points += 50;
        for(int i=0;i<8;i++) {
            panel1.remove(add[i]);
        }
        placePictures();
        if(points>=1000 && !thousand){
            thousand=true;
            long completed = System.currentTimeMillis();
            g1Points = completed - started;
            label3.setText("Congrats, you completed 1000 in " + String.valueOf(g1Points) + " milliseconds");
            g1Points/=100;
            g1Points = 5000 - (g1Points);
            for(int i=0;i<8;i++) {
                panel1.remove(add[i]);
            }
            mode = 2;
            game2();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mousePressed(MouseEvent e){
        if(mode == 1) {
            if (add[cPosition].contains(e.getX() - add[cPosition].getX(), e.getY() - add[cPosition].getY())) {
                add[cPosition].setVisible(false);
                correctReset();
            }
            for (int i = 0; i < 8; i++) {
                if (i != cPosition) {
                    if (add[i].contains(e.getX() - add[i].getX(), e.getY() - add[i].getY())) {
                        add[i].setVisible(false);
                        points -= 30;
                        label2.setText(String.valueOf(points));
                    }
                }
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){}

    public void actionPerformed(ActionEvent e){
        int value = Math.abs(correctPop.getPop() - population.getValue());
        int p;
        if(correctPop.getPop()*.99<population.getValue() && population.getValue()<correctPop.getPop()/.99){p = 250;}
        else if(correctPop.getPop()*.95<population.getValue() && population.getValue()<correctPop.getPop()/.95){p = 225;}
        else if(correctPop.getPop()*.90<population.getValue() && population.getValue()<correctPop.getPop()/.90){p = 200;}
        else if(correctPop.getPop()*.75<population.getValue() && population.getValue()<correctPop.getPop()/.75){p = 150;}
        else if(correctPop.getPop()*.5<population.getValue() && population.getValue()<correctPop.getPop()/.5){p = 100;}
        else if(correctPop.getPop()*.1<population.getValue() && population.getValue()<correctPop.getPop()/.1){p = 50;}
        else{p=5;}
        g2Points+=p;
        label4.setText("The country " + correctPop.getName() + " has a population of " + correctPop.getPop() + " Your estimate of " + population.getValue()
            + " provides you with " + p + " points");
        panel1.remove(population);
        panel1.remove(popButton);
        game2();
    }
}
