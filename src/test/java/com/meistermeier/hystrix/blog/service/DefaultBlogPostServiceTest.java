package com.meistermeier.hystrix.blog.service;

import com.meistermeier.hystrix.blog.domain.BlogPost;
import com.meistermeier.hystrix.blog.persistence.BlogPostRepository;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

public class DefaultBlogPostServiceTest {

    private HystrixRequestContext hystrixRequestContext;

    @Before
    public void setup() {
        hystrixRequestContext = HystrixRequestContext.initializeContext();
    }

    @After
    public void tearDown() {
        hystrixRequestContext.shutdown();
    }

    @Test
    public void testCollapsing() {
        DefaultBlogPostService blogPostService = new DefaultBlogPostService(new BlogPostRepository());
        List<Future<BlogPost>> blogPosts = blogPostService.getBlogPosts(Arrays.asList(1, 2, 3));
        waitUntilFinished(blogPosts);
    }

    private void waitUntilFinished(List<Future<BlogPost>> blogPosts) {
        boolean done = false;
        while(!done) {
            for (Future<BlogPost> blogPost : blogPosts) {
                done = blogPost.isDone();
            }
        }
    }

}