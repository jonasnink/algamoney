
package br.mp.mpro.algamoney.mail;

import br.mp.mpro.algamoney.domain.Lancamento;
import br.mp.mpro.algamoney.domain.User;
import br.mp.mpro.algamoney.repository.LancamentoRepository;
import br.mp.mpro.algamoney.service.MailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Mailer {

    private final JavaMailSender mailSender;

    private final LancamentoRepository lancamentoRepository;

    private final MailService mailService;

//    @EventListener
//    private void teste(ApplicationReadyEvent event){
//        try {
//            this.enviarEmail("jonasnink@hotmail.com", Arrays.asList("jonasnink@hotmail.com"), "Testando", "Olá!<br/>Teste Ok.");
//            System.out.println("event = [" + event + "], Terminado o Envio de e-mail...");
//        } catch (Exception e){
//            System.out.println("event = [" + event + "], ocorreu um erro -> ao tentar enviar o e-mail... ======>" + e.getMessage());
//            System.out.println(e.toString());
//            System.out.println(e.getCause().toString());
//        }
//
//    }

//    @EventListener
//    private void teste(ApplicationReadyEvent event){
//        String template = "mail/aviso-lancamentos-vencidos";
//        List<Lancamento> lista = lancamentoRepository.findAll();
//
//        Map<String, Object> variaveis = new HashMap<>();
//        variaveis.put("lancamentos", lista);
//
//        try {
//            //this.enviarEmail("jonasnink@hotmail.com", Arrays.asList("jonasnink@hotmail.com"), "Testando", template, variaveis);
//            System.out.println("event = [" + event + "], Terminado o Envio de e-mail...");
//        } catch (Exception e){
//            System.out.println("event = [" + event + "], ocorreu um erro -> ao tentar enviar o e-mail............. ======>" + e.getMessage());
//            System.out.println(e.toString());
//            System.out.println(e.getCause().toString());
//        }
//    }

    public Mailer(JavaMailSender mailSender, LancamentoRepository lancamentoRepository, MailService mailService) {
        this.mailSender = mailSender;
        this.lancamentoRepository = lancamentoRepository;
        this.mailService = mailService;
    }

//    public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, List<User> destinatarios){
//        Map<String, Object> variaveis = new HashMap<>();
//        variaveis.put("lancamentos", vencidos);
//
//        List<String> emails = destinatarios.stream().map(u -> u.getEmail()).collect(Collectors.toList());
//
//        this.enviarEmail("jonasnink@hotmail.com", emails, "Lançamentos vencidos", "mail/aviso-lancamentos-vencidos", variaveis);
//    }

//    public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String template, Map<String, Object> variaveis) {
//        Context context = new Context(new Locale("pt", "BR"));
//        variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));
//        String mensagem = thymeleaf.process(template, context);
//        this.enviarEmail(remetente, destinatarios, assunto, mensagem);
//    }
//
//    public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem){
//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
//            helper.setFrom(remetente);
//            helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
//            helper.setSubject(assunto);
//            helper.setText(mensagem, true);
//
//            mailSender.send(mimeMessage);
//        } catch (MessagingException e){
//            throw new RuntimeException("Problemas com o envio de e-mail!", e);
//        }
//    }


}
