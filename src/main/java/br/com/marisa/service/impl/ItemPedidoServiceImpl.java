package br.com.marisa.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marisa.model.ItemPedidoVO;
import br.com.marisa.model.PedidoVO;
import br.com.marisa.model.ProdutoVO;
import br.com.marisa.repository.ItemPedidoRepository;
import br.com.marisa.service.ItemPedidoService;
import br.com.marisa.service.PedidoService;
import br.com.marisa.service.ProdutoService;
import br.com.marisa.service.exception.MarisaPedidoException;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService{

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Override
	public void salvar(ItemPedidoVO itemPedidoVO) {
		itemPedidoRepository.save(itemPedidoVO);
		produtoService.atualizar(itemPedidoVO.getPk().getProdutoVO());
	}

	@Override
	public void deletar(ItemPedidoVO itemPedidoVO) {
		ProdutoVO produtoVO = itemPedidoVO.getPk().getProdutoVO();
		produtoVO.setEstoque(produtoVO.getEstoque() + itemPedidoVO.getQuantidade());
		produtoService.atualizar(produtoVO);
		
		itemPedidoRepository.delete(itemPedidoVO);
	}

	@Override
	public void deletar(Long numeroPedido) throws MarisaPedidoException, ObjectNotFoundException {
		Optional<PedidoVO> pedidoVO = pedidoService.buscarPorPedido(numeroPedido);
		pedidoVO.get().getListItemPedido().forEach(item -> deletar(item));
	}
}
