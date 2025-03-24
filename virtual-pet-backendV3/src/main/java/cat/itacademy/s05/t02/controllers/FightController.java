package cat.itacademy.s05.t02.controllers;

import cat.itacademy.s05.t02.dtos.fight.CreateFightDTO;
import cat.itacademy.s05.t02.dtos.fight.FightDTO;
import cat.itacademy.s05.t02.exceptions.FightNotFoundException;
import cat.itacademy.s05.t02.exceptions.RoboNotFoundException;
import cat.itacademy.s05.t02.models.Fight;
import cat.itacademy.s05.t02.models.Robo;
import cat.itacademy.s05.t02.repository.FightRepository;
import cat.itacademy.s05.t02.models.FightSimulator;
import cat.itacademy.s05.t02.service.RoboService;
import cat.itacademy.s05.t02.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fights")
public class FightController {
    private static final Logger logger = LogManager.getLogger(FightController.class);

    private final FightRepository fightRepository;

    private final RoboService roboService;

    private final UserService userService;

    private final FightSimulator fightSimulator;

    @Autowired
    public FightController(UserService userService, FightRepository fightRepository, RoboService roboService) {
        this.fightSimulator = new FightSimulator(userService);
        this.fightRepository = fightRepository;
        this.roboService = roboService;
        this.userService = userService;
    }

    @PostMapping("/fight")
    @Operation(summary = "Fight Robos", description = "Simulates a fight between two robos and saves the result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fight simulated successfully"),
            @ApiResponse(responseCode = "404", description = "Robo not found")
    })
    public ResponseEntity<FightDTO> simulateFight(@RequestBody CreateFightDTO createFightDTO) {
        Robo robo1 = roboService.getRoboById(createFightDTO.getRobo1Id());
        Robo robo2 = roboService.getRoboById(createFightDTO.getRobo2Id());

        if (robo1 == null || robo2 == null) {
            logger.error("Robo not found");
            throw new RoboNotFoundException("Robo not found");
        }
        List<String> combatLog = fightSimulator.simulateFight(robo1, robo2);
        String winner = robo1.getHealth() > 0 ? robo1.getName() : robo2.getName();
        Fight fight = new Fight(robo1.getName(), robo2.getName(), winner);
        Fight savedFight = fightRepository.save(fight);
        FightDTO fightDTO = new FightDTO(savedFight.getId(), savedFight.getRobo1Name(), savedFight.getRobo2Name(), savedFight.getWinner(), combatLog);
        fightDTO.setCombatLog(combatLog);
        return ResponseEntity.ok(fightDTO);
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a fight", description = "Deletes a fight by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fight deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Fight not found")
    })
    public ResponseEntity<Void> deleteFight(@PathVariable Long id) {
        Optional<Fight> fightOpt = fightRepository.findById(id);
        if (fightOpt.isEmpty()) {
            logger.error("Fight not found: " + id);
            throw new FightNotFoundException("Fight not found with id: " + id);
        }
        fightRepository.delete(fightOpt.get());
        return ResponseEntity.ok().build();
    }
}