package com.bolsadeideas.springboot.jpa.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.jpa.app.models.entity.Cliente;

public interface IClienteDaoCrudRepository extends CrudRepository<Cliente, Long> {

}
