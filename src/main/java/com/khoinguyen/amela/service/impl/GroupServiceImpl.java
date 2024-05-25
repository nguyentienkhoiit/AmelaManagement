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

        // Check for duplicate group name
        optionalValidator.findByGroupName(request.getName(), 0L)
                .ifPresent(group -> validationService.updateErrors("name", "Group name already exists", errors));

        // Check if any user IDs in the request do not exist in the database
        if (request.getUsersIds().stream().anyMatch(id -> userRepository.findByIdAndActive(id).isEmpty())) {
            validationService.updateErrors("usersIds", "Invalid user IDs", errors);
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
            User user = userRepository.findById(id).orElseThrow();
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

        Group groupExist = groupRepository.findById(request.getId()).orElseGet(() -> {
            validationService.updateErrors("id", "Group not found: " + request.getId(), errors);
            return null;
        });

        optionalValidator.findByGroupName(request.getName(), request.getId()).ifPresent(group ->
                validationService.updateErrors("name", "Group name already exists", errors)
        );

        //check validate email exist in database
        // Check if any user IDs in the request do not exist in the database
        String invalidUserIds = request.getUsersIds().stream()
                .filter(id -> userRepository.findByIdAndActive(id).isEmpty())
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        if (!invalidUserIds.isEmpty()) {
            validationService.updateErrors("usersIds", "Invalid user IDs: " + invalidUserIds, errors);
        }

        if (!errors.isEmpty()) return;

        // Update users in the group
        assert groupExist != null;
        List<Long> existingUserIds = groupExist.getUserGroups().stream()
                .map(UserGroup::getUser)
                .map(User::getId)
                .toList();

        List<Long> requestedUserIds = request.getUsersIds();

        // Identify users to delete
        List<UserGroup> userGroupsToDelete = existingUserIds.stream()
                .filter(userId -> !requestedUserIds.contains(userId))
                .map(userId -> userGroupRepository.findByUserIdAndGroupId(userId, groupExist.getId()).orElseThrow())
                .collect(Collectors.toList());

        if (!userGroupsToDelete.isEmpty()) {
            userGroupRepository.deleteAll(userGroupsToDelete);
        }

        // Identify users to add
        List<UserGroup> userGroupsToAdd = requestedUserIds.stream()
                .filter(userId -> !existingUserIds.contains(userId))
                .map(userId -> UserGroup.builder()
                        .group(groupExist)
                        .user(userRepository.findById(userId).orElseThrow())
                        .build())
                .toList();

        if (!userGroupsToAdd.isEmpty()) {
            userGroupRepository.saveAll(userGroupsToAdd);
        }

        // Update group details
        groupExist.setUpdateBy(userLoggedIn.getId());
        groupExist.setUpdateAt(LocalDateTime.now());
        groupExist.setName(request.getName());
        groupExist.setDescription(request.getDescription());
        groupRepository.save(groupExist);
    }

    @Override
    public GroupDtoResponse getGroupById(Long id) {
        return groupRepository.findById(id)
                .map(GroupMapper::toGroupDtoResponse)
                .orElse(null);
    }

    @Override
    public PagingDtoResponse<GroupDtoResponse> getAllGroups(PagingDtoRequest request) {
        return groupCriteria.getAllGroups(request);
    }

    @Override
    public void changeStatus(Long id) {
        User userLoggedIn = userHelper.getUserLogin();
        var groupOptional = groupRepository.findById(id);
        if (groupOptional.isPresent()) {
            var group = groupOptional.get();
            group.setUpdateAt(LocalDateTime.now());
            group.setUpdateBy(userLoggedIn.getId());
            group.setStatus(!group.isStatus());
            groupRepository.save(group);
        }
    }

    @Override
    public List<GroupDtoResponse> getAll() {
        return groupRepository.findAllByStatusTrue().stream()
                .map(GroupMapper::toGroupDtoResponse)
                .toList();
    }
}
