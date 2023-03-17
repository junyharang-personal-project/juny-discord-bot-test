package com.junyss.discordbottest.listener;

import com.junyss.discordbottest.command.CheckDiscordCommand;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * <b>디스 코드를 이용해서 메시지를 보낼 때 해당 메시지를 받고, 응답을 처리하는 Class</b>
 */
@Slf4j
public class JunyssDiscordListener extends ListenerAdapter {

    /**
     * <b>디스코드 사용자 메시지를 받게 되면 처리하게 되는 Method</b>
     * @param event Message를 통해 Event를 처리할 수 있는 JDA 객체
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        Message message = event.getMessage();

        log.info("get message : " + message.getContentDisplay());

        if (user.isBot()) {
            return;
        } else if (message.getContentDisplay().equals("")) {
            log.info("디스코드 Message 문자열 값 공백");
        }

        List<String> resultList = CheckDiscordCommand.checkCommand(event, message.getContentDisplay().split(" "));

        if (resultList.isEmpty()) {
            log.info("처리 결과 값 공백");
        }

        createSendMessage(event, resultList.get(0), Objects.requireNonNull(resultList.get(1)));

    }

    /**
     * <b>디스코드 응답 메시지를 만들기 위한 Method</b>
     * @param event Message를 통해 Event를 처리할 수 있는 JDA 객체
     * @param returnMessage 디스코드 일반 형태의 응답 Message 내용
     * @param returnEmbedMessage 디스코드 Embed 형태의 응답 Message 내용
     */
    private void createSendMessage (MessageReceivedEvent event, String returnMessage, String returnEmbedMessage) {
        int discordAllowMessageSize = 1950;
        int resultMessageSize = returnEmbedMessage.length();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("주니의 Spring Boot Discord Bot Test");
        embed.setColor(Color.GREEN);
        embed.setFooter("ⓒ 2023. 주니(junyharang8592@gmail.com) All Rights Reserved. Blog : https://junyharang.tistory.com/\n Bot Version : 1.0.0b\n");

        if (returnEmbedMessage.equals("명령어를 확인해 주세요.")) {
            embed.setColor(Color.RED);
        }

        if (resultMessageSize >= discordAllowMessageSize) {
            for (int index = 0; index < (resultMessageSize / discordAllowMessageSize) + 1; index++) {
                embed.setDescription(returnEmbedMessage.substring(0, discordAllowMessageSize));
                sendMessage(event, returnMessage, embed);
            }
        } else {
            embed.setDescription(returnEmbedMessage);
            sendMessage(event, returnMessage, embed);
        }
        embed.clear();
    }

    /**
     * <b>실제 디스코드로 응답 Message 보내는 Method</b>
     * @param event Message를 통해 Event를 처리할 수 있는 JDA 객체
     * @param returnMessage 디스코드 일반 형태의 응답 Message 내용
     * @param embed 디스코드 Embed 형태의 응답 Message 내용
     */

    private void sendMessage(MessageReceivedEvent event, String returnMessage, EmbedBuilder embed) {
        TextChannel textChannel = event.getChannel().asTextChannel();
        textChannel.sendMessage(returnMessage).setEmbeds(embed.build()).queue();
    }
}