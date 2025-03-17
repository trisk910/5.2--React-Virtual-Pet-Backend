package cat.itacademy.s05.t02.Models.Enums;

public enum CombatPhrases {
    ATTACK_HIT("attacks and hits, dealing %d damage!"),
    ATTACK_MISS("attacks but misses!"),
    WINNER("is the winner!");

    private final String phrase;

    CombatPhrases(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}