package cat.itacademy.s05.t02.service;


import cat.itacademy.s05.t02.Models.Robo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoboService {
    Mono<Robo> buildRobo(Robo robo);
    Mono<Void> destroyRobo(String id);
    Flux<Robo> getAllRobos();
    Mono<Robo> getRoboById(String id);
    Mono<Robo> updateRobo(Robo robo);
}
