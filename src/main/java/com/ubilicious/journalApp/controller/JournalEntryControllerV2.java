package com.ubilicious.journalApp.controller;

import com.ubilicious.journalApp.entity.JournalEntry;
import com.ubilicious.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

  @Autowired
  private JournalEntryService journalEntryService;

  @GetMapping
  public List<JournalEntry> getAll(){

   return journalEntryService.getAll();
  }

  @PostMapping
  public boolean createEntry(@RequestBody JournalEntry myEntry){
    myEntry.setDate(LocalDateTime.now());
    journalEntryService.saveEntry(myEntry);
    return true;
  }

  @GetMapping("id/{myId}")
  public JournalEntry getById(@PathVariable ObjectId myId){
    return journalEntryService.findById(myId).orElse(null);

  }

  @DeleteMapping("id/{myId}")
  public boolean deleteById(@PathVariable ObjectId myId){
     journalEntryService.deleteById(myId);
      return true;
  }

  @PutMapping("id/{myId}")
  public JournalEntry updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
    JournalEntry old = journalEntryService.findById(myId).orElse(null);
    if (old!=null){
      old.setTittle(newEntry.getTittle()!=null && !newEntry.getTittle().equals("") ? newEntry.getTittle() : old.getTittle());
      old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
    }
    journalEntryService.saveEntry(old);
    return old;
  }
}
