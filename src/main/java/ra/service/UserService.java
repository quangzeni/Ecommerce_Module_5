package ra.service;

import org.springframework.data.domain.Pageable;
import ra.dto.request.SignInRequest;
import ra.dto.request.SignUpRequest;
import ra.dto.response.SignInResponse;
import ra.dto.response.SignUpResponse;
import ra.dto.response.UserDTOResponse;

import java.util.List;

public interface UserService {
    //    Map<String, Objects> findByEmailorStatus(int page, int size, String userEmail,String direction, String orderBy);
    SignUpResponse register(SignUpRequest signUpRequest);
    SignInResponse login(SignInRequest signInRequest);
    List<UserDTOResponse> findAllByDirection(Pageable pageable);
    UserDTOResponse getUserById(Long id);
    UserDTOResponse toggleUserLock(Long id);
    List<UserDTOResponse> findByUserName(String userName);
}
