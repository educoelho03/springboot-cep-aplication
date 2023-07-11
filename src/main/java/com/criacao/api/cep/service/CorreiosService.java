package com.criacao.api.cep.service;

import com.criacao.api.cep.ViacepApplication;
import com.criacao.api.cep.exception.NoContentException;
import com.criacao.api.cep.exception.NotReadyException;
import com.criacao.api.cep.model.Address;
import com.criacao.api.cep.model.AddressStatus;
import com.criacao.api.cep.model.Status;
import com.criacao.api.cep.repository.AddressRepository;
import com.criacao.api.cep.repository.AddressStatusRepository;
import com.criacao.api.cep.repository.SetupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CorreiosService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressStatusRepository statusRepository;

    @Autowired
    private SetupRepository setupRepository;



    public Status getStatus(){
        return this.statusRepository.findById(AddressStatus.DEFAULT_ID)
                .orElse(AddressStatus.builder()
                .status(Status.NEED_SETUP).build())
                .getStatus();
    }


    public Address getAddressByZipCode(String zipcode) throws NoContentException, NotReadyException {

        if(!this.getStatus().equals(Status.READY))
            throw new NotReadyException();


        return addressRepository.findById(zipcode).orElseThrow(NoContentException:: new);

    }

    private void saveServiceStatus(Status status) {
        this.statusRepository.save(AddressStatus.builder()
                .id(AddressStatus.DEFAULT_ID)
                .status(status)
                .build());
    }

    @EventListener(ApplicationStartedEvent.class)
    protected void setupOnStartup(){
         try {
             this.setup();
         }catch (Exception e){
             ViacepApplication.close(999);
         }
    }

    public void setup() throws Exception {
        if(this.getStatus().equals(Status.NEED_SETUP)){
            this.saveServiceStatus(Status.SETUP_RUNNING);
            try {
                this.addressRepository.saveAll(setupRepository.listAdressesFromOrigin());
            } catch (Exception e){
                this.saveServiceStatus(Status.NEED_SETUP);
                throw e;
            }

            this.saveServiceStatus(Status.READY);
        }
    }
}
