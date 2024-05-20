package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.Group;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.entity.UserGroup;
import com.khoinguyen.amela.model.dto.group.GroupDtoRequest;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.mapper.GroupMapper;
import com.khoinguyen.amela.repository.GroupRepository;
import com.khoinguyen.amela.repository.UserGroupRepository;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.repository.criteria.GroupCriteria;
import com.khoinguyen.amela.service.GroupService;
import com.khoinguyen.amela.util.Constant;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.UserHelper;
import com.khoinguyen.amela.util.ValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupServiceImpl implements GroupService {
    GroupRepository groupRepository;
    UserGroupRepository userGroupRepository;
    UserRepository userRepository;
    UserHelper userHelper;
    GroupCriteria groupCriteria;
    OptionalValidator optionalValidator;
    ValidationService validationService;

    @Transactional
    @Override
    public void createGroups(GroupDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        //cut email to list
        Set<String> listEmail = Arrays.stream(request.getListMail()
                        .split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isEmpty())
                .filter(s -> s.contains("@"))
                .collect(Collectors.toSet());

        //list of email empty
        if (listEmail.isEmpty()) {
            validationService.updateErrors("listMail", "List of email must be at least one", errors);
        }

        //check validate email exist in database
        StringBuilder messages = new StringBuilder();
        for (var email : listEmail) {
            var userOptional = userRepository
                    .findByEmail(email)
                    .filter(u -> !u.getRole().getName().equals(Constant.ADMIN_NAME));
            if (userOptional.isEmpty()) {
                messages.append(email).append(", ");
            }
        }

        //check group name duplicate
        var groupOptional = optionalValidator.findByGroupName(request.getName(), 0L);
        if (groupOptional.isPresent()) {
            validationService.updateErrors("name", "Group name already exists", errors);
        }

        //mail is not exist
        if (!messages.isEmpty()) {
            String msg = messages.toString().trim().substring(0, messages.length() - 2).concat(" is not existed");
            validationService.updateErrors("listMail", msg, errors);
        }

        if (!errors.isEmpty()) return;

        //create group
        Group group = Group.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(true)
                .createdAt(LocalDateTime.now())
                .createdBy(userLoggedIn.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(userLoggedIn.getId())
                .build();
        group = groupRepository.save(group);

        //create user group
        List<UserGroup> userGroups = new ArrayList<>();
        for (var email : listEmail) {
            User user = userRepository.findByEmail(email).orElse(null);
            UserGroup userGroup = UserGroup.builder()
                    .group(group)
                    .user(user)
                    .build();
            userGroups.add(userGroup);
        }
        userGroupRepository.saveAll(userGroups);
    }

    @Transactional
    @Override
    public void updateGroups(GroupDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        Group groupExist = groupRepository.findById(request.getId()).orElse(null);
        if (groupExist == null) {
            validationService.updateErrors("id", "Group not found: " + request.getId(), errors);
        }

        //cut email to list
        Set<String> listEmail = Arrays.stream(request.getListMail()
                        .split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isEmpty())
                .filter(s -> s.contains("@"))
                .collect(Collectors.toSet());

        //check group name duplicate
        var groupOptional = optionalValidator.findByGroupName(request.getName(), request.getId());
        if (groupOptional.isPresent()) {
            validationService.updateErrors("name", "Group name already exists", errors);
        }

        //list of email empty
        if (listEmail.isEmpty()) {
            validationService.updateErrors("listMail", "List of email must be at least one", errors);
        }

        //check validate email exist in database
        StringBuilder messages = new StringBuilder();
        for (var email : listEmail) {
            var userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                messages.append(email).append(", ");
            }
        }

        //list of mail is not exist
        if (!messages.isEmpty()) {
            String msg = messages.toString().trim().substring(0, messages.length() - 2).concat(" is not existed");
            validationService.updateErrors("listMail", msg, errors);
        }

        if (!errors.isEmpty()) return;

        //update list email
        //user was existed in group
        assert groupExist != null;
        var userExist = groupExist.getUserGroups().stream()
                .map(UserGroup::getUser)
                .map(User::getId)
                .toList();

        //user request
        var userRequestList = listEmail.stream()
                .map(u -> userRepository.findByEmail(u).orElseThrow())
                .map(User::getId)
                .toList();

        var userDeletes = userExist.stream()
                .filter(userId -> !userRequestList.contains(userId))
                .map(u -> userGroupRepository.findByUserIdAndGroupId(u, groupExist.getId()).orElseThrow())
                .toList();

        if (!userDeletes.isEmpty()) {
            userGroupRepository.deleteAll(userDeletes);
        }

        //user adding: có trong request mà no có trong database
        var userAdditions = userRequestList.stream()
                .filter(userId -> !userExist.contains(userId))
                .map(u -> UserGroup.builder()
                        .group(groupExist)
                        .user(userRepository.findById(u).orElseThrow())
                        .build()
                )
                .toList();

        if (!userAdditions.isEmpty()) {
            userGroupRepository.saveAll(userAdditions);
        }
        groupExist.setUpdateBy(userLoggedIn.getId());
        groupExist.setUpdateAt(LocalDateTime.now());
        groupExist.setName(request.getName());
        groupExist.setDescription(request.getDescription());
        groupRepository.save(groupExist);
    }

    @Override
    public GroupDtoResponse getGroupById(Long id) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) return null;
        return GroupMapper.toGroupDtoResponse(group);
    }

    @Override
    public PagingDtoResponse<GroupDtoResponse> getAllGroups(PagingDtoRequest request) {
        return groupCriteria.getAllGroups(request);
    }

    @Override
    public boolean changeStatus(Long id) {
        User userLoggedIn = userHelper.getUserLogin();
        var groupOptional = groupRepository.findById(id);
        if (groupOptional.isPresent()) {
            var group = groupOptional.get();
            group.setUpdateAt(LocalDateTime.now());
            group.setUpdateBy(userLoggedIn.getId());
            group.setStatus(!group.isStatus());
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    @Override
    public List<GroupDtoResponse> getAll() {
        return groupRepository.findAllByStatusTrue().stream()
                .map(GroupMapper::toGroupDtoResponse)
                .toList();
    }
}
