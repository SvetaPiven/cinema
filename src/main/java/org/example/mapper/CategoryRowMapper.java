package org.example.mapper;

import org.example.entity.Category;
import org.example.entity.dto.CategoryDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRowMapper {

   // @Mapping(target = "films.id", source = "idFilms")
    Category toEntity(CategoryDto categoryDto);

  //  @Mapping(target = "films.id", source = "idFilms")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(CategoryDto categoryDto, @MappingTarget Category category);

    CategoryDto toDto(Category category);
}
