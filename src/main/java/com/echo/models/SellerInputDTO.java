package com.echo.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Getter
@Setter
public class SellerInputDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sellerId;

    @NotNull
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String firstName;

    @NotNull
    @Pattern(regexp = "[A-Za-z\\s]+")
    private String lastName;

    @NotNull
    @Pattern(regexp="[A-Za-z0-9!@#$%^&*_]{8,15}", message="Please Enter a valid Password")
    private String password;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp="[0-9]{10}", message="Enter a valid Mobile Number")
    private String phone;

    @NotNull
    @Email
    @Column(unique = true)
    private String emailId;

    public Integer getSellerId(){
        return sellerId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}

