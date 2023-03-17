package com.junyss.discordbottest.command;

import com.junyss.discordbottest.api.common.exception.CreateExceptionMessage;
import com.junyss.discordbottest.api.crew.model.dto.enumuration.SearchType;
import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import com.junyss.discordbottest.common.util.HttpUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <b>디스코드를 통해 사용자가 보낸 메시지를 분석하여 처리하기 위한 객체</b>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckDiscordCommand {

    /**
     * <b>디스코드를 통해 사용자가 보낸 메시지 중 디스코드 봇을 호출했을 때, 처리를 분기하기 위한 Method</b>
     * @param event Message를 통해 Event를 처리할 수 있는 JDA 객체
     * @param messageArray 사용자가 보낸 메시지를 공백을 통해 자른 뒤 만든 문자열 배열
     * @return 일반적으로 디스코드 봇이 응답할 메시지와 명령에 따른 결과값을 Embed에 넣어 응답하기 위한 문자열 List
     */
    public static List<String> checkCommand (MessageReceivedEvent event, String[] messageArray) {
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

    /**
     * <b>API 호출을 통해 결과값을 받으려는 명령어가 들어왔을 때를 처리하기 위한 Method</b>
     * @param messageArray 사용자가 보낸 메시지를 공백을 통해 자른 뒤 만든 문자열 배열
     * @return API를 통해 얻은 결과값에 대한 문자열
     */

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

    /**
     * <b>사용자가 디스코드 봇을 호출한 뒤 작성한 명령어를 분석하여 응답 메시지를 만들기 위한 Method</b>
     * @param event Message를 통해 Event를 처리할 수 있는 JDA 객체
     * @param messageArray 사용자가 보낸 메시지를 공백을 통해 자른 뒤 만든 문자열 배열
     * @return 디스코드 봇이 응답할 문자열 메시지
     */

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

    /**
     * <b>디스코드를 통해 사용자가 API 호출을 통한 응답을 얻으려고 할 때, 검색 조건이 있는지 확인하기 위한 Method</b>
     * @param commandOption 사용자가 API 호출 때, 검색하기 위한 검색 조건을 담은 Map
     * @return 검색 조건을 검사하여 참과 거짓으로 결과 반환
     */

    private static boolean commandOptionValidate (Map<String, String> commandOption) {
        return commandOption.containsKey(SearchType.INDEX.getSearchTypeKOREAN())
                || commandOption.containsKey(SearchType.ID.getSearchTypeKOREAN())
                || commandOption.containsKey(SearchType.JOIN_DATE.getSearchTypeKOREAN())
                || commandOption.containsKey(SearchType.NAME.getSearchTypeKOREAN())
                || commandOption.containsKey(SearchType.EMAIL.getSearchTypeKOREAN());
    }

    /**
     * <b>디스코드를 통해 사용자가 -이름=홍길동 -날짜=2023.03.10~2023.03.15와 같은 형태로 검색 조건을 보냈을 때,</b>
     * <b>해당 검색 조건 즉, 명령어 Option이 있는지 여부를 확인하기 위한 Method</b>
     * @param command 디스코드를 통해 사용자가 API 호출 당시 입력한 검색 조건 명령어
     * @return 사용자가 보낸 메시지 중 -가 있는지 없는지 여부를 확인
     */
    private static boolean commandOption(String command) {
        return command.contains("-");
    }

    /**
     * <b>사용자가 API 호출시 검색 조건으로 -이름=홍길동 같은 형태로 검색 조건을 주면 = 문자를 기준으로 Map에서 Key와 Value로 나누어 반환하기 위한 Method</b>
     * @param commandOptionMap 검색 조건에 검색 Type을 Key로 검색어를 Value로 담기 위한 Map
     * @param commandOption 사용자가 검색 조건으로 보낸 문자열
     */
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

    /**
     * <b>디스코드를 통해 사용자가 API 호출 관련 명령어를 입력했을 때, 검색 조건을 확인하기 위한 Method</b>
     * @param commandOption -이름=홍길동 라는 검색 조건이 입력 되었을 때, = 을 기준으로 앞에는 Key 뒤에는 Value를 갖는 검색 조건 Map
     * @return HTTP URL Parameter를 만들기 위한 DTO
     */

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

    /**
     * <b>디스코드를 통해 사용자가 API 호출 관련 명령어를 입력했을 때, 검색 조건을 확인하여 HTTP URL Parameter를 만들기 위한 DTO에 값을 넣어주기 위한 Method</b>
     * @param crewSearchDTO  HTTP URL Parameter를 만들기 위한 검색 조건이 들어 간 DTO
     * @param key 사용자가 검색을 위해 입력한 검색 Type
     * @param value 사용자가 검색을 위해 입력한 검색어
     */

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

    /**
     * <b>사용자가 검색할 때, 날짜 범위를 통해 검색하고자 하면 그 날짜 형태가 맞는지 확인하는 Method</b>
     * @param commandDateValue 날짜 검색을 위한 명령어 문자열
     * @return 날짜 검색을 위한 문자열을 SimpleDateFormat 객체로 변경하여 반환
     */

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
