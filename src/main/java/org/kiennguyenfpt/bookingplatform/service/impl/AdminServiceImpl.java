package org.kiennguyenfpt.bookingplatform.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kiennguyenfpt.bookingplatform.constant.ProfileStatus;
import org.kiennguyenfpt.bookingplatform.constant.Role;
import org.kiennguyenfpt.bookingplatform.constant.UserStatus;
import org.kiennguyenfpt.bookingplatform.entity.User;
import org.kiennguyenfpt.bookingplatform.entity.UserProfile;
import org.kiennguyenfpt.bookingplatform.repository.UserProfileRepository;
import org.kiennguyenfpt.bookingplatform.repository.UserRepository;
import org.kiennguyenfpt.bookingplatform.service.*;
import org.kiennguyenfpt.bookingplatform.util.StatusMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public void updateUserStatus(String token, BigInteger userId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserStatus newStatus = UserStatus.valueOf(status);
        user.setStatus(newStatus);
        userRepository.save(user);

        if (user.getRole() == Role.PHOTOGRAPHER) {
            UserProfile userProfile = userProfileRepository.findByUser_Id(userId)
                    .orElseThrow(() -> new RuntimeException("UserProfile not found for photographer"));

            ProfileStatus profileStatus = StatusMapper.mapUserStatusToProfileStatus(newStatus);

            userProfile.setStatus(profileStatus);
            userProfileRepository.save(userProfile);
        }
    }
}
