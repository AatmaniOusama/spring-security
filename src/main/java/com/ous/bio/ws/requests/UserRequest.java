package com.ous.bio.ws.requests;


import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class UserRequest {

    @NotBlank(message = "This field is not nullable !!")
    //@Size(min = 3, message = "This field must have at least 3 Chars !!")
    private String firstName;
    @NotNull(message = "This field is not nullable !!")
    @Size(min = 3, message = "This field must have at least 3 chars !!")
    private String lastName;
    @NotNull(message = "This field is not nullable !!")
    @Email(message = "You should respect the email format !!")
    private String email;
    @NotNull(message = "This field is not nullable !!")
    @Size(min = 8, max = 16, message = "The password must be between 8 and 1§ Chars !!")
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\]).{8,32}$")
    private String password;

    private List<AddressRequest> addresses = new ArrayList<>();
    private ContactRequest contact;

    public ContactRequest getContact() {
        return contact;
    }

    public void setContact(ContactRequest contact) {
        this.contact = contact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AddressRequest> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequest> addresses) {
        this.addresses = addresses;
    }
}