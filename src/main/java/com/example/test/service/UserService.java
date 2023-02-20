package com.example.test.service;

import com.example.test.dto.UserDto;
import com.example.test.entity.User;
import com.example.test.entity.content.Content;
import com.example.test.entity.content.UserContent;
import com.example.test.file.FileAllowType;
import com.example.test.file.FileUploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.test.repository.UserRepository;
import com.example.test.repository.UserRepositoryQuery;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final UserRepositoryQuery userRepositoryQuery;

    private final FileUploader fileUploader;

    /**
     *  admin 으로 접근했을 때 검색도 하고 전체를 볼 수 있게 리팩토링
     *  userRepositoryQuery 동적 쿼리 사용하기
     */
    public Page<UserDto.Info> findUser(Pageable pageable){
        return userRepository.findAll(pageable).map(UserDto.Info::new);
    }

    /**
     * admin 권한 일 때 user 관리 창으로
     *
     * user 권한 일 때 자신 정보 창으로
     */
    public UserDto.Info findOneUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return new UserDto.Info(user);
    }

    //아이디 중복, 비밀번호 검사
    public UserDto.Info saveUser(UserDto.Save param) {
        User user = User.create(param.getName(),
                                param.getLoginId(),
                                param.getLoginPw(),
                                param.getNickname(),
                                fileUploader.fileUpload(param.getUserContent(), "user", FileAllowType.ONLY_IMAGE));
        User save = userRepository.save(user);
        return new UserDto.Info(save);
    }

    public UserDto.Info updateUser(UserDto.Update param) {
        User user = userRepository.findById(param.getId()).orElseThrow();
        user.update(param.getName(),
                param.getLoginId(),
                param.getLoginPw(),
                param.getNickname(),
                fileUploader.fileUpload(param.getUserContent(), "user", FileAllowType.ONLY_IMAGE));

        return new UserDto.Info(user);
    }

    public void deleteUser(UserDto.Delete param) {
        userRepository.deleteAllById(param.getIds());
    }

}
