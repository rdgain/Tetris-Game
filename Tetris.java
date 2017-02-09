package Tetris2;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * reads highscores from file
 * 2 panels: left - main game; right - player information, buttons to start, pause, resume game
 */

public class Tetris extends JApplet implements Runnable {

    /**
     * score - integer, the player's score
     * mainPanel - object of type TetrisMain, contains the main game functionality
     * scoreKeeper - label used to display the score
     * levelIndicator - label used to display the level
     * sidePanel, secondarySidePanel - JPanels, used for layout
     * nextPiece - JPanel, used to display the next piece
     * t - thread used to run the game
     * finishedGame - boolean, indicates whether the game finished or not (true if finished, false otherwise)
     * speed - integer, the speed with which the pieces fall, starts at 1000
     * level - integer, indicates the current level, starts at 1
     * maxScore - integer, maximum score needed to advance to the next level, starts at 200
     * count - integer, counts frames
     */

    int score;
    TetrisMain mainPanel;
    JLabel scoreKeeper, levelIndicator;
    JEditorPane story, howTos;
    JPanel sidePanel, secondarySidePanel, buttonPanel, howTPanel, scorePanel, levelPanel;
    JPanel topPanel, botPanel;
    JLabel nameRegNumber;
    JButton startGame, pauseGame, resumeGame, endGame;
    Thread t;
    boolean finishedGame;
    int speed = 1000;
    int level = 1;
    int maxScore = 200;
    int count=0;

    /**
     * initialises the applet
     * sets the applet window size to 800x520
     * sets the applet layout to GridLayout
     */
    public void init () {
        setSize(800, 550);

        /**
         * create all layout elements
         */
        topPanel = new JPanel();
        botPanel = new JPanel();
        mainPanel = new TetrisMain();
        sidePanel = new JPanel();
        secondarySidePanel = new JPanel();
        buttonPanel = new JPanel();
        howTPanel = new JPanel();
        scorePanel = new JPanel();
        levelPanel = new JPanel();
        nameRegNumber = new JLabel("Name: Raluca Gaina   ;   Registration number: 1201870");
        howTos = new JEditorPane("text/html", "");
        story = new JEditorPane("text/html", "");


        /**
         * initialise the score, score label and level label
         */
        score = 0;
        scoreKeeper = new JLabel("Your saved "+score+" Christmas kittens out of "+maxScore+" needed to advance");
        levelIndicator = new JLabel("YOU ARE AT LEVEL "+level);

        /**
         * define the instructions field
         */
        howTos.setSize(335,40);
        howTos.setBorder(new BorderUIResource.LineBorderUIResource(Color.RED, 3));
        howTos.setBackground(new Color(33, 161, 91));
        howTos.setEditable(false);
        howTos.setText("<center><br>    <b><u>How To ...</u></b> <br><br>" +
                "  >> MOVE LEFT - left mouse button <br>" +
                "  >> MOVE RIGHT - right mouse button <br>" +
                "  >> ROTATE - middle mouse button<br><br>" +
                " <i><b>! Warning </b></i><br> the higher the level, the faster the pieces will fall. <br><br>");

        /**
         * define the story field
         */
        story.setSize(310,40);
        story.setBorder(new BorderUIResource.LineBorderUIResource(Color.RED, 3));
        story.setBackground(new Color(33, 161, 91));
        story.setEditable(false);
        story.setText("<center><br><b><big><u>YOUR MISSION</u></big></b> <br><br>" +
                "    You know Grinch, right? Well, he's an evil guy who happens to have <br>" +
                " stolen poor kittens from all over the world! Every piece you place <br>" +
                " correctly unleashes powerful Christmas magic that saves 10 kittens! <br>" +
                "    <b>HELP SAVE THEM ALL!</b> <br>" +
                " They might reward you with milk. <br>" +
                " Or a pretty Christmas gift. Or not. <br><br></center>");


        /**
         * define button Start game (starts the game)
         */
        startGame = new JButton("Start game");
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainPanel.started)  {
                    t.start();  // start game
                    mainPanel.started = true;
                    mainPanel.paused = false;
                }
            }
        });

        /**
         * define button Pause (pauses running game)
         */
        pauseGame = new JButton("Pause");
        pauseGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.started && !mainPanel.paused) {
                    mainPanel.paused = true;
                }
            }
        });

        /**
         * define button Resume (resumes paused game)
         */
        resumeGame = new JButton("Resume");
        resumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.started && mainPanel.paused) {
                    mainPanel.paused = false;
                }
            }
        });

        /**
         * define button End (ends running game)
         */
        endGame = new JButton("End");
        endGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.started)
                    finishedGame = true;    // game ended
            }
        });

        /**
         * add elements to their corresponding panels to create the layout
         */
        buttonPanel.add(startGame);
        buttonPanel.add(pauseGame);
        buttonPanel.add(resumeGame);
        buttonPanel.add(endGame);

        howTPanel.add(howTos);
        howTPanel.add(story);

        scorePanel.add(scoreKeeper);
        levelPanel.add(levelIndicator);

        secondarySidePanel.setLayout(new BorderLayout());
        secondarySidePanel.add(scorePanel, BorderLayout.NORTH);
        secondarySidePanel.add(howTPanel, BorderLayout.CENTER);

        sidePanel.setLayout(new BorderLayout());
        sidePanel.add(buttonPanel, BorderLayout.NORTH);
        sidePanel.add(levelPanel, BorderLayout.SOUTH);
        sidePanel.add(secondarySidePanel, BorderLayout.CENTER);

        topPanel.add(nameRegNumber);

        botPanel.setLayout(new GridLayout(1, 2));
        botPanel.add(mainPanel);
        botPanel.add(sidePanel);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(botPanel, BorderLayout.CENTER);

        /**
         * set game as not finished
         */
        finishedGame = false;


        /**
         * create a new thread
         */
        t=new Thread(this);
    }

    public void run() {

        score = mainPanel.getScore();
        scoreKeeper.setText(""+score);
        boolean allLevels = false;

        /**
         * continue running until game is finished
         */
        while(!finishedGame && level <= 5)
        {
            try
            {
                if (!mainPanel.paused) {
                    count++;

                    /**
                     * display the score
                     */
                    score = mainPanel.getScore();
                    scoreKeeper.setText("You saved "+score+" Christmas kittens out of "+maxScore+" needed to advance");


                    /**
                     * display level
                     */
                    if (score >= maxScore) {
                        speed -= 200;
                        level++;
                        maxScore += 300 * (int)Math.pow(level,1.16);
                    }
                    if (level < 6) {
                        levelIndicator.setText("YOU ARE AT LEVEL "+level);
                    }
                    else {
                        finishedGame = true;
                        allLevels = true;
                    }


                    /**
                     * check if game ended
                     */
                    if(mainPanel.finished()) finishedGame = true;

                    /**
                     * move the piece down
                     */
                    if (!mainPanel.checkHitBottom() && mainPanel.started && !finishedGame ) {
                        mainPanel.grid.addEmptyPiece(mainPanel.currentPiece);
                        mainPanel.currentPiece.moveDown();
                        mainPanel.grid.addPiece(mainPanel.currentPiece); }

                    mainPanel.repaint();
                    Thread.sleep(speed);
                }
            }
            catch(Exception e){}
        }

        if (allLevels) {
            scoreKeeper.setText("Yay! You did it! You saved all 4700 kittens!");
            score = 4700;
        }
        else
            scoreKeeper.setText("Oh no! You only saved "+score+" kittens! Sad kittens.");

        levelIndicator.setText("Game over");
    }
}
