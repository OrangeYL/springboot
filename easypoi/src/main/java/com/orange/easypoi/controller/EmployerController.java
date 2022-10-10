package com.orange.easypoi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.orange.easypoi.entity.Employer;
import com.orange.easypoi.service.EmployerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/10 9:49
 * @description:
 */
@RestController
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    /**
     * @description: Excel导入
     * @author: Li ZhiCheng
     * @date: 2022/10/10 9:55
     * @param: [file]
     * @return: java.lang.Boolean
     **/
    @PostMapping("/import")
    public Boolean importXls(@RequestParam("file") MultipartFile file){
        ImportParams params = new ImportParams();
        //表头占两行
        params.setHeadRows(2);
        List<Employer> employers=null;
        try {
            //读取表格内容
            employers = ExcelImportUtil.importExcel(file.getInputStream(), Employer.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return employerService.insertBatch(employers);
    }

    /**
     * @description:导出excel
     * @author: Li ZhiCheng
     * @date: 2022/10/10 10:10
     * @param: [response]
     * @return: void
     **/
    @RequestMapping("/export")
    public void exportXls(HttpServletResponse response){
        //查询数据
        List<Employer> employers = employerService.selectAll();
        //防止中文乱码
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String filename = null;
        try {
            filename = URLEncoder.encode("用户列表", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 文件下载方式(附件下载还是在当前浏览器打开)
        response.setHeader("Content-disposition",
                "attachment;filename=" + filename + ".xls");
        ExportParams exportParams = new ExportParams();
        exportParams.setSheetName("用户信息");
        exportParams.setTitle("员工信息");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Employer.class, employers);
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
