
package com.mindtree.omf.user.management.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mindtree.omf.user.management.model.SearchByRequest;
import com.mindtree.omf.user.management.model.SearchRestaurentResponse;

@FeignClient(name = "search-management-app")
public interface SearchServiceProxy {
	@PostMapping(value = "/api/omf/searchby", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchRestaurentResponse> searchbyAttribute(@RequestBody SearchByRequest searchByRequest);
}