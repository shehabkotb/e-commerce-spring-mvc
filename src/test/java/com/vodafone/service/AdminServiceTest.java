package com.vodafone.service;

import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.enums.Role;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.service.AdminService;
import com.vodafone.ecommerce.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AdminService.class)
class AdminServiceTest {
    @Mock
    UserRepository userRepository;

    AdminService adminService;
    @BeforeEach
    void setup(){
         adminService=new AdminServiceImpl(userRepository);
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
    void deleteAdminTest_deleteAdmin_returnNoting(){
        //Arrange
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .email("hema@example.com")
                .role(Role.ADMIN)
                .username("hema")
                .build();
        //Act
        doNothing().when(userRepository).deleteById(user1.getId());
        adminService.delete(user1.getId());

        //Assert
        verify(userRepository, times(1)).deleteById(user1.getId());

    }
}
