package com.tution.studentms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {
    private int stuId;
    private String stuName;
    private String stuMobile;
    private String stuPaidMonth;
}
