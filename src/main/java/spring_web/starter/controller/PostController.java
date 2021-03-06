package spring_web.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring_web.starter.Pagination;
import spring_web.starter.domain.Post;
import spring_web.starter.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/main")
    public String main(Model model, @RequestParam(defaultValue = "1") int page) {

        // 총 게시물 수
        int totalListCnt = postService.findAllCnt();

        // 생성 인자로 총 게시물 수, 현재 페이지를 전달
        Pagination pagination = new Pagination(page, totalListCnt);

        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        List<Post> posts = postService.findListPaging(startIndex, pageSize);


        model.addAttribute("posts", posts);
        model.addAttribute("pagination", pagination);
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

    @Transactional
    @GetMapping("post/view")
    public String view(@RequestParam Long post_id, Model model) {
        postService.view(post_id);
        Post found_post = postService.findById(post_id).get();
        model.addAttribute("post", found_post);
        return "post/view";
    }

    @Transactional
    @PostMapping("post/delete")
    public String delete(@RequestParam("post_id") Long post_id) {
        postService.delete(post_id);
        return "redirect:/main";
    }

    @GetMapping("post/update")
    public String update(@RequestParam("post_id") Long post_id, Model model) {
        Post post = postService.findById(post_id).get();
        model.addAttribute("post", post);

        return "post/updateForm";
    }

    @Transactional
    @PostMapping("post/update")
    public String update(@RequestParam("post_id") Long post_id, PostForm postForm) {
        Post post = postService.findById(post_id).get();
        post.setTitle(postForm.getTitle());
        post.setBody(postForm.getBody());

        postService.update(post_id, post);
        return "redirect:/main";
    }

}
