package com.ino.app.service;

import com.ino.app.dto.UserDto;
import com.ino.app.mapper.UserMapper;
import com.ino.app.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final FileUtil fileUtil;

    @Override
    public int getUserCount(String checkId) {
        return userMapper.selectUserCountById(checkId);
    }

    @Override
    public Map<String, Object> registUser(UserDto user) {
        try {
            int result = userMapper.insertUser(user);
            // 백엔드만 구현시 프론트는 jsp 등 아무렇게나 -> 입력값 검증 x 이므로 예외 처리 필요
        } catch (Exception e) { // insert 쿼리 실행시 예외 발생의 경우
            e.printStackTrace();
            return Map.of("message", "internal Error : regist failed");
        }
        return Map.of("message", "Success : regist successed");
    }

    @Override
    public Map<String, Object> loginUser(UserDto user) {

        UserDto selectedUser = userMapper.selectUserById(user.getUserId());

        if (selectedUser == null) {
            return Map.of("message", "Error : not matched ID");
        } else if (!selectedUser.getUserPwd().equals(user.getUserPwd())) {
           return Map.of("message", "Error : wrong password");
        }
        return Map.of("message", selectedUser.getUserName() + "님 환영합니다!", "user", selectedUser);
    }

    @Override
    public Map<String, Object> modifyUserProfile(String userId, MultipartFile file) {

        // 1. 아이디를 통해 기존 회원 정보 조회
        UserDto selectedUser = userMapper.selectUserById(userId);
        if (selectedUser == null){ // 예외 1. 조회 회원 x
            Map.of("message", "Error : user not found");
        }
        // 2. 첨부파일 업로드
        Map<String, String> map = null;
        try {
            map = fileUtil.fileupload("profile", file);
            // IOExceptio 발생 가능.
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("message", "Error: profile image upload failed");
        }
        // 3. edit user info db
        String newProfileURL = map.get("filePath") + "/" + map.get("filesystemName");
        selectedUser.setProfileURL(newProfileURL);
        int result = userMapper.updateProfileImg(selectedUser);
        if(result != 1){ // 쿼리 실패시
            new File(newProfileURL).delete(); // db 기록 실패시 저장 파일 삭제
            return Map.of("message", "Error: UserDto data upload failed");
        }

        return Map.of("message", "Success: UserDto successfully added", "profileURL" , newProfileURL);
    }
}
