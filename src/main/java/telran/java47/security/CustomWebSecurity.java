package telran.java47.security;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java47.accounting.dao.UserAccountRepository;
import telran.java47.accounting.model.UserAccount;
import telran.java47.post.dao.PostRepository;
import telran.java47.post.model.Post;

@Service("customSecurity")
@RequiredArgsConstructor
public class CustomWebSecurity {
	
	final PostRepository postRepository;
	final UserAccountRepository userAccountRepository;
	
	public boolean checkPostAuthor(String postId, String user) {
		Post post =  postRepository.findById(postId).orElse(null);
		return post != null && user.equalsIgnoreCase(post.getAuthor());
	}
	
	public boolean checkPasswordExpired(String user) {
		UserAccount userAccount = userAccountRepository.findById(user).orElse(null);
		return userAccount != null && userAccount.getPasswordWillExpire() != null && userAccount.getPasswordWillExpire().isAfter(LocalDateTime.now());
	}
}
