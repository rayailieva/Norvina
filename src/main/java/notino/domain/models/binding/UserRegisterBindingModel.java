package notino.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegisterBindingModel {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @NotNull(message = "Username cannot be null!")
    @Length(min = 3, max = 15, message = "Username must be at least 2 symbols long.")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "First name cannot be null!")
    @Length(min = 2, message = "First name must be at least 2 symbols long.")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull(message = "Last name cannot be null!")
    @Length(min = 2, message = "Last name must be at least 2 symbols long.")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull(message = "Email cannot be null.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email.")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = "Password cannot be null!")
    @Length(min = 6, message = "Password must be at least 6 characters long.")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "Confirm password cannot be null!")
    @Length(min = 6, message = "Confirm password must be at least 6 characters long.")
    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
