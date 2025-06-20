package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.dtos.PasswordUpdateDTO;
import com.volmaghreb.reservation.dtos.UserManagementDTO;
import com.volmaghreb.reservation.dtos.UserProfileDTO;
import com.volmaghreb.reservation.entities.User;
import com.volmaghreb.reservation.enums.Role;
import com.volmaghreb.reservation.repositories.ReservationRepository;
import com.volmaghreb.reservation.repositories.UserRepository;
import com.volmaghreb.reservation.services.UserService;
import com.volmaghreb.reservation.utilities.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public PaginatedResponse<UserManagementDTO> getAllClients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findByRole(Role.USER, pageable);

        List<UserManagementDTO> userDTO = userPage.getContent()
                .stream()
                .map(user -> {
                    UserManagementDTO dto = new UserManagementDTO();
                    dto.setId(user.getId());
                    dto.setFirstname(user.getFirstname());
                    dto.setLastname(user.getLastname());
                    dto.setEmail(user.getEmail());
                    dto.setPhone(user.getPhone());
                    dto.setNationality(user.getNationality());
                    dto.setNumberOfReservations(reservationRepository.countByUserId(user.getId()));
                    return dto;
                })
                .toList();

        PaginatedResponse<UserManagementDTO> response = new PaginatedResponse<>();
        response.setContent(userDTO);
        response.setPageNumber(userPage.getNumber());
        response.setPageSize(userPage.getSize());
        response.setTotalElements(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());
        response.setLast(userPage.isLast());

        return response;
    }

    @Override
    public UserProfileDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new UserProfileDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPhone(),
                user.getDateOfBirth(),
                user.getNationality(),
                user.getSex(),
                user.getAddress(),
                user.getRole()
        );
    }

    @Override
    public void updateUser(String email, UserProfileDTO user) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setPhone(user.getPhone());
        existingUser.setNationality(user.getNationality());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setSex(user.getSex());
        existingUser.setAddress(user.getAddress());

        userRepository.save(existingUser);
    }

    @Override
    public boolean updatePassword(String email, PasswordUpdateDTO dto, BindingResult result) {
        User existingUser = userRepository.findByEmail(email)
                .orElse(null);

        if (existingUser == null) {
            result.reject("user.notFound", "User not found");
            return false;
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(dto.getOldPassword(), existingUser.getPassword())) {
            result.rejectValue("oldPassword", "password.mismatch", "Incorrect old password");
            return false;
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "password.confirmMismatch", "Passwords do not match");
            return false;
        }

        existingUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(existingUser);
        return true;
    }

}
