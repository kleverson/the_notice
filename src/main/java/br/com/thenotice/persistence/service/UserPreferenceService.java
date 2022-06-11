package br.com.thenotice.persistence.service;

import br.com.thenotice.domain.request.PreferencePostBody;
import br.com.thenotice.persistence.entities.User;
import br.com.thenotice.persistence.entities.UserPreferences;
import br.com.thenotice.persistence.enums.PreferenceType;
import br.com.thenotice.persistence.repository.PreferenceRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final UserService userService;

    public UserPreferenceService(PreferenceRepository preferenceRepository, UserService userService) {
        this.preferenceRepository = preferenceRepository;
        this.userService = userService;
    }

    public List<UserPreferences> addPrefs(PreferencePostBody preferencePostBody, Authentication authentication){
        User currentUser = this.userService.getUser(authentication.getName());


        if(preferencePostBody.getInterests().size() > 0){

            preferencePostBody.getInterests().forEach(item ->{
                UserPreferences prefs = new UserPreferences();
                prefs.setType(PreferenceType.INTEREST);
                prefs.setUserId(currentUser.getUuid());
                this.preferenceRepository.save(prefs);
            });
        }

        if(preferencePostBody.getTags().size() > 0){
            preferencePostBody.getTags().forEach(item ->{
                UserPreferences prefs = new UserPreferences();
                prefs.setType(PreferenceType.TAG);
                prefs.setUserId(currentUser.getUuid());
                this.preferenceRepository.save(prefs);
            });
        }

        return this.preferenceRepository.findByUserId(currentUser.getUuid());
    }

    public List<UserPreferences> update(PreferencePostBody preferencePostBody, Authentication authentication){
        User currentUser = this.userService.getUser(authentication.getName());
        List<UserPreferences> listprefs  = this.preferenceRepository.findByUserId(currentUser.getUuid());

        listprefs.stream().forEach(item ->{
            this.preferenceRepository.deleteById(item.getItemId());
        });

        return this.addPrefs(preferencePostBody, authentication);

    }

    public List<UserPreferences> getPrefs(Authentication authentication){
        User currentUser = this.userService.getUser(authentication.getName());
        return this.preferenceRepository.findByUserId(currentUser.getUuid());
    }

}
