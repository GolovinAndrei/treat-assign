package co.il.treat.treatassign.service.mapper;

import co.il.treat.treatassign.dto.EstimatedDogDto;
import co.il.treat.treatassign.model.EstimatedDog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstimatedDogMapper extends CommonMapper<EstimatedDogDto, EstimatedDog> {
}
