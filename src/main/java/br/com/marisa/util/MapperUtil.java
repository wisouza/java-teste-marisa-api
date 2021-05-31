package br.com.marisa.util;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.marisa.model.ItemPedidoVO;
import br.com.marisa.model.dto.out.ItemPedidoDTO;

@Component("mapperUtil")
public final class MapperUtil {

    protected ModelMapper modelMapper;

    public MapperUtil() {
		 ModelMapper modelMapper = new ModelMapper();
		 modelMapper.createTypeMap(ItemPedidoVO.class, ItemPedidoDTO.class).
		 <String>addMapping(src -> src.getPk().getProdutoVO().getNome(), (dest, value) -> dest.setNomeProduto(value)).
		 <BigDecimal>addMapping(src -> src.getPk().getProdutoVO().getPreco(), (dest, value) -> dest.setPrecoUnitario(value));
		 this.modelMapper = modelMapper;
    }
    
    public <S, D> D mapTo(S source, Class<D> destClass) {
        return this.modelMapper.map(source, destClass);
    }

    public <S, D> List<D> toList(List<S> source, Type destClass) {
        return this.modelMapper.map(source, destClass);
    }

}
