package br.com.marisa.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marisa.model.ProdutoVO;
import br.com.marisa.repository.ProdutoRepository;
import br.com.marisa.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService{

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public Optional<ProdutoVO> findById(Long id) {
		return produtoRepository.findById(id);
	}

	@Override
	public void atualizar(ProdutoVO produtoVO) {
		produtoRepository.save(produtoVO);
	}

	
}
