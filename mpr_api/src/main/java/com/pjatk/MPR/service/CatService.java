package com.pjatk.MPR.service;

import com.pjatk.MPR.Cat;
import com.pjatk.MPR.exeption.CatAlreadyExistsExeption;
import com.pjatk.MPR.exeption.CatNotFoundExeption;
import com.pjatk.MPR.repository.CatRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;



@Service
public class CatService {

    CatRepository repository;
    public CatService(CatRepository repository){
        this.repository = repository;
        this.repository.save(new Cat("bubi",1));
        this.repository.save(new Cat("niunia",9));
        this.repository.save(new Cat("lala",8));
    }

    public Cat findByName(String name){
        if(this.repository.findByName(name) ==null){
            throw new CatNotFoundExeption();
        }
        return this.repository.findByName(name);
    }




    public ArrayList<Cat> findAll(){
        if(((ArrayList<Cat>) this.repository.findAll()).isEmpty()){
            throw new CatNotFoundExeption();
        }
        return (ArrayList<Cat>) this.repository.findAll();

    }


    public Cat addCat( Cat cat){
            if(repository.findByName(cat.getName())!=null ){
                throw new CatAlreadyExistsExeption();
            }
        return this.repository.save(cat);
    }

    public void deleteCat(String name){
        Cat cat = this.repository.findByName(name);
        if(cat == null){
            throw new CatNotFoundExeption();
        }
        this.repository.delete(cat);
    }

    public void updateCat(String name,Cat updateCat){
        Cat existingCat = this.repository.findByName(name);
        if(existingCat==null){
            throw new CatNotFoundExeption();
        }
        else{

            existingCat.setAge(updateCat.getAge());
            this.repository.save(existingCat);
        }
    }

    public ArrayList<Cat> filterByName(String string){
        ArrayList<Cat> cats= findAll();
        ArrayList<Cat> catmatch = new ArrayList<>();
        for (Cat  cat : cats ){
            if(cat.getName().contains(string)){
                catmatch.add(cat);
            }
        }
        return catmatch;
    }




}
