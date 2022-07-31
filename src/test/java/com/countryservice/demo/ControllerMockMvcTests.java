package com.countryservice.demo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.assertj.core.api.BDDAssertions.and;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Array.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.restservices.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {ControllerMockMvcTests.class})
public class ControllerMockMvcTests {
    @Autowired
    MockMvc mockMvc;
    @Mock
    CountryService countryService;
    @InjectMocks
    CountryController countryController;
    List<Country> myCountries;
    Country country;
    @BeforeEach
    public void setUp()
    {
        mockMvc= MockMvcBuilders.standaloneSetup(countryController).build();
    }
    @Test
    @Order(1)
    public void test_getAllCountries() throws Exception
    {
        myCountries=new ArrayList<Country>();
        myCountries.add(new Country(1,"India","Delhi"));
        myCountries.add(new Country(2,"USA","Washington"));

        when(countryService.getAllCountries()).thenReturn(myCountries);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/get")).andExpect(status().isFound())
                .andDo(print());
    }
    @Test
    @Order(2)
    public void test_getCountrybyID() throws Exception
    {
        country=new Country(2,"USA","Washington");
        int countryID=2;

        when(countryService.getCountrybyId(countryID)).thenReturn(country);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/get/{id}",countryID))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(print());

    }
    @Test
    @Order(3)
    public void test_getCountryByName() throws Exception {
        country = new Country(2, "USA", "Washington");
        String countryName = "USA";

        when(countryService.getCountrybyName(countryName)).thenReturn(country);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/get/country").param("name","USA")).andExpect(status()
                .isFound()).andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(print());
    }

    @Test
    @Order(4)
    public void test_addCountry() throws Exception
    {
        country=new Country(3,"Gerrmany","Berlin");
        when(countryService.addCountry(country)).thenReturn(country);
        ObjectMapper mapper=new ObjectMapper();
        String jsonbody=mapper.writeValueAsString(country);
        this.mockMvc.perform(post("/addcountry").content(jsonbody).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(print());
    }
    @Test
    @Order(5)
    public void test_updateCountry() throws Exception
    {
        country=new Country(3,"Japan","Tokyo");
        int countryID=3;
        when(countryService.getCountrybyId(countryID)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ObjectMapper mapper=new ObjectMapper();
        String jsonbody=mapper.writeValueAsString(country);

        this.mockMvc.perform(put("/updatecountry/{id}",countryID)
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Tokyo"))
                .andDo(print());
    }
    @Test
    @Order(6)
    public void test_deleteCountry() throws Exception
    {
        country=new Country(3,"Japan","Tokyo");
        int countryID=3;

        when(countryService.getCountrybyId(countryID)).thenReturn(country);
        this.mockMvc.perform(delete("/deleteCountry/{id}",countryID)).andExpect(status().isOk());
    }




}
