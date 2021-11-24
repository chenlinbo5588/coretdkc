package com.clb.componet;

import com.clb.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class UserDao {

        public UserService userService;
}
