package com.criacao.api.cep.repository;

import com.criacao.api.cep.model.Address;
import com.criacao.api.cep.model.AddressStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressStatusRepository extends CrudRepository<Address, String> {
    Optional<AddressStatus> findById(int defaultId);
}
