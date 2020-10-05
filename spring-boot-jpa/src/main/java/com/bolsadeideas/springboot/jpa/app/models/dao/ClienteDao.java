package com.bolsadeideas.springboot.jpa.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.jpa.app.models.entity.Cliente;

@Repository
public class ClienteDao implements IClienteDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> buscarTodos() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	public Cliente buscarPorId(Long pId) {
		return em.find(Cliente.class, pId);
	}

	@Override
	public void crear(Cliente cliente) {
		em.persist(cliente);
	}

	@Override
	public void actualizar(Cliente cliente) {
		em.merge(cliente);
	}

	@Override
	public void eliminar(Cliente cliente) {
		em.remove(cliente);
	}

}
