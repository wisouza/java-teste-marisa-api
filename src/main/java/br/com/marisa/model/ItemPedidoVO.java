package br.com.marisa.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "item_pedido")
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoVO implements Serializable{
	
	private static final long serialVersionUID = 2140071007514469059L;

	@Getter
	@Setter
	@EmbeddedId
	private ItemPedidoPK pk;
	
	@Getter
	@Setter
	private Integer quantidade;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedidoVO other = (ItemPedidoVO) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
}
