package com.techeer.svproject.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.svproject.domain.order.dto.OrderCreateDto;
import com.techeer.svproject.domain.order.dto.OrderMapper;
import com.techeer.svproject.domain.order.dto.OrderResponseDto;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.order.service.OrderService;
import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.techeer.svproject.domain.order.controller.OrderDocument.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = OrderControllerTest.class)
@AutoConfigureRestDocs
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private static final UUID givenId = new UUID(10L, 10L);

    private static final LocalDateTime givenLocalDateTime = LocalDateTime.now();

    private Order expectOrder;

    private OrderResponseDto expectOrderResponseDto;

    @BeforeEach
    void setup(WebApplicationContext context, RestDocumentationContextProvider provider) {

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .build();

        User givenUser = new User(givenId, "김", "영준", "Over@naver.com", "qwer12345!", 1023132312, null, null);
        expectOrder = new Order(givenId, givenUser, Set.of(Product.builder().build()), givenLocalDateTime);

        expectOrderResponseDto = OrderResponseDto.builder()
                .id(givenId)
                .userId(givenId)
                .orderDate(givenLocalDateTime)
                .build();

        when(orderService.save(any(), any())).thenReturn(expectOrder);
        when(orderService.findById(any())).thenReturn(expectOrder);

        when(orderMapper.toEntity(any())).thenReturn(expectOrder);
        when(orderMapper.toResponseDto(any())).thenReturn(expectOrderResponseDto);

        when(userService.findById(any())).thenReturn(givenUser);

    }

    @Test
    @DisplayName("컨트롤러 save")
    void save() throws Exception {
        //given
        OrderCreateDto givenOrderCreateDto = OrderCreateDto.builder()
                .id(givenId)
                .userId(givenId)
                .orderDate(givenLocalDateTime)
                .build();

//        ResponseEntity expectResponseEntity = new ResponseEntity<>(expectOrderResponseDto, HttpStatus.CREATED);
        //when
//        ResponseEntity actualResponseEntity = orderController.save(givenOrderCreateDto);
        //then
//        assertEquals(expectResponseEntity, actualResponseEntity);
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenOrderCreateDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(postOrder());

    }

    @Test
    @DisplayName("컨트롤러 ID로 조회")
    void getDetail() throws Exception {
        // given
//        ResponseEntity actualResponseEntity = ResponseEntity.ok().body(expectOrderResponseDto);
        // when
//        ResponseEntity expectResponseEntity = orderController.getDetail(givenId);
        // then
//        assertEquals(expectResponseEntity, actualResponseEntity);
        mockMvc.perform(get("/api/v1/orders/{id}", givenId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("id", String.valueOf(givenId))

                )
                .andExpect(status().isNotFound())
                .andDo(print())
                .andDo(getOneOrder());
    }

    @Test
    void getByUserId() {
        //given
        List<Order> actualOrderList = new ArrayList<>(1);
        actualOrderList.add(expectOrder);
        when(orderService.findAllByEmail(any())).thenReturn(actualOrderList);

        List<OrderResponseDto> actualOrderResponseList = new ArrayList<>(1);
        actualOrderResponseList.add(OrderResponseDto.builder()
                .id(givenId)
                .userId(givenId)
                .orderDate(givenLocalDateTime)
                .build());

        String givenEmail = "user@email.com";

        ResponseEntity actualResponseEntity = new ResponseEntity<>(actualOrderResponseList, HttpStatus.ACCEPTED);
        //when
        ResponseEntity expectResponseEntity = orderController.getByUserId(givenEmail);
        //then
        assertAll(
                () -> assertEquals(expectResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode()),
                () -> assertEquals(expectResponseEntity.getBody().getClass(), actualResponseEntity.getBody().getClass())
                // 객체 비교 수준의 테스트 코드, 좋지 않음
        );
    }

    @Test
    @DisplayName("삭제")
    void deleteOrder() {
        //given
        doNothing().when(orderService).delete(any());
        ResponseEntity actualResponseEntity = ResponseEntity.ok().build();
        //when
        ResponseEntity<OrderResponseDto> expectResponseEntity = orderController.deleteOrder(givenId);
        //then
        assertEquals(expectResponseEntity, actualResponseEntity);
    }
}