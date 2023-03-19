package com.nuracell.bs.controller;

import com.nuracell.bs.dto.PersonDTO;
import com.nuracell.bs.entity.Person;
import com.nuracell.bs.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAll() {
        return ResponseEntity.ok(
                personService.findAll()
                        .stream()
                        .map(this::convertToPersonDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("{personId}")
    public ResponseEntity<PersonDTO> getOne(@PathVariable("personId") Long id) {
        return ResponseEntity.ok(convertToPersonDTO(personService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> addNewPerson(@RequestBody PersonDTO personDTO) {

        Person p = personService.saveNewPerson(convertToPerson(personDTO));
        return ResponseEntity.ok(convertToPersonDTO(p));
    }

    @PutMapping("{personId}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable("personId") Long id,
                                                  @RequestBody PersonDTO personDTO) {
        Person p = personService.updatePerson(id, convertToPerson(personDTO));
        return ResponseEntity.ok(convertToPersonDTO(p));
    }

    @DeleteMapping("{personId}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("personId") Long id) {

        personService.deletePersonById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
