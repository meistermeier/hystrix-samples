package com.meistermeier.hystrix.blog.persistence;

import com.meistermeier.hystrix.blog.domain.BlogPost;

import java.util.ArrayList;
import java.util.List;

public class BlogPostRepository {

    public List<BlogPost> loadAll(List<Integer> ids) {
        ArrayList<BlogPost> blogPosts = new ArrayList<>(ids.size());
        for (Integer ignored : ids) {
            BlogPost blogPost = new BlogPost();
            blogPosts.add(blogPost);
        }
        return blogPosts;
    }

}
