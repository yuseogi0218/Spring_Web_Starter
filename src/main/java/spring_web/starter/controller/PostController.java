package spring_web.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring_web.starter.domain.Post;
import spring_web.starter.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/main")
    public String main(Model model) {
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "main";
    }

    @GetMapping("/write")
    public String writeForm() {
        return "post/writeForm";
    }

    @PostMapping("/write")
    public String write(PostForm postForm, HttpServletRequest req) {
        Post post = new Post();

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(today);

        HttpSession session = req.getSession();

        post.setUser_id(session.getAttribute("user_id").toString());
        post.setTitle(postForm.getTitle());
        post.setBody(postForm.getBody());
        post.setDate(date);
        post.setView(postForm.getView());

        postService.write(post);
        return "redirect:/main";
    }

    @GetMapping("post/view")
    public String view(@RequestParam Long post_id, Model model) {
        Post found_post = postService.findById(post_id).get();
        model.addAttribute("post", found_post);
        return "post/view";
    }

}
