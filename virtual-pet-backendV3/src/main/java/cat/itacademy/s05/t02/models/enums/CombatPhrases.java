package cat.itacademy.s05.t02.models.enums;

public enum CombatPhrases {
    ATTACK_HIT("hits for %d damage!"),
    ATTACK_MISS("misses the attack!"),
    WINNER("wins the battle!"),
    COUNTER_ATTACK("counterattacks for %d damage!"),
    DOUBLE_KO("Double KO! No one wins the battle.");

    private final String phrase;

    CombatPhrases(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}