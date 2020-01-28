package rc.noteit.api;

import java.util.List;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rc.noteit.Mapper;
import rc.noteit.api.viewmodel.NotebookViewModel;
import rc.noteit.db.NotebookRepository;
import rc.noteit.model.Notebook;

/*
Requests can be tested using the built in HTTP Rest Client. Use the
examples found in 'noteit.http' file.
 */

@RestController
@RequestMapping("/api/notebooks")
@CrossOrigin
public class NotebookController {
	private NotebookRepository notebookRepository;
	private Mapper mapper;

	public NotebookController(NotebookRepository notebookRepository, Mapper mapper) {
		this.notebookRepository = notebookRepository;
		this.mapper = mapper;
	}

	@GetMapping("/all")
	public List<Notebook> all() {
		List<Notebook> allCategories = this.notebookRepository.findAll();
		return allCategories;
	}

	@PostMapping
	public Notebook save(@RequestBody NotebookViewModel notebookViewModel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException();
		}

		Notebook notebookEntity = this.mapper.convertToNotebookEntity(notebookViewModel);

		// save notebookEntity instance to db
		this.notebookRepository.save(notebookEntity);

		return notebookEntity;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		this.notebookRepository.deleteById(UUID.fromString(id));
	}
}
