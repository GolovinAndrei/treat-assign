package co.il.treat.treatassign.service.mapper;

import co.il.treat.treatassign.dto.UserDto;
import co.il.treat.treatassign.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = EstimatedDogMapper.class)
public interface UserMapper extends CommonMapper<UserDto, User> {

}
