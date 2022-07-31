package com.countryservice.demo;
import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes ={ServiceMockitoTests.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceMockitoTests {
    @Mock
    CountryRepository countryRepository;
    @InjectMocks
    CountryService countryService;

    public List<Country> mycountries;

    @Test
    @Order(1)
    public void test_getAllCountries()
    {
        List<Country> mycountries=new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","washington"));

        when(countryRepository.findAll()).thenReturn(mycountries); //Mocking
        assertEquals(2,countryService.getAllCountries().size());

    }
    @Test
    @Order(2)
    public void test_getCountryById()
    {
       // List<Country> mycountry=new ArrayList<Country>();
       // mycountry.add(new Country(1,"India","Delhi"));
       // mycountry.add(new Country(2,"USA","washington"));
        //int countryId=1;
        Country country=new Country(1,"India","Delhi");
        when(countryRepository.findById(any())).thenReturn(Optional.of(country));//mocking

        assertEquals(country.getCountryName(),countryService.getCountrybyId(1).getCountryName());
    }
    @Test
    @Order(3)
    public void test_getCountryByName()
    {
        List<Country> mycountries=new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","washington"));
         String countryName="India";
        when(countryRepository.findAll()).thenReturn(mycountries); //Mocking
        assertEquals(countryName,countryService.getCountrybyName(countryName).getCountryName());
    }
    @Test
    @Order(4)
    public void test_addCountry()
    {
        Country country=new Country(3,"Germany","Berlin");
        when(countryRepository.save(country)).thenReturn(country);//mocked
        assertEquals(country,countryService.addCountry(country));
    }
    @Test
    @Order(5)
    public void test_updateCountry()
    {
        Country country=new Country(3,"Germany","Berlin");
        when(countryRepository.save(country)).thenReturn(country);//mocked
        assertEquals(country,countryService.updateCountry(country));
    }
    @Test
    @Order(6)
    public void test_deleteCountry()
    {
        //when and then used only if the method is returning something for us but delete doesn't return anything
       Country country=new Country(3,"Germany","Berlin");
       countryService.deleteCountry(3);
       verify(countryRepository,times(2)).deleteById(3);
       // work as assertion and mock both
    }

}
