package com.teamtreehouse.blog.model;


import com.github.slugify.Slugify;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//title, content, date,etc
public class BlogEntry {
    private String title;
    private String content;
    private LocalDateTime date;
    private String author;
    private List<Comment> comments;
    private Set<String> tags;
    private String slug;


    public BlogEntry (String title, String content, String author,String ...tags) {

        this.title = title;
        this.content = content;
        this.date = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.tags = tags!= null ? new HashSet<>(Arrays.asList(tags)): new HashSet<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getAuthor() {
        return author;
    }


    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(formatter);
    }

    public String getTitle() {
        return title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public boolean addComment(Comment comment) {
        if (comment == null) {
            return false;
        } comments.add(comment);
        return true;
    }

    public Object getSlug() {
        return slug;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags!= null ? new HashSet<>(tags): new HashSet<>();
    }

    public Set<String> getTags() {
        return tags;
    }
}
