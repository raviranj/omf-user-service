package com.mindtree.omf.user.management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.omf.user.management.model.UserDto;
import com.mindtree.omf.user.management.model.UserOrderRequest;
import com.mindtree.omf.user.management.service.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;

/**
 * @author M1034118
 *
 */
@RestController
@OpenAPIDefinition(info = @Info(title = "OMF SEARCH API", version = "v1"))
@RequestMapping("/api/omf/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService service;

	/**
	 * This api helps in storing the social login credential into db after
	 * successful login to scial sites like google ,facebook etc
	 * 
	 * @param OAuth2AuthenticationToken: hold the authorize user info
	 * @return message
	 */
	@GetMapping(value = "/saveRegisteredUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveRegisteredUser(OAuth2AuthenticationToken userPrincipal) {
		log.info("scial login user info " + userPrincipal.toString());
		String role = userPrincipal.getAuthorities().iterator().next().getAuthority();
		OAuth2User oAuth2User = userPrincipal.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		UserDto userDto = new UserDto();
		userDto.setName(attributes.get("name").toString());
		userDto.setEmail(attributes.get("email").toString());
		if (role.equals("ROLE_USER")) {
			userDto.setRole("USER");
		} else if (role.equals("ROLE_ADMIN")) {
			userDto.setRole("ADMIN");
		}
		try {
			service.createUser(userDto);
			return "User Successfully Authenticated and saved";
		} catch (Exception e) {
			log.info("error while saving  the registered user ");

		}
		return "User failed to saved into db";
	}

	/**
	 * This api is to store the anonymous user info.
	 * 
	 * @param userDto
	 * @return string message Failed or successfully updated
	 */
	@GetMapping(value = "/saveAnonymousUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveAnonymousUser(@RequestBody UserDto userDto) {

		service.createUser(userDto);
		return "Anonymous User Created";
	}

	@PostMapping("/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String updateUser(@RequestBody UserDto userDto) {
		try {
			service.updateUser(userDto);
			return "User Successfully updated";
		} catch (Exception e) {
			log.info("error while updatting registered user ");
		}
		return "User Update Failed";
	}

	/**
	 * This Api is to delete the user from the db.Only accessable by the admin
	 * 
	 * @param Long id , i.e userid
	 * @return string message deleted
	 */
	@PostMapping("/deleteUser/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@PathVariable("id") Long id) {
		service.deleteUser(id);
		return "User Deleted";
	}

	/**
	 * This api is used for placing the order by registered user(USER or ADMIN) This
	 * api connect with search service to get the details of the restaurant info
	 * using Feign client and after successful fetch, pass the object to kafka
	 * producer which is Consumed by kafka listener in the orderservice.
	 * 
	 * @param UserOrderRequest orderDto
	 * @return string message deleted
	 */
	@PostMapping("/placeOrder")
	public String saveOrder(@RequestBody UserOrderRequest orderDto) {
		log.info("inside save  method   for registered user " + orderDto);
		String saveOrder = service.saveOrder(orderDto);
		return saveOrder;
	}

	/**
	 * This api is used for placing the order by anonymous user only This api
	 * connect with search service to get the details of the restaurant info using
	 * Feign client and after successful fetch, pass the object to kafka producer
	 * which is Consumed by kafka listener in the orderservice.
	 * 
	 * @param UserOrderRequest orderDto
	 * @return string message deleted
	 */
	@PostMapping("/anonymous/placeOrder")
	public String saveOrderByanonymous(@RequestBody UserOrderRequest orderDto) {
		log.info("inside save  method   for annonymous user " + orderDto);
		return service.saveOrder(orderDto);
	}

	/**
	 * This api is used to fetch all the user
	 * 
	 * @param
	 * @return List<UserDto>
	 */

	@GetMapping("/findAllUsers")
	public List<UserDto> findUsers() {
		return service.findUsers();
	}

	/**
	 * This api is used to find the user by user id
	 * 
	 * @param
	 * @return List<UserDto>
	 */
	@GetMapping("/findUser/{id}")
	public UserDto findUser(@PathVariable("id") Long id) {
		return service.findUser(id);
	}
}
