package com.orange.mail.service.impl;

import com.orange.common.exception.BizException;
import com.orange.common.utils.Result;
import com.orange.common.utils.ResultEnum;
import com.orange.mail.entity.Mail;
import com.orange.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/18 19:20
 * @description:
 */
@Service
public class MailServiceImpl implements MailService {

    //获取发送方
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * @description: 发送普通邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/18 19:25
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    @Override
    public Result<?> sendCommonMail(Mail mail) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //设置发送方
        message.setFrom(from);
        //设置接收方
        message.setTo(mail.getTos());
        //设置标题
        message.setSubject(mail.getSubject());
        //设置内容
        message.setText(mail.getContent());
        try {
            mailSender.send(message);
            return Result.success(ResultEnum.SUCCESS);
        } catch (MailException e) {
            throw new BizException("发送失败",e);
        }
    }

    /**
     * @description: 发送Html格式的邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 13:47
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    @Override
    public Result<?> sendHtmlMail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(mail.getTos());
            messageHelper.setSubject(mail.getSubject());
            StringBuilder sb = new StringBuilder();
            sb.append("<h1>Html测试邮件</h1>")
                    .append("\"<p style='color:#F00'>你是真的太棒了！</p>")
                    .append("<p style='text-align:right'>右对齐</p>")
                    .append("<p"+mail.getContent()+"</p>");
           messageHelper.setText(sb.toString(), true);
           mailSender.send(mimeMessage);
           return Result.success(ResultEnum.SUCCESS);
        } catch (MessagingException e) {
            throw new BizException("发送失败",e);
        }
    }

    /**
     * @description: 发送带静态资源的邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 13:58
     * @param: [mail]
     * @return: com.orange.common.utils.Result<?>
     **/
    @Override
    public Result<?> sendStaticMail(Mail mail,MultipartFile multipartFile,String id) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(mail.getTos());
            messageHelper.setSubject(mail.getSubject());
            String content =
                    "<html><body><img width='250px' src=\'cid:" + id + "\'>" + mail.getContent()
                            + "</body></html>";
            messageHelper.setText(content,true);
            //将multipartFile转换成file
            File file = MultipartFileToFile(multipartFile);
            FileSystemResource resource = new FileSystemResource(file);
            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            messageHelper.addInline(id,resource);
            mailSender.send(mimeMessage);
            return Result.success(ResultEnum.SUCCESS);
        } catch (MessagingException e) {
            throw new BizException("发送失败",e);
        }
    }

    /**
     * @description: 发送带附件的邮件
     * @author: Li ZhiCheng
     * @date: 2022/10/19 14:29
     * @param: [mail, multipartFile]
     * @return: com.orange.common.utils.Result<?>
     **/
    @Override
    public Result<?> sendAttachmentMail(Mail mail, MultipartFile multipartFile) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(mail.getTos());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getContent(),true);
            File file = MultipartFileToFile(multipartFile);
            FileSystemResource resource = new FileSystemResource(file);
            String filename = resource.getFilename();
            messageHelper.addAttachment(filename,resource);
            mailSender.send(mimeMessage);
            return Result.success(ResultEnum.SUCCESS);
        } catch (MessagingException e) {
            throw new BizException("发送失败",e);
        }
    }

    /**
     * @description: 将multipartFile转换成file
     * @author: Li ZhiCheng
     * @date: 2022/10/19 14:22
     * @param: [multiFile]
     * @return: java.io.File
     **/
    private File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
