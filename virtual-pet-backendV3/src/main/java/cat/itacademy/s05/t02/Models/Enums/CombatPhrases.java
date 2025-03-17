package cat.itacademy.s05.t02.Models.Enums;

public enum CombatPhrases {
    ATTACK_HIT("hits for %d damage!"),
    ATTACK_MISS("misses the attack!"),
    WINNER("wins the battle!"),
    COUNTER_ATTACK("counterattacks for %d damage!"); // New phrase

    private final String phrase;

    CombatPhrases(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}