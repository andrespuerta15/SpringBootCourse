package com.bolsadeideas.springboot.jpa.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.jpa.app.models.entity.Cliente;
import com.bolsadeideas.springboot.jpa.app.models.services.IClienteServices;
import com.bolsadeideas.springboot.jpa.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteServices clienteService;

	@RequestMapping(value = "/clientes/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Page<Cliente> lClientes = clienteService.buscarTodos(page, 2);
		PageRender<Cliente> paginadorClientes = new PageRender<>("/clientes/listar", lClientes);

		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", lClientes);
		model.addAttribute("page", paginadorClientes);

		return "clientes/listar";
	}

	@RequestMapping(value = "/clientes/crear")
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario para crear clientes");

		return "clientes/crear";
	}

	@RequestMapping(value = "/clientes/crear", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus sessionStatus) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario para crear clientes");
			return "clientes/crear";
		}

		String strMensajeFlash = "Cliente creado exitosamente!";
		if (cliente.getId() != null) {
			strMensajeFlash = "Cliente editado exitosamente!";
		}

		clienteService.crear(cliente);
		sessionStatus.setComplete();
		flash.addFlashAttribute("success", strMensajeFlash);
		return "redirect:listar";
	}

	@RequestMapping(value = "/clientes/{id}")
	public String editar(@PathVariable(value = "id") Long pId, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (pId != null && pId > 0) {
			cliente = clienteService.buscarPorId(pId);
			if (cliente == null) {
				flash.addFlashAttribute("error", "No se encontro el cliente para editar");
				return "redirect:listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id del cliente no puede ser cero!");
			return "redirect:listar";
		}

		model.put("cliente", cliente);
		model.put("titulo", "Formulario para editar clientes");
		flash.addFlashAttribute("success", "Cliente editado exitosamente!");

		return "clientes/crear";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long pId, RedirectAttributes flash) {

		if (pId != null && pId > 0) {
			if (clienteService.buscarPorId(pId) == null) {
				flash.addFlashAttribute("error", "No se encontro el cliente con id: " + pId + " para eliminar");
			} else {
				clienteService.eliminar(pId);
				flash.addFlashAttribute("success", "Cliente eliminado exitosamente!");
			}
		} else {
			flash.addFlashAttribute("error", "El Id del cliente a eliminar no puede ser cero!");
		}

		return "redirect:/clientes/listar";
	}

}
