package br.com.thenotice.persistence.service;

import br.com.thenotice.domain.request.UserPostBody;
import br.com.thenotice.persistence.entities.User;
import br.com.thenotice.persistence.repository.UserRepository;
import br.com.thenotice.util.Helper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User existUser(String username){
        User user = this.userRepository.findByUsername(username);

        return user;
    }


    public User addUser(UserPostBody userPostBody) {
        String password = new BCryptPasswordEncoder().encode(userPostBody.getPassword());

        User newUser = new User();
        newUser.setName(userPostBody.getName());
        newUser.setUsername(userPostBody.getUsername());
        newUser.setPassword(password);
        newUser.setRemember_token(Helper.generateRandomString());
        newUser.setSlug(this.makeSlug(userPostBody.getName()));

        this.userRepository.save(newUser);

        newUser.setPassword(null);
        return newUser;

    }

    public boolean enableUser(String token){
        try{
            User user = this.userRepository.userWhereToken(token);

            user.setActive(!user.isActive());
            user.setRemember_token(Helper.generateRandomString());
            this.userRepository.save(user);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public User getUser(String username){
        return this.userRepository.findByUsername(username);
    }

    private String makeSlug(String title){
        String slug = Helper.makeSlug(title);

        int countPost = this.userRepository.countBySlugEquals(slug);

        if(countPost > 0){
            return String.format("%s-%d", slug, countPost);
        }else{
            return slug;
        }
    }
}
