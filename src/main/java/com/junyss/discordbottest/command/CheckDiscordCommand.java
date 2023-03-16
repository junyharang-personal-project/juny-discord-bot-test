package com.junyss.discordbottest.command;

import com.junyss.discordbottest.api.common.exception.CreateExceptionMessage;
import com.junyss.discordbottest.api.crew.model.dto.enumuration.SearchType;
import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import com.junyss.discordbottest.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class CheckDiscordCommand {

    private CheckDiscordCommand() {}

    public static List<String> checkCommand (MessageReceivedEvent event, String[] messageArray) throws IOException {
        List<String> result = new ArrayList<>();

        if (messageArray[0].equalsIgnoreCase("주니야") && messageArray[1].equalsIgnoreCase("크루전체조회")) {
            result.add(event.getAuthor().getName() + "님 크루들의 정보는 아래와 같아요!");
            result.add(checkBodyCommandForAPICall(messageArray));
            return result;
        } else if (messageArray[0].equalsIgnoreCase("주니야")){
            result.add(event.getAuthor().getName() + "님 어떤 정보가 필요하세요?");
            result.add(checkBodyCommand(event, messageArray));
            return result;
        } else {
            return result;
        }
    }

    private static String checkBodyCommandForAPICall (String[] messageArray) {
        Map<String, String> commandOption = new HashMap<>();
        CrewSearchDTO crewSearchDTO = null;

        for (int index = 0; index < messageArray.length; index++) {
            if (commandOption(messageArray[index])) {
                checkCommandOption(commandOption, messageArray[index]);

                if (!commandOptionValidate(commandOption)) {
                    return HttpUtil.crewByAll( "GET");
                }
                crewSearchDTO = makeUriParam(commandOption);
            }
        }

        if (crewSearchDTO != null) {
            return HttpUtil.crewBySearchOption(crewSearchDTO, "GET");
        } else {
            return HttpUtil.crewByAll( "GET");
        }
    }

    private static String checkBodyCommand (MessageReceivedEvent event, String[] messageArray) {
        User user = event.getAuthor();
        String result;

        switch (messageArray[1]) {
            case "안녕":
                result = user.getName() + "님 안녕하세요? 주니에요!";
                break;
            case "test":
                result = user.getAsTag() + "님 테스트 중이세요?";
                break;
            case "누구야":
                result = user.getAsMention() + "님 저는 주니님이 JAVA로 생성한 Bot이에요!";
                break;
            case "크루전체조회":
                result = checkBodyCommandForAPICall(messageArray);
                break;
            default:
                result = "명령어를 확인해 주세요.";
                break;
        }
        return result;
    }

    private static boolean commandOptionValidate (Map<String, String> commandOption) {
        return commandOption.containsKey(SearchType.INDEX.getSearchTypeKOREAN())
                || commandOption.containsKey(SearchType.ID.getSearchTypeKOREAN())
                || commandOption.containsKey(SearchType.JOIN_DATE.getSearchTypeKOREAN())
                || commandOption.containsKey(SearchType.NAME.getSearchTypeKOREAN())
                || commandOption.containsKey(SearchType.EMAIL.getSearchTypeKOREAN());
    }

    private static boolean commandOption(String command) {
        return command.contains("-");
    }

    private static void checkCommandOption(Map<String, String> commandOptionMap, String commandOption) {

        String[] commandOptionArray = commandOption.split("=");

        if (commandOptionArray.length >= 2) {
            for (int index = 0; index < (commandOptionArray.length - 1); index++) {
                String[] searchTypeArray = commandOptionArray[index].split("-");
                String searchType = null;

                if (searchTypeArray.length > 0) {
                    searchType = searchTypeArray[1];
                }

                if (searchType != null) {
                    commandOptionMap.put(searchType, commandOptionArray[index + 1]);
                } else {
                    commandOptionMap.put(commandOptionArray[index], commandOptionArray[index + 1]);
                }
            }
        }
    }

    private static CrewSearchDTO makeUriParam(Map<String, String> commandOption) {
        CrewSearchDTO crewSearchDTO = new CrewSearchDTO();

        for (Map.Entry<String, String> entry : commandOption.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "고유번호":
                    key = SearchType.INDEX.getDescription();
                    break;
                case "계정":
                    key = SearchType.ID.getDescription();
                    break;
                case "날짜":
                    key = SearchType.JOIN_DATE.getDescription();
                    break;
                case "이름":
                    key = SearchType.NAME.getDescription();
                    break;
                case "이메일":
                    key = SearchType.EMAIL.getDescription();
                    break;
                default:
                    key = "-";
                    break;
            }
            setCrewSearchDTO(crewSearchDTO, key, value);
        }
        return crewSearchDTO;
    }

    private static void setCrewSearchDTO(CrewSearchDTO crewSearchDTO, String key, String value) {
        if ((key.equals(SearchType.JOIN_DATE.getDescription()))) {

            String[] splitDate = value.split("~");
            String startDate = null;
            String endDate = null;
            int loopCount = 0;

            for (String isValidSearchDate : splitDate) {
                loopCount += 1;

                if (loopCount > 1) {
                    endDate = commandOptionDateValidate(isValidSearchDate);
                } else {
                    startDate = commandOptionDateValidate(isValidSearchDate);
                }
            }
            crewSearchDTO.setStartDate(startDate);
            crewSearchDTO.setEndDate(endDate);
        } else {
            crewSearchDTO.setSearchType(key);
            crewSearchDTO.setSearchWord(value);
        }
    }

    private static String commandOptionDateValidate(String commandDateValue) {
        Date isSearchDate;
        SimpleDateFormat changeDateFormat = new SimpleDateFormat("yyyy.MM.dd");

        try {
            isSearchDate = changeDateFormat.parse(commandDateValue);
            return changeDateFormat.format(isSearchDate);
        } catch (ParseException parseException) {
            log.error(CreateExceptionMessage.makeExceptionMessage() + parseException.getMessage());
        }
        return null;
    }
}
