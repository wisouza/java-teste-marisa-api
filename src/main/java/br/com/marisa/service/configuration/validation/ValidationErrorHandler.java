package br.com.marisa.service.configuration.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.marisa.model.dto.out.RespostaDTO;
import br.com.marisa.service.exception.MarisaPedidoException;
import br.com.marisa.util.MensagemConstante;
import javassist.tools.rmi.ObjectNotFoundException;

@RestControllerAdvice
public class ValidationErrorHandler {

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ObjectNotFoundException.class)
	public RespostaDTO handle(ObjectNotFoundException exception) {

        RespostaDTO responseDTO = new RespostaDTO(MensagemConstante.ERRO, exception.getMessage());

		return responseDTO;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MarisaPedidoException.class)
	public RespostaDTO handle(MarisaPedidoException exception) {

        RespostaDTO responseDTO = new RespostaDTO(MensagemConstante.ERRO, exception.getMessage());

		return responseDTO;
	}
}

