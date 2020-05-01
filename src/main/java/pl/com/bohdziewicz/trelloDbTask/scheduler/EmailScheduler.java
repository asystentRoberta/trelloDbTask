package pl.com.bohdziewicz.trelloDbTask.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pl.com.bohdziewicz.trelloDbTask.config.AdminConfig;
import pl.com.bohdziewicz.trelloDbTask.domain.Mail;
import pl.com.bohdziewicz.trelloDbTask.repository.TaskRepository;
import pl.com.bohdziewicz.trelloDbTask.service.SimpleEmailService;
import pl.com.bohdziewicz.trelloDbTask.utils.MailTypes;

@Component
public class EmailScheduler {

    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    EmailScheduler(SimpleEmailService simpleEmailService,
            TaskRepository taskRepository, AdminConfig adminConfig) {

        this.simpleEmailService = simpleEmailService;
        this.taskRepository = taskRepository;
        this.adminConfig = adminConfig;
    }

    @Scheduled(cron = "0 0 10 * * *") //cron every day at 10 am
    public void sendInformationEmail() {

        long size = taskRepository.count();
        StringBuilder stringBuilder = new StringBuilder("Currently you have got: ");
        String endOfSTatemnt;
        stringBuilder.append(size)
                .append(" task");
        endOfSTatemnt = (size > 6) ? "s." : ".";
        stringBuilder.append(endOfSTatemnt);
        simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                MailTypes.EMAIL_SCHEDULER.getSubjectOfMail(),
                stringBuilder.toString(),
                MailTypes.EMAIL_SCHEDULER)
        );
        LOGGER.info("Scheduled an email was sent");
    }
}
