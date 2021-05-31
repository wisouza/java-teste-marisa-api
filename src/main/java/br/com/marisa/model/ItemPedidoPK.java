package br.com.marisa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class ItemPedidoPK implements Serializable {

	private static final long serialVersionUID = 4001368589417731487L;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="idproduto")
	private ProdutoVO produtoVO;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="idpedido")
	private PedidoVO pedidoVO;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedidoPK other = (ItemPedidoPK) obj;
		if (pedidoVO == null) {
			if (other.pedidoVO != null)
				return false;
		} else if (!pedidoVO.equals(other.pedidoVO))
			return false;
		if (produtoVO == null) {
			if (other.produtoVO != null)
				return false;
		} else if (!produtoVO.equals(other.produtoVO))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedidoVO == null) ? 0 : pedidoVO.hashCode());
		result = prime * result + ((produtoVO == null) ? 0 : produtoVO.hashCode());
		return result;
	}

}
