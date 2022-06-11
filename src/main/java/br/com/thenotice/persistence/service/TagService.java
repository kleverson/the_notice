package br.com.thenotice.persistence.service;

import br.com.thenotice.domain.request.InterestPostBody;
import br.com.thenotice.domain.response.GenericData;
import br.com.thenotice.persistence.entities.Interest;
import br.com.thenotice.persistence.entities.Tag;
import br.com.thenotice.persistence.repository.TagRepository;
import br.com.thenotice.util.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAll(String term){
        if(!Helper.isNullOrEmpty(term)) {
            return this.tagRepository.listByTerm(term);
        }else{
            return this.tagRepository.listTop(10);
        }
    }

    public GenericData addItem(InterestPostBody interestPostBody){
        try{
            Optional<Tag> interest = this.tagRepository.findByName(interestPostBody.getName());

            if(interest.isPresent()){
                return new GenericData(HttpStatus.BAD_REQUEST, "This tag alread existis", null);
            }else {

                Tag newTag = new Tag();
                newTag.setName(interestPostBody.getName());
                newTag.setSlug(this.makeSlug(interestPostBody.getName()));

                this.tagRepository.save(newTag);


                return new GenericData(HttpStatus.OK, "New tag", newTag);
            }

        }catch (Exception e){
            return new GenericData(HttpStatus.BAD_REQUEST, "New tag not created", null);
        }

    }

    private String makeSlug(String title){
        String slug = Helper.makeSlug(title);

        int countPost = this.tagRepository.countBySlugEquals(slug);

        if(countPost > 0){
            return String.format("%s-%d", slug, countPost);
        }else{
            return slug;
        }
    }
}
