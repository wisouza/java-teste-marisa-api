package br.com.marisa.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.marisa.controller.app.AppControllerBase;
import br.com.marisa.model.ItemPedidoPK;
import br.com.marisa.model.ItemPedidoVO;
import br.com.marisa.model.PedidoVO;
import br.com.marisa.model.ProdutoVO;
import br.com.marisa.model.dto.out.ItemPedidoDTO;
import br.com.marisa.model.dto.out.PedidoDTO;
import br.com.marisa.model.form.ItemForm;
import br.com.marisa.model.form.PedidoForm;
import br.com.marisa.repository.PedidoRepository;
import br.com.marisa.service.ItemPedidoService;
import br.com.marisa.service.PedidoService;
import br.com.marisa.service.ProdutoService;
import br.com.marisa.exception.MarisaPedidoException;
import br.com.marisa.util.MensagemConstante;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemPedidoService itemPedidoService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private AppControllerBase appControllerBase;

	private BigDecimal valorTotal; 

	private PedidoDTO pedidoDTO;

	@Override
	public List<PedidoVO> carregarTodos() {
		return pedidoRepository.findAll(Sort.by("id"));
	}

	@Override
	public Optional<PedidoVO> buscarPorPedido(Long numeroPedido) throws ObjectNotFoundException {
		Optional<PedidoVO> pedidoVO = pedidoRepository.findById(numeroPedido);
		if (pedidoVO.isPresent()) {
			return pedidoVO;
		}
		throw new ObjectNotFoundException(MensagemConstante.MSG_PEDIDO_NAO_ENCONTRADO + " Numero pedido  " + numeroPedido);
	}

	@Override
	public void salvar(PedidoVO pedidoVO) throws MarisaPedidoException {
		for (ItemPedidoVO itemPedido : pedidoVO.getListItemPedido()) {
			if (itemPedido.getQuantidade() > itemPedido.getPk().getProdutoVO().getEstoque()) {
				throw new MarisaPedidoException(MensagemConstante.MSG_QUANTIDADE_INDISPONIVEL_ITEM + itemPedido.getPk().getProdutoVO().getId()
						+ " - " + itemPedido.getPk().getProdutoVO().getNome());
			} else if (!itemPedido.getPk().getProdutoVO().isEmEstoque()) {
				throw new MarisaPedidoException(MensagemConstante.MSG_PRODUTO_INDISPONIVEL + " ID Produto: " + itemPedido.getPk().getProdutoVO().getId());
			}
		}
		
		pedidoRepository.save(pedidoVO);

		pedidoVO.getListItemPedido().forEach(item -> {
			item.getPk().getProdutoVO().setEstoque(item.getPk().getProdutoVO().getEstoque() - item.getQuantidade());
			itemPedidoService.salvar(item);
		});
	}

	@Override
	public void finalizar(PedidoVO pedidoVO) throws MarisaPedidoException {
		if (pedidoVO.isFinalizado()) {
			throw new MarisaPedidoException(MensagemConstante.MSG_PEDIDO_JA_FINALIZADO_ANTERIORMENTE);
		} else if (null == pedidoVO.getListItemPedido() || pedidoVO.getListItemPedido().isEmpty()) {
			throw new MarisaPedidoException(MensagemConstante.MSG_NAO_POSSIVEL_FINALIZAR_PEDIDO_SEM_ITEM);
		}
		pedidoVO.setFinalizado(true);
		pedidoRepository.save(pedidoVO);
	}

	@Override
	public void atualizar(Long numeroPedido, PedidoForm pedidoForm) throws MarisaPedidoException, ObjectNotFoundException {
		Optional<PedidoVO> pedidoVO = buscarPorPedido(numeroPedido);
		List<ItemPedidoVO> listaItemPedidoVO = new ArrayList<>();
		if (pedidoVO.isPresent()) {

			if (pedidoVO.get().isFinalizado()) {
				throw new MarisaPedidoException(MensagemConstante.MSG_PEDIDO_NAO_PODE_SER_ALTERADO_JA_FINALIZADO);
			}

			Map<Long, Integer> idProdQtd = adicionarQuantidadeProduto(pedidoForm);
			
			for (Map.Entry<Long, Integer> entry : idProdQtd.entrySet()) {
				ItemPedidoVO itemPedido = null;
				for (ItemPedidoVO item : pedidoVO.get().getListItemPedido()) {

					if (item.getPk().getProdutoVO().getId().equals(entry.getKey())) {
						itemPedido = item;
						break;
					}
				}
				/*
				 * Optional<ItemPedidoVO> itemPedido =
				 * pedidoVO.get().getListItemPedido().stream().findAny() .filter(item ->
				 * item.getPk().getProdutoVO().getId().equals(pedido.getIdProduto())); if
				 * (itemPedido.isPresent()) {
				 */
				if (null != itemPedido) {
					if (entry.getValue() > (itemPedido.getPk().getProdutoVO().getEstoque() + itemPedido.getQuantidade())) {
						throw new MarisaPedidoException(MensagemConstante.MSG_QUANTIDADE_INDISPONIVEL_ITEM + itemPedido.getPk().getProdutoVO().getId()
								+ " - " + itemPedido.getPk().getProdutoVO().getNome());

					} else {
						Integer estoque = itemPedido.getPk().getProdutoVO().getEstoque() + itemPedido.getQuantidade() - entry.getValue();
						itemPedido.getPk().getProdutoVO().setEstoque(estoque);
						itemPedido.setQuantidade(entry.getValue());
						listaItemPedidoVO.add(itemPedido);
					}
				} else {
					ItemPedidoVO novoItemPedido = criarItemPedido(pedidoVO.get(), new ItemForm(entry.getKey(), entry.getValue()));
					if (entry.getValue() > novoItemPedido.getPk().getProdutoVO().getEstoque()) {
					
						throw new MarisaPedidoException(MensagemConstante.MSG_QUANTIDADE_INDISPONIVEL_ITEM + novoItemPedido.getPk().getProdutoVO().getId()
							+ " - " + novoItemPedido.getPk().getProdutoVO().getNome());

				}
					novoItemPedido.getPk().getProdutoVO().setEstoque(novoItemPedido.getPk().getProdutoVO().getEstoque() - novoItemPedido.getQuantidade());
					listaItemPedidoVO.add(novoItemPedido);
				}
			}
			/*
			 * form.getListaItemForm().forEach(pedido -> {
			 * 
			 * Optional<ItemPedidoVO> itemPedido =
			 * pedidoVO.get().getListItemPedido().stream().findAny() .filter(item ->
			 * item.getPk().getProdutoVO().getId().equals(pedido.getIdProduto())); if
			 * (itemPedido.isPresent()) { if (pedido.getQuantidade() >
			 * (itemPedido.get().getPk().getProdutoVO().getEstoque() +
			 * itemPedido.get().getQuantidade())) {
			 * System.out.println("QUANTIDADE MAIOR QUE O ESTOQUE DO ITEM " +
			 * itemPedido.get().getPk().getProdutoVO().getId() + " - " +
			 * itemPedido.get().getPk().getProdutoVO().getNome()); } else {
			 * System.out.println("ESTOQUE ATUAL " +
			 * itemPedido.get().getPk().getProdutoVO().getEstoque()); Integer estoque =
			 * itemPedido.get().getPk().getProdutoVO().getEstoque() +
			 * itemPedido.get().getQuantidade() - pedido.getQuantidade();
			 * itemPedido.get().getPk().getProdutoVO().setEstoque(estoque);
			 * listaItemPedidoVO.add(itemPedido.get()); } } else { ItemPedidoVO
			 * novoItemPedido = criarItemPedido(pedidoVO.get(), pedido);
			 * novoItemPedido.getPk().getProdutoVO().setEstoque(novoItemPedido.getPk().
			 * getProdutoVO().getEstoque() - novoItemPedido.getQuantidade());
			 * listaItemPedidoVO.add(novoItemPedido); } });
			 */
		} else {
			throw new MarisaPedidoException(MensagemConstante.MSG_PEDIDO_NAO_ENCONTRADO + "Numero Pedido: " + numeroPedido);
		}
		listaItemPedidoVO.forEach(item -> itemPedidoService.salvar(item));
	}

	private ItemPedidoVO criarItemPedido(PedidoVO pedidoVO, ItemForm item) throws ObjectNotFoundException, MarisaPedidoException {
		ItemPedidoVO itemPedidoVO = new ItemPedidoVO();
		ItemPedidoPK pk = new ItemPedidoPK();
		Optional<ProdutoVO> produtoVO = produtoService.findById(item.getIdProduto());
		if(!produtoVO.isPresent()) {
			throw new ObjectNotFoundException(MensagemConstante.MSG_PRODUTO_NAO_ENCONTRADO + " ID Produto: " + item.getIdProduto());
		} else if (!produtoVO.get().isEmEstoque()) {
			throw new MarisaPedidoException(MensagemConstante.MSG_PRODUTO_INDISPONIVEL + " ID Produto: " + item.getIdProduto());
		}
		pk.setPedidoVO(pedidoVO);
		pk.setProdutoVO(produtoVO.get());

		itemPedidoVO.setQuantidade(item.getQuantidade());
		itemPedidoVO.setPk(pk);
		return itemPedidoVO;
	}

	@Override
	public PedidoDTO converterPedidoToDTO(PedidoVO pedidoVO) {
		pedidoDTO = new PedidoDTO();
		valorTotal = new BigDecimal(0);
		pedidoDTO = appControllerBase.mapTo(pedidoVO, PedidoDTO.class);
		pedidoDTO.setListItemPedidoDTO(new ArrayList<>());

		pedidoVO.getListItemPedido().forEach(item -> {
			pedidoDTO.getListItemPedidoDTO().add(appControllerBase.mapTo(item, ItemPedidoDTO.class));
			valorTotal = valorTotal.add(item.getPk().getProdutoVO().getPreco().multiply(new BigDecimal(item.getQuantidade())));
		});
		pedidoDTO.setPrecoTotal(valorTotal);
		return pedidoDTO;
	}

	@Override
	public PedidoVO converterFormToVO(PedidoForm pedidoForm) throws ObjectNotFoundException, MarisaPedidoException {
		PedidoVO pedidoVO;

		List<ItemPedidoVO> listItemPedido = new ArrayList<>();
		pedidoVO = new PedidoVO();
		pedidoVO.setDataPedido(new Date());
		pedidoVO.setListItemPedido(listItemPedido);

		Map<Long, Integer> idProdQtd = adicionarQuantidadeProduto(pedidoForm);
		
		for (Map.Entry<Long, Integer> entry : idProdQtd.entrySet()) {
			ItemPedidoVO itemPedidoVO = criarItemPedido(pedidoVO, new ItemForm(entry.getKey(), entry.getValue()));
			listItemPedido.add(itemPedidoVO);
		}

		return pedidoVO;
	}

	/**
	 * @param pedidoForm
	 * @return
	 * caso venha o mesmo produto varias vezes em um pedido, vai somando a qtd do produto
	 */
	private Map<Long, Integer> adicionarQuantidadeProduto(PedidoForm pedidoForm) {
		Map<Long, Integer> idProdQtd = new HashMap<Long, Integer>(); 
		for (ItemForm item : pedidoForm.getListaItemForm()) {
			if (idProdQtd.containsKey(item.getIdProduto())) {
				idProdQtd.put(item.getIdProduto(), idProdQtd.get(item.getIdProduto()) + item.getQuantidade());
			} else {
				idProdQtd.put(item.getIdProduto(), item.getQuantidade());
			}
		}
		return idProdQtd;
	}
}
