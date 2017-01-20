import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.sound.sampled.*;
import java.net.URL; 
import java.applet.*;

/*********************************************************
*  Name: George C, Brian C, Charles W                    *
*  Course: ICS 4U  Pd. 2                                 *
*  Final Game: Tim's Run                                 *
*  Purpose: Compsci Final project 2015 programmed on Java*
*  Due Date: June 2, 2015                                *
*********************************************************/

public class TimsRun implements ActionListener{
   /*Introduction variables*/
//Display frames
   JFrame frame = new JFrame("Tim's Run");
   JFrame introFrame = new JFrame ("Tim's Run");
   Drawing draw = new Drawing();
   Drawing draw2 = new Drawing();

//Image
   ImageIcon ione = new ImageIcon("ClockIntroImage.png");
   ImageIcon itwo = new ImageIcon("TeacherImageIntro.png");
   ImageIcon ithree = new ImageIcon("HCImage.png");
   ImageIcon ifour = new ImageIcon("ClockLastIntro.png");
   
   ImageIcon menuImage = new ImageIcon("Title image.png");
   ImageIcon mute = new ImageIcon("Mute.png");
   ImageIcon musicIcon = new ImageIcon("MusicIcon.png");
   ImageIcon helpImage = new ImageIcon("HelpImage.png");
   ImageIcon btm = new ImageIcon("menubutton.png");
   ImageIcon next = new ImageIcon("Next.png");
   
   
   ImageIcon b1 = new ImageIcon("schoolWorld.jpg");
   ImageIcon b2 = new ImageIcon("zombieWorld.png");
   ImageIcon b3 = new ImageIcon("futureWorld.jpg");
   ImageIcon b4 = new ImageIcon ("ghostWorld.jpg");
   
   ImageIcon interOne = new ImageIcon("Interpage 1.png");
   ImageIcon interTwo = new ImageIcon("Interpage 2.png");
   ImageIcon interThree = new ImageIcon("Interpage 3.png");
   ImageIcon interFour = new ImageIcon("Interpage 4.png");
   ImageIcon lost = new ImageIcon("Lose.png");
   ImageIcon yay = new ImageIcon("Win.png");
   
   ImageIcon man = new ImageIcon("Tim.gif");
   ImageIcon manFall = new ImageIcon("Timfall.gif");
   
   ImageIcon p1 = new ImageIcon ("redbull.png");
   ImageIcon p2 = new ImageIcon ("potion.png");
   ImageIcon p3 = new ImageIcon ("clock.png");
   ImageIcon p4 = new ImageIcon ("star.png");
   ImageIcon o1 = new ImageIcon ("pet.png");
   ImageIcon o2 = new ImageIcon ("poop.png");
   ImageIcon o3 = new ImageIcon ("car.png");
   ImageIcon o4 = new ImageIcon ("zombie.png");
   ImageIcon o5 = new ImageIcon ("ghost.png");
   ImageIcon o6 = new ImageIcon ("ufo.png");

//Text for the story
   String storyOne = "<html>It's the year 1977,<br>" + "and schoolboy Tim Timothy<br>" + "has a problem:<br>" +
   "the school bell rings<br>" + "at exactly 9:00 am,<br>" + "but he always arrives late<br>" + "by at least 30 seconds.</html>";
   String storyTwo = "<html>Tim attends the top-ranking school<br>" + "in the country, which has the strictest<br>" + "attendance policy in the country.<br>" +
   "Because Tim's always late,<br>" + "the principal has phoned<br>" +
   "Tim's parents 367 times in<br>" + "the last two months.</html>";
   String storyThree ="<html>Tim's parents give Tim<br>" + "a final warning<br>" + "\"if you are late one more<br>" +
   "time, you will go live with<br>" + "your great, great Auntie,<br>" + "Margaret Margareta\"</html>";
   String storyFour = "<html>Time does not want to<br>" + "live with his great, great<br>" + "Auntie, Margaret Margareta.<br>"
   + "He promises them that he<br>" + "will never be late be<br>" + "late for school ever again.<br>" + "But...........</html>";

//intro features
   JPanel introButtonPane = new JPanel();
   JButton backButton = new JButton("Back");
   JButton skipButton = new JButton("Skip to Menu");
   JButton nextButton = new JButton("Next");
   JLabel story = new JLabel(storyOne);

//keeping track of pages/mouse position
   int page = 1;
   boolean menu, help, game, music;
   
//Keeping track of intermediate pages
   boolean pause;
   boolean win;
   boolean lose;
   int interpage = 1;

   int x, y;

/* Game Variables */
   final int WIDTH = 1000;
   final int HEIGHT = 600;
   final int TIMX = 10; //leftmost of Tim's range
   final int TIMY = 380;
   int speed = 7;

//Variable that keep track of movement
   int destination = 18000; 
   int distance = 0;
   int releaseCount = 0;
   int jumpTime = 0;
   int timL = 120, timH = 120;
   int score = 0; //keeping track of score
   int reqScore = 10; //Score required to advance to next level
   int level = 1;
   int temp, temp1, count = 0;
   
