package com.example.beliemeserver.service;

import com.example.beliemeserver.data.entity.UserEntity;
import com.example.beliemeserver.data.repository.UserRepository;
import com.example.beliemeserver.exception.FormatDoesNotMatchException;
import com.example.beliemeserver.exception.NotFoundOnDBException;
import com.example.beliemeserver.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDto> getAllUser() throws FormatDoesNotMatchException {
        Iterator<UserEntity> iterator = userRepository.findAll().iterator();

        ArrayList<UserDto> userDtoList = new ArrayList<>();
        while(iterator.hasNext()) {
            userDtoList.add(iterator.next().toUserDto());
        }
        return userDtoList;
    }

    @Override
    public UserDto getUserByToken(String token) throws NotFoundOnDBException, FormatDoesNotMatchException {
        UserEntity userEntity = userRepository.findWithAuthoritiesByToken(token).orElse(null);
        if(userEntity == null) {
            throw new NotFoundOnDBException();
        }
        return userEntity.toUserDto();
    }

    @Override
    public UserDto getUserByStudentId(String studentId) throws NotFoundOnDBException, FormatDoesNotMatchException {
        UserEntity userEntity = userRepository.findWithAuthoritiesByStudentId(studentId).orElse(null);
        if(userEntity == null) {
            throw new NotFoundOnDBException();
        }
        return userEntity.toUserDto();
    }

    @Override
    public UserDto addUser(UserDto user) throws FormatDoesNotMatchException {
        System.out.println("new ID : " + user.getId());
        return userRepository.save(UserEntity.from(user)).toUserDto();
    }

    @Override
    public UserDto updateUser(String studentId, UserDto user) throws NotFoundOnDBException, FormatDoesNotMatchException {
        UserEntity target = userRepository.findWithAuthoritiesByStudentId(studentId).orElse(null);
        UserEntity newUserEntity = UserEntity.from(user);
        if(target == null) {
            throw new NotFoundOnDBException();
        }
        target.setStudentId(newUserEntity.getStudentId());
        target.setName(newUserEntity.getName());
        target.setToken(newUserEntity.getToken());
        target.setCreateTimeStamp(newUserEntity.getCreateTimeStamp());
        target.setApprovalTimeStamp(newUserEntity.getApprovalTimeStamp());
        return userRepository.save(target).toUserDto();
    }
}
