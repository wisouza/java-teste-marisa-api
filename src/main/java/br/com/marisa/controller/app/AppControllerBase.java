package br.com.marisa.controller.app;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.marisa.util.MapperUtil;

@Component("appControllerBase")
public class AppControllerBase {

    @Autowired
    MapperUtil mapperUtil;

    public <S, D> D mapTo(S source, Class<D> dest) {
        return mapperUtil.mapTo(source, dest);
    }

    public <S, D> List<D> toList(List<S> source, Type dest) {
        return mapperUtil.toList(source, dest);
    }

}