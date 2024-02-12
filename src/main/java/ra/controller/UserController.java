package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.SignInRequest;
import ra.dto.request.SignUpRequest;
import ra.dto.response.Message;
import ra.dto.response.SignInResponse;
import ra.dto.response.SignUpResponse;
import ra.dto.response.UserDTOResponse;
import ra.model.Roles;
import ra.service.RoleService;
import ra.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api.myservice.com/v1/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
//Đăng kí tài khoản người dùng
    @PostMapping("auth/sign-up")
    public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest signUpRequest){
        SignUpResponse signUpResponse = userService.register(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }
//Đăng nhập tài khoản bằng username và password
    @PostMapping("auth/sign-in")
    public ResponseEntity<?> login(@RequestBody SignInRequest signInRequest){
//        SignInResponse signInResponse = userService.login(signInRequest);
//        return new ResponseEntity<>(signInResponse,HttpStatus.OK);
        try {
            SignInResponse signInResponse = userService.login(signInRequest);

            return new ResponseEntity<>(signInResponse, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(new Message("ERROR"), HttpStatus.UNAUTHORIZED);
        }
    }

//    Thông tin tài khoản người dùng theo id
    @GetMapping("/account/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        UserDTOResponse userDTOResponse = userService.getUserById(userId);
        if (userDTOResponse == null){
            return new ResponseEntity<>(new Message("There is no user with this id"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(userDTOResponse,HttpStatus.OK);
        }
    }

//Lấy ra danh sách người dùng  (phân trang và sắp xếp)


    @GetMapping("admin/users")
    public ResponseEntity<?> findAll(@PageableDefault(size = 3,page = 0,sort = "userName"
            ,direction = Sort.Direction.ASC) Pageable pageable){
        List<UserDTOResponse> userDTOResponseList = userService.findAllByDirection(pageable);
        return new ResponseEntity<>(userDTOResponseList,HttpStatus.OK);
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<?> toggleUserLock(@PathVariable Long userId){
        UserDTOResponse userDTOResponse = userService.toggleUserLock(userId);
        if (userDTOResponse == null){
            return new ResponseEntity<>(new Message("User not found"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(userDTOResponse,HttpStatus.OK);
        }
    }

    //    Lấy về danh sách quyền
    @GetMapping("/admin/roles")
    public ResponseEntity<?> getRolesList(){
        List<Roles> rolesList = roleService.findAll();
        return new ResponseEntity<>(rolesList,HttpStatus.OK);
    }

//    Tìm kiếm người dùng theo tên
    @GetMapping("/admin/users/search")
    public ResponseEntity<?> searchByUserName(@RequestParam String userName){
        List<UserDTOResponse> userDTOResponseList = userService.findByUserName(userName);

        if (userDTOResponseList.isEmpty()){
            return new ResponseEntity<>(new Message("UserName not found!"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(userDTOResponseList,HttpStatus.OK);
        }
    }

}
