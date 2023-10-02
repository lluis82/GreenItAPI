package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Models.UserEjemplo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    List<UserEjemplo> userList;

    public UserService(){
        userList = new ArrayList<>();

        UserEjemplo user1 = new UserEjemplo("ida@mail.com","Ida");
        UserEjemplo user2 = new UserEjemplo("hans@mail.com","Hans");
        UserEjemplo user3 = new UserEjemplo("lars@mail.com","Lars");
        UserEjemplo user4 = new UserEjemplo("ben@mail.com","Ben");
        UserEjemplo user5 = new UserEjemplo("eva@mail.com","Eva");

        userList.addAll(Arrays.asList(user1,user2,user3,user4,user5));
    }

    public Optional<UserEjemplo> getUser(String email) {
        Optional<UserEjemplo> optional = Optional.empty();
        for (UserEjemplo user: userList) {
            if(email.equals(user.getEmail())){
                optional = Optional.of(user);
                return optional;
            }
        }
        return optional;
    }
}
