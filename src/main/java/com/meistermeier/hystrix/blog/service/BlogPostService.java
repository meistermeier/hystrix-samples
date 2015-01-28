package com.meistermeier.hystrix.blog.service;

import com.meistermeier.hystrix.blog.domain.BlogPost;

import java.util.List;

public interface BlogPostService {

    BlogPost getBlogPost(Integer id);

    List<BlogPost> getBlogPosts(List<Integer> ids);

}
