package com.ansung.ansung.service;

import java.util.Collections;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ansung.ansung.constant.UserRole;
import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.data.auth.TokenDto;
import com.ansung.ansung.domain.Manager;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.repository.ManagerRepository;
import com.ansung.ansung.utils.JwtTokenProvider;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final ManagerRepository managerRepository;
	private final Environment environment;
	private final PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void init() {
		List<Manager> managers = managerRepository.findAll();
		if(managers.size() == 0) {
			String initManagerPw = environment.getProperty("manager.init.id");
			String initManagerName = environment.getProperty("manager.init.pw");
			addManager(initManagerPw, initManagerName);
		}
	}
	//관리자 추가
	public void addManager(String pw,String name) {
		String encodedPw = passwordEncoder.encode(pw);
		boolean existsCheck = managerRepository.existsByName(name);
		if(existsCheck) {
			throw new CommonApiException(CommonExceptionEnum.ALREADY_EXISTS_Manager);
		}else {
			Manager manager = new Manager(encodedPw,name);
			managerRepository.save(manager);
		}
	}
	
	//관리자 인증
	public void verifyManager(String name ,String pw) {
		Manager manager = managerRepository.findByName(name)
				.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_MANAGER));
		if(passwordEncoder.matches(pw, manager.getPassword())) {
			User principal = new User(manager.getName(),manager.getPassword(), Collections.singletonList(UserRole.ROLE_MANAGER.getAuthority()));
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, manager.getPassword(), principal.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}else {
			throw new CommonApiException(CommonExceptionEnum.NO_MANAGER);
		}
	}
	
	//관리자 비밀번호 변경
	public void changePwManager(String name, String pw, String newPw) {
		Manager manager = managerRepository.findByName(name)
				.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_MANAGER));
		if(passwordEncoder.matches(pw,manager.getPassword())) {
			String encodedNewPw = passwordEncoder.encode(newPw);
			manager.setPassword(encodedNewPw);
			managerRepository.save(manager);
		}else {
			throw new CommonApiException(CommonExceptionEnum.NO_MANAGER);
		}
	}
	//토큰 발급
	public TokenDto getManagerToken(String name,String pw) {
		Manager manager = managerRepository.findByName(name)
				.orElseThrow(()-> new CommonApiException(CommonExceptionEnum.NO_MANAGER));
		if(passwordEncoder.matches(pw, manager.getPassword())) {
			String accessToken = "Bearer " + JwtTokenProvider.createAcessToken(manager.getId().toString());
			String refreshToken = JwtTokenProvider.createRefeshToken(manager.getId().toString());
			return new TokenDto(accessToken,refreshToken);
		}else {
			throw new CommonApiException(CommonExceptionEnum.NO_MANAGER);
		}
	}
}