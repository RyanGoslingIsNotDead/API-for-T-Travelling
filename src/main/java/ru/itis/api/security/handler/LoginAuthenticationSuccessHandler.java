package ru.itis.api.security.handler;

import com.nimbusds.common.contenttype.ContentType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.itis.api.dto.MessageDto;
import ru.itis.api.util.JsonUtil;

import java.io.IOException;

@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MessageDto messageDto = new MessageDto();

        response.setStatus(200);

        response.setContentType(ContentType.APPLICATION_JSON.getType());

        response.getWriter().write(JsonUtil.write(messageDto.setStatusSuccess(true).setMessage("Successfully logged in")));
    }
}
