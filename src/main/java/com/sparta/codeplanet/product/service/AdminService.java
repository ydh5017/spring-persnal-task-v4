package com.sparta.codeplanet.product.service;

import com.sparta.codeplanet.global.enums.Status;
import com.sparta.codeplanet.product.dto.CompanyRequestDto;
import com.sparta.codeplanet.product.dto.CompanyResponseDto;
import com.sparta.codeplanet.product.entity.Company;
import com.sparta.codeplanet.product.entity.User;
import com.sparta.codeplanet.product.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CompanyRepository companyRepository;

    public CompanyResponseDto createCompany(CompanyRequestDto requestDto, User user) {

        Company company = Company.builder()
                .name(requestDto.getName())
                .domain(requestDto.getDomain())
                .status(Status.ACTIVE)
                .build();

        Company saveCompany = companyRepository.save(company);

        return new CompanyResponseDto(saveCompany);
    }
}
