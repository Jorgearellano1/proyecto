package com.example.demo.persons;

import com.example.demo.groups.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/persons")
public class PersonsController {

    @Autowired
    private PersonsRepository personsRepository;

    @GetMapping
    public List<Persons> getAllPersons() {
        return personsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persons> getPersonById(@PathVariable Long id) {
        return personsRepository.findById(id)
                .map(person -> ResponseEntity.ok().body(person))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{personId}/groups/x")
    public ResponseEntity<Set<Groups>> getGroupsOfPersonById(@PathVariable Long personId) {
        return personsRepository.findById(personId)
                .map(person -> ResponseEntity.ok().body(person.getGroups()))
                .orElse(ResponseEntity.notFound().build());
    }

}