package com.nuracell.bs.service;

import com.nuracell.bs.entity.Person;
import com.nuracell.bs.exception.PersonNotFoundException;
import com.nuracell.bs.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    public Person saveNewPerson(Person person) {
        enrichPerson(person);
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person newPerson) {
        return personRepository.findById(id).map(
                person -> {
                    person.setName(newPerson.getName());
                    return personRepository.save(person);
                }
        ).orElseThrow(() -> new PersonNotFoundException(id));
    }

    public void deletePersonById(Long id) {
        personRepository.delete(
                personRepository.findById(id)
                        .orElseThrow(() -> new PersonNotFoundException(id))
        );
    }

    //////////////////////////////////////////////////////////////////////////

    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
    }
}
