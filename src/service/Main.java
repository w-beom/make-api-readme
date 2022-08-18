package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static Pattern GET_NUMBER = Pattern.compile("[0-9]+");
    // 프로젝트 경로 > 모듈 경로
    public static String DATA_DIRECTORY = "C:\\Users\\woo\\IdeaProjects\\Groupware-api-spec\\api\\schedule";
    // 모듈이름 소문자로 작성
    public static String MODULE = "schedule";

    public static void main(String[] args) throws IOException {
        File directory = new File(DATA_DIRECTORY);

        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            throw new FileNotFoundException("파일목록을 찾을 수 없습니다.");
        }

        for (File file : files) {
            if (!file.getName().contains(MODULE)) {
                continue;
            }
            String title = getTitle(file);
            String number = getNumber(file);

            System.out.println("* [" + MODULE.toUpperCase() + "-" + number + title + "]" + "(./" + file.getName() + ")");
        }
    }

    private static String getNumber(File file) {
        Matcher matcher = GET_NUMBER.matcher(file.getName());
        if (matcher.find()) {
            return matcher.group();
        }

        return "숫자 없음";
    }

    private static String getTitle(File file) throws IOException {
        return Files.lines(Paths.get(file.getPath()))
                .skip(0)
                .findFirst()
                .get()
                .replaceAll("#", "");
    }
}
