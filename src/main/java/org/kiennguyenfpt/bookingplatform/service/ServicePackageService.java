package org.kiennguyenfpt.bookingplatform.service;

import org.kiennguyenfpt.bookingplatform.dto.photographer.CreateServicePackageRequest;
import org.kiennguyenfpt.bookingplatform.dto.photographer.ServicePackageResponse;

import java.math.BigInteger;
import java.util.List;

public interface ServicePackageService {
    ServicePackageResponse createServicePackage(String token, CreateServicePackageRequest request);

    void deleteServicePackage(String token, BigInteger packageId);

    List<ServicePackageResponse> getMyServicePackages(String token, Boolean isActive);

    ServicePackageResponse getServicePackageById(BigInteger packageId);

    List<ServicePackageResponse> getAll();

}
