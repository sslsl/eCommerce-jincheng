package com.echo.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sessionId;

    @Column(unique = true)
    private String token;

    @Column(unique = true)
    private Integer userId;

    private String userType;

    private LocalDateTime sessionStartTimeStamp;

    private LocalDateTime sessionEndTimeStamp;

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public LocalDateTime getSessionStartTime() {
        return sessionStartTimeStamp;
    }

    public void setSessionStartTime(LocalDateTime sessionStartTime) {
        this.sessionStartTimeStamp = sessionStartTime;
    }

    public LocalDateTime getSessionEndTime() {
        return sessionEndTimeStamp;
    }

    public void setSessionEndTime(LocalDateTime sessionEndTime) {
        this.sessionEndTimeStamp = sessionEndTime;
    }
}

