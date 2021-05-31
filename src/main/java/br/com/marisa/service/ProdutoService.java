package br.com.marisa.service;

import java.util.Optional;

import br.com.marisa.model.ProdutoVO;

public interface ProdutoService {

	Optional<ProdutoVO>  findById(Long id);

	void atualizar(ProdutoVO produtoVO);
}
