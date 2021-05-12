import basis.*;
import javax.swing.*;

/**
 * View Controller basiert auf vereinfachtem MVC Prinzip
 * @author rckp.me
 */
public class ViewController implements KnopfLauscher{
    //MODEL
    PlayField sF;


    // VIEW
    Fenster win;
    Knopf playAgain;
    Knopf[][] field;
    Leinwand log;
    Stift myPen;

    // VARIABLES
    private int y = 20;


    public ViewController(){
        sF = new PlayField(this);
        String nameP1 = JOptionPane.showInputDialog("Name Player1: ", "Player 1");
        String nameP2 = JOptionPane.showInputDialog("Name Player2: ", "Player 2");
        sF.createPlayer(nameP1, nameP2);


        createGameField();
        setLog("", true);
    }

    /**
     * generelles Fenster wird erstellt
     */
    public void createGameField(){
        win = new Fenster("Tic Tac Toe - PaulRecker", 600, 340);
        win.setzeHintergrundFarbe(Farbe.rgb(226, 226, 226));
        playAgain = new Knopf("Play Again",350,23,220,70);
        playAgain.setzeSchriftGroesse(30);
        playAgain.setzeKnopfLauscher(this);
        log = new Leinwand(350, 123, 220, 200, win);
        myPen = new Stift(log);
        win.getMeinJFrame().setIconImage(new ImageIcon("images/icons8-arcade_cabinet_filled.png").getImage());
        createButtons();
    }

    /**
     * Button Array und die Anordnung auf dem Fenster
     */
    public void createButtons(){
        field = new Knopf[3][3];
        int x = 10;
        int y = 10;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                field[r][c] = new Knopf("", x, y, 100, 100);
                field[r][c].setzeHintergrundFarbe(Farbe.rgb(212, 212, 212));
                field[r][c].setzeSchriftArt("Calibri");
                field[r][c].setzeSchriftGroesse(120);
                field[r][c].setzeKnopfLauscher(this);
                x = x + 110;
            }
            x = 10;
            y = y + 110;
        }
    }


    /**
     * Log für das Spiel
     * @param s String
     * @param del Lösche vorherige Einträge
     */
    public void setLog(String s, boolean del){
        if (del){
            y=25;
            log.loescheAlles();
            myPen.setzeFarbe(Farbe.SCHWARZ);
            myPen.rechteck(0,0,220, 200);
            myPen.bewegeBis(5,y);
            myPen.setzeSchriftGroesse(15);
            myPen.setzeSchriftArt("Calibri");
            myPen.schreibeText("LOG:");
            y=y+20;
            myPen.bewegeBis(5,y);
            myPen.setzeSchriftGroesse(13);
            myPen.setzeFarbe(Farbe.BLAU);
            myPen.schreibeText((sF.getPlayerName(1) + ": " + sF.getPlayerWin(1)));
            y=y+15;
            myPen.setzeFarbe(Farbe.ROT);
            myPen.bewegeBis(5,y);
            myPen.schreibeText((sF.getPlayerName(2) + ": " + sF.getPlayerWin(2)));
            y=y+15;

        }
        myPen.setzeFarbe(Farbe.SCHWARZ);
        myPen.setzeSchriftGroesse(13);
        myPen.setzeSchriftArt("Calibri");
        myPen.bewegeBis(5,y);
        myPen.schreibeText(s);
        y=y+15;

    }

    /**
     * Gibt den Gewinn im Log aus
     * @param winner Gewinner des akt. Spiels
     */
    public void setWin(String winner){
        switch (winner) {
            case "X", "O" -> setLog("", true);
            case "XO" -> setLog("Gleichstand", true);
        }
    }

    /**
     * An übergebener Stelle wird die Farbe und das Kürzel des Spielers plaziert
     * @param r x
     * @param c y
     * @param Zeichen X / O
     */
    public void setField(int r, int c, String Zeichen){
        field[r][c].setzeSchriftArt("Calibri");
        field[r][c].setzeSchriftGroesse(80);
        if (Zeichen.equals("X")){
            field[r][c].setzeHintergrundFarbe(Farbe.BLAU);
            field[r][c].setzeText("X");
        } else if (Zeichen.equals("O")) {
            field[r][c].setzeHintergrundFarbe(Farbe.ROT);
            field[r][c].setzeText("O");
        }
    }

    /**
     * Setzt den Inhalt aller Knöpfe zurück
     */
    public void clearButtons(){
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                field[r][c].setzeHintergrundFarbe(Farbe.WEISS);
                field[r][c].setzeText("");
            }
        }
    }



    @Override
    public void bearbeiteKnopfDruck(Knopf knopf) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if(knopf == field[r][c]) {
                    sF.setField(r,c);
                }
            }
        }
        if (knopf == playAgain){
            sF.playAgain();
        }
    }

}
