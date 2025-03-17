package com.ansung.ansung.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ansung.ansung.domain.Manager;
import com.ansung.ansung.repository.ManagerRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
	@Mock
	private ManagerRepository managerRepository;
	
	@InjectMocks
	private AuthService authService;
	
	@Mock
    private PasswordEncoder passwordEncoder;
	
	 @Mock
	 private Environment environment;
	
	@Test
	@DisplayName("초기 설정 테스트 : 초기 관리자 없을 때")
	void initTest() {
		when(environment.getProperty("manager.init.id")).thenReturn("testPassword");
		when(environment.getProperty("manager.init.pw")).thenReturn("testName");
		when(managerRepository.findAll()).thenReturn(Collections.emptyList());
		when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
		authService.init();
		verify(managerRepository,times(1)).save(any(Manager.class));
	}
	@Test
	@DisplayName("관리자 추가")
	void addManagerTest() {
		String name = "testManager";
		String pw = "testManager";
		authService.addManager(pw, name);
		verify(managerRepository,times(1)).save(any(Manager.class));
	}
	@Test
	@DisplayName("관리자 인증 테스트")
	void verifyManagerTest() {
		Manager testManager = new Manager();
		testManager.setName("testManagerName");
		testManager.setPassword("testManagerPw");
		when(managerRepository.findByName(testManager.getName())).thenReturn(Optional.of(testManager));
		when(passwordEncoder.matches(testManager.getPassword(), testManager.getPassword())).thenReturn(true);
		authService.verifyManager(testManager.getName(),testManager.getPassword());
	}
	@Test
	@DisplayName("관리자 비밀번호 변경 테스트")
	void changeManagerTest() {
	    Manager testManager = new Manager();
	    testManager.setName("testManagerName");
	    testManager.setPassword("testManagerPw");

	    when(managerRepository.findByName(testManager.getName())).thenReturn(Optional.of(testManager));
	    when(passwordEncoder.matches(testManager.getPassword(), testManager.getPassword())).thenReturn(true);

	    String newPassword = "new pw";
	    String encodedNewPassword = passwordEncoder.encode(newPassword);
	    when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);


	    authService.changePwManager(testManager.getName(), testManager.getPassword(), newPassword);

	    verify(managerRepository, times(1)).save(any(Manager.class));

	    Manager savedManager = testManager;
	    assertEquals(encodedNewPassword, savedManager.getPassword());
	}
}
