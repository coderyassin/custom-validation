package com.yascode.customvalidation.payload;

import com.yascode.customvalidation.validation.PasswordMatching;
import com.yascode.customvalidation.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm Password must be matched!"
)
@Data
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 50)
    @Email
    private String email;

    @StrongPassword
    private String password;

    private String confirmPassword;

}
