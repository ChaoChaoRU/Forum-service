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
import telran.java47.accounting.dao.UserAccountRepository;
import telran.java47.accounting.model.UserAccount;

@Component
@RequiredArgsConstructor
@Order(20)
public class AdminFilter implements Filter {
	
	final UserAccountRepository userAccountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if(checkEdpoint(request.getMethod(), request.getServletPath())) {
			UserAccount userAccount = userAccountRepository.findById(request.getUserPrincipal().getName()).orElse(null);
			if(userAccount == null || !userAccount.getRoles().contains("Administrator")) {
				response.sendError(403, "You are permitted for this action");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEdpoint(String method, String servletPath) {
		return !("PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)
				&& servletPath.matches("account/user/[a-zA-Z0-9]+?(/role/[a-zA-Z0-9]+)?/? "));
	}

}
