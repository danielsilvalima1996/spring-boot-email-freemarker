package com.example.emailtemplate.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.emailtemplate.model.MailRequest;
import com.example.emailtemplate.model.MailResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender sender;

  @Autowired
  private Configuration config;

  public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {

    MailResponse response = new MailResponse();
    MimeMessage message = sender.createMimeMessage();

    try {
      // set mediatype
      MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
          StandardCharsets.UTF_8.name());

      // add attachment | anexo
      // helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
      Template t = config.getTemplate("email-template.flt");
      String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

      helper.setTo(request.getTo());
      helper.setText(html, true);
      helper.setSubject(request.getSubject());
      helper.setFrom(request.getFrom());

      sender.send(message);

      response.setMessage("E-mail enviado para: " + request.getTo());
      response.setStatus(Boolean.TRUE);

    } catch (MessagingException | IOException | TemplateException e) {
      response.setMessage("E-mail enviado com falha: " + e.getMessage());
      response.setStatus(Boolean.FALSE);
    }

    return response;

  }

}
