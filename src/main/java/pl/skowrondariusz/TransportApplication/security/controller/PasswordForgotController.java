package pl.skowrondariusz.TransportApplication.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.skowrondariusz.TransportApplication.security.form.PasswordForgotForm;
import pl.skowrondariusz.TransportApplication.security.model.Mail;
import pl.skowrondariusz.TransportApplication.security.model.PasswordResetToken;
import pl.skowrondariusz.TransportApplication.security.model.User;
import pl.skowrondariusz.TransportApplication.security.repository.PasswordResetTokenRepository;
import pl.skowrondariusz.TransportApplication.security.service.EmailService;
import pl.skowrondariusz.TransportApplication.security.service.TokenService;
import pl.skowrondariusz.TransportApplication.security.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {
    private Logger logger;
    private static final Logger LOG = LoggerFactory.getLogger(PasswordForgotController.class);
    @Autowired
    private UserService userService;
    @Autowired private PasswordResetTokenRepository tokenRepository;
    @Autowired private EmailService emailService;
    @Autowired
    private TokenService tokenService;

    @ModelAttribute("forgotPasswordForm")
    public PasswordForgotForm passwordForgotForm() {
        return new PasswordForgotForm();
    }

    @GetMapping
    public String displayForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping
    public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotForm form,
                                            BindingResult result,
                                            HttpServletRequest request) {

        if (result.hasErrors()){
            return "forgot-password";
        }

        User user = userService.findByEmail(form.getEmail());
        if (user == null){
            result.rejectValue("email", null, "We could not find an account for that e-mail address.");
            return "forgot-password";
        }

        PasswordResetToken token = tokenService.getPasswordResetToken(user);
        if (token != null){
            result.rejectValue("email", null,"Password reset token has been sent to your email already, check spam");
            return "forgot-password";
        }



        emailService.sendPasswordResetEmail(user, request);

//        PasswordResetToken token = new PasswordResetToken();
////        token.setToken(UUID.randomUUID().toString());
////        token.setUser(user);
////        token.setExpiryDate(30);
////        tokenRepository.save(token);
////
////        Mail mail = new Mail();
////        mail.setFrom("no-reply@skowrondariusz.com");
////        mail.setTo(user.getEmail());
////        mail.setSubject("Password reset request");
////
////        Map<String, Object> model = new HashMap<>();
////        model.put("token", token);
////        model.put("user", user);
////        model.put("signature", "https://skowrondariusz.com");
////        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
////        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
////        mail.setModel(model);
////        emailService.sendEmail(mail);

        return "redirect:/forgot-password?success";

    }

}