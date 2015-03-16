package com.meistermeier.hystrix.blog.service;

import com.meistermeier.hystrix.blog.domain.BlogPost;
import com.meistermeier.hystrix.blog.persistence.BlogPostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class DefaultBlogPostService implements BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public DefaultBlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public List<Future<BlogPost>> getBlogPosts(List<Integer> ids) {
        List<Future<BlogPost>> results = new ArrayList<>(ids.size());
        results.addAll(
                ids.stream()
                        .map(id -> new BlogPostCollapsedCommand(blogPostRepository, id).queue())
                        .collect(Collectors.toList())
        );
        return results;
    }
}
