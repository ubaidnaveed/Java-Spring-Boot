package com.ubilicious.journalApp.controller;

import com.ubilicious.journalApp.entity.JournalEntry;
import com.ubilicious.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

  @Autowired
  private JournalEntryService journalEntryService;

  @GetMapping
  public ResponseEntity<?> getAll(){

    List<JournalEntry> all = journalEntryService.getAll();
    if (all!=null && !all.isEmpty()){
      return new ResponseEntity<>(all,HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping
  public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
   try {
     myEntry.setDate(LocalDateTime.now());
     journalEntryService.saveEntry(myEntry);
     return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
   }catch (Exception e){
     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }

  }

  @GetMapping("id/{myId}")
  public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId){
    Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
    if (journalEntry.isPresent()){
      return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("id/{myId}")
  public ResponseEntity<?> deleteById(@PathVariable ObjectId myId){
     journalEntryService.deleteById(myId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("id/{myId}")
  public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
    JournalEntry old = journalEntryService.findById(myId).orElse(null);
    if (old!=null){
      old.setTittle(newEntry.getTittle()!=null && !newEntry.getTittle().equals("") ? newEntry.getTittle() : old.getTittle());
      old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
      journalEntryService.saveEntry(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
