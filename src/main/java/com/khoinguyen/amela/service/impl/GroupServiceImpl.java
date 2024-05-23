package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.Group;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.entity.UserGroup;
import com.khoinguyen.amela.model.dto.group.GroupDtoRequest;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.mapper.GroupMapper;
import com.khoinguyen.amela.repository.GroupRepository;
import com.khoinguyen.amela.repository.UserGroupRepository;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.repository.criteria.GroupCriteria;
import com.khoinguyen.amela.service.GroupService;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.UserHelper;
import com.khoinguyen.amela.util.ValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        //check group name duplicate
        var groupOptional = optionalValidator.findByGroupName(request.getName(), 0L);
        if (groupOptional.isPresent()) {
            validationService.updateErrors("name", "Group name already exists", errors);
        }

        //check validate email exist in database
        String messages = request.getUsersIds().stream()
                .filter(id -> userRepository.findByIdAndActive(id)
                        .isEmpty())
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        // Mail is not exist
        if (!messages.isEmpty()) {
            validationService.updateErrors("usersIds", "Something went wrong", errors);
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
        for (var id : request.getUsersIds()) {
            User user = userRepository.findById(id).orElse(null);
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

        Group groupExist = groupRepository.findById(request.getId()).orElse(null);
        if (groupExist == null) {
            validationService.updateErrors("id", "Group not found: " + request.getId(), errors);
        }

        //check group name duplicate
        var groupOptional = optionalValidator.findByGroupName(request.getName(), request.getId());
        if (groupOptional.isPresent()) {
            validationService.updateErrors("name", "Group name already exists", errors);
        }

        //check validate email exist in database
        String messages = request.getUsersIds().stream()
                .filter(id -> userRepository.findByIdAndActive(id)
                        .isEmpty())
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        // Mail is not exist
        if (!messages.isEmpty()) {
            validationService.updateErrors("usersIds", "Something went wrong", errors);
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
        var userRequestList = request.getUsersIds().stream()
                .map(id -> userRepository.findById(id).orElseThrow())
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
