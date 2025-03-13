/*package cat.itacademy.s05.t01.n01.Controllers;
import cat.itacademy.s05.t01.n01.Models.Player;
import cat.itacademy.s05.t01.n01.service.Player.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

class PlayerControllerTest {
    @Mock
    private PlayerServiceImpl playerService;

    @InjectMocks
    private PlayerController playerController;
    private Player player1 = new Player("Johny");
    private Player player2 = new Player("Mary");

    @BeforeEach
    void initialSetup() {
        MockitoAnnotations.openMocks(this);
        player1.setId(1);
        player1.setScore(10.00);
        player2.setId(2);
        player2.setScore(50.00);
    }

    @Test
    void updatePlayerNameWhenFound() {
        String newName = "Joe";
        Player expectedPlayer = new Player(newName);
        expectedPlayer.setId(player1.getId());
        expectedPlayer.setScore(player1.getScore());
        when(playerService.updatePlayerName(player1.getId(), newName)).thenReturn(Mono.just(expectedPlayer));
        StepVerifier.create(playerController.updatePlayerName(player1.getId(), newName))
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.OK) &&
                        Objects.equals(response.getBody(), expectedPlayer))
                .verifyComplete();
    }

    @Test
    void updatePlayerNameWhenNotFound() {
        String newName = "Unbongo";
        int playerId = 999999999;
        when(playerService.updatePlayerName(playerId, newName)).thenReturn(Mono.error(new RuntimeException("Player not found")));
        StepVerifier.create(playerController.updatePlayerName(playerId, newName))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void getPlayerRanking() {
        List<Player> players = List.of(player1, player2);
        when(playerService.getPlayersSorted()).thenReturn(Flux.fromIterable(players));
        StepVerifier.create(playerController.getPlayersRanking())
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.OK) &&
                        Objects.equals(response.getBody(), players))
                .verifyComplete();
    }
}*/