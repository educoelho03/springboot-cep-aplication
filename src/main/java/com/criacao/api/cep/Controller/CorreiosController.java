package com.criacao.api.cep.Controller;

import com.criacao.api.cep.exception.NoContentException;
import com.criacao.api.cep.exception.NotReadyException;
import com.criacao.api.cep.model.Address;
import com.criacao.api.cep.service.CorreiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Controller - onde recebemos as requisições.

@RestController
public class CorreiosController {

    @Autowired
    private CorreiosService service; // Injeção de dependencia


    @GetMapping("/status")
    public String getStatus(){
        return "Service Status: " + this.service.getStatus();
    }

    @GetMapping("/zipcode/{zipcode}")
    public Address getAddressByZipCode(@PathVariable("zipcode") String zipcode) throws NoContentException, NotReadyException {
        return this.service.getAddressByZipCode(zipcode);


    }
}
