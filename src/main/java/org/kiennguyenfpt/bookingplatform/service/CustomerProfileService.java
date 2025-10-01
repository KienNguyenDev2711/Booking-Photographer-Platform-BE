package org.kiennguyenfpt.bookingplatform.service;

import org.kiennguyenfpt.bookingplatform.dto.customer.CreateCustomerProfileRequest;
import org.kiennguyenfpt.bookingplatform.dto.customer.CustomerProfileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

public interface CustomerProfileService {
    CustomerProfileResponse updateCustomerProfile(
            String token,
            CreateCustomerProfileRequest request,
            MultipartFile avatarFile);

    CustomerProfileResponse getProfile(String token);

    List<CustomerProfileResponse> searchCustomers();
}