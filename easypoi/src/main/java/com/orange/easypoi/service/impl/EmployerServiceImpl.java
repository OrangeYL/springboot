package com.orange.easypoi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.easypoi.entity.Education;
import com.orange.easypoi.entity.Employer;
import com.orange.easypoi.mapper.EducationMapper;
import com.orange.easypoi.mapper.EmployerMapper;
import com.orange.easypoi.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/9 10:14
 * @description:
 */
@Service
public class EmployerServiceImpl extends ServiceImpl<EmployerMapper, Employer> implements EmployerService {
    @Autowired
    private EmployerMapper employerMapper;

    @Autowired
    private EducationMapper educationMapper;

    @Override
    public Boolean insertBatch(List<Employer> list) {
        employerMapper.insertBatch(list);
        for(Employer employer:list){
            List<Education> educationList = employer.getEducationList();
            for(Education education:educationList){
                education.setEmployerId(employer.getId());
            }
            educationMapper.insertBatch(educationList);
        }
        return true;
    }

    @Override
    public List<Employer> selectAll() {
        List<Employer> employers = employerMapper.selectAll();
        for(Employer employer:employers){
            List<Education> educations = educationMapper.selectByEmployerId(employer.getId());
            employer.setEducationList(educations);
        }
        return employers;
    }
}
