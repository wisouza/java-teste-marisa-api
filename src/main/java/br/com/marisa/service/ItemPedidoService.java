package br.com.marisa.service;

import br.com.marisa.model.ItemPedidoVO;
import br.com.marisa.exception.MarisaPedidoException;
import javassist.tools.rmi.ObjectNotFoundException;


public interface ItemPedidoService {
	void salvar(ItemPedidoVO itemPedidoVO);

	void deletar(ItemPedidoVO item);

	void deletar(Long numeroPedido) throws MarisaPedidoException, ObjectNotFoundException;
}
