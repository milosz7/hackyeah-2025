package com.example.Prompt2CodeDemo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Wrapper class for requests that require both a UserDto for authentication
 * and the actual request data
 */
public class UserRequestWrapper<T> {
    
    @NotNull
    @Valid
    private UserDto user;
    
    @NotNull
    @Valid
    private T data;
    
    public UserRequestWrapper() {}
    
    public UserRequestWrapper(UserDto user, T data) {
        this.user = user;
        this.data = data;
    }
    
    public UserDto getUser() {
        return user;
    }
    
    public void setUser(UserDto user) {
        this.user = user;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}
