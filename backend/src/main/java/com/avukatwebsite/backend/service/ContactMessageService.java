package com.avukatwebsite.backend.service;

import com.avukatwebsite.backend.dto.request.RequestContactMessage;
import com.avukatwebsite.backend.dto.response.ResponseContactMessage;

import java.util.List;

public interface ContactMessageService {

    ResponseContactMessage create(RequestContactMessage dto);

    List<ResponseContactMessage> getAll();

    void delete(Long id);

}
