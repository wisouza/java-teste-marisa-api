package br.com.marisa.model.form;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ItemForm {
	
	@Getter @Setter
	@NotNull
	private Long idProduto;
	
	@Getter @Setter
	@NotNull
	private Integer quantidade;
}
