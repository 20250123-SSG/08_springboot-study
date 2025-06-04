package com.ino.security.service;

import com.ino.security.Mapper.UserMapper;
import com.ino.security.auth.LoginUser;
import com.ino.security.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    /**
     * 로그인 처리시 자동 실행 메소드
     *
     * @param username - 폼 로그인 요청시 전달 사용자명(id)
     * @return - 조회된 사용자의 정보 중 인증에 필요한 최소한의 정보가 담긴 UserDetails 객체 반환
     * @throws UsernameNotFoundException - 존재하는 사용자가 없을 경우 발생시킬 예외
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto foundedUser = userMapper.selectUserById(username);

        if(foundedUser == null) {
            throw new UsernameNotFoundException("name not found");
        }

        List<GrantedAuthority> authorityList
                = Arrays.stream(foundedUser.getUserRole().split(","))
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        return new LoginUser(foundedUser.getUserId(), foundedUser.getUserPwd(), authorityList, foundedUser.getUserName());

        // 반환 UserDetails(LoginUser) 객체를 통한 인증 진행
        // 인증 성공시 -> Authentication(LoginUser : Principal, authorityList : GrantAuthority)로 등록
    }
}
