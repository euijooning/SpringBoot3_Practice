package boot3.jwtAction.service;

import boot3.jwtAction.domain.User;
import boot3.jwtAction.dto.request.AddUserRequest;
import boot3.jwtAction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // UserRepository를 통해 새 사용자를 저장.
        // - AddUserRequest에서 전달된 이메일과 비밀번호를 사용하여 새 사용자를 생성.
        // - 비밀번호는 BCryptPasswordEncoder를 사용하여 안전하게 해싱됨
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId(); // 저장된 사용자의 ID를 반환
    }
    /*
    이걸 리펙토링하면
    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userRepository.save(User.builder()
            .email(dto.getEmail())
            .password(encoder.encode(dto.getPassword()))  // 비밀번호 해싱
            .build());

        return user.getId();
    } 이렇게도 가능
     */

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
