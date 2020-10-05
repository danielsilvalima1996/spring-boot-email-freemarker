package com.example.emailtemplate.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import com.example.emailtemplate.model.MailRequest;
import com.example.emailtemplate.model.MailResponse;
import com.example.emailtemplate.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("email")
public class EmailController {

    @Autowired
    EmailService service;

    @PostMapping
    public MailResponse testEnvioEmail(@RequestBody MailRequest request) throws MessagingException {
        
        Map<String, Object> model = new HashMap<>();
        model.put("name", request.getName());
        model.put("location", "São Paulo, Guarulhos");
        
        return service.sendEmail(request, model);
    }

}