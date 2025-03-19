package cat.itacademy.s05.t02.Models;

                        import cat.itacademy.s05.t02.Models.Enums.CombatPhrases;
                        import cat.itacademy.s05.t02.Service.UserService;
                        import org.springframework.beans.factory.annotation.Autowired;
                        import org.springframework.stereotype.Component;

                        import java.util.ArrayList;
                        import java.util.List;
                        import java.util.Random;

                        @Component
                        public class FightSimulator {

                            private final Random random = new Random();
                            private final UserService userService;
                            private final static int CURRENCY_REWARD = 12;

                            @Autowired
                            public FightSimulator(UserService userService) {
                                this.userService = userService;
                            }

                            public List<String> simulateFight(Robo robo1, Robo robo2) {
                                List<String> combatLog = new ArrayList<>();

                                if (!robo1.canFight() || !robo2.canFight()) {
                                    combatLog.add("One or both Robos cannot fight due to heavy damage.");
                                    return combatLog;
                                }

                                int initialHealthRobo1 = robo1.getHealth();
                                int initialHealthRobo2 = robo2.getHealth();
                                /*int currentHealthRobo1 = robo1.getHealth();
                                int currentHealthRobo2 = robo2.getHealth();*/

                                Robo firstAttacker = robo1.getSpeed() <= robo2.getSpeed() ? robo1 : robo2;
                                Robo secondAttacker = firstAttacker == robo1 ? robo2 : robo1;

                                do {
                                    combatLog.add(performAttack(firstAttacker, secondAttacker));
                                    if (secondAttacker.getHealth() <= 0) {
                                        secondAttacker.setHealth(0);
                                        combatLog.add(firstAttacker.getName() + " " + CombatPhrases.WINNER.getPhrase());
                                        userService.addCurrency(firstAttacker.getUserId(), CURRENCY_REWARD);
                                        userService.incrementWins(firstAttacker.getUserId());
                                        resetHealthAndStats(robo1, initialHealthRobo1);
                                        resetHealthAndStats(robo2, initialHealthRobo2);
                                        break;
                                    }
                                    combatLog.add(performAttack(secondAttacker, firstAttacker));
                                    if (firstAttacker.getHealth() <= 0) {
                                        firstAttacker.setHealth(0);
                                        combatLog.add(secondAttacker.getName() + " " + CombatPhrases.WINNER.getPhrase());
                                        userService.addCurrency(secondAttacker.getUserId(), CURRENCY_REWARD);
                                        userService.incrementWins(secondAttacker.getUserId());
                                        resetHealthAndStats(robo1, initialHealthRobo1);
                                        resetHealthAndStats(robo2, initialHealthRobo2);
                                        break;
                                    }
                                } while (robo1.getHealth() > 0 && robo2.getHealth() > 0);

                                return combatLog;
                            }

                            private String performAttack(Robo attacker, Robo defender) {
                                if (random.nextBoolean()) {
                                    int damage = random.nextInt(attacker.getAttack());
                                    defender.setHealth(Math.max(defender.getHealth() - damage, 0));
                                    String attackLog = String.format(attacker.getName() + " " + CombatPhrases.ATTACK_HIT.getPhrase(), damage);

                                    if (random.nextDouble() < 0.15) {
                                        int counterDamage = random.nextInt(defender.getAttack());
                                        attacker.setHealth(Math.max(attacker.getHealth() - counterDamage, 0));
                                        attackLog += " And " + String.format(defender.getName() + " " + CombatPhrases.COUNTER_ATTACK.getPhrase(), counterDamage);
                                        if (attacker.getHealth() <= 0) {
                                            return attackLog;
                                        }
                                    }
                                    return attackLog;
                                } else {
                                    return attacker.getName() + " " + CombatPhrases.ATTACK_MISS.getPhrase();
                                }
                            }

                            private void resetHealthAndStats(Robo robo, int initialHealth) {
                                robo.setHealth(initialHealth);
                                if (robo.getHealth() < initialHealth) {
                                    robo.reduceStats(false);
                                } else {
                                    robo.reduceStats(true);
                                }
                            }
                        }