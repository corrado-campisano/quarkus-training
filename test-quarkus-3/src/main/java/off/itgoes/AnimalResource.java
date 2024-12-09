package off.itgoes;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import java.util.List;

@Path("/animals")
public class AnimalResource {
	private final AnimalRepository fruitRepository;
	public AnimalResource(AnimalRepository fruitRepository) {
		this.fruitRepository = fruitRepository;
	}
	@GET
	public Iterable<Animal> findAll() {
		return fruitRepository.findAll();
	}
	@DELETE
	@Path("{id}")
	public void delete(@PathParam long id) {
		fruitRepository.deleteById(id);
	}
	@POST
	@Path("/name/{name}/type/{type}")
	public Animal create(@PathParam String name, @PathParam String type) {
		return fruitRepository.save(new Animal(name, type));
	}
	@GET
	@Path("/type/{type}")
	public List<Animal> findByType(@PathParam String type) {
		return fruitRepository.findByType(type);
	}
}