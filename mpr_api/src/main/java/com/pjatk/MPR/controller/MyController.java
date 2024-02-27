package com.pjatk.MPR.controller;

import com.pjatk.MPR.Cat;
import com.pjatk.MPR.service.CatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class MyController {

    private final CatService service;

    @Autowired
    public MyController(CatService service){
        this.service= service;
    }

    @GetMapping("cat/findall")
    public ArrayList<Cat> findAll(){
        return this.service.findAll();
    }

    @GetMapping("cat/{name}")
    public Cat findByName(@PathVariable("name")String name){
        return this.service.findByName(name);
    }

    @PostMapping("cat/add")
    public Cat addCat(@RequestBody Cat cat){
        return this.service.addCat(cat);
    }

    @DeleteMapping("cat/delete/{name}")
    public void deleteCat(@PathVariable("name")String name){
       this.service.deleteCat(name);
    }

    @PutMapping("cat/update/{name}")
    public void updateCat(@PathVariable("name")String name,@RequestBody Cat updatedCat){
        this.service.updateCat( name, updatedCat);
    }

    @GetMapping("cat/filter/{name}")
    public ArrayList<Cat> filterByName(@PathVariable("name")String name){
        return this.service.filterByName(name);
    }




}
