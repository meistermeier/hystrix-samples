package com.meistermeier.hystrix.blog.service;

import com.meistermeier.hystrix.blog.domain.BlogPost;
import com.meistermeier.hystrix.blog.persistence.BlogPostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultBlogPostService implements BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public DefaultBlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public BlogPost getBlogPost(Integer id) {
        return new BlogPostCollapsedCommand(blogPostRepository, id).execute();
    }

    @Override
    public List<BlogPost> getBlogPosts(List<Integer> ids) {
        List<BlogPost> results = new ArrayList<>(ids.size());
        results.addAll(
                ids.stream()
                        .map(id -> new BlogPostCollapsedCommand(blogPostRepository, id).execute())
                        .collect(Collectors.toList())
        );
        return results;
    }
}
