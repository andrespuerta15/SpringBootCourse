package com.bolsadeideas.springboot.jpa.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.jpa.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.jpa.app.models.dao.IClienteDaoCrudRepository;
import com.bolsadeideas.springboot.jpa.app.models.dao.IClienteDaoPagingSortRepository;
import com.bolsadeideas.springboot.jpa.app.models.entity.Cliente;

@Service
public class ClienteService implements IClienteServices {

	@Autowired
	IClienteDao clienteDao;

	@Autowired
	private IClienteDaoCrudRepository clienteDaoCrudRepository;

	@Autowired
	private IClienteDaoPagingSortRepository clienteDaoPagingSortRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarTodos() {
		return (List<Cliente>) clienteDaoCrudRepository.findAll();
		// return clienteDao.buscarTodos();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> buscarTodos(int pPage, int pSize) {
		
		Pageable pageable = PageRequest.of(pPage, pSize);
		
		return (Page<Cliente>) clienteDaoPagingSortRepository.findAll(pageable);
		// return clienteDao.buscarTodos();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long pId) {
		return clienteDaoCrudRepository.findById(pId).orElse(null);
		// return clienteDao.buscarPorId(pId);
	}

	@Override
	@Transactional
	public void crear(Cliente cliente) {

		clienteDaoCrudRepository.save(cliente);
		/*
		 * if (cliente.getId() != null && cliente.getId() > 0) {
		 * clienteDao.actualizar(cliente); } else { clienteDao.crear(cliente); }
		 */
	}

	@Override
	@Transactional
	public void eliminar(Long pId) {
		// clienteDaoCrudRepository.deleteById(pId);
		if (pId != null && pId > 0) {
			Cliente cliente = buscarPorId(pId);
			if (cliente != null) {
				clienteDaoCrudRepository.delete(cliente);
				// clienteDao.eliminar(cliente);
			}
		}
	}

}
