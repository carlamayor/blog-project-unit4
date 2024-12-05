package com.teamtreehouse.blog;

import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.dao.NotFoundException;
import com.teamtreehouse.blog.dao.SimpleBlogDAO;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;

/*
Create routes for index, detail, add/edit pages, and comment posting
Ensure each route uses appropriate HTTP method (GET, POST)
Link routes to corresponding DAO methods and model operations
 */
public class Main {
    private static final String FLASH_MESSAGE_KEY = "flash_message";

    public static void main(String[] args) {
        final String ADMIN_PASSWORD = "admin";
        staticFileLocation("/public");
        BlogDao dao = new SimpleBlogDAO();
        //Adding entries to the main entries page
        BlogEntry blogEntry1 = new BlogEntry("The best day I’ve ever had","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at", "carla-boyd", "travel", "love", "trips", "women");
        BlogEntry blogEntry2 = new BlogEntry("The absolute worst day I’ve ever had","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "rhshs5362", "sports", "hobbies", "climbing", "running", "marathon");
        BlogEntry blogEntry3 = new BlogEntry("That time at the mall"," Lorem ipsum odor amet, consectetuer adipiscing elit. Nulla morbi lacus cursus donec, class curabitur tortor? Dignissim porta elit varius efficitur; curabitur faucibus. Lacinia natoque volutpat sit rhoncus risus magnis quisque torquent. Sociosqu pretium posuere, ut elementum natoque augue parturient ut litora. Class porta condimentum mattis rutrum elementum phasellus vestibulum; nisi condimentum. Hendrerit penatibus curabitur dapibus ullamcorper; nascetur lacus arcu.t", "parkdirt27563", "family", "elder", "life");
        BlogEntry blogEntry4 = new BlogEntry("Dude, where’s my car?","Lorem ipsum dolor sit amet, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", "christian-lksub", "house", "repair");
        BlogEntry blogEntry5765 = new BlogEntry("jcks", "ljkdhcl", "cskj", "skbjc", "kjshbck");
        System.out.println("TAGS IN EXAMPLE" + blogEntry5765.getTags());
        if(blogEntry5765.getTags() != null) {
            System.out.println("TAGS IN EXAMPLE" +   blogEntry5765.getTags());
            System.out.println("TAGS class" +   blogEntry5765.getTags().getClass().getName());

        } else {
            System.out.println("TAGS IN EXAMPLEare null");
        }
        dao.addEntry(blogEntry1);
        dao.addEntry(blogEntry2);
        dao.addEntry(blogEntry3);
        dao.addEntry(blogEntry4);
        //Adding comments to each entry just to have some examples.
        Comment comment1 = new Comment("Maria Rodriguez", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.");
        Comment comment2 = new Comment("Carolyn Parra", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        blogEntry1.addComment(comment1);
        blogEntry1.addComment(comment2);
        Comment comment3 = new Comment("Diego Cruz", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.");
        Comment comment4 = new Comment("Paulette Williams", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        blogEntry2.addComment(comment3);
        blogEntry2.addComment(comment4);
        Comment comment5 = new Comment("Diego Cruz", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.");
        Comment comment6 = new Comment("Paulette Williams", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        blogEntry3.addComment(comment5);
        blogEntry4.addComment(comment6);




        before( (req, res) -> {
            if (req.cookie("password") != null) {
                req.attribute("password", req.cookie("password"));
            }
        });
        //This checks that if you try to edit an entry without having the right password,
        // that it doesn't allow you to edit that page.
        before("/entries/:slug/edit", (req, res) -> {
            if ((req.cookie("password") == null || !req.cookie("password").equals(ADMIN_PASSWORD))) {
                setFlashMessage(req, "Whoops, please sign in first!");
                res.redirect("/password");
                halt(); //halt is for the program not to do anything else (stop)
                //if username exists we redirect to sign in
            }
        });

        //Checks if user password exists and is correct to be able to delete a post
        before("/entries/:slug/delete", (req, res) -> {
            if ((req.cookie("password") == null || !req.cookie("password").equals(ADMIN_PASSWORD))) {
                setFlashMessage(req, "Whoops, please sign in first!");
                res.redirect("/password");
                halt(); //halt is for the program not to do anything else (stop)
                //if username exists we redirect to sign in
            }
        });

        //This checks that if you try to edit an entry without having the right password,
        // that it doesn't allow you to edit that page.
        before("/entries/new", (req, res) -> {
            if ((req.cookie("password") == null || !req.cookie("password").equals(ADMIN_PASSWORD))) {
                setFlashMessage(req, "Whoops, please sign in first!");
                res.redirect("/password");
                halt(); //halt is for the program not to do anything else (stop)
                //if username exists we redirect to sign in
            }
        });

        //Main index page.
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntries", dao.findAllEntries());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //Creates a password page template
        get("/password", (req, res) -> {
           Map<String, String> model = new HashMap<>();
           model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model, "password.hbs");
        }, new HandlebarsTemplateEngine());

        // It sends the password to process the data to redirect the user or send an error message.
        post("/password", (req, res) -> {
            String password = req.queryParams("password");
            if (ADMIN_PASSWORD.equals(password)) {
                res.cookie("password", password);
                res.redirect("/");
            } else {
                setFlashMessage(req, "Ooops, that's not the correct password. Try again");
                res.redirect("/password");
            }
            return null;
        });

        //Adds a new entry to the entries
        get("/entries/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "new.hbs");
        }, new HandlebarsTemplateEngine());

        //this helps us create a new entry in the entries and after adding it, it redirects us to all the entries.
        post("/entries", (req, res) -> {
            String title = req.queryParams("title");
            String content = req.queryParams("content");
            String author = req.queryParams("author");
          String tagsParam = req.queryParams("tags");
            Set<String> tags = tagsParam != null
                    ? Arrays.stream(tagsParam.split(","))
                        .map(String::trim)
                        .filter(tag -> !tag.isEmpty())
                        .collect(Collectors.toSet())
                    : new HashSet<>();
            BlogEntry createBlogEntry = new BlogEntry(title, content, author, tags.toArray(new String[0]));
            dao.addEntry(createBlogEntry);
            res.redirect("/");
            return null;
        });

        //Retrieves the path of the corresponding slug.
        get("/entries/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntry", dao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());


        get("/test-route", (req, res) -> {
            return "Test return works";});

        // creates the edit page of the corresponding slug
        get("/entries/:slug/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntry", dao.findEntryBySlug(req.params("slug")));
            return new ModelAndView( model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        //Allows users to edit the Blog Entry data and saves that information.
        post("/entries/:slug/edit", (req, res) -> {
            String newTitle = req.queryParams("title");
            String newContent = req.queryParams("content");
            String newTags = req.queryParams("tags");
            BlogEntry blogEntry = dao.editEntry(req.params("slug"), newTitle, newContent, newTags);
            res.redirect("/entries/"+blogEntry.getSlug());
            return null;
        });

        //Allows users to send a coment and save it in the database.
        post("/entries/:slug/comment", (req, res) -> {
            BlogEntry blogEntry = dao.findEntryBySlug(req.params("slug"));
            String author = req.queryParams("name");
            String content = req.queryParams("comment");
            Comment comment = new Comment(author, content);
            boolean added = blogEntry.addComment(comment);
            if (added) {
                setFlashMessage(req, "Your comment was added!");
            } else {
                setFlashMessage(req, "Failed to add your comment. Please try again!");
            }
            res.redirect("/entries/" + req.params("slug"));
            return null;
        });

        post("entries/:slug/delete", (req, res) ->{
            dao.deleteEntry(req.params("slug"));
            res.redirect("/");
            return null;
        });

        exception(NotFoundException.class, (exc, req, res) -> {
            res.status(404);
            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
            String html = engine.render(
                    new ModelAndView(null, "not-found.hbs"));
            res.body(html);
        });
    }

    private static String captureFlashMessage(Request req) {
        String message = getFlashMessage(req);
        if (message != null) {
            req.session().removeAttribute(FLASH_MESSAGE_KEY);
        }
        return message;
    }
    private static String getFlashMessage(Request req) {
        if (req.session(false) == null) {
            return null;
        }
        if (!req.session().attributes().contains(FLASH_MESSAGE_KEY)) {
            return null;
        }
        return (String) req.session().attribute(FLASH_MESSAGE_KEY);
    }

    private static void setFlashMessage(Request req, String message) {
        req.session().attribute(FLASH_MESSAGE_KEY, message);
   }
}
