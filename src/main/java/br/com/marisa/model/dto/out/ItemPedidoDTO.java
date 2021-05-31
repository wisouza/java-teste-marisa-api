package br.com.marisa.model.dto.out;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {

	@Getter @Setter
	private String nomeProduto;
	
	@Getter
	@Setter
	private Integer quantidade;
	
	@Getter
	@Setter
	private BigDecimal precoUnitario;
}
