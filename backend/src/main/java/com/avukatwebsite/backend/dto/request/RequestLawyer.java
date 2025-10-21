package com.avukatwebsite.backend.dto.request;

import com.avukatwebsite.backend.entity.Experience;
import com.avukatwebsite.backend.entity.LawyerSchedule;
import com.avukatwebsite.backend.entity.Membership;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLawyer {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String bio;

    private List<Experience> experiences;

    private List<Membership> memberships;

    private List<LawyerSchedule> lawyerSchedule;
}
