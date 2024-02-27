package com.pjatk.MPR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pjatk.MPR.exeption.CatAlreadyExistsExeption;
import org.springframework.http.MediaType;
import com.pjatk.MPR.controller.MyController;
import com.pjatk.MPR.exeption.CatExeptionHolder;
import com.pjatk.MPR.exeption.CatNotFoundExeption;
import com.pjatk.MPR.service.CatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MyControllerTest {
    private MockMvc mockMvc;
    @Mock
    private CatService service;
    @InjectMocks
    private MyController controller;
    private AutoCloseable openMocks;



    @BeforeEach
    public void init(){
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new MyController(service);
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
    public void getCatByNameWhenExists() throws Exception{

        Cat cat = new Cat("Lara",1);
        Mockito.when(service.findByName("Lara")).thenReturn(cat);
        mockMvc.perform(get("/cat/Lara"))
                .andExpect(jsonPath("$.age").value("1"))
                .andExpect(jsonPath("$.name").value("Lara"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCatByNameWhenNoExists() throws Exception{
        Mockito.when(service.findByName("Lara")).thenThrow(new CatNotFoundExeption());
        mockMvc.perform(get("/cat/Lara"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void postCatWhenNotExists() throws Exception{
        Cat cat = new Cat("Lara",1);
        when(service.addCat(any())).thenReturn(cat);
        mockMvc.perform(post("/cat/add")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lara\",\"age\":\"1\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postCatWhenExists() throws Exception{
        doThrow(new CatAlreadyExistsExeption()).when(service).addCat(any());
        mockMvc.perform(post("/cat/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lara\",\"age\":\"1\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllCatsWhenExists() throws Exception {
        var catList = new ArrayList<Cat>();
        catList.add(new Cat("A",1));
        catList.add(new Cat("AA",1));
        catList.add(new Cat("AAA",1));
        when(service.findAll()).thenReturn(catList);

        mockMvc.perform(get("/cat/findall"))
                .andExpect(jsonPath("$.[0].name").value("A"))
                .andExpect(jsonPath("$.[0].age").value("1"))
                .andExpect(jsonPath("$.[1].name").value("AA"))
                .andExpect(jsonPath("$.[1].age").value("1"))
                .andExpect(jsonPath("$.[2].name").value("AAA"))
                .andExpect(jsonPath("$.[2].age").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCatsWhenNotExists() throws Exception {
        List<Cat> cats = new ArrayList<>();
        when(service.findAll()).thenReturn((ArrayList<Cat>) cats);
        mockMvc.perform(get("/cat/findall"))
                .andExpect(jsonPath("$").isEmpty())
                .andExpect(status().isOk());
    }


    @Test
    public void deleteCatWhenExists() throws Exception {
        Cat cat =new Cat("Bibi", 1);
        doNothing().when(service).deleteCat(cat.getName());
        mockMvc.perform(delete("/cat/delete/Bibi"))
                .andExpect(status().isOk());

}


    @Test
    public void deleteCatWhenNotExists() throws Exception {
        Cat cat =new Cat("Bibi", 1);
        doThrow(new CatNotFoundExeption()).when(service).deleteCat(cat.getName());
        mockMvc.perform(delete("/cat/delete/Bibi"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCatWhenExists() throws Exception {
        Cat cat = new Cat("Lara",1);
        doNothing().when(service).updateCat(eq(cat.getName()),any());
        mockMvc.perform(put("/cat/update/Lara")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lara\",\"age\":\"1\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void updateCatWhenNotExists() throws Exception {
        Cat cat = new Cat("Marcel",1);
        doThrow(new CatNotFoundExeption()).when(service).updateCat(eq(cat.getName()), any());
        mockMvc.perform(put("/cat/update/Marcel" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Marcel\",\"age\":\"1\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



}


