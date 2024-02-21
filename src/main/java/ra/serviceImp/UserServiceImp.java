package ra.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ra.dto.request.AddAddressRequest;
import ra.dto.request.ChangePasswordRequest;
import ra.dto.request.SignInRequest;
import ra.dto.request.SignUpRequest;
import ra.dto.response.AddAddressResponse;
import ra.dto.response.SignInResponse;
import ra.dto.response.SignUpResponse;
import ra.dto.response.UserDTOResponse;
import ra.model.Address;
import ra.model.ERoles;
import ra.model.Roles;
import ra.model.User;
import ra.repository.AddressRepository;
import ra.repository.RolesRepository;
import ra.repository.UserRepository;
import ra.security.jwt.JwtProvider;
import ra.security.principle.CustomUserDetail;
import ra.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) {
        Set<Roles> setRoles = new HashSet<>();
        signUpRequest.getListRoles().forEach(role -> {
            switch (role) {
                case "admin":
                    setRoles.add(rolesRepository.findByName(ERoles.ROLES_ADMIN).orElseThrow(() ->
                            new RuntimeException("No administrator rights exist")));
                    break;
                case "user":
                    setRoles.add(rolesRepository.findByName(ERoles.ROLES_USER).orElseThrow(()->
                            new RuntimeException("No administrator rights exist")));
            }
        });
        User user = modelMapper.map(signUpRequest, User.class);
        user.setListRoles(setRoles);
        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userCreated = userRepository.save(user);
        SignUpResponse signUpResponse = modelMapper.map(userCreated, SignUpResponse.class);

        List<String> listUserRoles = new ArrayList<>();
        userCreated.getListRoles().stream().forEach(roles -> {
            listUserRoles.add(roles.getName().name());
        });
        signUpResponse.setListRoles(listUserRoles);

        return signUpResponse;
    }

    @Override
    public SignInResponse login(SignInRequest signInRequest) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUserName(), signInRequest.getPassword()
            ));
        }catch (Exception ex){
            throw new RuntimeException("UserName or Password is not correct");
        }
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        if (!userDetail.isEnabled()) {
            throw new RuntimeException("Your account is disabled");
        }
        String accessToken = jwtProvider.generateAccessToken(userDetail);
        String refreshToken = jwtProvider.generateRefreshToken(userDetail);

        return new SignInResponse(userDetail.getUsername(), userDetail.getEmail(), userDetail.getFullName(),
                userDetail.getAuthorities(),accessToken,refreshToken);
    }

    @Override
    public List<UserDTOResponse> findAllByDirection(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> userList = userPage.getContent();
        List<UserDTOResponse> userDTOResponsesList = userList.stream().map(user ->
                modelMapper.map(user, UserDTOResponse.class)).collect(Collectors.toList());
        return userDTOResponsesList;
    }

    @Override
    public UserDTOResponse getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> modelMapper.map(user, UserDTOResponse.class)).orElse(null);
    }

    @Override
    public UserDTOResponse toggleUserLock(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setStatus(!user.isStatus());
            userRepository.save(user);
            return modelMapper.map(user,UserDTOResponse.class);
        }else {
            return null;
        }
    }

    @Override
    public List<UserDTOResponse> findByUserName(String userName) {
        List<User> userList = userRepository.searchByUserNameIsContainingIgnoreCase(userName);
        List<UserDTOResponse> userDTOResponseList = userList.stream().map(user ->
                modelMapper.map(user,UserDTOResponse.class)).collect(Collectors.toList());
        return userDTOResponseList;
    }

    @Override
    public UserDTOResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found!"));

        String encodedPassword = user.getPassword();
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        String confirmPass = changePasswordRequest.getConfirmPass();

        if (!newPassword.equals(confirmPass)) {
            throw new RuntimeException("Confirm Password is not correct");
        }

        if (!passwordEncoder.matches(oldPassword,encodedPassword)) {
            return null;
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        return modelMapper.map(user, UserDTOResponse.class);
    }

    @Override
    public AddAddressResponse addAddress(Long id, AddAddressRequest addAddressRequest) {
        Address address = Address.builder()
                .fullAddress(addAddressRequest.getFullAddress())
                .phone(addAddressRequest.getPhone())
                .receiveName(addAddressRequest.getReceiveName())
                .user(userRepository.getUserById(id))
                .build();
        Address newAddress = addressRepository.save(address);
        return modelMapper.map(newAddress,AddAddressResponse.class);
    }

    @Override
    public List<AddAddressResponse> getListAddress(Long id) {
        List<Address> addressList = addressRepository.findAllByUserId(id);
        return addressList.stream().map(address ->
                modelMapper.map(address,AddAddressResponse.class)).collect(Collectors.toList());
    }


}
