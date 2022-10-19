package com.orange.mail.controller;

import com.orange.common.utils.Result;
import com.orange.mail.entity.Mail;
import com.orange.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/18 19:26
 * @description:
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * @description: 测试发送普通邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 9:17
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    @GetMapping("/common")
    public Result<?> sendCommonMail(Mail mail){
        return mailService.sendCommonMail(mail);
    }
    /**
     * @description: 测试发送Html邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 13:57
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    @GetMapping("/Html")
    public Result<?> sendHtmlMail(Mail mail){
        return mailService.sendHtmlMail(mail);
    }
    /**
     * @description: 测试发送带静态资源的邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 13:57
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    @GetMapping("/Static")
    public Result<?> sendStaticMail(Mail mail, MultipartFile file,String id){
        return mailService.sendStaticMail(mail, file, id);
    }
    /**
     * @description: 测试发送带附件的邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 13:57
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    @GetMapping("/Attachment")
    public Result<?> sendAttachmentMail(Mail mail, MultipartFile file){
        return mailService.sendAttachmentMail(mail, file);
    }
}
