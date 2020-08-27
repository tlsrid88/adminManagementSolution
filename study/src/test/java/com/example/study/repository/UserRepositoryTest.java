package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        String account = "Test03";
        String password = "Test03";
        String status = "REGITERED";
        String email = "Test03@naver.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        /*LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";*/

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        //user.setStatus(User);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
        /*user.setCreatedAt(createdAt);
        user.setCreatedBy(createdBy);*/

        User newUser = userRepository.save(user);

        Assert.assertNotNull(newUser);

        User u = User
                .builder()
                .account("")
                .password("")
                .email("")
                .build(); // 결함연산자를 끊을 때는 뒤를 끊었는데 도트연산자의 경우는 앞에서 끊어준다.
    }

    @Test
    @Transactional
    public void read(){
        Optional<User> user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");

        user.ifPresent(c-> {
            c.getOrderGroupList().stream().forEach(orderGroup -> {

                    System.out.println("----------주문묶음----------");
                    System.out.println(orderGroup.getRevAddress());
                    System.out.println(orderGroup.getTotalPrice());
                    System.out.println(orderGroup.getTotalQuantity());

                System.out.println("----------주문상세----------");
                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("파트너사 이름 : " + orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 이름 : " + orderDetail.getItem().getPartner().
                            getCategory().getTitle());
                    System.out.println("주문 상품 : " + orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 : " + orderDetail.getItem().
                            getPartner().getCallCenter());
                    System.out.println("상품도착 날짜 : " + orderDetail.getArrivalDate());


                });
            });
        });

        Assert.assertNotNull(user);

    }

    @Test
    @Transactional
    public void update(){
        Optional<User> user = userRepository.findById(3L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("ppppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setCreatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    public void delete(){
        Optional<User> user = userRepository.findById(4L);

        Assert.assertTrue(user.isPresent());

        user.ifPresent( selectUser -> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(4L);

        Assert.assertFalse(deleteUser.isPresent());
    }
}
