package com.pjatk.MPR;

import com.pjatk.MPR.controller.WebController;

import com.pjatk.MPR.exeption.CatExeptionHolder;
import com.pjatk.MPR.service.CatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyWebControllerTest {
    private MockMvc mockMvc;
    @Mock
    private Model model;
    @Mock
    private CatService service;
    @InjectMocks
    private WebController controller;
    private AutoCloseable openMocks;



    @BeforeEach
    public void init(){
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new WebController(service);
    }


    @AfterEach
    public void tearDown() throws Exception{
        openMocks.close();
    }

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new CatExeptionHolder(),controller).build();
    }

    @Test
    public void viewAllTest(){
        //given
        Cat cat = new Cat("Milek", 2);
        Cat cat2 = new Cat("Bartus", 3);
        List<Cat> catsInDb = new ArrayList<>();
        catsInDb.add(cat);
        catsInDb.add(cat2);
        //when
        when(service.findAll()).thenReturn(catsInDb);
        String viewName = controller.viewAll(model);
        //then
        verify(service).findAll();
        assertEquals("index", viewName);
        verify(model).addAttribute("cats", catsInDb);
    }
    @Test

    public void addCatTest(){
        //given
        Cat cat = new Cat("Milek", 2);
        //when
        doNothing().when(service).addCat(cat);
        String viewName = controller.add(cat);
        //then
        verify(service).addCat(cat);
        assertEquals("redirect:/index", viewName);
    }

    @Test
    public void deleteCatTest(){
        //given
        Cat cat = new Cat("Milek", 2);
        //when
        doNothing().when(service).deleteCat("Milek");
        String viewName = controller.delete("Milek");
        //then
        verify(service).deleteCat("Milek");
        assertEquals("redirect:/index", viewName);
    }

    @Test
    public void updateCatTest(){
        //given
        Cat cat = new Cat("Milek", 2);
        //when
        doNothing().when(service).updatedCat(cat);
        String viewName = controller.update(cat);
        //then
        verify(service).updatedCat(cat);
        assertEquals("redirect:/index", viewName);
    }


}





