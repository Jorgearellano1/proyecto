package com.example.demo.groups;

import com.example.demo.persons.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    @Autowired
    private GroupsRepository groupRepository;

    @GetMapping
    public List<Groups> getAllGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Groups> getGroupById(@PathVariable Long id) {
        return groupRepository.findById(id)
                .map(group -> ResponseEntity.ok().body(group))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{groupId}/persons")
    public ResponseEntity<Set<Persons>> getPersonsInGroupById(@PathVariable Long groupId) {
        return groupRepository.findById(groupId)
                .map(group -> ResponseEntity.ok().body(group.getPersons()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Groups createGroup(@RequestBody Groups group) {
        return groupRepository.save(group);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Groups> updateGroup(@PathVariable Long id, @RequestBody Groups groupDetails) {
        return groupRepository.findById(id).map(group -> {
            group.setName(groupDetails.getName());
            Groups updatedGroup = groupRepository.save(group);
            return ResponseEntity.ok().body(updatedGroup);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        return groupRepository.findById(id).map(group -> {
            groupRepository.delete(group);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}