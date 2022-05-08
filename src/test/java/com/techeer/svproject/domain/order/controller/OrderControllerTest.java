package com.techeer.svproject.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import java.util.*;

import static com.techeer.svproject.domain.order.controller.OrderDocument.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = OrderController.class)
@AutoConfigureRestDocs
class OrderControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private static final UUID givenId = new UUID(10L, 10L);

    private static LocalDateTime givenLocalDateTime;

    private Order expectOrder;

    private Map<String, String> expectOrderResponseJson;

    @BeforeEach
    void setup(WebApplicationContext context, RestDocumentationContextProvider provider) {

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .build();

        givenLocalDateTime = LocalDateTime.now();

        User givenUser = new User(givenId, "김", "영준", "Over@naver.com",
                "qwer12345!", 1023132312, null, null);

        expectOrder = new Order(givenId, givenUser, Set.of(Product.builder().build()), givenLocalDateTime);

        expectOrderResponseJson = new HashMap<>();
        expectOrderResponseJson.put("id", givenId.toString());
        expectOrderResponseJson.put("userId", givenId.toString());
        expectOrderResponseJson.put("orderDate", givenLocalDateTime.toString());

        OrderResponseDto givenRequest = OrderResponseDto.builder()
                .id(givenId)
                .userId(givenId)
                .orderDate(givenLocalDateTime)
                .build();

        when(orderService.save(any(), any())).thenReturn(expectOrder);
        when(orderService.findById(any())).thenReturn(expectOrder);

        when(orderMapper.toEntity(any())).thenReturn(expectOrder);
        when(orderMapper.toResponseDto(any())).thenReturn(givenRequest);

        when(userService.findById(any())).thenReturn(givenUser);

    }

    @Test
    @DisplayName("컨트롤러 save")
    void save() throws Exception {
        //given
        Map<String, String> givenJson = new HashMap<>();
        givenJson.put("id", givenId.toString());
        givenJson.put("userId", givenId.toString());
        givenJson.put("orderDate", givenLocalDateTime.toString());
        //when
        //then
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenJson)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectOrderResponseJson)))
                .andDo(print())
                .andDo(createOrder());
    }

    @Test
    @DisplayName("컨트롤러 ID로 조회")
    void getDetail() throws Exception {
        mockMvc.perform(get("/api/v1/orders/{id}", givenId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(getOneOrder());
    }

    @Test
    @DisplayName("컨트롤러 email로 조회")
    void getByUserId() throws Exception {
        //given
        String givenEmail = "user@email.com";

        List<Order> actualOrderList = new ArrayList<>(1);
        actualOrderList.add(expectOrder);
        when(orderService.findAllByEmail(any())).thenReturn(actualOrderList);

        Map<String, String> givenJson = new HashMap<>();
        givenJson.put("id", givenId.toString());
        givenJson.put("userId", givenId.toString());
        givenJson.put("orderDate", givenLocalDateTime.toString());

        List<Map<String, String>> actualOrderResponseJsonList = new ArrayList<>(1);
        actualOrderResponseJsonList.add(givenJson);

        //when
        //then
        mockMvc.perform(get("/api/v1/orders")
                        .param("userEmail", givenEmail))
                .andExpect(status().isAccepted())
                .andExpect(content().json(objectMapper.writeValueAsString(actualOrderResponseJsonList)))
                .andDo(print())
                .andDo(findAllOrderByEmil());
    }

    @Test
    @DisplayName("컨트롤러 ID로 삭제")
    void deleteOrder() throws Exception {
        //given
        doNothing().when(orderService).delete(any());
        //when
        //then
        mockMvc.perform(delete("/api/v1/orders/{id}", givenId.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(OrderDocument.deleteOrder());
    }
}