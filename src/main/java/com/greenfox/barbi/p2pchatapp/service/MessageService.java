package com.greenfox.barbi.p2pchatapp.service;

import com.greenfox.barbi.p2pchatapp.model.AnswerStatus;
import com.greenfox.barbi.p2pchatapp.model.Client;
import com.greenfox.barbi.p2pchatapp.model.Message;
import com.greenfox.barbi.p2pchatapp.model.Received;
import com.greenfox.barbi.p2pchatapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageService {

  @Autowired
  MessageRepository messageRepository;

  @Autowired
  ChatUserService chatUserService;

  public Iterable<Message> findMessages() {
    return messageRepository.findAll();
  }

  public void setUser(Message message) {
    message.setUsername(chatUserService.findFirst().getUsername());
  }

  public void sendMessage(Message message) {
    Received received = new Received(message, new Client());
    RestTemplate restTemplate = new RestTemplate();
    String url = System.getenv("CHAT_APP_PEER_ADDRESS");
    AnswerStatus answer = restTemplate.postForObject(url, received, AnswerStatus.class);
  }

}
