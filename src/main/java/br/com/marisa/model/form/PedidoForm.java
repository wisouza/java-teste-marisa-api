package br.com.marisa.model.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PedidoForm {

	private Long id;

	@NotNull
	@NotEmpty
	private List<ItemForm> listaItemForm;
}
