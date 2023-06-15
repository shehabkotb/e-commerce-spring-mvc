package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.enums.Role;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.service.AdminService;
import com.vodafone.ecommerce.service.impl.AdminServiceImpl;
import org.apache.catalina.startup.UserConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AdminService.class)
class AdminServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
//    @Mock
//    private UserConfig userConfig;



    @InjectMocks
    private AdminServiceImpl adminService;


    @Test
    void saveAdminTest_saveAdmin_returnAdmin() {
        // Arrange

        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .email("he@example.com")
                .role(Role.ADMIN)
                .username("he")
                .build();
        UserDto user2 = UserDto.builder()
                .id(1L)
                .email("he@example.com")
                .role(Role.ADMIN)
                .username("he")
                .build();


        // Act
        when(userRepository.save(any(UserEntity.class))).thenReturn(user1);

        // Call savAdmin() method of the adminService object
        UserDto userDto = adminService.saveAdmin(user2);
        // Assert
        assertThat(userDto).isNotNull();
        assertEquals(user1.getId(), userDto.getId());
        assertEquals(user1.getUsername(), userDto.getUsername());
        assertEquals(user1.getRole(), userDto.getRole());
    }
    @Test
     void getAllAdminTest_addAdmin_returnListOfAdmin() {
        // Arrange
        List<UserEntity> entityList = new ArrayList<>();

        UserEntity user1 = UserEntity.builder()
                .email("he@example.com")
                .role(Role.ADMIN)
                .username("he")
                .build();

        UserEntity user2 = UserEntity.builder()
                .email("hema@example.com")
                .role(Role.ADMIN)
                .username("hema")
                .build();

        entityList.add(user1);
        entityList.add(user2);

       // Act
        when(userRepository.findByRole(Role.ADMIN)).thenReturn(entityList);

        // Call the getAllAdmin() method of the adminService object
        List<UserDto> dtoList = adminService.getAllAdmin();

        // Assert
        assertEquals(2, dtoList.size());
        assertEquals("he", dtoList.get(0).getUsername());
        assertEquals("hema", dtoList.get(1).getUsername());
    }

    @Test
    void findAdminByIdTest_findAdminById_returnAdminWithId(){
        //Arrange
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .email("hema@example.com")
                .role(Role.ADMIN)
                .username("hema")
                .build();

        //Act
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        UserDto user=adminService.findAdminById(1L);

        //Assert
        assertNotNull(user);
        assertEquals(user.getEmail(),user1.getEmail());
        assertEquals(user.getUsername(),user1.getUsername());
        assertEquals(user.getRole(),user1.getRole());

    }
    @Test
    void updateAdminTest_updateAdminNotExist_throwNotFound(){
        //Arrange
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .email("hema@example.com")
                .role(Role.ADMIN)
                .username("hema")
                .build();
        UserDto user2 = UserDto.builder()
                .id(1L)
                .email("he@example.com")
                .role(Role.ADMIN)
                .username("he")
                .build();
        //Act
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());

        //Assert
        assertThrows(NotFoundException.class, () -> adminService.updateAdmin(user2,user2.getId()));
    }

    @Test
    void updateAdminTest_updateAdminExistByEmail_throwDataIntegrityViolationException(){
        //Arrange
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .email("hema@example.com")
                .role(Role.ADMIN)
                .username("hema")
                .build();
        UserDto user2 = UserDto.builder()
                .id(1L)
                .email("he@example.com")
                .role(Role.ADMIN)
                .username("he")
                .build();
        //Act
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.existsByEmail(user2.getEmail())).thenReturn(true);


        //Assert
        assertThrows(DataIntegrityViolationException.class, () -> adminService.updateAdmin(user2,user1.getId()));
    }
    @Test
    void updateAdminTest_updateAdminExistByUserName_throwDataIntegrityViolationException(){
        //Arrange
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .email("hema@example.com")
                .role(Role.ADMIN)
                .username("hema")
                .build();
        UserDto user2 = UserDto.builder()
                .id(1L)
                .email("he@example.com")
                .role(Role.ADMIN)
                .username("he")
                .build();
        //Act
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.existsByusername(user2.getUsername())).thenReturn(true);


        //Assert
        assertThrows(DataIntegrityViolationException.class, () -> adminService.updateAdmin(user2,user1.getId()));
    }


    @Test
    void deleteAdminTest_deleteAdminNotExist_throwNotFound(){
        //Arrange
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .email("hema@example.com")
                .role(Role.ADMIN)
                .username("hema")
                .build();
        //Act
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());

        //Assert
        assertThrows(NotFoundException.class, () -> adminService.deleteAdmin(user1.getId()));
    }

    @Test
     void deleteAdminTest_deleteAdmin_returnNothing() {
        // Arrange
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .email("hema@example.com")
                .role(Role.ADMIN)
                .username("hema")
                .build();
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        // Act
        adminService.deleteAdmin(user1.getId());

        // Assert
        verify(userRepository,times(1)).deleteById(user1.getId());

    }

}
