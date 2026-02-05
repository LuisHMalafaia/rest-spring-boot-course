package br.com.luishmalafaia.services;

import br.com.luishmalafaia.controllers.PersonController;
import br.com.luishmalafaia.data.dto.PersonDTO;
import br.com.luishmalafaia.exception.RequiredObjectIsNullException;
import br.com.luishmalafaia.exception.ResourceNotFoundException;
import static br.com.luishmalafaia.mapper.ObjectMapper.parseListObjects;
import static br.com.luishmalafaia.mapper.ObjectMapper.parseObject;
import br.com.luishmalafaia.model.Person;
import br.com.luishmalafaia.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public PersonDTO findById(Long id){
        logger.info("Finding one person!");

        var entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var dto = parseObject(entity, PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public List<PersonDTO> findAll(){
        logger.info("Finding all people!");

        var persons = parseListObjects(repository.findAll(), PersonDTO.class);

        persons.forEach(this::addHateoasLinks);

        return persons;
    }

    public PersonDTO create(PersonDTO person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person!");

        var entity = parseObject(person, Person.class);

        var dto = parseObject(repository.save(entity), PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO update(PersonDTO person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person!");

        Person entity = repository
                .findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        Person entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}