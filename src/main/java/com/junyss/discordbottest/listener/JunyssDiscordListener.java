package com.junyss.discordbottest.listener;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

@Slf4j
public class JunyssDiscordListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        log.info(" get message : " + message.getContentDisplay());

        if (user.isBot()) {
            return;
        } else if (message.getContentDisplay().equals("")) {
            log.info("디스코드 Message 문자열 값 공백");
        }

        String[] messageArray = message.getContentDisplay().split(" ");

        if (messageArray[0].equalsIgnoreCase("주니야")) {

            String[] massageArgs = Arrays.copyOfRange(messageArray, 1, messageArray.length);

            for (String msg : massageArgs) {
                String returnMessage = sendMessage(event, msg);
                textChannel.sendMessage(returnMessage).queue();
            }
        }
    }

    private String sendMessage(MessageReceivedEvent event, String message) {

        User user = event.getAuthor();
        String returnMessage = "";

        switch (message) {
            case "안녕" : returnMessage = user.getName() + "님 안녕하세요? 주니에요!";
            break;
            case "test" : returnMessage = user.getAsTag() + "님 테스트 중이세요?";
            break;
            case "누구야" : returnMessage = user.getAsMention() + "님 저는 주니님이 JAVA로 생성한 Bot이에요!";
            break;
            default: returnMessage = "명령어를 확인해 주세요.";
            break;
            }
        return returnMessage;
    }
}