package com.tution.studentms.controller;

import com.tution.studentms.dto.ResponseDTO;
import com.tution.studentms.dto.StudentDTO;
import com.tution.studentms.service.StudentService;
import com.tution.studentms.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/studentms")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ResponseDTO responseDto;

    private String res, message, code;
    private Object content;
    private HttpStatus httpStatus;

    //save
    @PostMapping("/saveStudent")
    public ResponseEntity saveStudent(@RequestBody StudentDTO studentDto){
        try{
            res = studentService.saveStudent(studentDto);
            if(res.equals("00")){
                message    = "Success";
                content    = studentDto;
                code       = VarList.RSP_SUCCESS;
                httpStatus = HttpStatus.ACCEPTED;
            }else if(res.equals("06")){
                message    = "Data is Already Available";
                content    = studentDto;
                code       = VarList.RSP_DUPLICATED;
                httpStatus = HttpStatus.BAD_REQUEST;
            }else{
                message    = "Error";
                content    = null;
                code       = VarList.RSP_ERROR;
                httpStatus = HttpStatus.BAD_REQUEST;
            }

            responseDto.setMessage(message);
            responseDto.setCode(code);
            responseDto.setContent(content);
            return new ResponseEntity(responseDto, httpStatus);

        }catch(Exception e){
            responseDto.setMessage("Error");
            responseDto.setContent(null);
            responseDto.setCode(VarList.RSP_ERROR);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update
    @PutMapping("updateStudent")
    public ResponseEntity updateStudent(@RequestBody StudentDTO studentDto){
        try{
            res     = studentService.updateStudent(studentDto);
            content = studentDto;
            if(res.equals("00")){
                message    = "Success";
                code       = VarList.RSP_SUCCESS;
                httpStatus = HttpStatus.ACCEPTED;
            }else if(res.equals("01")){
                message    = "Do Not Found";
                code       = VarList.RSP_NO_DATA_FOUND;
                httpStatus = HttpStatus.ACCEPTED;
            }else{
                message    = "Error";
                code       = VarList.RSP_ERROR;
                httpStatus = HttpStatus.BAD_REQUEST;
            }

            responseDto.setContent(content);
            responseDto.setMessage(message);
            responseDto.setCode(code);
            return new ResponseEntity(responseDto, httpStatus);

        }catch(Exception e){
            responseDto.setContent(null);
            responseDto.setMessage(e.getMessage());
            responseDto.setCode(VarList.RSP_ERROR);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update by id
    @PutMapping("/updateById/{paidMonth}/{stuId}")
    public ResponseEntity updateStudentById(@PathVariable String paidMonth, @PathVariable int stuId){
        try{
            res = studentService.updateStudentById(paidMonth, stuId);


            if(res.equals("00")){
                message    = "Success";
                code       = VarList.RSP_SUCCESS;
                httpStatus = HttpStatus.ACCEPTED;
            }else if(res.equals("06")){
                message    = "Data Not Found";
                code       = VarList.RSP_NO_DATA_FOUND;
                httpStatus = HttpStatus.ACCEPTED;
            }else{
                message    = "Error";
                content    = null;
                code       = VarList.RSP_ERROR;
                httpStatus = HttpStatus.BAD_REQUEST;
            }

            responseDto.setMessage(message);
            responseDto.setContent(content);
            responseDto.setCode(code);

            return new ResponseEntity(responseDto, httpStatus);


        }catch(Exception e){
            responseDto.setContent(null);
            responseDto.setMessage(e.getMessage());
            responseDto.setCode(VarList.RSP_ERROR);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get all student
    @GetMapping("/getAllStudent")
    public ResponseEntity getAllStudent(){
        try{
            List<StudentDTO> student = studentService.getAllStudent();
            responseDto.setContent(student);
            responseDto.setMessage("Success");
            responseDto.setCode(VarList.RSP_SUCCESS);
            return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);

        }catch(Exception e){
            responseDto.setContent(null);
            responseDto.setMessage(e.getMessage());
            responseDto.setCode(VarList.RSP_ERROR);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //search
    @GetMapping("/search/{searchedValue}")
    public ResponseEntity getStudentBySearch(@PathVariable String searchedValue){
        try{
            List<StudentDTO> student = studentService.getStudentBySearch(searchedValue);
            if(student.isEmpty()){
                responseDto.setContent(null);
                responseDto.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDto.setMessage("No Data Found");
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setContent(student);
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Success");
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }


        }catch(Exception e){
            responseDto.setContent(null);
            responseDto.setMessage(e.getMessage());
            responseDto.setCode(VarList.RSP_ERROR);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete
    @DeleteMapping("/studentDelete/{stuId}")
    public ResponseEntity deleteStudent(@PathVariable String stuId){
        try{
            res     = studentService.deleteStudent(Integer.parseInt(stuId));
            content = res;

            if(res.equals("00")){
                message    = "Success";
                code       = VarList.RSP_SUCCESS;
                httpStatus = HttpStatus.ACCEPTED;
            }else if(res.equals("06")){
                message    = "Data Not Found";
                code       = VarList.RSP_NO_DATA_FOUND;
                httpStatus = HttpStatus.BAD_REQUEST;
            }else{
                message    = "Error";
                code       = VarList.RSP_ERROR;
                httpStatus = HttpStatus.BAD_REQUEST;
            }

            responseDto.setMessage(message);
            responseDto.setContent(content);
            responseDto.setCode(code);
            return new ResponseEntity(responseDto, httpStatus);

        }catch(Exception e){
            responseDto.setContent(null);
            responseDto.setMessage(e.getMessage());
            responseDto.setCode(VarList.RSP_ERROR);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }








}
