package com.stackingrule.contentcenter.controller.content;

import com.github.pagehelper.PageInfo;
import com.stackingrule.contentcenter.auth.CheckLogin;
import com.stackingrule.contentcenter.domain.dto.content.ShareDTO;
import com.stackingrule.contentcenter.domain.entity.content.Share;
import com.stackingrule.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/shares")
public class ShareController {

    private final ShareService shareService;

    @GetMapping("/{id}")
    @CheckLogin
    public ShareDTO findById(@PathVariable Integer id) {

        return this.shareService.findById(id);

    }

    @GetMapping("/q")
    public PageInfo<Share> q(@RequestParam(required = false) String title,
                             @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        if (pageSize > 100) {
            pageSize = 100;
        }

        return this.shareService.q(title, pageNo, pageSize);


    }

}
