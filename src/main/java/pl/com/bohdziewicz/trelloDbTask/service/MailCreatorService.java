package pl.com.bohdziewicz.trelloDbTask.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import pl.com.bohdziewicz.trelloDbTask.config.AdminConfig;
import pl.com.bohdziewicz.trelloDbTask.domain.Task;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    private DbService dbService;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can menage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("App allows sending tasks to trllo");
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://bohdziewicz.com.pl/java_tests/index.html");
        context.setVariable("button", "Visit the website");
        context.setVariable("admin_name", adminConfig);
        context.setVariable("companyDetail", "Trello_DB_APP by bohdziewicz.com.pl");
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("app_functions", functionality);

        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildScheduledMail(String message) {

        List<Task> taskInDb = new ArrayList<>(dbService.getAllTask());
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_name", adminConfig);
        context.setVariable("tasks_in_db", taskInDb);
        return templateEngine.process("mail/scheulded-mail", context);
    }
}
