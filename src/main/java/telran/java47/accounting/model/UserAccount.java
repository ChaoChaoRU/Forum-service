package telran.java47.accounting.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Document(collection = "users")
public class UserAccount {
	@Id
	String login;
	@Setter
	String firstName;
	@Setter
	String lastName;
	@Setter
	String password;
	Set<String> roles;
	@Setter
	LocalDateTime passwordWillExpire;
	
	public UserAccount() {
		roles = new HashSet<>();
		
	}
	
	public UserAccount(String login, String firstName, String lastName, String password) {
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	public boolean addRole(String role) {
		return roles.add(role);
	}
	
	public boolean removeRole(String role) {
		return roles.remove(role);
	}
	
	public void addPasswordExpiration() {
		passwordWillExpire = LocalDateTime.now().plus(60, ChronoUnit.DAYS);
	}
	
}
