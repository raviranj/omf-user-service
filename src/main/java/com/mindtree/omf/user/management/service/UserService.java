package com.mindtree.omf.user.management.service;

import java.util.List;

import com.mindtree.omf.user.management.model.OmfUser;
import com.mindtree.omf.user.management.model.UserOrderRequest;
import com.mindtree.omf.user.management.model.UserDto;

public interface UserService {

	public void createUser(UserDto userDto);

	public List<UserDto> findUsers();

	public UserDto findUser(Long id);

	public void updateUser(UserDto userDto);

	public void deleteUser(Long id);

	public OmfUser findUserByName(String name);

	public String saveOrder(UserOrderRequest orderDto);

}
