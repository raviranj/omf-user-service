
package com.mindtree.omf.user.management.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mindtree.omf.user.management.model.OmfUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		OmfUser user = userService.findUserByName(name);
		log.info("User info in MydetailService " + user.toString());
		Collection<? extends GrantedAuthority> authorities = null;
		if (user.getPassword() != null) {
			if (user.getRole().name().equals("USER")) {
				authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
			} else {
				authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
		} else {
			authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
		}
		return new User(user.getName(), user.getPassword(), authorities);
	}
}
