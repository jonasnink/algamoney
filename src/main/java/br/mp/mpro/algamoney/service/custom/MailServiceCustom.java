package br.mp.mpro.algamoney.service.custom;

import br.mp.mpro.algamoney.domain.Lancamento;
import br.mp.mpro.algamoney.domain.User;
import br.mp.mpro.algamoney.service.MailService;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MailServiceCustom extends MailService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailServiceCustom(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        super(jHipsterProperties, javaMailSender, messageSource, templateEngine);
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }
    @Async
    public void enviarLamentosVencidos(List<Lancamento> lancamentosVencidos, List<User> destinatarios){
        log.debug("Enviando Lançamentos Vencidos");
        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("lancamentos", lancamentosVencidos);
        String templateName = "mail/aviso-lancamentos-vencidos";
        String titulo = "Lançamentos vencidos";
        this.sendEmailFromTemplate(destinatarios, templateName, titulo, variaveis);
    }

    @Async
    public void sendEmailFromTemplate(List<User> destinatarios, String templateName, String titleKey, Map<String, Object> variables) {
        Locale locale = new Locale("pt", "BR");
        Context context = new Context(locale);
        variables.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));
        String content = templateEngine.process(templateName, context);
        List<String> emails = destinatarios.stream().map(u -> u.getEmail()).collect(Collectors.toList());
        sendEmail(emails, titleKey, content, false, true);
    }

    @Async
    public void sendEmail(List<String> destinatarios, String assunto, String mensagem, boolean isMultipart, boolean isHtml){
        log.debug("Enviando email para o usuário: '{}'", destinatarios.toString());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setTo(destinatarios.toArray(new String[destinatarios.size()]));
            message.setSubject(assunto);
            message.setText(mensagem, isHtml);

            javaMailSender.send(mimeMessage);
            log.debug("e-mail enviado ao usuário: '{}'", destinatarios.toString());
        } catch (MessagingException e){
            throw new RuntimeException("Problemas com o envio de e-mail!", e);
        } catch (Exception e){
            if(log.isDebugEnabled()){
                log.warn("E-mail não pôde ser enviado para o usuário '{}'", destinatarios.toString(), e);
            } else {
                log.warn("E-mail não pôde ser enviado para o usuário '{}'", destinatarios.toString(), e.getMessage());
            }
        }
    }

}
