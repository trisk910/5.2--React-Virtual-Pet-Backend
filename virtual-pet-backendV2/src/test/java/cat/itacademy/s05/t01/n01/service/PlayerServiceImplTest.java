/*package cat.itacademy.s05.t01.n01.service.Player;

import cat.itacademy.s05.t01.n01.Models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player player1;
    private Player player2;

   @BeforeEach
   void setUp() {
       MockitoAnnotations.openMocks(this);
       player1 = new Player("Johny");
       player1.setId(1);
       player1.setScore(10.00);
       player2 = new Player("Mary");
       player2.setId(2);
       player2.setScore(50.00);
       when(playerRepository.findById(player1.getId())).thenReturn(Mono.just(player1));
       when(playerRepository.save(player1)).thenReturn(Mono.just(player1));
   }

    @Test
    void updatePlayerNameWhenFound() {
        String newName = "Marc";
        Player updatedPlayer = new Player(newName);
        updatedPlayer.setId(player1.getId());
        updatedPlayer.setScore(player1.getScore());
        when(playerRepository.findById(player1.getId())).thenReturn(Mono.just(player1));
        when(playerRepository.save(updatedPlayer)).thenReturn(Mono.just(updatedPlayer));
        StepVerifier.create(playerService.updatePlayerName(player1.getId(), newName))
                .expectNext(updatedPlayer)
                .verifyComplete();
    }

    @Test
    void updatePlayerNameWhenNotFound() {
        String newName = "Unbongo";
        int playerId = 999999999;
        when(playerRepository.findById(playerId)).thenReturn(Mono.empty());
        StepVerifier.create(playerService.updatePlayerName(playerId, newName))
                .expectError(RuntimeException.class)
                .verify();
    }

}*/