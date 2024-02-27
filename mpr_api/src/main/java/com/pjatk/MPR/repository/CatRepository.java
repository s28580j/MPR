package com.pjatk.MPR.repository;

import com.pjatk.MPR.Cat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends CrudRepository<Cat, Long> {
    public Cat findByName(String name);
}
