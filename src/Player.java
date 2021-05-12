/**
 * Klasse Player als Speicher für Spielername und Wins
 * @author rckp.me
 */
public class Player {
    private final String name;
    private int wins;
    private final String character;

    public Player(String name, String character, int wins){
        this.character = character;
        this.name = name;
        this.wins = wins;
    }

    public String getName() {
        return this.name;
    }

    public int getWins() {
        return this.wins;
    }

    public String getCharacter() {
        return this.character;
    }

    public void won() {
        this.wins = this.wins + 1;
    }
}
