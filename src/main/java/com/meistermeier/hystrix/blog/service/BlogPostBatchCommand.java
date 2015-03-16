package com.meistermeier.hystrix.blog.service;

import com.meistermeier.hystrix.blog.domain.BlogPost;
import com.meistermeier.hystrix.blog.persistence.BlogPostRepository;
import com.netflix.hystrix.HystrixCommand;

import java.util.List;

import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;

public class BlogPostBatchCommand extends HystrixCommand<List<BlogPost>> {

    private final BlogPostRepository blogPostRepository;
    private final List<Integer> entryIds;

    public BlogPostBatchCommand(BlogPostRepository blogPostRepository, List<Integer> entryIds) {
        super(Setter.withGroupKey(asKey("BlogPost Command")));
        this.blogPostRepository = blogPostRepository;
        this.entryIds = entryIds;
    }

    @Override
    protected List<BlogPost> run() throws Exception {
        System.out.println(entryIds.size());
        return blogPostRepository.loadAll(entryIds);
    }
}
