package br.com.thenotice.persistence.service;

import br.com.thenotice.domain.request.PostBody;
import br.com.thenotice.domain.response.GenericData;
import br.com.thenotice.domain.response.PostResponse;
import br.com.thenotice.persistence.entities.*;
import br.com.thenotice.persistence.enums.PostInteractions;
import br.com.thenotice.persistence.enums.PostStatus;
import br.com.thenotice.persistence.enums.PreferenceType;
import br.com.thenotice.persistence.repository.*;
import br.com.thenotice.util.Helper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.function.Predicate;

@Service
public class PostService {

    @Autowired
    private EntityManager entityManager;

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final InteractionsRepository interactionsRepository;
    private final CommentService commentService;
    private final InteractionService interactionService;
    private final UserPreferenceService userPreferenceService;

    public PostService(PostRepository postRepository, TagRepository tagRepository, InterestRepository interestRepository, UserRepository userRepository, InteractionsRepository interactionsRepository, CommentService commentService, InteractionService interactionService, UserPreferenceService userPreferenceService) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.interestRepository = interestRepository;
        this.userRepository = userRepository;
        this.interactionsRepository = interactionsRepository;
        this.commentService = commentService;
        this.interactionService = interactionService;
        this.userPreferenceService = userPreferenceService;
    }

    public Page<Post> getAll(String term, String[] tags, String[] interests, String author,Authentication authentication, Pageable pageable)
    {
        List<UserPreferences> preferences = new ArrayList<>();
        if(authentication != null && authentication.isAuthenticated()){
            preferences = this.userPreferenceService.getPrefs(authentication);
        }



        QPost post = QPost.post;
        JPAQuery query = new JPAQuery(entityManager);

        query.from(post);

        if(!Helper.isNullOrEmpty(term))
        {
            query.where(post.title.like("%"+term+"%").orAllOf(
                post.content.like("%"+term+"%"),
                post.excerpt.like("%"+term+"%")
            ));
        }

        if(!Helper.isNullOrEmpty(author)){
            query.where(post.author.uuid.eq(UUID.fromString(author)));
        }

        if(interests.length > 0){
            List<Interest> listInterest = new ArrayList<>();

            for(String uuid : interests){
                Interest itemInterest = this.interestRepository.findById(UUID.fromString(uuid)).get();
                listInterest.add(itemInterest);
            }


            query.in(post.interests.equals(listInterest));
        }else{
            List<Interest> listInterest = new ArrayList<>();
            preferences.stream().filter(item ->{
                return item.getType() == PreferenceType.INTEREST;
            }).forEach(filterItem ->{
                Interest interest = this.interestRepository.findById(filterItem.getItemId()).get();
                listInterest.add(interest);
            });

            query.in(post.tags.equals(listInterest));
        }

        if(tags.length > 0){
            List<Tag> listTags = new ArrayList<>();

            for(String uuid : tags){
                Tag tag = this.tagRepository.findById(UUID.fromString(uuid)).get();
                listTags.add(tag);
            }

            query.in(post.tags.equals(listTags));
        }else{
            List<Tag> listTags = new ArrayList<>();
            preferences.stream().filter(item ->{
                return item.getType() == PreferenceType.TAG;
            }).forEach(filterItem ->{
                Tag tag = this.tagRepository.findById(filterItem.getItemId()).get();
                listTags.add(tag);
            });

            query.in(post.tags.equals(listTags));
        }

        long total = query.fetchCount();
        List<Post> result = query.fetch();
        List<PostResponse> response = new ArrayList<>();

        result.stream().forEach(item ->{
            PostResponse postItem = new PostResponse();
            postItem.setUuid(item.getUuid());
            postItem.setTitle(item.getTitle());
            postItem.setSlug(item.getSlug());
            postItem.setExcerpt(item.getExcerpt());
            postItem.setImage(item.getImage());
            postItem.setPostStatus(item.getPostStatus());
            postItem.setInterests(item.getInterests());
            postItem.setTags(item.getTags());
            postItem.setAuthor(item.getAuthor());
            postItem.setComment(this.commentService.commnetCount(item.getUuid()));
            postItem.setInteractions(this.interactionService.getData(item.getUuid()));
            response.add(postItem);

        });


        return new PageImpl(response, pageable, total);

    }

    public Page<Post> myPosts(String term, String[] tags, String[] interests, String status, Pageable pageable, Authentication authentication)
    {
        User currentUser = this.userRepository.findByUsername(authentication.getName());
        QPost post = QPost.post;
        JPAQuery query = new JPAQuery(this.entityManager);

        query.from(post);
        query.where(post.author.uuid.eq(currentUser.getUuid()));

        if(!Helper.isNullOrEmpty(status)){
            PostStatus eStatus = PostStatus.valueOf(status);
            query.where(post.postStatus.eq(eStatus));
        }

        if(!Helper.isNullOrEmpty(term))
        {
            query.where(post.title.like("%"+term+"%").orAllOf(
                    post.content.like("%"+term+"%"),
                    post.excerpt.like("%"+term+"%")
            ));
        }


        if(interests.length > 0){
            List<Interest> listInterest = new ArrayList<>();

            for(String uuid : interests){
                Interest itemInterest = this.interestRepository.findById(UUID.fromString(uuid)).get();
                listInterest.add(itemInterest);
            }


            query.in(post.interests.equals(listInterest));
        }

        if(tags.length > 0){
            List<Tag> listTags = new ArrayList<>();

            for(String uuid : tags){
                Tag tag = this.tagRepository.findById(UUID.fromString(uuid)).get();
                listTags.add(tag);
            }

            query.in(post.tags.equals(listTags));
        }

        long total = query.fetchCount();
        List<Post> result = query.fetch();
        List<PostResponse> response = new ArrayList<>();

        result.stream().forEach(item ->{
            PostResponse postItem = new PostResponse();
            postItem.setUuid(item.getUuid());
            postItem.setTitle(item.getTitle());
            postItem.setSlug(item.getSlug());
            postItem.setExcerpt(item.getExcerpt());
            postItem.setImage(item.getImage());
            postItem.setPostStatus(item.getPostStatus());
            postItem.setInterests(item.getInterests());
            postItem.setTags(item.getTags());
            postItem.setAuthor(item.getAuthor());
            postItem.setComment(this.commentService.commnetCount(item.getUuid()));
            postItem.setInteractions(this.interactionService.getData(item.getUuid()));
            response.add(postItem);

        });


        return new PageImpl(response, pageable, total);

    }

    public Page<Post> listBy(String option, String value, Pageable pageable){

        QPost post = QPost.post;
        JPAQuery query = new JPAQuery(entityManager);

        query.from(post);

        if(option.equalsIgnoreCase("author")){
            User author = this.userRepository.findBySlug(value);

            query.where(post.author.uuid.eq(author.getUuid()));
        }

        if(option.equalsIgnoreCase("tag")){
            Tag tag = this.tagRepository.findBySlug(value);
            query.where(post.tags.contains(tag));
        }

        if(option.equalsIgnoreCase("interest")){
            Interest interest = this.interestRepository.findBySlug(value);
            query.where(post.interests.contains(interest));
        }

        long total = query.fetchCount();
        List<Post> result = query.fetch();

        return  new PageImpl<>(result, pageable, total);

    }

    public GenericData getPost(String slug){
        PostResponse postItem = new PostResponse();
        Post currentPost = this.postRepository.findBySlug(slug);

        if(null != currentPost){
            postItem.setUuid(currentPost.getUuid());
            postItem.setTitle(currentPost.getTitle());
            postItem.setSlug(currentPost.getSlug());
            postItem.setExcerpt(currentPost.getExcerpt());
            postItem.setImage(currentPost.getImage());
            postItem.setPostStatus(currentPost.getPostStatus());
            postItem.setInterests(currentPost.getInterests());
            postItem.setTags(currentPost.getTags());
            postItem.setAuthor(currentPost.getAuthor());
            postItem.setComment(this.commentService.commnetCount(currentPost.getUuid()));


            postItem.setInteractions(this.interactionService.getData(currentPost.getUuid()));

            return new GenericData(HttpStatus.OK, null, postItem);
        }else{
            return new GenericData(HttpStatus.BAD_REQUEST, "Post not exists", null);
        }
    }

    public Post addNew(PostBody postBody, Authentication authentication){
        User currentUser = this.userRepository.findByUsername(authentication.getName());


        Post post = new Post();
        post.setTitle(postBody.getTitle());
        post.setSlug(this.makeSlug(postBody.getTitle()));
        post.setExcerpt(postBody.getExcerpt());
        post.setContent(postBody.getContent());
        post.setPostStatus(postBody.getPostStatus());

        HashSet<Interest> interestHashSet = new HashSet<>();
        HashSet<Tag> tagHashSet = new HashSet<>();

        if(postBody.getInterests().size() > 0) {
            postBody.getInterests().stream().forEach(item -> {
                Interest interestItem = this.interestRepository.findById(UUID.fromString(item)).get();
                interestHashSet.add(interestItem);
            });
            post.setInterests(interestHashSet);
        }

        if(postBody.getTags().size() > 0) {
            postBody.getTags().stream().forEach(item -> {
                Tag tag = this.tagRepository.findById(UUID.fromString(item)).get();
                tagHashSet.add(tag);
            });
            post.setTags(tagHashSet);
        }

        post.setAuthor(currentUser);
        this.postRepository.save(post);

        return post;
    }

    private String makeSlug(String title){
        String slug = Helper.makeSlug(title);

        int countPost = this.postRepository.countBySlugEquals(slug);

        if(countPost > 0){
            return String.format("%s-%d", slug, countPost);
        }else{
            return slug;
        }
    }

}
