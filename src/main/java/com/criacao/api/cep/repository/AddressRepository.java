package com.criacao.api.cep.repository;

import com.criacao.api.cep.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, String> {
}
