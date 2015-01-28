package com.meistermeier.hystrix.blog.service;

import com.meistermeier.hystrix.blog.domain.BlogPost;
import com.meistermeier.hystrix.blog.persistence.BlogPostRepository;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlogPostCollapsedCommand extends HystrixCollapser<List<BlogPost>, BlogPost, Integer> {

    private final BlogPostRepository blogPostRepository;
    private final Integer entryId;

    public BlogPostCollapsedCommand(BlogPostRepository blogPostRepository, Integer entryId) {
        this.blogPostRepository = blogPostRepository;
        this.entryId = entryId;
    }

    @Override
    public Integer getRequestArgument() {
        return entryId;
    }

    @Override
    protected HystrixCommand<List<BlogPost>> createCommand(
            Collection<CollapsedRequest<BlogPost, Integer>> collapsedRequests) {

        final List<Integer> entryIds = new ArrayList<>(collapsedRequests.size());
        entryIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument)
                .collect(Collectors.toList()));

        return new BlogPostBatchCommand(blogPostRepository, entryIds);
    }

    @Override
    protected void mapResponseToRequests(List<BlogPost> batchResponse,
            Collection<CollapsedRequest<BlogPost, Integer>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<BlogPost, Integer> collapsedRequest : collapsedRequests) {
            final BlogPost blogPost = batchResponse.get(count++);
            collapsedRequest.setResponse(blogPost);
        }
    }
}
