package com.orange.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/18 19:11
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮件接收方，可多人
     */
    private String[] tos;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;

}
