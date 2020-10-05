package com.bolsadeideas.springboot.jpa.app.models.dao;

import java.util.List;

import com.bolsadeideas.springboot.jpa.app.models.entity.Cliente;

public interface IClienteDao {

	List<Cliente> buscarTodos();

	Cliente buscarPorId(Long pId);

	void crear(Cliente cliente);

	void actualizar(Cliente cliente);
	
	void eliminar(Cliente cliente);
}
