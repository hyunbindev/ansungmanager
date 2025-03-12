package com.ansung.ansung.service;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.domain.Manager;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.repository.ManagerRepository;

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
		Manager manager = new Manager(encodedPw,name);
		managerRepository.save(manager);
	}
	//관리자 인증
	public String verifyManager(String name ,String pw) {
		Manager manager = managerRepository.findByName(name)
				.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_MANAGER));
		if(passwordEncoder.matches(pw, manager.getPassword())) {
			return manager.getName();
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
}