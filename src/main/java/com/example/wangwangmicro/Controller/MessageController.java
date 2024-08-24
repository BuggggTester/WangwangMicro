package com.example.wangwangmicro.Controller;

import com.example.wangwangmicro.Entity.Message;
import com.example.wangwangmicro.Entity.R;
import com.example.wangwangmicro.Service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Slf4j
@Controller
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping(value = "/create")
    public R CreateMessage(@RequestBody Map<String, String> messageMap){
        String send = messageMap.get("sender");
        int receive = Integer.parseInt(messageMap.get("receiver"));
        String title = messageMap.get("title");
        String body = messageMap.get("body");
        LocalDate send_date = LocalDate.now();
        LocalTime send_time = LocalTime.now();
        try {
            messageService.CreateMessage(send, receive, title, body, send_date, send_time);
            return R.ok("消息推送成功！");
        }catch (Exception e){
            return R.error("消息推送失败！");
        }
    }

    @GetMapping(value = "/baseselect")
    public R SelectMessageByReceiver(@RequestParam("receiver") int receiver){
        List<Message> messages = messageService.SelectMessageByReceiver(receiver);
        return R.ok().put("messages", messages);
    }

    @GetMapping(value = "/numberselect")
    public R CountMessageByReceiver(@RequestParam("receiver") int receiver){
        int num = messageService.CountNumberOfMessageByReceiver(receiver);
        return R.ok().put("num", num);
    }

    @GetMapping(value = "/unreadnumberselect")
    public R CountUnreadMessageByReceiver(@RequestParam("receiver") int receiver){
        int num = messageService.CountNumberOfUnreadMessageByReceiver(receiver);
        return R.ok().put("num", num);
    }

    @GetMapping(value = "/unreadselect")
    public R SelectUnreadMessageByReceiver(@RequestParam("receiver") int receiver){
        List<Message> messages = messageService.SelectUnreadMessageByReceiver(receiver);
        return R.ok().put("messages", messages);
    }

    @GetMapping(value = "/dateselect")
    public R SelectMessageByReceiverAndDate(@RequestParam("receiver") int receiver, @RequestParam("send_date") LocalDate send_date){
        List<Message> messages = messageService.SelectMessageByDateAndReceiver(receiver, send_date);
        return R.ok().put("messages", messages);
    }

    @GetMapping(value = "/unreaddateselect")
    public R SelectUnreadMessageByReceiverAndDate(@RequestParam("receiver") int receiver, @RequestParam("send_date") LocalDate send_date){
        List<Message> messages = messageService.SelectUnreadMessageByDateAndReceiver(receiver, send_date);
        return R.ok().put("messages", messages);
    }

    @PutMapping(value = "/setread")
    public R SetRead(@RequestParam("message_id") int message_id){
        try {
            messageService.SetRead(message_id);
            return R.ok("已设置为已读。");
        }catch (Exception e){
            return R.error(e.toString());
        }
    }
}
