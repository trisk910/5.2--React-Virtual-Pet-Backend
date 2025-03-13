package cat.itacademy.s05.t02.Service;

        import cat.itacademy.s05.t02.Models.Robo;
        import cat.itacademy.s05.t02.Repository.RoboRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

        @Service
        public class RoboServiceImpl implements RoboService {

            @Autowired
            private RoboRepository roboRepository;

            @Override
            public Robo buildRobo(Robo robo) {
                return roboRepository.save(robo);
            }

            @Override
            public void destroyRobo(Long id) {
                roboRepository.deleteById(id);
            }

            @Override
            public List<Robo> getAllRobos() {
                return roboRepository.findAll();
            }

            @Override
            public Robo getRoboById(Long id) {
                return roboRepository.findById(id).orElse(null);
            }

            @Override
            public Robo updateRobo(Robo robo) {
                return roboRepository.save(robo);
            }
        }