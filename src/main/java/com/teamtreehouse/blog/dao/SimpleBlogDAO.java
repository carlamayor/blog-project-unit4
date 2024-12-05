package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleBlogDAO implements BlogDao{
    private List<BlogEntry> entries;


    public SimpleBlogDAO() {
        entries = new ArrayList<>();
    }

//Implement addEntry method to store new blog entries
    @Override
    public boolean addEntry(BlogEntry blogEntry) {
        return entries.add(blogEntry);
    }

//Develop findAllEntries method to retrieve all blog posts
    @Override
    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(entries);
    }

//Implement findEntryBySlug to fetch a specific blog entry
    @Override
    public BlogEntry findEntryBySlug(String slug) {
        return entries.stream()
                .filter(entries-> entries.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    //Implement editEntry to change/update a specific blog entry
    @Override
    public BlogEntry editEntry(String slug, String newTitle, String newContent,String newTags) {
        BlogEntry blogEntry = findEntryBySlug(slug);
        if (blogEntry != null) {
            blogEntry.setSlug(slug);
            blogEntry.setTitle(newTitle);
            blogEntry.setContent(newContent);
            if (newTags != null && !newTags.trim().isEmpty()){
                Set<String> tagSet = Arrays.stream(newTags.split(","))
                        .map(String::trim)
                        .filter(tag -> !tag.isEmpty())
                        .collect(Collectors.toSet());
                blogEntry.setTags(tagSet);
            } else {
                blogEntry.setTags(new HashSet<>());
            }

        }
        return blogEntry;
    }

    @Override
    public void deleteEntry(String slug) {
        BlogEntry blogEntry = findEntryBySlug(slug);
        if (blogEntry != null) {
            entries.remove(blogEntry);
        }

    }
}
