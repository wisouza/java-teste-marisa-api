package br.com.marisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marisa.model.PedidoVO;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoVO, Long>{

}
