package com.mindtree.omf.user.management.mapperImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import com.mindtree.omf.user.management.mapper.UserMapper;
import com.mindtree.omf.user.management.model.OmfUser;
import com.mindtree.omf.user.management.model.UserDto;

@Service
public class UserMapperImpl implements UserMapper {

	DozerBeanMapper mapper = new DozerBeanMapper();

	@Override
	public OmfUser mapToUser(UserDto userDto) {
		return mapper.map(userDto, OmfUser.class);
	}

	@Override
	public UserDto mapToUserDto(OmfUser user) {
		return mapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> mapToUserDtoList(List<OmfUser> userList) {
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		for (OmfUser user : userList) {
			userDtoList.add(mapper.map(user, UserDto.class));
		}
		return userDtoList;
	}

	@Override
	public OmfUser mapToUpdateUser(OmfUser user, UserDto userDto) {
		/*
		 * if (ObjectUtils.isNotEmpty(userDto.getOmfUserType()) &&
		 * ObjectUtils.isNotEmpty(userDto.getOmfUserType().getType())) { OmfUserType
		 * userTpe = new OmfUserType();
		 * userTpe.setType(userDto.getOmfUserType().getType());
		 * user.setOmfUserType(userTpe); }
		 */

		if (ObjectUtils.isNotEmpty(userDto.getRole()))
			user.setName(userDto.getRole());

		if (ObjectUtils.isNotEmpty(userDto.getName()))
			user.setName(userDto.getName());
		if (ObjectUtils.isNotEmpty(userDto.getContactNumber()))
			user.setContactNumber(userDto.getContactNumber());
		if (ObjectUtils.isNotEmpty(userDto.getEmail()))
			user.setEmail(userDto.getEmail());
		/*
		 * if (ObjectUtils.isNotEmpty(userDto.getPassword()))
		 * user.setPassword(userDto.getPassword());
		 */
		return user;
	}

}
