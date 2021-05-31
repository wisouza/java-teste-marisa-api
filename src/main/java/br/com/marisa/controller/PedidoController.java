package br.com.marisa.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.marisa.model.PedidoVO;
import br.com.marisa.model.dto.out.PedidoDTO;
import br.com.marisa.model.dto.out.RespostaDTO;
import br.com.marisa.model.form.PedidoForm;
import br.com.marisa.service.ItemPedidoService;
import br.com.marisa.service.PedidoService;
import br.com.marisa.service.exception.MarisaPedidoException;
import br.com.marisa.util.MensagemConstante;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ItemPedidoService itemPedidoService;

	@GetMapping("/listar")
	public ResponseEntity<?> listarTodos() {
		List<PedidoVO> listPedidoVO = pedidoService.carregarTodos();

		List<PedidoDTO> listaPedidoDTO = new ArrayList<>();

		listPedidoVO.forEach(pedido -> {
			listaPedidoDTO.add(pedidoService.converterPedidoToDTO(pedido));
		});

		return ResponseEntity.ok(listaPedidoDTO);
	}

	@GetMapping("/detalhar-pedido/{numeroPedido}")
	public ResponseEntity<PedidoDTO> buscarPorPedido(@PathVariable Long numeroPedido) throws MarisaPedidoException, ObjectNotFoundException {
		Optional<PedidoVO> pedidoVO = pedidoService.buscarPorPedido(numeroPedido);

		return ResponseEntity.ok(pedidoService.converterPedidoToDTO(pedidoVO.get()));
	}

	@PostMapping("/cadastrar")
	@Transactional(rollbackOn = { Exception.class })
	public ResponseEntity<?> cadastrar(@RequestBody @Valid PedidoForm form, UriComponentsBuilder uriBuilder) throws ObjectNotFoundException, MarisaPedidoException {
		PedidoVO pedidoVO = pedidoService.converterFormToVO(form);
		pedidoService.salvar(pedidoVO);

		URI uri = uriBuilder.path("/detalhar-pedido/{numeroPedido}").buildAndExpand(pedidoVO.getId()).toUri();
		return ResponseEntity.created(uri).body(pedidoService.converterPedidoToDTO(pedidoService.buscarPorPedido(pedidoVO.getId()).get()));
	}

	@DeleteMapping("/limpar-pedido/{numeroPedido}")
	@Transactional
	public ResponseEntity<?> limparPedido(@PathVariable @NotNull Long numeroPedido) throws MarisaPedidoException, ObjectNotFoundException {
		itemPedidoService.deletar(numeroPedido);
		return ResponseEntity.status(HttpStatus.OK) .body(new RespostaDTO(MensagemConstante.SUCESSO, MensagemConstante.MSG_PEDIDO_LIMPO_SUCESSO));
	}

	@PutMapping("/finalizar-pedido/{numeroPedido}")
	@Transactional
	public ResponseEntity<?> finalizarPedido(@PathVariable Long numeroPedido) throws MarisaPedidoException, ObjectNotFoundException {
		Optional<PedidoVO> pedidoVO = pedidoService.buscarPorPedido(numeroPedido);

		pedidoService.finalizar(pedidoVO.get());
		return ResponseEntity.status(HttpStatus.OK).body(new RespostaDTO(MensagemConstante.SUCESSO, MensagemConstante.MSG_PEDIDO_FINALIZADO_SUCESSO));
	}

	@PutMapping("/alterar-pedido/{numeroPedido}")
	@Transactional(rollbackOn = { Exception.class })
	public ResponseEntity<?> alterarPedido(@PathVariable @NotNull Long numeroPedido, @RequestBody @Valid PedidoForm form) throws MarisaPedidoException, ObjectNotFoundException {
		pedidoService.atualizar(numeroPedido, form);

		return ResponseEntity.status(HttpStatus.OK).body(new RespostaDTO(MensagemConstante.SUCESSO, MensagemConstante.MSG_PEDIDO_ALTERADO_SUCESSO));
	}
}
