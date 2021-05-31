package br.com.marisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marisa.model.ProdutoVO;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoVO, Long>{

}
