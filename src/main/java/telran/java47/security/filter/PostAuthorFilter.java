package telran.java47.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(50)
public class PostAuthorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String path = request.getServletPath();
		
		if (checkEndPoint(request.getMethod(), path)) {
            if (path.lastIndexOf(request.getUserPrincipal().getName()) == -1) {
                response.sendError(403);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean checkEndPoint(String method, String path) {
        return (method.equalsIgnoreCase("POST") && path.matches("/forum/post/\\w+/?"))
                || (method.equalsIgnoreCase("PUT") && path.matches("/forum/post/\\w+/comment/\\w+/?"));
    }
}
