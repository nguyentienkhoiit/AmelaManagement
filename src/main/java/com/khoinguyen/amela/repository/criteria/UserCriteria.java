package com.khoinguyen.amela.repository.criteria;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingUserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.model.mapper.UserMapper;
import com.khoinguyen.amela.util.Constant;
import com.khoinguyen.amela.util.UserHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCriteria {
    EntityManager em;
    UserHelper userHelper;

//    @org.springframework.data.jpa.repository.Query("select u from User u join u.userGroups ug join ug.group g where g.id = 1")
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingUserDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("select u from User u ");

        if(request.getGroupId() != null) {
            sql.append(" join u.userGroups ug join ug.group g where g.id = :groupId ");
            params.put("groupId", request.getGroupId());
        }
        else sql.append(" where 1 = 1 ");

        if (request.getText() != null) {
            sql.append(" and  (u.email like :email or u.firstname like :firstName or u.lastname like :lastName " +
                    "or u.code like :code or u.department.name like :department or u.role.name like :role " +
                    "or u.jobPosition.name like :position) ");
            params.put("email", "%" + request.getText().trim() + "%");
            params.put("firstName", "%" + request.getText().trim() + "%");
            params.put("lastName", "%" + request.getText().trim() + "%");
            params.put("code", "%" + request.getText().trim() + "%");
            params.put("department", "%" + request.getText().trim() + "%");
            params.put("role", "%" + request.getText().trim() + "%");
            params.put("position", "%" + request.getText().trim() + "%");
        }

        if (userLoggedIn.getRole().getName().equals(Constant.USER_NAME)) {
            sql.append(" and u.enabled = true and u.activated = true and u.role.name = 'USER'");
        }

        if (request.getDepartmentId() != null) {
            sql.append(" and u.department.id = :departmentId ");
            params.put("departmentId", request.getDepartmentId());
        }

        if (request.getPositionId() != null) {
            sql.append(" and u.jobPosition.id = :jobPosition ");
            params.put("jobPosition", request.getPositionId());
        }

        //filter search
        Query countQuery = em.createQuery(sql.toString()
                .replace("select u", "select count(u.id)"));

        sql.append(" order by u.code asc");

        long pageIndex = request.getPageIndex();
        long pageSize = request.getPageSize();

        TypedQuery<User> userTypedQuery = em.createQuery(sql.toString(), User.class);
        params.forEach((k, v) -> {
            userTypedQuery.setParameter(k, v);
            countQuery.setParameter(k, v);
        });

        //paging
        userTypedQuery.setFirstResult((int) ((pageIndex - 1) * pageSize));
        userTypedQuery.setMaxResults(Math.toIntExact(pageSize));
        List<User> userList = userTypedQuery.getResultList();

        long totalUser = (long) countQuery.getSingleResult();
        long totalPage = (totalUser / pageSize);
        if (totalUser % pageSize != 0) {
            totalPage++;
        }

        List<UserDtoResponse> userDtoResponses = userList.stream()
                .map(UserMapper::toUserDtoResponse)
                .toList();
        return new PagingDtoResponse<>(totalPage, totalUser, userDtoResponses);
    }
}
