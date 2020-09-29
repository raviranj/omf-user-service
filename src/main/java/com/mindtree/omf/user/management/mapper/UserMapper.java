package com.mindtree.omf.user.management.mapper;

import java.util.List;

import com.mindtree.omf.user.management.model.OmfUser;
import com.mindtree.omf.user.management.model.UserDto;

public interface UserMapper {

	public OmfUser mapToUser(UserDto userDto);
	public UserDto mapToUserDto(OmfUser user);
	public List<UserDto> mapToUserDtoList(List<OmfUser> userList);
	public OmfUser mapToUpdateUser(OmfUser user, UserDto userDto);
}
