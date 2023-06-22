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

import lombok.RequiredArgsConstructor;
import telran.java47.post.dao.PostRepository;
import telran.java47.post.dto.exceptions.PostNotFoundException;
import telran.java47.post.model.Post;

@Component
@RequiredArgsConstructor
@Order(60)
public class UpdatePostByAuthorFilter implements Filter {
	
	final PostRepository postRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String path = request.getServletPath();

        if (checkEndPoint(request.getMethod(), path)) {
            Post post = postRepository.findById(path.split("/")[3]).orElseThrow(PostNotFoundException::new);
            if (post.getAuthor().equalsIgnoreCase(request.getUserPrincipal().getName())) {
                chain.doFilter(request, response);
            } else {
                response.sendError(403);
            }
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean checkEndPoint(String method, String path) {
        return method.equalsIgnoreCase("PUT") && path.matches("/forum/post/\\w+/?");
    }
	}

