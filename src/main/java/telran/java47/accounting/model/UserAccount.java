package telran.java47.accounting.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Document(collection = "users")
public class UserAccount {
	private final static int DAYS_EXPIRED = 60;
	
	@Id
	String login;
	@Setter
	String password;
	@Setter
	String firstName;
	@Setter
	String lastName;
	Set<UserRole> roles;
	@Setter
	LocalDate passwordDate;

	public UserAccount() {
		roles = new HashSet<>();
		passwordDate = LocalDate.now();
	}	
	
	public UserAccount(String login, String password, String firstName, String lastName) {
		this();
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public boolean addRole(UserRole role) {
		return roles.add(role);
	}
	
	public boolean removeRole(UserRole role) {
		return roles.remove(role);
	}
	
	public boolean isPasswordExpired() {
		return passwordDate.isBefore(LocalDate.now().minusDays(DAYS_EXPIRED));
	}

}
