package com.training.service.user;

import com.training.common.constant.Errors;
import com.training.entity.User;
import com.training.exception.BadRequestException;
import com.training.exception.ErrorParam;
import com.training.exception.SysError;
import com.training.repository.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUserName(String username) {
        User user = userRepository.findUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new BadRequestException(new SysError(Errors.ERROR_USER_NOT_FOUND, new ErrorParam(Errors.USER)));
        }

        return user;
    }

}
