package com.project.shopviet.Service.ServiceImpl;

import com.project.shopviet.DTO.RegisterDto;
import com.project.shopviet.Model.Product;
import com.project.shopviet.Model.Role;
import com.project.shopviet.Model.User;
import com.project.shopviet.Repository.RoleRepository;
import com.project.shopviet.Repository.UserRepository;
import com.project.shopviet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public void addRoleToUser(int userId, int roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found!"));
        user.addRole(role);
        userRepository.save(user);
    }
    @Override
    public boolean addRegisterUser(RegisterDto registerDto) {
        try{
            User user=new User();
            Role role=roleRepository.findRoleByName(registerDto.getRoleName());
            if(userRepository.findUserByName(registerDto.getUsername())!=null){
                return false;
            }
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setFname(registerDto.getFname());
            user.setImage(registerDto.getImage());
            user.setPhone(registerDto.getPhone());
            user.setEmail(registerDto.getEmail());
            user.addRole(role);
            userRepository.save(user);
            return true;
        }catch (IllegalArgumentException e){
            System.out.println("Register User Error: "+e.getMessage());
            return false;
        }
    }

    @Override
    public String addRoleUser(String username, String name) {

        return "Thanh cong";
    }


    @Override
    public Optional<User> findUserByUsername(String username) {
        try{
            return userRepository.findUserByUsername(username);
        }catch (IllegalArgumentException e){
            System.out.println("Find User Error: "+e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> findUserByRole(Role role) {
        try {
            return userRepository.findByRolesContaining(role);
        }catch (IllegalArgumentException e){
            System.out.println("Find User Error: "+e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> findByRolesIn(Set<Role> roles) {
        try{
            return userRepository.findByRolesIn(roles);
        }catch (IllegalArgumentException e){
            System.out.println("Find User Error: "+e.getMessage());
            return null;
        }
    }

    @Override
    public String deleteUser(int id) {
        try{
            userRepository.deleteById(id);
            return "Delete User Successful";
        }catch (IllegalArgumentException e){
            return "Delete User Error: "+e.getMessage();
        }
    }

    @Override
    public User updateUser(int id, User user) {
        try {
            if (isExistById(id)) {
                User currentUser = userRepository.findById(id).get();
                currentUser.setFname(user.getFname());
                currentUser.setEmail(user.getEmail());
                currentUser.setPhone(user.getPhone());
                currentUser.setImage(user.getImage());
                currentUser.setRoles(user.getRoles());

                userRepository.save(currentUser);
                return currentUser;
            } else return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Update User Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getAllUser() {
        try{
            return (List<User>) userRepository.findAll();
        }catch (IllegalArgumentException e){
            System.out.println("Get All User Error: "+e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getUserForRole(int role_id) {
        return null;
    }

    @Override
    public Set<Role> getRoleForUser(int user_id) {
        try{
            return userRepository.getRoleFromUserId(user_id);
        }catch (IllegalArgumentException e){
            System.out.println("Get All Role for User Id Error: "+e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isExistById(int id) {
        return userRepository.existsById(id);
    }
}
