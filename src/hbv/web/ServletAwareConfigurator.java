package hbv.web;
import jakarta.websocket.*;
import jakarta.websocket.server.*;
import jakarta.servlet.http.*;
import java.util.logging.*;
import java.io.*;

public class ServletAwareConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
      Object httpSessionObject = request.getHttpSession();
      if(httpSessionObject instanceof HttpSession httpSession){
        Logger.getLogger("default").info("session:"+httpSession);
        Logger.getLogger("default").info("user:"+httpSession.getAttribute("user"));
      }
    }
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpoint = super.getEndpointInstance(endpointClass);
        return endpoint;
    }

}
