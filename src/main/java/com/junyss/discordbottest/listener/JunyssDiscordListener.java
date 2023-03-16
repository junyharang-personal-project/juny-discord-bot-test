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
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
public class JunyssDiscordListener extends ListenerAdapter {

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

        try {
            List<String> resultList = CheckDiscordCommand.checkCommand(event, message.getContentDisplay().split(" "));

            if (resultList.isEmpty()) {
                log.info("처리 결과 값 공백");
            }

            createSendMessage(event, resultList.get(0), Objects.requireNonNull(resultList.get(1)));

        } catch (IOException exception) {
            log.error("디스코드 봇이 응답하는 중 문제가 발생하였습니다. \n 문제 내용 : " + exception);
        }
    }

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

    private void sendMessage(MessageReceivedEvent event, String returnMessage, EmbedBuilder embed) {
        TextChannel textChannel = event.getChannel().asTextChannel();
        textChannel.sendMessage(returnMessage).setEmbeds(embed.build()).queue();
    }
}