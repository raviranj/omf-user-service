package com.mindtree.omf.user.management.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mindtree.omf.user.management.mapperImpl.UserMapperImpl;
import com.mindtree.omf.user.management.model.OderRequestDto;
import com.mindtree.omf.user.management.model.OmfUser;
import com.mindtree.omf.user.management.model.SearchByRequest;
import com.mindtree.omf.user.management.model.UserOrderRequest;
import com.mindtree.omf.user.management.model.SearchRestaurentResponse;
import com.mindtree.omf.user.management.model.UserDto;
import com.mindtree.omf.user.management.repository.UserRepository;
import com.mindtree.omf.user.management.service.SearchServiceProxy;
import com.mindtree.omf.user.management.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapperImpl mapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SearchServiceProxy searchServiceProxy;

	@Autowired
	private KafkaTemplate<String, OderRequestDto> kafkaTemplate;

	private static final String CREATE_ACCOUNT_TOPIC = "saveOrder";

	@Override
	public void createUser(UserDto userDto) {
		userRepository.save(mapper.mapToUser(userDto));
	}

	@Override
	public List<UserDto> findUsers() {
		List<OmfUser> userList = userRepository.findAll();
		return mapper.mapToUserDtoList(userList);
	}

	@Override
	public UserDto findUser(Long id) {
		Optional<OmfUser> Optuser = userRepository.findById(id);
		UserDto userDto = null;
		if (Optuser.isPresent())
			userDto = mapper.mapToUserDto(Optuser.get());
		return userDto;
	}

	@Override
	public void updateUser(UserDto userDto) {
		if (userDto.getUserId() != null) {
			Optional<OmfUser> Optuser = userRepository.findById(userDto.getUserId());
			if (Optuser.isPresent())
				userRepository.save(mapper.mapToUpdateUser(Optuser.get(), userDto));
		}
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public OmfUser findUserByName(String name) {
		OmfUser user = userRepository.findByName(name);
		return user;
	}

	@Override
	@HystrixCommand(fallbackMethod = "saveOrder_Fallback")
	public String saveOrder(UserOrderRequest userOrderRequest) {
		log.info("inside save Order " + userOrderRequest.toString());
		Optional<OmfUser> Optuser = userRepository.findById(userOrderRequest.getUserId());
		if (Optuser.isPresent()) {
			SearchByRequest searchByRequest = new SearchByRequest();
			searchByRequest.setRestaurantId(userOrderRequest.getRestaurantId());
			OmfUser omfUser = Optuser.get();
			List<SearchRestaurentResponse> restaurantinfolist = searchServiceProxy.searchbyAttribute(searchByRequest);
			if (restaurantinfolist != null) {
				log.info("restarant info found " + restaurantinfolist.get(0).getRestaurantName());
				SearchRestaurentResponse searchRestaurentResponse = restaurantinfolist.get(0);
				OderRequestDto oderRequestDto = new OderRequestDto();
				oderRequestDto.setRestaurantinfo(searchRestaurentResponse);
				oderRequestDto.setOrderDishDetailList(userOrderRequest.getOrderDishDetailList());
				oderRequestDto.setUserId(Optuser.get().getUserId());
				oderRequestDto.setOffer(searchRestaurentResponse.getRestaurantOffer());
				oderRequestDto.setUsername(omfUser.getName());
				oderRequestDto.setPaymentType(userOrderRequest.getPaymentType());
				oderRequestDto.setInstruction(userOrderRequest.getInstruction());
				log.info("invoking  saveOrder from User Service  " + oderRequestDto);
				String invokeOrderServiceToSaveOrder = invokeOrderServiceToSaveOrder(oderRequestDto);
				log.info("reponse from orderservice  " + invokeOrderServiceToSaveOrder);
				
				if (invokeOrderServiceToSaveOrder != null) {
					return "Order has sucessfully placed for the user " + omfUser.getName() + System.lineSeparator();
				}

			}
		}
		return "Order Failed";
	}

	@SuppressWarnings("unused")
	private String saveOrder_Fallback(UserOrderRequest orderRequest) {
		System.out.println("Search Service is down!!! fallback route enabled...");
		return "CIRCUIT BREAKER ENABLED!!! No Response From Search Service/Order Service at this moment.Sorry for inconvenience in placing Order "
				+ " Service will be back shortly - " + new Date();
	}

	private String invokeOrderServiceToSaveOrder(OderRequestDto oderRequestDto) {
		kafkaTemplate.send(CREATE_ACCOUNT_TOPIC, oderRequestDto);
		return "Order has sucessfully placed for the user " + oderRequestDto.getUserId() + System.lineSeparator();
	}

}
