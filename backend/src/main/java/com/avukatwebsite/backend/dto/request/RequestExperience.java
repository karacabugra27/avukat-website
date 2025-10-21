package com.avukatwebsite.backend.dto.request;

import com.avukatwebsite.backend.entity.Lawyer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestExperience {

    private Long lawyerId;

    private String title;

    private String description;

}
