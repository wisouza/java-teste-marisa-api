package br.com.marisa.model.dto.out;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
	
	@Getter @Setter
	private Long id;

	@Getter @Setter
	private BigDecimal precoTotal;
	
	@Getter @Setter
	private boolean finalizado;
	
	@Getter @Setter
	private List<ItemPedidoDTO> listItemPedidoDTO = new ArrayList<>();
}
