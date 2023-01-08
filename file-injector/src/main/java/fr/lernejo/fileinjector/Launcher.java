package fr.lernejo.fileinjector;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class Launcher {
    public static void main(String[] args) throws IOException {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {

            if (args.length > 0 && args[0] != "") {
                ObjectMapper map = new ObjectMapper();
                List<Game> games = Arrays.asList(map.readValue(Paths.get(args[0]).toFile(), Game[].class));
                RabbitTemplate template = springContext.getBean(RabbitTemplate.class);
                for (Game game : games) {
                    template.setMessageConverter(new Jackson2JsonMessageConverter());
                    template.convertAndSend("", "game_info", game,m -> { m.getMessageProperties().getHeaders().put("game_id", game.id());return m; });
                }
            } else { throw new IOException("No parameters passed!"); }
        } catch (IOException e) { throw new IOException(e); }
    }
}
