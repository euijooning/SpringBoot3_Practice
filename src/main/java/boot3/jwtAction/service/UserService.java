package boot3.jwtAction.service;

import boot3.jwtAction.domain.User;
import boot3.jwtAction.dto.request.AddUserRequest;
import boot3.jwtAction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) { // 사용자 정보 저장 메서드

        // UserRepository를 사용하여 새로운 사용자를 데이터베이스에 저장하고 그 사용자의 ID를 반환
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // 패스워드 암호화
                .build())
                .getId(); // 저장된 사용자의 ID 반환
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
