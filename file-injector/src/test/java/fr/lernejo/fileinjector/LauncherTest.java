package fr.lernejo.fileinjector;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {
    @Test
    void generateGamesMessagesInRabbitWithoutGoodUrl() {
        String path = new File("src/test/resources").getAbsolutePath();
        String jsonPath = path + "/fd.json";
        assertThrows(IOException.class, () -> Launcher.main(new String[]{jsonPath}));
    }

    @Test
    void generateGamesMessagesInRabbitWithoutParameters() {
        assertThrows(IOException.class, () -> Launcher.main(new String[]{}));
    }

    @Test
    void generateGamesMessagesInRabbitWithEmptyStringParameters() {
        assertThrows(IOException.class, () -> Launcher.main(new String[]{""}));
    }

    @Test
    void generateGamesMessagesInRabbitWithGoodParameters() throws IOException {
        File file = new File("src/test/resources");
        String jsonTestFile = file.getAbsolutePath()+"/games.json";
        Launcher.main(new String[]{jsonTestFile});
    }

    @Test
    void main_terminates_before_20_sec() {
        File file = new File("src/test/resources");
        String jsonTestFile = file.getAbsolutePath()+"/games.json";
        assertTimeoutPreemptively(
            Duration.ofSeconds(20L),
            () -> Launcher.main(new String[]{jsonTestFile})
        );
    }


}