   //check if Tim falls
   boolean[] fall = new boolean[7]; //record which obstacle (#1-6, #0 unused) cause Tim to fall
   boolean fallen = false;

//powerup and game timer
   int bullTimer = 0;
   int shrinkTimer = 0;
   int hitTimer = 0; //if Tim hits an obstacle
   int gameTimer = 0;
   int timeCount = 0;
   
   int backgroundX = 0; //x coordinate of background's top corner
   int backgroundX_2 = WIDTH;

//to count the number of thread executions in Tim class
   int runCount = 0;
/* obstacle/powerup stats
red shrink alarm star pet poop car zombie ghost ufo
level 1 5 1 3 40 5 10 1 0 0 0
level 2 7 1 3 50 0 0 0 15 0 0
level 3 10 2 4 60 0 0 2 0 20 0
level 4 15 2 5 70 0 0 0 0 0 30
level 5 10 3 7 80 10 15 3 0 0 0
*/

/* Game Objects */
   Tim tim = new Tim (TIMX, TIMY, level);

/* initializing game */

   public TimsRun(){
   //initializing variables
      menu = false;
      help = false;
      game = false;
      music = true;
      pause = false;
      win = false;
      lose = false;
   //initializing intro items
      introFrame.setSize(WIDTH, HEIGHT);
      introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      introFrame.setResizable(false);
      
      backButton.addActionListener(this);
      skipButton.addActionListener(this);
      nextButton.addActionListener(this);
      
      introButtonPane.add(backButton);
      introButtonPane.add(skipButton);
      introButtonPane.add(nextButton);
      
      draw.addMouseListener(new MouseListen());
      
      introFrame.add(story, "West");
      introFrame.add(introButtonPane, "South");
      introFrame.add(draw2, "Center");
      introFrame.setVisible(true);
      
      frame.setSize(WIDTH, HEIGHT);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.addKeyListener(new KeyListen());
      frame.add(draw, "Center");
      frame.setResizable(false);
      frame.setVisible(false);
      
      //sounds
         
      try
      {
         AudioClip open = Applet.newAudioClip(new URL("H:\\Grade 12\\Grade 12 Compsci\\Final project 2015\\Compsci Final GeorgeBrianCharles\\Game V4\\IWBTB_Soundtrack_-_16_-_Fourth_World_VVVVVV_.wav"));
         open.loop();
      }
      catch(MalformedURLException e)
      {};
   }
   

   class Drawing extends JComponent{
   
   /*** paint ********************************************
   * Purpose: Graphics Section of the game               *
   *                                                     *
   * Parameters: Graphics g                              *
   * Returns: none                                       *
   ******************************************************/
      public void paint(Graphics g){
         /* Intro graphics */
         if(page == 1)
            g.drawImage(ione.getImage(),10,0,900,600,this);
         if(page == 2)
            g.drawImage(itwo.getImage(),25,0,800,550,this);
         if(page == 3)
            g.drawImage(ithree.getImage(),50,0,750,550,this);
         if(page == 4)
            g.drawImage(ifour.getImage(),30,0,750,550, this);
         
         /* Menu graphics */
         if(menu){
            g.drawImage(menuImage.getImage(),0,0,998,580,this);
            if(music)
               g.drawImage(mute.getImage(),915,518,75,55,this);
         } 
         if(help)
            g.drawImage(helpImage.getImage(),0,0,995,578,this);
         /* Game graphics */   
         if(game){
            drawGame(g);
            
            //Music Icon
            if(music)
               g.drawImage(mute.getImage(),75,10,55,45,this);
            else
               g.drawImage(musicIcon.getImage(),75,10,55,45,this);
               
            //Back to Menu button
            g.drawImage (btm.getImage(), 10, 10, 55, 45, this); 
            
            //Displaying score and time
            //track board
            g.drawRect (50, 500, 900, 100);
            g.setFont(new Font ("Serif", Font.BOLD, 20));
            g.drawString ("Level: " + level, 75, 525);
            g.drawString ("Score: " + score, 75, 550);
            g.drawString ("Time: " + gameTimer, 175, 525);
            g.drawString ("Distance: " + distance + "/" + destination, 175, 550);
         }
         
         //Intermediate page
         if(pause){
            if(interpage == 1) {
               g.drawImage(interOne.getImage(),0,0, 995, 572, this);
            }
            if(interpage == 2){
               g.drawImage(interTwo.getImage(),0,0, 995, 572, this);
            }
            if(interpage == 3) {
               g.drawImage(interThree.getImage(),0,0, 995, 572, this);
            }
            if(interpage == 4) {
               g.drawImage(interFour.getImage(),0,0, 995, 572, this);
            }
            g.drawImage(btm.getImage(),505,518,55,45,this);
            g.drawImage(next.getImage(),570,518,55,45,this);
            g.setFont(new Font ("Serif", Font.BOLD, 20));
            g.drawString ("Score: " + score, 870, 545);
         }
         
         if(lose)
            g.drawImage(lost.getImage(),0,0,995,572,this);
         if(win)
            g.drawImage(yay.getImage(),0,0,995,572,this);
      
      }
   }
   
