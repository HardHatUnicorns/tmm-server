package pl.aogiri.tmm.server.util;

import org.modelmapper.ModelMapper;

public class Mapper {
    private static ModelMapper mapper = new ModelMapper();

    public static <D> D map(Object source, Class<D> destinationType){
        return mapper.map(source, destinationType);
    }

}
