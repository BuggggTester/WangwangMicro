package com.example.wangwangmicro;

import com.example.wangwangmicro.Controller.MessageController;
import com.example.wangwangmicro.Entity.Message;
import com.example.wangwangmicro.Entity.R;
import com.example.wangwangmicro.Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@WebMvcTest(MessageController.class)
public class MessageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMessage() throws Exception {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("sender", "John");
        messageMap.put("receiver", "1");
        messageMap.put("title", "Hello");
        messageMap.put("body", "World");

        doNothing().when(messageService).CreateMessage(anyString(), anyInt(), anyString(), anyString(), any(LocalDate.class), any(LocalTime.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/message/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(messageMap)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("消息推送成功！"));

        verify(messageService, times(1)).CreateMessage(anyString(), anyInt(), anyString(), anyString(), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    public void testSelectMessage() throws Exception {
        List<Message> messages = Collections.singletonList(new Message());
        when(messageService.SelectMessageByReceiver(anyInt())).thenReturn(messages);

        mockMvc.perform(MockMvcRequestBuilders.get("/message/baseselect")
                        .param("receiver", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists());

        verify(messageService, times(1)).SelectMessageByReceiver(anyInt());
    }

    @Test
    public void testSelectNumberOfMessage() throws Exception {
        when(messageService.CountNumberOfMessageByReceiver(anyInt())).thenReturn(5);

        mockMvc.perform(MockMvcRequestBuilders.get("/message/numberselect")
                        .param("receiver", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("5"));

        verify(messageService, times(1)).CountNumberOfMessageByReceiver(anyInt());
    }

    @Test
    public void testSelectNumberOfUnreadMessage() throws Exception {
        when(messageService.CountNumberOfUnreadMessageByReceiver(anyInt())).thenReturn(3);

        mockMvc.perform(MockMvcRequestBuilders.get("/message/unreadnumberselect")
                        .param("receiver", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("3"));

        verify(messageService, times(1)).CountNumberOfUnreadMessageByReceiver(anyInt());
    }

    @Test
    public void testSelectUnreadMessage() throws Exception {
        List<Message> messages = Collections.singletonList(new Message());
        when(messageService.SelectUnreadMessageByReceiver(anyInt())).thenReturn(messages);

        mockMvc.perform(MockMvcRequestBuilders.get("/message/unreadselect")
                        .param("receiver", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists());

        verify(messageService, times(1)).SelectUnreadMessageByReceiver(anyInt());
    }

    @Test
    public void testSelectMessageByDate() throws Exception {
        List<Message> messages = Collections.singletonList(new Message());
        when(messageService.SelectMessageByDateAndReceiver(anyInt(), any(LocalDate.class))).thenReturn(messages);

        mockMvc.perform(MockMvcRequestBuilders.get("/message/dateselect")
                        .param("receiver", "1")
                        .param("send_date", LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists());

        verify(messageService, times(1)).SelectMessageByDateAndReceiver(anyInt(), any(LocalDate.class));
    }

    @Test
    public void testSelectUnreadMessageByDate() throws Exception {
        List<Message> messages = Collections.singletonList(new Message());
        when(messageService.SelectUnreadMessageByDateAndReceiver(anyInt(), any(LocalDate.class))).thenReturn(messages);

        mockMvc.perform(MockMvcRequestBuilders.get("/message/unreaddateselect")
                        .param("receiver", "1")
                        .param("send_date", LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists());

        verify(messageService, times(1)).SelectUnreadMessageByDateAndReceiver(anyInt(), any(LocalDate.class));
    }

    @Test
    public void testSetRead() throws Exception {
        doNothing().when(messageService).SetRead(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.put("/message/setread")
                        .param("message_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("已设置为已读。"));

        verify(messageService, times(1)).SetRead(anyInt());
    }
}

