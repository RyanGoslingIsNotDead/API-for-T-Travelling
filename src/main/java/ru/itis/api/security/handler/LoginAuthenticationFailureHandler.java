package ru.itis.api.security.handler;

import com.nimbusds.common.contenttype.ContentType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ru.itis.api.dto.MessageDto;
import ru.itis.api.util.JsonUtil;

import java.io.IOException;

@Component("loginAuthenticationFailureHandler")
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        MessageDto messageDto = new MessageDto();

        response.setStatus(401);

        response.setContentType(ContentType.APPLICATION_JSON.getType());

        response.getWriter().write(JsonUtil.write(messageDto.setStatusSuccess(false).setMessage("Unauthorized")));

    }
}
