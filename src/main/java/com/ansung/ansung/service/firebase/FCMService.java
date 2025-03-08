package com.ansung.ansung.service.firebase;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.exception.CommonApiException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FCMService {
	public String sendAllMessage(String title, String body){
		Message message = Message.builder()
				.setTopic("all")
				.setNotification(Notification.builder()
						.setTitle(title)
						.setBody(body)
						.build())
				.putData("title", title)
				.putData("message",body)
				.build();
		try {
			String response = FirebaseMessaging.getInstance().sendAsync(message).get();
			return response;
		}catch(ExecutionException|InterruptedException e) {
			e.printStackTrace();
			log.error("fcm error{}",e);
			throw new CommonApiException(CommonExceptionEnum.NO_PRODUCT);
		}
	}
}
