package com.bolsadeideas.springboot.jpa.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.jpa.app.models.entity.Cliente;

public interface IClienteDaoPagingSortRepository extends PagingAndSortingRepository<Cliente, Long> {

}
