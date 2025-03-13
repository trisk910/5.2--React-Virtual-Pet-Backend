package cat.itacademy.s05.t02.Controllers;

import cat.itacademy.s05.t02.DTOs.Fight.CreateFightDTO;
import cat.itacademy.s05.t02.DTOs.Fight.FightDTO;
import cat.itacademy.s05.t02.Exceptions.FightNotFoundException;
import cat.itacademy.s05.t02.Exceptions.RoboNotFoundException;
import cat.itacademy.s05.t02.Models.Fight;
import cat.itacademy.s05.t02.Models.Robo;
import cat.itacademy.s05.t02.Repository.FightRepository;
import cat.itacademy.s05.t02.Models.FightSimulator;
import cat.itacademy.s05.t02.Service.RoboService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/fights")
public class FightController {
    private static final Logger logger = LogManager.getLogger(FightController.class);

    @Autowired
    private FightRepository fightRepository;

    @Autowired
    private RoboService roboService;

    private final FightSimulator fightSimulator = new FightSimulator();

    @PostMapping("/fight")
    @Operation(summary = "Fight Robos", description = "Simulates a fight between two robos and saves the result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fight simulated successfully"),
            @ApiResponse(responseCode = "404", description = "Robo not found")
    })
    public ResponseEntity<FightDTO> simulateFight(@RequestBody CreateFightDTO createFightDTO) {
        Optional<Robo> robo1Opt = Optional.ofNullable(roboService.getRoboById(createFightDTO.getRobo1Id()));
        Optional<Robo> robo2Opt = Optional.ofNullable(roboService.getRoboById(createFightDTO.getRobo2Id()));

        if (robo1Opt.isEmpty() || robo2Opt.isEmpty()) {
            logger.error("Robo not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Robo robo1 = robo1Opt.get();
        Robo robo2 = robo2Opt.get();
        String winner = fightSimulator.determineWinner(robo1, robo2);
        logger.info("Winner: " + winner);
        Fight fight = new Fight(robo1.getName(), robo2.getName(), winner);
        Fight savedFight = fightRepository.save(fight);
        FightDTO fightDTO = new FightDTO(savedFight.getId(), savedFight.getRobo1Name(), savedFight.getRobo2Name(), savedFight.getWinner());
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        fightRepository.delete(fightOpt.get());
        return ResponseEntity.ok().build();
    }
}