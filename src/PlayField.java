import java.util.Arrays;

/**
 * Model basiert auf vereinfachtem MVC Prinzip
 * @author rckp.me
 */
public class PlayField
{
    private final ViewController vC;

    // PLAYER
    private Player p1;
    private Player p2;

    // FIELD
    private int turn = 1;
    private boolean gameWon = false;
    private String winner;
    private final String[][] field = new String[3][3];

    public PlayField(ViewController pVC)
    {
        vC = pVC;
        // Array mit Leerzeichen belegen
        for (String[] strings : field) {
            Arrays.fill(strings, " ");
        }
    }




    // PLAYER
    /**
     * Man übergibt Namen für zwei Spieler und zwei Player Objekte werden erstellt
     * @param nameP1 P1 Name
     * @param nameP2 P2 Name
     */
    public void createPlayer(String nameP1, String nameP2){
        p1 = new Player(nameP1, "X", 0);
        p2 = new Player(nameP2, "O", 0);
    }


    /**
     * Man übergibt den Player (int 1/2) und kriegt den Namen zurück
     * @param player int
     * @return Name of Player
     */
    public String getPlayerName(int player){
        return switch (player) {
            case 1 -> p1.getName();
            case 2 -> p2.getName();
            default -> null;
        };
    }

    /**
     * Übergibt den Player (int 1/2) und kriegt die Anzahl der Wins zurück
     * @param player int
     * @return Player Win
     */
    public int getPlayerWin(int player){
        return switch (player) {
            case 1 -> p1.getWins();
            case 2 -> p2.getWins();
            default -> -1;
        };
    }



    // FIELD
    /**
     * Leert das Sielfeld
     */
    public void emptyField()
    {
        // Array mit Leerzeichen belegen
        for (String[] strings : field) {
            Arrays.fill(strings, " ");
        }

    }

    /**
     * Diese Methode gibt das komplette Spielfeld aus
     */
    public void printField()
    {
        for(int i = 0; i< field.length; i++)
        {
            for(int j = 0; j< field[i].length; j++)
            {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
            System.out.println("------");
        }
    }

    /**
     * Diese Methode prüft ob ein bestimmtes Feld belegt ist.
     */
    public boolean fieldOccupied(int x, int y)
    {
        if (!field[x][y].equals("X")) {
            return field[x][y].equals("O");
        } else {
            return true;
        }
    }

    /**
     * Man setzt entweder X oder O auf ein bestimmtes Feld.
     */
    public void setField(int x, int y)
    {
            if(!checkOnWinners() && !getFieldFull() && !fieldOccupied(x,y))
            {
                if (turn%2==0){
                    field[x][y] = "X";
                    vC.setField(x,y,"X");
                } else {
                    field[x][y] = "O";
                    vC.setField(x,y,"O");
                }
                turn++;
            }
            if (checkOnWinners() && !gameWon) {
                switch (winner) {
                    case "X" -> {
                        p1.won();
                        vC.setWin(winner);
                        gameWon = true;
                        winner = null;
                    }
                    case "O" -> {
                        p2.won();
                        vC.setWin(winner);
                        gameWon = true;
                        winner = null;
                    }
                    case "XO" -> {
                        gameWon = true;
                        vC.setWin(winner);
                        winner = null;
                    }
                }
            }
    }

    /**
     * Findet heraus ob das Spielfeld voll mit X oder O ist und gibt es mit Rückgabewert wider.
     */
    public boolean getFieldFull()
    {

        for(int i = 0; i< field.length; i++)
        {
            for(int j = 0; j< field[i].length; j++)
            {
                if(field[i][j].equals(" "))
                {
                    return false;
                }
            }
        }
        return true;

    }


    /**
     * Leitet neues Spiel ein
     */
    public void playAgain() {
        gameWon =false;
        emptyField();
        vC.clearButtons();
        vC.setLog("",true);
    }

    /**
     * Findet heraus wer das Spiel gewonnen hat.
     */
    public boolean checkOnWinners()
    {


        // Check Zeilen und Spalten
        for(int i = 0; i< field.length; i++)
        {
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(" "))
            {
                winner = field[i][0];
                return true;
            }
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(" "))
            {
                winner = field[0][i];
                 return true;
            }
        }



        // Check Diagonalen
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) &&!field[0][0].equals(" "))
        {
            winner = field[0][0];
            return true;
        }

        else if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) &&!field[0][2].equals(" "))
        {
            winner = field[0][2];
             return true;
        }

        if (getFieldFull()){
        winner = "XO";
        return true;
        }
        return false;
    }
}
