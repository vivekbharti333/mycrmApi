package com.spring.object.response;

import java.util.Date;

import lombok.Data;

@Data
public class AttendanceStatusResponse {
    private Date date;
    private String status;      // Present / Absent
    private String punchInTime;
    private String punchOutTime;
    private String punchInLocation;
    private String punchOutLocation;
}
