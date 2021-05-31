package br.com.marisa.model.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RespostaDTO {

	@Getter @Setter
	private String status;
	
	@Getter @Setter
	private String mensagem;
}
