package br.com.marisa.model.dto.out;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private BigDecimal preco;
	
	
}
