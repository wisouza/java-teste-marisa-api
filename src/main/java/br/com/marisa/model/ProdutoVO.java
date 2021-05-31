package br.com.marisa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "produto")
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVO implements Serializable {

	private static final long serialVersionUID = 2129268651738127528L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	@Column(name = "idproduto")
	private Long id;

	@Getter
	@Setter
	private String sku;

	@Getter
	@Setter
	@Column(name = "name")
	private String nome;

	@Getter
	@Setter
	@Column(name = "stock")
	private Integer estoque;

	@Getter
	@Setter
	@Column(name = "is_in_sotck")
	private boolean emEstoque;

	@Getter
	@Setter
	@Column(name = "price")
	private BigDecimal preco;

	@Getter
	@Setter
	@OneToMany(mappedBy = "pk.produtoVO")
	private List<ItemPedidoVO> listItemPedido;

	public ProdutoVO(Long id) {
		super();
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProdutoVO other = (ProdutoVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}