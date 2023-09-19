package org.example.mapper;

import org.example.entity.Language;
import org.example.entity.dto.LanguageDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LanguageRowMapper {

   // @Mapping(target = "films.id", source = "idFilms")
    Language toEntity(LanguageDto languageDto);

   // @Mapping(target = "films.id", source = "idFilms")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(LanguageDto languageDto, @MappingTarget Language language);

    LanguageDto toDto(Language language);
}