package br.com.luishmalafaia.services;

import br.com.luishmalafaia.data.dto.PersonDTO;
import br.com.luishmalafaia.exception.ResourceNotFoundException;
import static br.com.luishmalafaia.mapper.ObjectMapper.parseListObjects;
import static br.com.luishmalafaia.mapper.ObjectMapper.parseObject;
import br.com.luishmalafaia.model.Person;
import br.com.luishmalafaia.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return parseObject(entity, PersonDTO.class);
    }

    public List<PersonDTO> findAll(){
        logger.info("Finding all people!");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one person!");

        var entity = parseObject(person, Person.class);

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one person!");

        Person entity = repository
                .findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        Person entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }

}