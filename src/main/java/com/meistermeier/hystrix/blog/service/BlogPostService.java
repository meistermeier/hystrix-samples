package com.meistermeier.hystrix.blog.service;

import com.meistermeier.hystrix.blog.domain.BlogPost;

import java.util.List;
import java.util.concurrent.Future;

public interface BlogPostService {

    List<Future<BlogPost>> getBlogPosts(List<Integer> ids);

}