   /*** drawGame *****************************************
   * Purpose: Draws game objects                         *
   *                                                     *
   * Parameters: Graphics g                              *
   * Returns: none                                       *
   ******************************************************/
   public void drawGame (Graphics g){
      //background
      if(level == 1)
      {
         
         g.drawImage (b1.getImage(), backgroundX, 0, 1000, 500, null);
         g.drawImage (b1.getImage(), backgroundX_2, 0, 1000, 500, null);
      
      }
      
      if(level == 2)
      {
         g.drawImage (b2.getImage(), backgroundX, 0, 1000, 500, null);
         g.drawImage (b2.getImage(), backgroundX_2, 0, 1000, 500, null);
      }
      
      if(level == 3)
      {
         g.drawImage (b4.getImage(), backgroundX, 0, 1000, 500, null);
         g.drawImage (b4.getImage(), backgroundX_2, 0, 1000, 500, null);
      }
      
      if(level == 4)
      {
         g.drawImage (b3.getImage(), backgroundX, 0, 1000, 500, null);
         g.drawImage (b3.getImage(), backgroundX_2, 0, 1000, 500, null);
      }
      
      if(level == 5)
      {
         
         g.drawImage (b1.getImage(), backgroundX, 0, 1000, 500, null);
         g.drawImage (b1.getImage(), backgroundX_2, 0, 1000, 500, null);
      
      }
   
      
      //platform, obstacle and powerup   
      drawPlats(g);
      drawObstacles(g);
      drawPowers(g);
      //tim
      if (hitTimer != 0){
         g.drawImage (manFall.getImage(), tim.getX(), TIMY, timL, timH, null); //Tim's fallen
      }
      else{
         g.drawImage (man.getImage(), tim.getX(), tim.getY(), timL, timH, null); //timL = 120, timH = 120
      }
   
   }
 
   /*** drawObstacles*************************************
   * Purpose: Draws the obstacles in the game            *
   *                                                     *
   * Parameters: Graphics g                              *
   * Returns: none                                       *
   ******************************************************/
 
   public void drawObstacles (Graphics g){
      for (int i = 0; i < tim.getObstacleLength(); i++){
         if (tim.getObstacleType(i) == 1){ //pets
            g.drawImage (o1.getImage(), tim.getObstacleX(i), tim.getObstacleY(i), tim.getObstacleWidth(i), tim.getObstacleHeight(i), null);
         }
         else if (tim.getObstacleType(i)==2){ //poop
            g.drawImage (o2.getImage(), tim.getObstacleX(i), tim.getObstacleY(i), tim.getObstacleWidth(i), tim.getObstacleHeight(i), null);
         }
         else if (tim.getObstacleType(i)==3){ //car
            g.drawImage (o3.getImage(), tim.getObstacleX(i), tim.getObstacleY(i), tim.getObstacleWidth(i), tim.getObstacleHeight(i), null);
         }
         else if (tim.getObstacleType(i)==4){ //zombie
            g.drawImage (o4.getImage(), tim.getObstacleX(i), tim.getObstacleY(i), tim.getObstacleWidth(i), tim.getObstacleHeight(i), null);
         }
         else if (tim.getObstacleType(i)==5){ //ghost
            g.drawImage (o5.getImage(), tim.getObstacleX(i), tim.getObstacleY(i), tim.getObstacleWidth(i), tim.getObstacleHeight(i), null);
         }
         else{ //ufo
            g.drawImage (o6.getImage(), tim.getObstacleX(i), tim.getObstacleY(i), tim.getObstacleWidth(i), tim.getObstacleHeight(i), null);
         }
      }
   }

    /*** drawPowers **************************************
   * Purpose: Draws powerups in the game                 *
   *                                                     *
   * Parameters: Graphics g                              *
   * Returns: none                                       *
   ******************************************************/

   public void drawPowers (Graphics g){
      for (int i = 0; i < tim.getPowerLength(); i++){
         if (tim.getPowerType(i) == 1){ //red bull
            g.drawImage (p1.getImage(), tim.getPowerupX(i), tim.getPowerupY(i), 50, 50, null);
         }
         else if (tim.getPowerType(i) == 2){ //shrink
            g.drawImage (p2.getImage(), tim.getPowerupX(i), tim.getPowerupY(i), 50, 50, null);
         }
         else if (tim.getPowerType(i) == 3){ //clock
            g.drawImage (p3.getImage(), tim.getPowerupX(i), tim.getPowerupY(i), 50, 50, null);
         }
         else if (tim.getPowerType(i) == 4)
         {
            g.drawImage (p4.getImage(), tim.getPowerupX(i), tim.getPowerupY(i), 50, 50, null);
         }
      }
   }
   
    /*** drawPlats ***************************************
   * Purpose: Draws platforms                            *
   *                                                     *
   * Parameters: Graphics g                              *
   * Returns: none                                       *
   ******************************************************/
 
   public void drawPlats (Graphics g){
      for (int i = 0; i < 100; i++){
         g.fillRect (tim.getPlatformX(i), tim.getPlatformY(i), 75, 30);
      }
   }

