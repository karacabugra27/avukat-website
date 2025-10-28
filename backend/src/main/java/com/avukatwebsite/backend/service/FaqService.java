package com.avukatwebsite.backend.service;

import com.avukatwebsite.backend.dto.request.RequestFaq;
import com.avukatwebsite.backend.dto.response.ResponseFaq;

import java.util.List;

public interface FaqService {

    ResponseFaq createFaq(RequestFaq dto);

    List<ResponseFaq> getAllFaq();

    ResponseFaq getFaqById(Long id);

    void delete(Long id);

    ResponseFaq update(Long id, RequestFaq dto);

}
