package com.lionheart15.ideamarket.auth;

import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // Spring이 로그인 요청을 가로챌 때 username, password 변수 2개를 가로채는데,
    // password는 알아서 처리, username은 DB에 있는지 확인해 줘야 함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(username)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
                });
        return new PrincipalDetail(user);  // Security의 Session에 유저 정보가 저장됨
    }
}
