package com.countryservice.demo.services;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.AddResponse;
import com.countryservice.demo.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
@Service
public class CountryService {
    @Autowired
    CountryRepository countryRepository;
    public List getAllCountries()
    {
       return countryRepository.findAll();

    }
    public Country getCountrybyId(int id)
    {
       return countryRepository.findById(id).get();
    }
    public Country getCountrybyName(String countryName)
    {
        List<Country> countries =countryRepository.findAll();
        Country country=null;
        for (Country con:countries) {
            if(con.getCountryName().equalsIgnoreCase(countryName))
                country=con;
        }
        return country;
    }
    public Country addCountry(Country country)
    {
        country.setId(getMaxId());
        countryRepository.save(country);
        return country;
    }
    //utility method to get max id
    public  int getMaxId()
    {
         return countryRepository.findAll().size()+1;
    }
    public Country updateCountry(Country country)
    {
     countryRepository.save(country);
        return country;
    }
    public AddResponse deleteCountry(int id)
    {
        countryRepository.deleteById(id);
        countryRepository.deleteById(id);
        AddResponse res=new AddResponse();
        res.setMsg("Country Deleted !");
        res.setId(id);
        return  res;
    }
}
