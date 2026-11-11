package com.wd_aljak.employee_management.abstracts;

import com.wd_aljak.employee_management.dtos.LeaveRequestCreateDTO;
import com.wd_aljak.employee_management.model.LeaveRequest;

import java.util.List;
import java.util.UUID;

public interface LeaveRequestService {
    LeaveRequest createOne(LeaveRequestCreateDTO leaveRequest, UUID employeeId);

    List<LeaveRequest> findAllByEmployee_Id(UUID id);
}
