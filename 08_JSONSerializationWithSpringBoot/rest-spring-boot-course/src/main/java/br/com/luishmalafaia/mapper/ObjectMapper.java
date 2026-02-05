package br.com.luishmalafaia.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

// Mapear Entidade para DTO e vice-versa
public class ObjectMapper {

    // Instânciando o Dozer Mapper
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    // Mapeando um objeto de um tipo para outro
    public static <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    // Mapeando uma lista de objetos de um tipo para outro
    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){
        List<D> destinationObjects = new ArrayList<D>();

        for(Object o : origin){
            destinationObjects.add(mapper.map(o, destination));
        }

        return destinationObjects;
    }

}
