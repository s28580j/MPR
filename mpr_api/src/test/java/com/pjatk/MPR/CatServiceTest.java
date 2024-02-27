package com.pjatk.MPR;

import com.pjatk.MPR.exeption.CatAlreadyExistsExeption;
import com.pjatk.MPR.exeption.CatNotFoundExeption;
import com.pjatk.MPR.repository.CatRepository;
import com.pjatk.MPR.service.CatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class CatServiceTest {

    @Mock
    private CatRepository repository;
    private AutoCloseable openMocks;
    @InjectMocks
    private CatService catService;


    @BeforeEach
    public void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        catService = new CatService(repository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    //get
    @Test

    public void findCatByNameWhenExists() {
        String name = "Stefek";
        Cat cat = new Cat(name, 1);
        Mockito.when(repository.findByName(name)).thenReturn(cat);
        Cat result = catService.findByName(name);
        assertEquals(cat, result);
    }

    @Test

    public void findCatByNameWhenNotExists() {
        String name = "Lila";
        when(repository.findByName(name)).thenReturn(null);
        assertThrows(CatNotFoundExeption.class, () -> {
            catService.filterByName(name);
        });

    }


    //allget
    @Test

    public void findAllCatsWhenFound() {
        List<Cat> cat = new ArrayList<>();
        cat.add(new Cat("A", 1));
        cat.add(new Cat("AA", 1));
        cat.add(new Cat("AAA", 1));
        when(repository.findAll()).thenReturn(cat);
        List<Cat> result = catService.findAll();
        assertEquals(cat, result);
    }

    @Test
    public void findAllCatsWhenNotFound() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(CatNotFoundExeption.class, () -> {
            catService.findAll();
        });
    }

    //post
    @Test
    public void addCatWhenNotExisit() {
        Cat newCat = new Cat("Simba", 3);
        when(repository.findByName("Simba")).thenReturn(null);
        catService.addCat(newCat);
        Mockito.verify(repository).save(newCat);
    }


    @Test
    public void addCatWhenExists() {
        Cat existingCat = new Cat("Milo", 2);
        when(repository.findByName("Milo")).thenReturn(existingCat);
        assertThrows(CatAlreadyExistsExeption.class, () -> {
            catService.addCat(existingCat);
        });

    }

    @Test
    public void deteteCatWhenExists() {
        Cat catToDelete = new Cat("Koko", 1);
        when(repository.findByName("Koko")).thenReturn(catToDelete);
        catService.deleteCat("Koko");
        Mockito.verify(repository).delete(catToDelete);
    }

    @Test
    public void deleteCatWhenNotExists() {
        String nonExistingCatName = "NonExistingCat";
        when(repository.findByName(nonExistingCatName)).thenReturn(null);
        assertThrows(CatNotFoundExeption.class, () -> {
            catService.deleteCat(nonExistingCatName);
        });


    }


    @Test
    public void updateCatWhenExists() {
        String name = "Whiskers";
        Cat existingCat = new Cat(name, 5);
        Cat updatedCat = new Cat(name, 6);


        when(repository.findByName(name)).thenReturn(existingCat);
        ArgumentCaptor<Cat> captor = ArgumentCaptor.forClass(Cat.class);
        when(repository.save(captor.capture())).thenReturn(updatedCat);


        catService.updateCat(name, updatedCat);


        Mockito.verify(repository).save(existingCat);
        Cat savedCat = captor.getValue();
        assertEquals(6, savedCat.getAge());

    }

    @Test
    public void updateCatWhenNoExists() {
        String name = "Unknown";
        Cat updatedCat = new Cat(name, 4);

        when(repository.findByName(name)).thenReturn(null);

        assertThrows(CatNotFoundExeption.class, () -> {
            catService.updateCat(name, updatedCat);
        });

    }


//pomimo ze daje bubi

    @Test
    public void filterByNameTest() {
        String name = "bubi";
        Cat cat = new Cat(name, 9);
        ArrayList<Cat> cats = new ArrayList<>();
        cats.add(new Cat("B", 8));
        cats.add(new Cat("BB", 9));
        cats.add(new Cat("BBB", 8));


    }


}

