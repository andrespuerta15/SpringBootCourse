package com.bolsadeideas.springboot.jpa.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bolsadeideas.springboot.jpa.app.models.entity.Cliente;

public interface IClienteServices {

	List<Cliente> buscarTodos();

	Page<Cliente> buscarTodos(int pPage, int pSize);

	Cliente buscarPorId(Long pId);

	void crear(Cliente cliente);

	void eliminar(Long pId);
}
