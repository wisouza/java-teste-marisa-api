package br.com.marisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marisa.model.ItemPedidoPK;
import br.com.marisa.model.ItemPedidoVO;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedidoVO, ItemPedidoPK>{

}
