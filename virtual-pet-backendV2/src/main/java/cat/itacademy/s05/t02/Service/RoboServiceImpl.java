package cat.itacademy.s05.t02.Service;


import cat.itacademy.s05.t02.Models.Robo;
import cat.itacademy.s05.t02.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoboServiceImpl implements RoboService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public Mono<Robo> buildRobo(Robo robo) {
        return petRepository.save(robo);
    }

    @Override
    public Mono<Void> destroyRobo(String id) {
        return petRepository.deleteById(id);
    }

    @Override
    public Flux<Robo> getAllRobos() {
        return petRepository.findAll();
    }

    @Override
    public Mono<Robo> getRoboById(String id) {
        return petRepository.findById(id);
    }

    @Override
    public Mono<Robo> updateRobo(Robo robo) {
        return petRepository.findById(robo.getId())
                .flatMap(existingPet -> {
                    existingPet.setName(robo.getName());
                    return petRepository.save(existingPet);
                });
    }
}