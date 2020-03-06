package pl.aogiri.tmm.server.dto;

import pl.aogiri.tmm.server.entity.GenericEntity;

public interface GenericDTO<S extends GenericEntity> {
    S toEntity();
}