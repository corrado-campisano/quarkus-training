package off.itgoes;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

	List<Animal> findByType(String type);
}
