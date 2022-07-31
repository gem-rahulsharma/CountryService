package com.countryservice.demo.controllers;
import com.countryservice.demo.beans.Country;
import com.countryservice.demo.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class CountryController {
    @Autowired
    CountryService countryService;

    @GetMapping("/get")
    public ResponseEntity<List<Country>> getCountries()
    {
        try {
            return new ResponseEntity<List<Country>>(countryService.getAllCountries(),HttpStatus.FOUND);
        }catch(Exception e)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Country> getCountryByID(@PathVariable(value = "id" )int id)
    {
        try {
            Country country= countryService.getCountrybyId(id);
            return  new ResponseEntity<Country> (country,HttpStatus.FOUND);
        }catch(Exception e)
        {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get/country")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value="name" ,required = true) String countryName)
    {
        try {
            Country country= countryService.getCountrybyName(countryName);
            return  new ResponseEntity<Country>(country,HttpStatus.FOUND);
        }catch(NoSuchElementException e)
        {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addcountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country)
    {
        try {
            country=countryService.addCountry(country);
            return new ResponseEntity<Country>(country,HttpStatus.CREATED);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }
    @PutMapping("/updatecountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id")
                                                     int id, @RequestBody Country country)
    {
        try{
             Country existCountry=countryService.getCountrybyId(id);
             existCountry.setCountryName(country.getCountryName());
             existCountry.setCountryCapital(country.getCountryCapital());
              Country updated_country=countryService.updateCountry(existCountry);
        return new ResponseEntity<Country>(updated_country,HttpStatus.OK);}
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @DeleteMapping( "/deleteCountry/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable(value = "id") int id)
    {
        Country country=null;
        try {
            country=countryService.getCountrybyId(id);
            countryService.deleteCountry(id);
        }catch(NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Country>(country,HttpStatus.OK);

    }
}
