package com.orange.mail.service;

import com.orange.common.utils.Result;
import com.orange.mail.entity.Mail;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/18 19:18
 * @description:
 */
public interface MailService {

    /**
     * @description: 发送普通邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/18 19:19
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    public Result<?> sendCommonMail(Mail mail);
    /**
     * @description: 发送Html格式的邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 13:47
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    public Result<?> sendHtmlMail(Mail mail);
    /**
     * @description: 发送带静态资源的邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 13:58
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    public Result<?> sendStaticMail(Mail mail, MultipartFile multipartFile,String id);
    /**
     * @description: 发送带附件的邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 14:29
     * @param: [mail, multipartFile]
     * @return: com.orange.common.utils.Result<?>
     **/
    public Result<?> sendAttachmentMail(Mail mail, MultipartFile multipartFile);
}
