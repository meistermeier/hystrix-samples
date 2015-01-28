package com.meistermeier.hystrix.blog.persistence;

import com.meistermeier.hystrix.blog.domain.BlogPost;

import java.util.ArrayList;
import java.util.List;

public class BlogPostRepository {

    public List<BlogPost> loadAll(List<Integer> ids) {
        return new ArrayList<>();
    }

    public BlogPost load(Integer id) {
        return new BlogPost();
    }
}
