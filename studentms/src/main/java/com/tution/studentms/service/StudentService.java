package com.tution.studentms.service;

import com.tution.studentms.dto.StudentDTO;
import com.tution.studentms.entity.Student;
import com.tution.studentms.repository.StudentRepo;
import com.tution.studentms.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;


    //Insert Data
    public String saveStudent(StudentDTO studentDto){
        if(studentRepo.existsById(studentDto.getStuId())){
            return VarList.RSP_DUPLICATED;
        }else{
            studentRepo.save(modelMapper.map(studentDto, Student.class));
            return VarList.RSP_SUCCESS;
        }
    }

    //Update All
    public String updateStudent(StudentDTO studentDto){
        if(studentRepo.existsById(studentDto.getStuId())){
            studentRepo.save(modelMapper.map(studentDto, Student.class));
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    //GetAll
    public List<StudentDTO> getAllStudent(){
        List<Student> student = studentRepo.findAll();
        return modelMapper.map(student, new TypeToken<List<StudentDTO>>(){}.getType());
    }

    //Search
    public List<StudentDTO> getStudentBySearch(String searchedValue){
        List<Student> student = studentRepo.getStudentBySearch(searchedValue);
        return modelMapper.map(student, new TypeToken<List<StudentDTO>>(){}.getType());
    }

    //Delete
    public String deleteStudent(int stuId){
        if(studentRepo.existsById(stuId)){
            studentRepo.deleteById(stuId);
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    //Update
    public String updateStudentById(String paidMonth, int stuId){
        if(studentRepo.existsById(stuId)){
            studentRepo.updateStudentById(paidMonth, stuId);
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

}
