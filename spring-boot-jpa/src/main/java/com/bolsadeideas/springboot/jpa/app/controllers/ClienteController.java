package com.bolsadeideas.springboot.jpa.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.jpa.app.models.entity.Cliente;
import com.bolsadeideas.springboot.jpa.app.models.services.IClienteServices;
import com.bolsadeideas.springboot.jpa.app.models.services.IUploadFileService;
import com.bolsadeideas.springboot.jpa.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteServices clienteService;

	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping(value = "/uploads/{fileName:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String fileName) {

		Resource recursoFoto = null;
		try {
			recursoFoto = uploadFileService.loadFile(fileName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recursoFoto.getFilename() + "\"")
				.body(recursoFoto);

	}

	@GetMapping(value = "/clientes/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = clienteService.buscarPorId(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/clientes/listar";
		}

		model.put("cliente", cliente);
		model.put("titulo", "Detalle del cliente " + cliente.getNombre().toUpperCase());

		return "clientes/ver";
	}

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
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus sessionStatus) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario para crear clientes");
			return "clientes/crear";
		}

		if (!foto.isEmpty()) {

			if ((cliente.getId() != null && cliente.getId() > 0l)
					&& (cliente.getFoto() != null && !cliente.getFoto().isEmpty())) {
				uploadFileService.deleteFile(cliente.getFoto());
			}

			try {
				String uniqueFileName = uploadFileService.copyFile(foto);
				flash.addFlashAttribute("info", "Has subido correctamente el archivo: '" + uniqueFileName + "'");
				cliente.setFoto(uniqueFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

			Cliente cliente = clienteService.buscarPorId(pId);

			if (cliente == null) {
				flash.addFlashAttribute("error", "No se encontro el cliente con id: " + pId + " para eliminar");
			} else {
				clienteService.eliminar(pId);
				flash.addFlashAttribute("success", "Cliente eliminado exitosamente!");

				if (uploadFileService.deleteFile(cliente.getFoto())) {
					flash.addFlashAttribute("info", "La imagen " + cliente.getFoto() + " ha sido eliminada con exito!");
				}
			}
		} else {
			flash.addFlashAttribute("error", "El Id del cliente a eliminar no puede ser cero!");
		}

		return "redirect:/clientes/listar";
	}

}
