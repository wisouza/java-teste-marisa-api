package br.com.marisa.service;

import java.util.List;
import java.util.Optional;

import br.com.marisa.model.PedidoVO;
import br.com.marisa.model.dto.out.PedidoDTO;
import br.com.marisa.model.form.PedidoForm;
import br.com.marisa.exception.MarisaPedidoException;
import javassist.tools.rmi.ObjectNotFoundException;


public interface PedidoService {

	List<PedidoVO> carregarTodos();

	Optional<PedidoVO> buscarPorPedido(Long numeroPedido) throws MarisaPedidoException, ObjectNotFoundException;

	PedidoDTO converterPedidoToDTO(PedidoVO pedido);

	PedidoVO converterFormToVO(PedidoForm form) throws ObjectNotFoundException, MarisaPedidoException;

	void salvar(PedidoVO pedidoVO) throws MarisaPedidoException;

	void finalizar(PedidoVO pedidoVO) throws MarisaPedidoException;

	void atualizar(Long numeroPedido, PedidoForm form) throws MarisaPedidoException, ObjectNotFoundException;
}