   /* Button Events */
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == backButton && page > 1) {  
         page--;
         if(page == 1)
            story.setText(storyOne);
         
         if(page == 2)
            story.setText(storyTwo);
         
         if(page == 3)
            story.setText(storyThree);
         draw.repaint();
      }  
      if(e.getSource() == skipButton && page < 5) {
         page = 5;
         menu = true;
         frame.add(draw, "Center");
         introFrame.setVisible(false);
         frame.setVisible(true);
         draw.repaint();
      }
      
      if(e.getSource() == nextButton && page < 5) {
         page++;
         
         if(page == 2)
            story.setText(storyTwo);
         
         if(page == 3)
            story.setText(storyThree);
         
         if(page == 4)
            story.setText(storyFour);  
         
         if(page == 5){
            menu = true;
            frame.add(draw, "Center");
            introFrame.setVisible(false);
            frame.setVisible(true);
         }
         draw.repaint();
      }
   }
   
   /* Keeping track of the mouse */
   class MouseListen extends MouseAdapter {
      public void mouseReleased(MouseEvent e) {
         x = e.getX();
         y = e.getY();
         
         if(menu) {
            if(x >= 33 && x <= 210 && y >= 279 && y <= 332) {
               game = true;
               menu = false;
               System.out.println("Starto desu!~");       
               tim.start();
               draw.repaint();
            }         
            if(x >= 33 && x <= 210 && y >= 366 && y <= 417){
               System.out.println("helpu suruno?");
               help = true;
               menu = false;
               draw.repaint();
            }
            if(x >= 33 && x <= 210 && y >= 456 && y <= 508)
               System.exit(0);
         
            if(x >= 915 && x <= 988 && y >= 519 && y <= 566) {
               music = !music;
               draw.repaint();
            }
         }
         
         if(help && x >= 454 && x <= 542 && y >= 529 && y <= 565) {
            menu = true;
            help = false;
            draw.repaint();
         }
         
         if(game) {
            if(x >= 10 && x <= 65 && y >= 10 && y <= 55){
               game = false;
               menu = true;
               tim.interrupt();
               level = 1;
               interpage = 1;
               reset(level);
               reqScore = 10;
               score = 0;                 
               draw.repaint();
            } 
            if(x >= 75 && x <= 130 && y >= 10 && y <= 55) {
               music = !music;
               draw.repaint();
            } 
         }
         
         if(pause) {
            if(x >= 505 && x <= 560 && y >= 518 && y <= 565) {
               level = 1;
               menu = true;
               reset(level);
               pause = false;
               interpage = 1;
               reqScore = 10;
               score = 0; 
               draw.repaint();
            }
            if(x >= 570 && x <= 625 && y >= 518 && y <= 565) {
               level++;
               interpage++;
               reqScore += 15;
               game = true;
               System.out.println(level);
               reset(level);
               pause = false;
               tim.start();
               draw.repaint();
            }                   
         }
         
         if(win && x >= 454 && x <= 589 && y >= 525 && y <= 559) {
            interpage = 1;
            level = 1;
            reset(level);
            reqScore = 10;
            score = 0; 
            win = false;
            menu = true;
            draw.repaint();
         }
         if(lose && x >= 399 && x <= 581 && y >= 536 && y <= 562){
            interpage = 1;
            level = 1;
            reset(level);
            reqScore = 10;
            score = 0; 
            lose = false;
            menu = true;
            draw.repaint();
         }
         System.out.println("("+x+", " + y + ")");
      }
   }
   
   /*** reset ********************************************
   * Purpose: resets Tim object                          *
   *                                                     *
   * Parameters: int level                               *
   * Returns: none                                       *
   ******************************************************/
   
   public void reset (int level){
      tim = new Tim (TIMX, TIMY, level);
      timH = 120;
      timL = 120;
      distance = 0;
      destination = 18000;
   }

   /* Keeping track of keyboard activity */
   class KeyListen extends KeyAdapter {
   
      public void keyPressed (KeyEvent e) { 
         //if right arrow is pressed, move Tim to the right (positive position)
         if (hitTimer == 0){
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
               tim.setDirection(1);  
            }
            //if left arrow is pressed, move Tim to the left (negative position)
            else if (e.getKeyCode() == KeyEvent.VK_LEFT){
               tim.setDirection(2);
            }
         
         //if up arrow is pressed, move Tim upwards
            if (e.getKeyCode() == KeyEvent.VK_UP) {
               if (jumpTime == 0) {
                  jumpTime = speed*2;
               }
            //else if (tim.getY() > 100) {
            //jumpTime = jumpTime+2;
            //}
            }
         }
         //System.out.println (distance);      
      }
     
      //if either right or left arrow key is released
      public void keyReleased (KeyEvent e){
         //if (hitTimer == 0){
         if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT){
            tim.setDirection (0);
         }
         //}
      }
   }
   //returns the height Tim will jump
   public double jumpHeight(int t){
   //return (Math.pow(t, 2)*(-2) + 4*t /*+ 3*/);
      return (t*-20 +20);
   }
    /* The player class */
   class Tim extends Thread{
   
    //Tim fields
      int playerX;
      int playerY;
      int direction; //direction 1 means Tim is going forward, 2 means backward, 0 means standing still
      boolean onPlatform = false;
      
   //powerup fields
      int pLength;
      int [] pX;
      int [] pY;
      int [] pType;
      int [] pSpawn; //how far the player travels before this object spawns
      
   //obstacle fields
      int oLength;
      int [] oX;
      int [] oY;
      int [] oDirection; //1=left, 2=right, 0=stationary
      int [] oSpawn;
      int [] oType; //1=dog, 2=poop, 3=car, 4=zombie, 5=ghost, 6=UFO
      int [] oHeight;
      int [] oWidth;
      
   //platform fields
      int [] platformX = new int [100]; //100 platforms
      int [] platformY = new int [100];
      
   //Keeps track of time
      //int timer;
      //int timeCount = 0;
   
      //constructor
      Tim (int x, int y, int level) { //constructor
      
      //coordinates of Tim's position
         this.playerX = x;
         this.playerY = y;
         this.direction =0;
         
      //initialize powerups and obstacles
         if (level==1){
            this.pLength = 49;
            this.oLength = 16;
            this.pX = new int [pLength];
            this.pY = new int [pLength];
            this.pType = new int [pLength];
            this.pSpawn = new int [pLength];
            this.oX = new int [oLength];
            this.oY = new int [oLength];
            this.oDirection = new int [oLength];
            this.oSpawn = new int [oLength];
            this.oType = new int [oLength];
            this.oHeight = new int [oLength];
            this.oWidth = new int [oLength];
            obstacleInit(5, 10, 1, 0, 0, 0);
            powerInit(5, 1, 3, 40);
         }
         else if (level==2){
            this.pLength = 61;
            this.oLength = 15;
            this.pX = new int [pLength];
            this.pY = new int [pLength];
            this.pType = new int [pLength];
            this.pSpawn = new int [pLength];
            this.oX = new int [oLength];
            this.oY = new int [oLength];
            this.oDirection = new int [oLength];
            this.oSpawn = new int [oLength];
            this.oType = new int [oLength];
            this.oHeight = new int [oLength];
            this.oWidth = new int [oLength];
            obstacleInit(0, 0, 0, 15, 0, 0);
            powerInit(7, 1, 3, 50);
         }
         else if (level==3){
            this.pLength = 76;
            this.oLength = 22;
            this.pX = new int [pLength];
            this.pY = new int [pLength];
            this.pType = new int [pLength];
            this.pSpawn = new int [pLength];
            this.oX = new int [oLength];
            this.oY = new int [oLength];
            this.oDirection = new int [oLength];
            this.oSpawn = new int [oLength];
            this.oType = new int [oLength];
            this.oHeight = new int [oLength];
            this.oWidth = new int [oLength];
            obstacleInit(0, 0, 2, 0, 20, 0);
            powerInit(10, 2, 4, 60);
         }
         else if (level==4){
            this.pLength = 92;
            this.oLength = 30;
            this.pX = new int [pLength];
            this.pY = new int [pLength];
            this.pType = new int [pLength];
            this.pSpawn = new int [pLength];
            this.oX = new int [oLength];
            this.oY = new int [oLength];
            this.oDirection = new int [oLength];
            this.oSpawn = new int [oLength];
            this.oType = new int [oLength];
            this.oHeight = new int [oLength];
            this.oWidth = new int [oLength];
            obstacleInit(0, 0, 0, 0, 0, 30);
            powerInit(15, 2, 5, 70);
         }
         else{
            this.pLength = 100;
            this.oLength = 28;
            this.pX = new int [pLength];
            this.pY = new int [pLength];
            this.pType = new int [pLength];
            this.pSpawn = new int [pLength];
            this.oX = new int [oLength];
            this.oY = new int [oLength];
            this.oDirection = new int [oLength];
            this.oSpawn = new int [oLength];
            this.oType = new int [oLength];
            this.oHeight = new int [oLength];
            this.oWidth = new int [oLength];
            obstacleInit(10, 15, 3, 0, 0, 0);
            powerInit(10, 3, 7, 80);
         }
      //array of platforms
         for (int i = 0; i < 100; i++){
            //approximate random non-overlapping horizontal locations
            //by confining platforms into distinct range of x coordinates:
            //i.e. x: (0, 100), (175, 275), (375, 475), (575, 675)...etc.
            platformX[i] = (int)((i + 1) * 175 + Math.random()*100);
            platformY[i] = 180 + (int) (Math.random()*291);
         }
         
      //sets the time
         setTime(level);
      }
   
   /*** obstacleInit *************************************
   * Purpose: Initializes obstacles                      *
   *                                                     *
   * Parameters:int pe,int po,int ca,int zo,int gh,int uf*
   * Returns: none                                       *
   ******************************************************/
   
      public void obstacleInit (int pe, int po, int ca, int zo, int gh, int uf){
         int spawnLocation;
         int indexCount = 0;
         for (int i = 0; i < pe; i++){ //pets
            spawnLocation = (int)(Math.random()*(destination/pe));
            this.oX[i] = WIDTH+100;
            this.oY[i] = 410;
            this.oDirection[i] = 0; // obstacle stationary
            this.oSpawn[i] = i*(destination/pe)+spawnLocation;
            this.oType[i] = 1;
            this.oHeight[i] = 100;
            this.oWidth[i] = 100;
         //System.out.println (oX[i] + " " + oSpawn[i]);
         }
         for (int i = pe; i < pe+po; i++){ //poop
            spawnLocation = (int)(Math.random()*(destination/po));
            this.oX[i]= WIDTH+100;
            this.oY[i]= 415;
            this.oDirection[i]=0; //obstacle stationary
            this.oSpawn[i]=(i-pe)*(destination/po)+spawnLocation;
            this.oType[i]=2;
            this.oHeight[i] = 100;
            this.oWidth[i] = 100;
         //System.out.println (oX[i] + " " + oSpawn[i]);
         }
         for (int i = pe+po; i < pe+po+ca; i++){ //cars
            spawnLocation = (int)(Math.random()*(destination/ca));
            this.oX[i]=WIDTH+100;
            this.oY[i]=360;
            this.oDirection[i]=2;//obstacle moves right to left
            this.oSpawn[i]=(i-(pe+po))*(destination/ca)+spawnLocation;
            this.oType[i]=3;
            this.oHeight[i] = 150;
            this.oWidth[i] = 200;
         //System.out.println (oX[i] + " " + oSpawn[i]);
         }
         for (int i = pe+po+ca; i < pe+po+ca+zo; i++){ //zombies
            spawnLocation = (int)(Math.random()*(destination/zo));
            this.oX[i]= -100;
            this.oY[i]= 400;
            this.oDirection[i]= 1; //ostacle moves left to right
            this.oSpawn[i]= (i-(pe+po+ca))*(destination/zo)+spawnLocation;
            this.oType[i]=4;
            this.oHeight[i] = 100;
            this.oWidth[i] = 90;
         }   
         for (int i = pe+po+ca+zo; i < pe+po+ca+zo+gh; i++){ //ghosts
            spawnLocation = (int)(Math.random()*(destination/gh));
            this.oX[i]=WIDTH+100;
            this.oY[i]=400;
            this.oDirection[i]=2; //obstacle moves right to left
            this.oSpawn[i]=(i-(pe+po+ca+zo))*(destination/gh)+spawnLocation;
            this.oType[i]=5;
            this.oHeight[i] = 100;
            this.oWidth[i] = 100;
         }
         for (int i = pe+po+ca+zo+gh; i < pe+po+ca+zo+gh+uf; i++){ //ufos
            spawnLocation = (int)(Math.random()*(destination/uf));
            this.oX[i]=WIDTH+100;
            this.oY[i]=400;
            this.oDirection[i]=2; //obstacle moves left to right
            this.oSpawn[i]=i*(destination/uf)+spawnLocation;
            this.oType[i]=6;
            this.oHeight[i] = 100;
            this.oWidth[i] = 125;
         }
      }
      
   /*** powerInit ****************************************
   * Purpose: initializes powerups                       *
   *                                                     *
   * Parameters: int r,int s, int a, int g               *
   * Returns: none                                       *
   ******************************************************/
   
      public void powerInit (int r, int s, int a, int g){
         int spawnLocation;
         for (int i = 0; i < r; i++){ //red bull
            spawnLocation = (int)(Math.random()*(destination/s));
            this.pX[i]= WIDTH+100;
         //randomized at interval 100 to 380
            this.pY[i]= 100 + (int)(Math.random()*280);
            this.pSpawn[i]= i*(destination/r)+spawnLocation;
            this.pType[i]=1;
            System.out.println (i + " " + pSpawn[i]);
         }
         for (int i = r; i < r+s; i++){ //shrink potion
            spawnLocation = (int)(Math.random()*(destination/s));
            this.pX[i]= WIDTH+100;
            this.pY[i]= 100 + (int)(Math.random()*280);
            this.pSpawn[i]= (i-r)*(destination/s)+spawnLocation;
            this.pType[i]=2;
            System.out.println (i + " " + pSpawn[i]);
         }
         for (int i = r+s; i < r+s+a; i++){ //clock
            spawnLocation = (int)(Math.random()*(destination/a));
            this.pX[i]= WIDTH+100;
            this.pY[i]= 100 + (int)(Math.random()*280);
            this.pSpawn[i]= (i-(r+s))*(destination/a)+spawnLocation;
            this.pType[i]=3;
            System.out.println (i + " " + pSpawn[i]);
         }
         for (int i = r+s+a; i < r+s+a+g; i++){ //gold star
            spawnLocation = (int)(Math.random()*(destination/g));
            this.pX[i]= WIDTH+100;
            this.pY[i]= 100 + (int)(Math.random()*280);
            this.pSpawn[i]= (i-(r+s+a))*(destination/g)+spawnLocation;
            this.pType[i]=4;
            System.out.println (i + " " + pSpawn[i]);
         }
      }
   
   /* Tim methods - returns fields in the Tim class */
      public int getX () {
         return playerX;
      }
      public int getY(){
         return playerY;
      }
      public void setDirection(int a){
         this.direction = a;
      }
      public int getDirection(){
         return this.direction;
      }
      public void timAnimation (){
         
         if (direction == 1){ //make sure Tim stays in the activity range
            if (playerX <= WIDTH/2)
               playerX += speed;
            else{
               backgroundX -= speed;
               backgroundX_2 -= speed;
               if (backgroundX <= -WIDTH){ //if background2 reaches right edge of screen, put background in front of background2
                  backgroundX = backgroundX_2+WIDTH;
               }
               if (backgroundX_2 <= -WIDTH){ //vice versa
                  backgroundX_2 = backgroundX+WIDTH;
               }
             
               //move platforms
               for (int i =0; i<platformX.length; i++)
                  platformX[i] -=speed; 
                          
                //move power-ups
               for (int i=0; i<pLength; i++) {
                  if (pSpawn[i] < 0 && pX[i]>-WIDTH && pX[i]<WIDTH*2)
                  //if the powerup has spawned
                     pX[i]-=speed;
               }
            }
           
            distance += speed;
         }
                 
         else if (direction == 2) { //make sure Tim stays in the activity range
            if (playerX > TIMX)
               playerX -= speed;
             
            else {
               backgroundX += speed;
               backgroundX_2 += speed;
               if (backgroundX >= 0){ //if background exceeds left edge of screen, put background2 behind background
                  backgroundX_2 = backgroundX-WIDTH;
               }
               if (backgroundX_2 >= 0) {
                  backgroundX = backgroundX_2-WIDTH; //vice versa
               }
              
               //move the platform
               for (int i =0; i<platformX.length; i++)
                  platformX[i] +=speed;
            
                //move the power-ups
               for (int i=0; i<pLength; i++) {
                  if (pSpawn[i] < 0 && pX[i]>-WIDTH && pX[i]<WIDTH*2)
                  //if the powerup has spawned
                     pX[i]+=speed;
               }
            }
            distance -= speed;
         }
         if (jumpTime > 0){ //check if up arrow key is pressed
            if (onPlatform){
               playerY = (int)jumpHeight(jumpTime) + getPlatformY(count)-timH;
            }
            else {
               playerY = (int)jumpHeight(jumpTime) + (500-timH);
            }
         //System.out.println (jumpHeight(jumpTime));
            jumpTime--;//to jump, change the y-coordinate of the Tim
         }
         if (playerY > 500-timH){ //if Tim lands below 500, correct his y position
            playerY = 500-timH;
         }
      
      }  
      
   /*** checkSpawn ***************************************
   * Purpose: spawns the objects                         *
   *                                                     *
   * Parameters: int d                                   *
   * Returns: none                                       *
   ******************************************************/
    
      public void checkSpawn(int d){
         for (int i=0; i<pLength; i++){ //check powerup spawns
            if (d > pSpawn[i]){
               pSpawn[i] = -1000;
            }
         }
         for (int i = 0; i<oLength; i++){ //check obstacle spawns
            if (d > oSpawn[i]){
               oSpawn[i] = -1000;
            }
         }
      }
      
    /* Give powerups effects */
      public int getPowerLength(){
         return this.pLength;
      }
      public int getPowerType(int index){
         return this.pType[index];
      }
      public int getPowerupX(int index){
         return this.pX[index];
      }
      public int getPowerupY(int index){
         return this.pY[index];
      }
      public void powerEffect (int type){
         if (type == 1){
            speed += 1; //red bull
            bullTimer = 1000;
         }
         else if (type == 2){
            timL = timL/2; //shrink potion
            timH = timH/2;
            shrinkTimer = 1000;
         }
         else if (type == 3) { //clock
            gameTimer+=3;
         }
         else if (type == 4) {
            score++; //gold star
         }
      }
   
      // Checks if Tim touches powerup
      public void checkPowers() {
         for (int i = 0; i<pLength; i++){
            if (playerX >= pX[i]- 15 && playerX <= pX[i] + 65){
               if (playerY + timH <= pY[i] + 60 && playerY + timH >= pY[i] - 10 || playerY >= pY[i] - 10  && playerY <= pY[i] + 60){
                  powerEffect (pType[i]);
                  pY[i] = HEIGHT+1000; //make powerup disappear
               }
            }
         }
      }
      //checks if Tim touches obstacles
      public void checkObstacles() {
         for (int i = 0; i<oLength; i++)  {
            if (playerX >= oX[i] && playerX <= oX[i]+oWidth[i]) {
               if ((playerY+timH >= oY[i] && playerY+timH <= oY[i]+oHeight[i]) || (playerY >= oY[i] && playerY <= oY[i] + oHeight[i])){
               //hitTimer = 3000;
                  oY[i] = HEIGHT+1000; //make obstacle disappear
                 
                  //the effects of the obstacles: 
                  if (oType[i] == 1) { //pets
                  //cause Tim to fall down, but he can stand up immediately
                     fall[1] = true;
                     fallen = true;
                  }
                  if (oType[i] == 2){ //poop
                  //cause Tim to fall down, but he can stand up immediately
                     fall[2] = true;
                     fallen = true;
                  }
                  if (oType[i] == 3){ //car
                  //kill Tim and end game
                     lose = true;
                     game = false;
                     tim.interrupt();
                     draw.repaint();
                  }
                  if (oType[i] == 4){ //zombie
                  //caue Tim to fall down and stay there for 3 seconds
                     fall[4] = true;
                     fallen = true;
                  }
                  if (oType[i] == 5){ //ghost
                  //cause Tim to fall down and stay for 6 seconds
                     fall[5] = true;
                     fallen = true;
                  }
                  if (oType[i] == 6){ //ufo
                  //cause Tim to fall down and stay for 9 seconds
                     fall[6] = true;
                     fallen = true;
                  }
               }
            }
         }
      }
      
      /* moves the objects/obstacles relative to Tim */
      public void obstacleMove(int d){
         for (int i = 0; i < oLength; i++){
            if (oSpawn[i] < 0 && oX[i] > -WIDTH && oX[i] < WIDTH*2){
               if (oDirection[i] == 1) {
                  oX[i] += 6*speed; //move right at a speed faster than Tim's
               }
               else if (oDirection[i] == 2){
                  oX[i] -= 6*speed; //move left at a speed faster than Tim's
               }
               else if (oDirection[i] == 0){
                  if (direction == 1 && playerX > WIDTH/2) {
                     oX[i]-=speed;
                  }
                  else if (direction == 2 && tim.playerX == TIMX) {
                     oX[i]+=speed;
                  }
               }
            }
         }
      }
      
      //Obstacle methods
      public int getObstacleWidth(int index){
         return this.oWidth[index];
      }
      public int getObstacleHeight(int index){
         return this.oHeight[index];
      }
      public int getObstacleLength(){
         return this.oLength;
      }
      public int getObstacleType(int index){
         return this.oType[index];
      }
      public int getObstacleX (int index) {
         return this.oX[index];
      }
      public int getObstacleY (int index){
         return this.oY[index];
      }
   
   //platform methods
      public int getPlatformX (int a){
         return platformX [a];
      }
      public int getPlatformY (int a){
         return platformY [a];
      }
      public void checkPlats(){
         if (count < 0){
            for (int i = 0; i < 100; i++){
               if (getPlatformX(i) >= 0 && getPlatformX(i) <= 925){
                  if (playerX >= getPlatformX(i)-25 && playerX <= getPlatformX(i)+58){
                     if (playerY+timH >= getPlatformY(i)-20 && playerY+timH <= getPlatformY(i)+20){
                        onPlatform = true;
                        count = i;
                        playerY = getPlatformY(count)-timH; // make Tim stand on platform
                        temp = jumpTime;
                        jumpTime = 0;
                     }
                  }
               }
            }
         }
         else {
            if (playerX < getPlatformX(count)-25 || playerX > getPlatformX(count)+58){
               onPlatform = false;
               count = -1;
               if (jumpTime == 0){
                  jumpTime = temp;
               }
               else {
                  jumpTime = jumpTime + temp;
               }
            }
         }
      }
      
      //Timer Methods
      public void timerCountDown(){
         if (bullTimer > 0){
            bullTimer--;
         }
         else if (bullTimer == 0){
            speed = 7;
         }
         if (shrinkTimer > 0){
            shrinkTimer--;
         }
         else if (shrinkTimer == 0){
            timH = 120;
            timL = 120;
         }
         if (hitTimer > 0){
            hitTimer--;
         }
      }
      
      //sets the Timer to the correct time
      public void setTime(int l){
         if(l == 1 || l == 2 || l == 5)
            gameTimer = 60; //6;
         else
            gameTimer = 120; //12;
      }
      
      //method to start the timer
      public void countTime() {
         timeCount++;
         if(timeCount == 20){
            if(gameTimer != 0) {
               gameTimer--;
               //System.out.println(timer);
            } 
            else if(distance >= 18000){
               gameTimer = 0;
            }
            
            else {
               //check to see if player got enough points to advance
               if(level < 5){
                  if(score > reqScore){
                     pause = true;
                     game = false;
                     tim.interrupt();
                     draw.repaint();
                  }
                  else {
                     lose = true;
                     game = false;
                     tim.interrupt();
                     draw.repaint();
                  }
               } 
               else {
                  if(score > reqScore) {
                     win = true;
                     game = false;
                     tim.interrupt();
                     draw.repaint();
                  }  
                  else{
                     lose = true;
                     game = false;
                     tim.interrupt();
                     draw.repaint();
                  }  
               }
            }     
            timeCount = 0;
         }
      }
   
      
      //Runs the Thread
      public void run (){
         try{
            while (true) {
               sleep (50);
               countTime();
               timerCountDown();
               
               if (hitTimer == 0){
                  checkSpawn(distance);
                  checkPlats();               
                  checkPowers();
                  checkObstacles();
                  obstacleMove(this.direction);
                  timAnimation();
               }
               
               draw.repaint();
               if (fallen){
                  if (fall[1])
                     hitTimer = 10;
                     //Thread.sleep(1000);
                  else if (fall[2])
                     hitTimer = 10;
                     //Thread.sleep(1000);
                  else if (fall[4])
                     hitTimer = 30;
                     //Thread.sleep(3000);
                  else if (fall[5])
                     hitTimer = 60;
                     //Thread.sleep(6000);  
                  else if (fall[6])
                     hitTimer = 90;
                     //Thread.sleep(9000);  
                  fallen = false;
               }
               runCount++;
            }
         }
         catch (InterruptedException e){
            System.out.println ("Exception: Thread Interrupted");
         }
      }
   }
   
   
   //main Method
   public static void main(String[] args) {
      new TimsRun();  
   }
}
