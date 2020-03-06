package pl.aogiri.tmm.server.entity;


import pl.aogiri.tmm.server.dto.GenericDTO;

public interface GenericEntity <T extends GenericDTO>{

    public T toDTO();
}
