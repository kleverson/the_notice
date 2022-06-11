package br.com.thenotice.persistence.service;

import br.com.thenotice.domain.request.InterestPostBody;
import br.com.thenotice.domain.response.GenericData;
import br.com.thenotice.persistence.entities.Interest;
import br.com.thenotice.persistence.repository.InterestRepository;
import br.com.thenotice.util.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterestService {

    private InterestRepository interestRepository;

    public InterestService(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    public List<Interest> getAll(String term){
        if(!Helper.isNullOrEmpty(term)) {
            return this.interestRepository.listByTerm(term);
        }else{
            return this.interestRepository.listTop(10);
        }
    }

    public GenericData addItem(InterestPostBody interestPostBody){
        try{
            Optional<Interest> interest = this.interestRepository.findByName(interestPostBody.getName());

            if(interest.isPresent()){
                return new GenericData(HttpStatus.BAD_REQUEST, "This interest alread existis", null);
            }else {

                Interest newInterest = new Interest();
                newInterest.setName(interestPostBody.getName());
                newInterest.setSlug(this.makeSlug(interestPostBody.getName()));

                this.interestRepository.save(newInterest);


                return new GenericData(HttpStatus.OK, "New interest", newInterest);
            }

        }catch (Exception e){
            return new GenericData(HttpStatus.BAD_REQUEST, "New interest not created", null);
        }

    }

    private String makeSlug(String title){
        String slug = Helper.makeSlug(title);

        int countPost = this.interestRepository.countBySlugEquals(slug);

        if(countPost > 0){
            return String.format("%s-%d", slug, countPost);
        }else{
            return slug;
        }
    }

}
