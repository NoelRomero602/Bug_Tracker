package com.Bug_Tracker.Controller;

import com.Bug_Tracker.Model.*;
import com.Bug_Tracker.constant.SecurityConstant;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.repository.AdminRepository;
import com.Bug_Tracker.repository.UserRepository;
import com.Bug_Tracker.service.AdminService;
import com.Bug_Tracker.service.UserService;
import com.Bug_Tracker.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path ={"/","adminPortal"})
// @CrossOrigin("http://localost:4200")
public class AdminController {
  //  @Autowired
   private UserService userService;
  //  @Autowired
   private JWTTokenProvider jwtTokenProvider;
  //  @Autowired
   private AuthenticationManager test;
   // @Autowired
   private AdminService adminService;
   // @Autowired
   private AdminRepository adminRepository;
 //   @Autowired
   private UserRepository userRepository;

 @Autowired // we can autowire userserivce because the implementation uses @service annotation
    public AdminController(UserService userService, JWTTokenProvider jwtTokenProvider, @Qualifier("auth2") AuthenticationManager test, UserRepository userRepository, AdminService adminService, AdminRepository adminRepository){
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.test = test;
        this.userRepository = userRepository;
        this.adminService = adminService;
        this.adminRepository = adminRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody Admin user) throws Exception, UsernameNotFoundException, UsernameExistException, EmailExistException  {
        Admin newUser = adminService.register(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword(), user.getEmail());
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/login")
    @PreAuthorize("hasAnyAuthority('admin:login')")
    public ResponseEntity<Admin> login(@RequestBody Admin adminUser) {
        authenticate(adminUser.getUsername(),adminUser.getPassword());
        Admin loginAdmin = adminRepository.findAdminByUsername(adminUser.getUsername()); //() part returns string // could do by Id since id is a primary key
        AdminPrinciple adminPrincipal = new AdminPrinciple(loginAdmin); // when we do this we auto set spring based pass, username

        HttpHeaders jwtHeaders = getJWTHeader(adminPrincipal);
        return new ResponseEntity<>(loginAdmin, jwtHeaders, OK);

    }
    private HttpHeaders getJWTHeader(AdminPrinciple admin) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstant.JWT_TOKEN_HEADER,jwtTokenProvider.generateAdminJwtToken(admin));

        return headers;
    }

    private void authenticate(String username, String password) {
        test.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

    @GetMapping("/find/{username}") // let admin search users by username
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = userRepository.findUserByUsername(username);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/list") // list all the users in db for admin to check
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }
    @DeleteMapping("/delete/{id}") // allows admin to delete user
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") Long id) throws IOException {
        userService.deleteUser(id);
        return response(OK, "USER_DELETED_SUCCESSFULLY");
    }



    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

}

